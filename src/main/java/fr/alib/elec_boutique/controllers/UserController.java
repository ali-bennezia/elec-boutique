package fr.alib.elec_boutique.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.elec_boutique.dtos.inbound.UserProfileInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserRegisterInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserSignInInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.AuthenticationSessionOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.UserOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.UserProfileOutboundDTO;
import fr.alib.elec_boutique.exceptions.AddConflictException;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.UserService;
import fr.alib.elec_boutique.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate( HttpServletRequest request ) throws UsernameNotFoundException, BadCredentialsException
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String username = jwtUtils.extractUsername(token);
			if (username != null) {
				UserDetails usr = this.userService.loadUserByUsername(username);
				if (usr.isEnabled() && usr.isCredentialsNonExpired() && usr.isAccountNonLocked() && usr.isAccountNonExpired()) {
					return ResponseEntity.ok().build();
				}
			} throw new BadCredentialsException("Invalid token.");
		}	
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signIn( @Valid @RequestBody UserSignInInboundDTO dto ) throws BadCredentialsException
	{
		AuthenticationSessionOutboundDTO sess = this.userService.login(dto, pwdEncoder);
		return ResponseEntity.ok(sess);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register( @Valid @RequestBody UserRegisterInboundDTO dto ) throws AddConflictException
	{
		this.userService.register(dto, pwdEncoder, false);
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById( @PathVariable("id") Long id ) throws IdNotFoundException
	{
		CustomUserDetails user = (CustomUserDetails) this.userService.loadUserById(id);
		UserOutboundDTO dto =  new UserOutboundDTO(user.getUser());
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile( HttpServletRequest request ) throws IdNotFoundException, UsernameNotFoundException
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			CustomUserDetails user = (CustomUserDetails) this.userService.loadUserByUsername(currentUsername);
			return ResponseEntity.ok(new UserProfileOutboundDTO(user.getUser()));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/profile")
	public ResponseEntity<?> setProfile( @Valid @RequestBody UserProfileInboundDTO dto, HttpServletRequest request )
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			CustomUserDetails user = (CustomUserDetails) this.userService.loadUserByUsername(currentUsername);
			if (
					dto.getAuthPassword().isBlank() || 
					!dto.getAuthPassword().equals(dto.getAuthPasswordConfirmation())
				) 
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			return ResponseEntity.ok(new UserProfileOutboundDTO(user.getUser()));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@ExceptionHandler({UsernameNotFoundException.class, IdNotFoundException.class})
	public ResponseEntity<?> notFoundHandler()
	{
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> badCredentialsHandler()
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@ExceptionHandler(AddConflictException.class)
	public ResponseEntity<?> conflictHandler()
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
}
