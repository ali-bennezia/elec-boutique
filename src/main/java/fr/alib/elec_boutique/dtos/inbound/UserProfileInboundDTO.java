package fr.alib.elec_boutique.dtos.inbound;

import java.util.Objects;

import fr.alib.elec_boutique.dtos.duplex.AddressDTO;
import jakarta.validation.constraints.NotEmpty;

public class UserProfileInboundDTO {
	
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private AddressDTO address;
	private String businessName;
	private AddressDTO businessAddress;
	
	@NotEmpty
	private String authPassword;
	@NotEmpty
	private String authPasswordConfirmation;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public AddressDTO getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(AddressDTO businessAddress) {
		this.businessAddress = businessAddress;
	}
	public String getAuthPassword() {
		return authPassword;
	}
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}
	public String getAuthPasswordConfirmation() {
		return authPasswordConfirmation;
	}
	public void setAuthPasswordConfirmation(String authPasswordConfirmation) {
		this.authPasswordConfirmation = authPasswordConfirmation;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, authPassword, authPasswordConfirmation, businessAddress, businessName, email,
				firstName, lastName, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfileInboundDTO other = (UserProfileInboundDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(authPassword, other.authPassword)
				&& Objects.equals(authPasswordConfirmation, other.authPasswordConfirmation)
				&& Objects.equals(businessAddress, other.businessAddress)
				&& Objects.equals(businessName, other.businessName) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}
	
	public UserProfileInboundDTO(String email, String firstName, String lastName, String password, AddressDTO address,
			String businessName, AddressDTO businessAddress, String authPassword, String authPasswordConfirmation) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.address = address;
		this.businessName = businessName;
		this.businessAddress = businessAddress;
		this.authPassword = authPassword;
		this.authPasswordConfirmation = authPasswordConfirmation;
	}
	public UserProfileInboundDTO() {
		super();
	}
	
	
}
