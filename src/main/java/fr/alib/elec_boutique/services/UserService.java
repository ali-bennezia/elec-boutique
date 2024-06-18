package fr.alib.elec_boutique.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.elec_boutique.dtos.inbound.UserRegisterInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserSignInInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.AuthenticationSessionOutboundDTO;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.UserRepository;
import fr.alib.elec_boutique.utils.JWTUtils;
import fr.alib.elec_boutique.utils.TimeUtils;
import fr.alib.gotrips.model.auth.CustomUserDetails;
import fr.alib.gotrips.model.dto.inbound.UserRegisterDTO;
import fr.alib.gotrips.model.entity.company.ActivityCompany;
import fr.alib.gotrips.model.entity.company.FlightCompany;
import fr.alib.gotrips.model.entity.company.HotelCompany;
import io.jsonwebtoken.lang.Arrays;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> result = this.userRepository.findUserByUsernameOrEmail(username, null);
		if (result.isEmpty()) throw new UsernameNotFoundException("Couldn't find user with username '" + username + "'.");
		return new CustomUserDetails( result.get() );
	}
	
	/**
	 * Finds a user using specified id.
	 * @param id The id.
	 * @return The user. Throws IdNotFoundException if not found.
	 * @throws IdNotFoundException
	 */
	public UserDetails loadUserById(Long id) throws IdNotFoundException
	{
		Optional<User> result = this.userRepository.findById(id);
		if (result.isPresent()) {
			return new CustomUserDetails( result.get() );
		}else {
			throw new IdNotFoundException("Couldn't find user with id '" + id + "'.");
		}
	}
	
	/**
	 * Checks user credentials.
	 * @return An AuthenticationSessionDTO if successful, null otherwise.
	 */
	public AuthenticationSessionOutboundDTO login(UserSignInInboundDTO dto, PasswordEncoder pwdEncoder)
	{
		Optional<User> usr = this.userRepository.findUserByUsernameOrEmail(null, dto.getEmail());
		Long signInTime = TimeUtils.getNowUnixEpochMilis();
		Long expiresTime = TimeUtils.getNowUnixEpochMilis() + jwtUtils.getExpirationTime();

		return (
				usr.isPresent() 
				&& pwdEncoder.matches(dto.getPassword(), usr.get().getPassword()) 
				&& usr.get().getEnabled()
				) ? 
				new AuthenticationSessionOutboundDTO(
						this.jwtUtils.generateToken(usr.get().getUsername()),
						usr.get().getUsername(),
						usr.get().getId().toString(),
						usr.get().getEmail(),
						Arrays.asList(usr.get().getRoles().split(", ")),
						signInTime,
						expiresTime
				) : null;
	}

	/**
	 * Registers a new user.
	 * @return A UserDetails if successful, null otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserDetails register(UserRegisterInboundDTO dto, PasswordEncoder pwdEncoder, boolean isAdmin)
	{
		if (userRepository.findUserByUsernameOrEmail(dto.getUsername(), dto.getEmail()).isPresent()) {
			return null;
		}
		
		User user = null;
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_USER");
		if (isAdmin) roles.add("ROLE_ADMIN");

		user = new User(dto, pwdEncoder, String.join(", ", roles));
		user = userRepository.save(user);
		
		return new CustomUserDetails(user);
	}
	
	/**
	 * Disables a user.
	 * @return true if the operation was successful, false otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void setUserIsEnabledById(Long id, Boolean isEnabled) throws IdNotFoundException
	{
		Optional<User> userResult = this.userRepository.findById(id);
		if (userResult.isPresent()) {
			User user = userResult.get();
			user.setEnabled(isEnabled);
			userRepository.save(user);
		}else {
			throw new IdNotFoundException("Couldn't find user with id '" + id + "'.");
		}
	}
	
}
