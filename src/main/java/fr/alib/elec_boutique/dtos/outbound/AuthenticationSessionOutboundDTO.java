package fr.alib.elec_boutique.dtos.outbound;

import java.util.List;
import java.util.Objects;


public class AuthenticationSessionOutboundDTO {
	private String token;
	private String username;
	private String profilePhotoMedia;
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
	public String getProfilePhotoMedia() {
		return profilePhotoMedia;
	}
	public void setProfilePhotoMedia(String profilePhotoMedia) {
		this.profilePhotoMedia = profilePhotoMedia;
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
	@Override
	public int hashCode() {
		return Objects.hash(email, expiresAtTime, id, profilePhotoMedia, roles, signedInAtTime, token, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationSessionOutboundDTO other = (AuthenticationSessionOutboundDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(expiresAtTime, other.expiresAtTime)
				&& Objects.equals(id, other.id) && Objects.equals(profilePhotoMedia, other.profilePhotoMedia)
				&& Objects.equals(roles, other.roles) && Objects.equals(signedInAtTime, other.signedInAtTime)
				&& Objects.equals(token, other.token) && Objects.equals(username, other.username);
	}
	public AuthenticationSessionOutboundDTO(String token, String username, String profilePhotoMedia, String id,
			String email, List<String> roles, Long signedInAtTime, Long expiresAtTime) {
		super();
		this.token = token;
		this.username = username;
		this.profilePhotoMedia = profilePhotoMedia;
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
