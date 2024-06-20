package fr.alib.elec_boutique.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.alib.elec_boutique.dtos.inbound.UserProfileInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserRegisterInboundDTO;
import fr.alib.elec_boutique.dtos.inbound.UserSignInInboundDTO;
import fr.alib.elec_boutique.dtos.outbound.AuthenticationSessionOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.UserOutboundDTO;
import fr.alib.elec_boutique.dtos.outbound.UserProfileOutboundDTO;
import fr.alib.elec_boutique.entities.User;
import fr.alib.elec_boutique.exceptions.AddConflictException;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.services.CustomUserDetails;
import fr.alib.elec_boutique.services.MediaService;
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
	private MediaService mediaService;
	
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
	
	@PutMapping("/profile")
	@PatchMapping("/profile")
	public ResponseEntity<?> setProfile( 
			@Valid @RequestBody UserProfileInboundDTO dto, 
			@RequestParam(name = "profilePhoto", required = false) MultipartFile photoFile,
			HttpServletRequest request )
	{
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String currentUsername = this.jwtUtils.extractUsername(token);
			CustomUserDetails userDetails = (CustomUserDetails) this.userService.loadUserByUsername(currentUsername);
			User user = userDetails.getUser();
			if (
					dto.getAuthPassword().isBlank() || 
					!dto.getAuthPassword().equals(dto.getAuthPasswordConfirmation()) ||
					!pwdEncoder.matches(dto.getAuthPassword(), user.getPassword())
				) 
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			if (photoFile != null) {
				String profilePhotoMedia = this.mediaService.storeFile(photoFile);
				user = this.userService.editUserProfilePhotoMedia(user, profilePhotoMedia);
			}
			return ResponseEntity.ok(new UserProfileOutboundDTO(user));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/{id}/{value}")
	public ResponseEntity<?> toggleUser( @PathVariable("id") Long id, @PathVariable("value") Boolean value )
	{
		User user = ( (CustomUserDetails) this.userService.loadUserById(id) ).getUser();
		user.setEnabled(value);
		user = this.userService.saveUser(user);
		return ResponseEntity.ok(new UserOutboundDTO(user));
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
