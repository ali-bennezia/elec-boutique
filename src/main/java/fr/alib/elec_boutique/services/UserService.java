package fr.alib.elec_boutique.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.alib.elec_boutique.dtos.inbound.UserProfileInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserRegisterInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserSignInInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.AuthenticationSessionOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.CardOutboundDTO;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.AddConflictException;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.repositories.UserRepository;
import fr.alib.elec_boutique.utils.EncryptionUtils;
import fr.alib.elec_boutique.utils.JWTUtils;
import fr.alib.elec_boutique.utils.TimeUtils;
import io.jsonwebtoken.lang.Arrays;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MediaService mediaService;
	
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
	public AuthenticationSessionOutboundDTO login(UserSignInInboundDTO dto, PasswordEncoder pwdEncoder) throws 
		BadCredentialsException,
		IllegalArgumentException
	{
		Optional<User> usr = this.userRepository.findUserByUsernameOrEmail(null, dto.getEmail());
		Long signInTime = TimeUtils.getNowUnixEpochMilis();
		Long expiresTime = TimeUtils.getNowUnixEpochMilis() + (dto.getRememberMe() ? jwtUtils.getLongExpirationTime() : jwtUtils.getExpirationTime());

		if (usr.isPresent() && pwdEncoder.matches(dto.getPassword(), usr.get().getPassword()) && usr.get().getEnabled())
		{
			return new AuthenticationSessionOutboundDTO(
					this.jwtUtils.generateToken(usr.get().getUsername(), dto.getRememberMe()),
					usr.get().getUsername(),
					usr.get().getProfilePhotoMedia(),
					usr.get().getId().toString(),
					usr.get().getEmail(),
					Arrays.asList(usr.get().getRoles().split(", ")),
					signInTime,
					expiresTime
			);
		}else {
			throw new BadCredentialsException("Couldn't authenticate user: wrong email or password.");
		}
	}

	/**
	 * Registers a new user.
	 * @return A UserDetails if successful, null otherwise.
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserDetails register(UserRegisterInboundDTO dto, PasswordEncoder pwdEncoder, boolean isAdmin) throws AddConflictException
	{
		if (userRepository.findUserByUsernameOrEmail(dto.getUsername(), dto.getEmail()).isPresent()) {
			throw new AddConflictException("Couldn't register user. Username or email already known.");
		}
		
		User user = null;
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_USER");
		if (isAdmin) roles.add("ROLE_ADMIN");
		if (dto.isProvider()) roles.add("ROLE_PROVIDER");
		
		user = new User(dto, pwdEncoder, String.join(", ", roles), null, true);
		user = userRepository.save(user);
		
		return new CustomUserDetails(user);
	}
	
	/**
	 * Deletes a user.
	 * @param id The user id.
	 * @throws IdNotFoundException If user with specified id couldn't be found.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteUserById(Long id) throws IdNotFoundException
	{
		Optional<User> result = this.userRepository.findById(id);
		if (result.isPresent()) {
			User user = result.get();
			String profilePhotoMedia = user.getProfilePhotoMedia();
			if (profilePhotoMedia != null) {
				this.mediaService.deleteMedia(profilePhotoMedia);
			}
			this.userRepository.deleteById(id);
		}else throw new IdNotFoundException("Failed to delete: couldn't find user with id '" + id + "'.");
	}
		
	/**
	 * Enables or disable a user.
	 * @param id The user's id.
	 * @param isEnabled The enabled value to set.
	 * @throws IdNotFoundException If user with specified id couldn't be found.
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
	
	/**
	 * Edits a user using a profile DTO.
	 * @param id The user's id.
	 * @param dto The profile DTO.
	 * @param pwdEncoder A password encoder.
	 * @return The modifier user entity.
	 * @throws IdNotFoundException If user with given id couldn't be found.
	 * @throws IllegalArgumentException If given arguments are invalid.
	 * @throws OptimisticLockingFailureException
	 */
	public User editUserProfileById(Long id, UserProfileInboundDTO dto, PasswordEncoder pwdEncoder) throws 
		IdNotFoundException,
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		Optional<User> result = this.userRepository.findById(id);
		if (result.isEmpty()) throw new IdNotFoundException("Couldn't find user with id '" + id + "'.");
		User user = result.get();
		user.applyProfilePatchDTO(dto, pwdEncoder);
		user = this.userRepository.save(user);
		return user;
	}
	

	/**
	 * Saves a given user.
	 * @param user The user.
	 * @return The saved user entity.
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	public User saveUser(User user) throws
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		return this.userRepository.save(user);
	}
	
	/**
	 * Edits a user's profile photo media.
	 * @param user The user entity.
	 * @param media The media file name.
	 * @return The modified user entity.
	 * @throws IllegalArgumentException
	 * @throws OptimisticLockingFailureException
	 */
	public User editUserProfilePhotoMedia(User user, String media) throws
		IllegalArgumentException,
		OptimisticLockingFailureException
	{
		if (user.getProfilePhotoMedia() != null) {
			this.mediaService.deleteMedia(user.getProfilePhotoMedia());
		}
		user.setProfilePhotoMedia( media );
		return this.userRepository.save(user);
	}


}
