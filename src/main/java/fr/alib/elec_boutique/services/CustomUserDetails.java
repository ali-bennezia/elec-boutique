package fr.alib.elec_boutique.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import fr.alib.elec_boutique.entities.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private User user;
	public CustomUserDetails(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(this.getUser().getRoles());
	}

	@Override
	public String getPassword() {
		return this.getUser().getPassword();
	}

	@Override
	public String getUsername() {
		return this.getUser().getUsername();
	}

	@Override
	public boolean isEnabled() {
		return this.getUser().getEnabled();
	}
	
}
