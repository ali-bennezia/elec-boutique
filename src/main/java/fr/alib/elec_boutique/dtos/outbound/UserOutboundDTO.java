package fr.alib.elec_boutique.dtos.outbound;

import java.util.List;
import java.util.Objects;

import fr.alib.elec_boutique.entities.User;
import io.jsonwebtoken.lang.Arrays;

public class UserOutboundDTO {
	private Long id;
	private String username;
	private String profilePhotoMedia;
	private Boolean enabled;
	private Long createdAtTime;
	private List<String> roles;
	private String businessName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Long getCreatedAtTime() {
		return createdAtTime;
	}
	public void setCreatedAtTime(Long createdAtTime) {
		this.createdAtTime = createdAtTime;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(businessName, createdAtTime, enabled, id, profilePhotoMedia, roles, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserOutboundDTO other = (UserOutboundDTO) obj;
		return Objects.equals(businessName, other.businessName) && Objects.equals(createdAtTime, other.createdAtTime)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(id, other.id)
				&& Objects.equals(profilePhotoMedia, other.profilePhotoMedia) && Objects.equals(roles, other.roles)
				&& Objects.equals(username, other.username);
	}
	public UserOutboundDTO(Long id, String username, String profilePhotoMedia, Boolean enabled, Long createdAtTime,
			List<String> roles, String businessName) {
		super();
		this.id = id;
		this.username = username;
		this.profilePhotoMedia = profilePhotoMedia;
		this.enabled = enabled;
		this.createdAtTime = createdAtTime;
		this.roles = roles;
		this.businessName = businessName;
	}
	public UserOutboundDTO(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.profilePhotoMedia = user.getProfilePhotoMedia();
		this.enabled = user.getEnabled();
		this.createdAtTime = user.getCreatedAt().getTime();
		this.roles = Arrays.asList( user.getRoles().split(", ") );
		this.businessName = user.getBusinessName();
	}
	public UserOutboundDTO() {
		super();
	}
	
	
}
