package fr.alib.elec_boutique.dtos.duplex;

import java.util.Objects;

import fr.alib.elec_boutique.entities.embedded.Address;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddressDTO {
	@NotEmpty
	private String street;
	@NotEmpty
	private String city;
	@NotNull
	private String zipCode;
	@NotEmpty
	private String country;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public int hashCode() {
		return Objects.hash(city, country, street, zipCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDTO other = (AddressDTO) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country)
				&& Objects.equals(street, other.street) && Objects.equals(zipCode, other.zipCode);
	}
	public AddressDTO(@NotEmpty String street, @NotEmpty String city, @NotNull String zipCode,
			@NotEmpty String country) {
		super();
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}
	public AddressDTO(Address address) {
		super();
		this.street = address.getStreet();
		this.city = address.getCity();
		this.zipCode = address.getZipCode();
		this.country = address.getCountry();
	}
	public AddressDTO() {
		super();
	}
	
}
