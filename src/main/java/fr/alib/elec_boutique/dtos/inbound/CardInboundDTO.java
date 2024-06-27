package fr.alib.elec_boutique.dtos.inbound;

import fr.alib.elec_boutique.dtos.duplex.AddressDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CardInboundDTO {
	@Size(min=13, max=19)
	private String code;
	@Size(min=3, max=3)
	private String ccv;
	@NotNull
	private Long expirationDateTime;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	private AddressDTO address;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCcv() {
		return ccv;
	}
	public void setCcv(String ccv) {
		this.ccv = ccv;
	}
	public Long getExpirationDateTime() {
		return expirationDateTime;
	}
	public void setExpirationDateTime(Long expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
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
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public CardInboundDTO(@Size(min = 13, max = 19) String code, @Size(min = 3, max = 3) String ccv,
			@NotNull Long expirationDateTime, @NotEmpty String firstName, @NotEmpty String lastName,
			AddressDTO address) {
		super();
		this.code = code;
		this.ccv = ccv;
		this.expirationDateTime = expirationDateTime;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	public CardInboundDTO() {
		super();
	}
}
