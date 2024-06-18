package fr.alib.elec_boutique.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.alib.elec_boutique.services.UserService;
import fr.alib.elec_boutique.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtils jwt;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null && !token.isBlank()) {
			token = token.replace("Bearer ", "");
			String username = jwt.extractUsername(token);
			if (username != null) {
				try {
					UserDetails usr = this.userService.loadUserByUsername(username);
					if (usr.isEnabled() && usr.isCredentialsNonExpired() && usr.isAccountNonLocked() && usr.isAccountNonExpired()) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								usr, 
								null, 
								usr == null ? List.of() : usr.getAuthorities()
						);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				} catch (UsernameNotFoundException ex) {
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}