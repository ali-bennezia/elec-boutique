package fr.alib.elec_boutique.dtos.outbound;

import java.util.Objects;

import fr.alib.elec_boutique.entities.User;

public class UserProfileOutboundDTO {

	private Long id;
	private UserOutboundDTO userData;
	private String firstName;
	private String lastName;
	private Integer passwordLength;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserOutboundDTO getUserData() {
		return userData;
	}
	public void setUserData(UserOutboundDTO userData) {
		this.userData = userData;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getPasswordLength() {
		return passwordLength;
	}
	public void setPasswordLength(Integer passwordLength) {
		this.passwordLength = passwordLength;
	}
	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, passwordLength, userData);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfileOutboundDTO other = (UserProfileOutboundDTO) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(passwordLength, other.passwordLength)
				&& Objects.equals(userData, other.userData);
	}
	public UserProfileOutboundDTO(Long id, UserOutboundDTO userData, String firstName, String lastName,
			Integer passwordLength) {
		super();
		this.id = id;
		this.userData = userData;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passwordLength = passwordLength;
	}
	public UserProfileOutboundDTO(User user)
	{
		this.id = user.getId();
		this.userData = new UserOutboundDTO(user);
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.passwordLength = user.getPassword().length();
	}
	public UserProfileOutboundDTO() {
		super();
	}
	
}
