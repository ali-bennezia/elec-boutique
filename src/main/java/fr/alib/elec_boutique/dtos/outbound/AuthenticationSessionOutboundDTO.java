package fr.alib.elec_boutique.dtos.outbound;

import java.util.List;


public class AuthenticationSessionOutboundDTO {
	private String token;
	private String username;
	private String id;
	private String email;
	private List<String> roles;
	private Long signedInAtTime;
	private Long expiresAtTime;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public Long getSignedInAtTime() {
		return signedInAtTime;
	}
	public void setSignedInAtTime(Long signedInAtTime) {
		this.signedInAtTime = signedInAtTime;
	}
	public Long getExpiresAtTime() {
		return expiresAtTime;
	}
	public void setExpiresAtTime(Long expiresAtTime) {
		this.expiresAtTime = expiresAtTime;
	}
	public AuthenticationSessionOutboundDTO(String token, String username, String id, String email, List<String> roles,
			Long signedInAtTime, Long expiresAtTime) {
		super();
		this.token = token;
		this.username = username;
		this.id = id;
		this.email = email;
		this.roles = roles;
		this.signedInAtTime = signedInAtTime;
		this.expiresAtTime = expiresAtTime;
	}
	public AuthenticationSessionOutboundDTO() {
		super();
	}
}
