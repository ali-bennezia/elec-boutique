package fr.alib.elec_boutique.dtos.inbound;

import java.util.Objects;

import fr.alib.elec_boutique.dtos.duplex.AddressDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterInboundDTO {

	@NotEmpty
	@Size(min = 6, max = 60)
	private String username;
	@NotEmpty
	@Email
	@Size(min = 3, max = 254)
	private String email;
	@NotEmpty
	@Size(min = 2, max = 50)
	private String firstName;
	@NotEmpty
	@Size(min = 2, max = 50)
	private String lastName;
	@NotEmpty
	@Size(min = 8, max = 128)
	private String password;
	@NotNull
	private AddressDTO address;
	@Nullable
	private String businessName;
	@Nullable
	private AddressDTO businessAddress;
	private boolean isProvider;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
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
	public boolean isProvider() {
		return isProvider;
	}
	public void setProvider(boolean isProvider) {
		this.isProvider = isProvider;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, businessAddress, businessName, email, firstName, isProvider, lastName, password,
				username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRegisterInboundDTO other = (UserRegisterInboundDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(businessAddress, other.businessAddress)
				&& Objects.equals(businessName, other.businessName) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && isProvider == other.isProvider
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}
	public UserRegisterInboundDTO(@NotEmpty @Size(min = 6, max = 60) String username,
			@NotEmpty @Email @Size(min = 3, max = 254) String email,
			@NotEmpty @Size(min = 2, max = 50) String firstName, @NotEmpty @Size(min = 2, max = 50) String lastName,
			@NotEmpty @Size(min = 8, max = 128) String password, @NotNull AddressDTO address, String businessName,
			AddressDTO businessAddress, boolean isProvider) {
		super();
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.address = address;
		this.businessName = businessName;
		this.businessAddress = businessAddress;
		this.isProvider = isProvider;
	}
	public UserRegisterInboundDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
