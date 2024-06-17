package fr.alib.elec_boutique.dtos.inbound;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserSignInInboundDTO {
	@Email
	@Size(min = 3, max = 254)
	private String email;
	@NotEmpty
	@Size(min = 8, max = 128)
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, password);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSignInInboundDTO other = (UserSignInInboundDTO) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password);
	}
	
}
