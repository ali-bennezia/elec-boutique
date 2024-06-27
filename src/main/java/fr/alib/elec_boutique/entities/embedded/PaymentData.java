package fr.alib.elec_boutique.entities.embedded;

import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class PaymentData {
	@Column(nullable = false)
	private byte[] codeEncrypted;
	@Column(nullable = false)
	private byte[] ccvEncrypted;
	@Column(nullable = false)
	private byte[] expirationDateTimeEncrypted;
	private String firstName;
	private String lastName;
	@Embedded
	private Address address;
	
	public byte[] getCodeEncrypted() {
		return codeEncrypted;
	}
	public void setCodeEncrypted(byte[] codeEncrypted) {
		this.codeEncrypted = codeEncrypted;
	}
	public byte[] getCcvEncrypted() {
		return ccvEncrypted;
	}
	public void setCcvEncrypted(byte[] ccvEncrypted) {
		this.ccvEncrypted = ccvEncrypted;
	}
	public byte[] getExpirationDateTimeEncrypted() {
		return expirationDateTimeEncrypted;
	}
	public void setExpirationDateTimeEncrypted(byte[] expirationDateTimeEncrypted) {
		this.expirationDateTimeEncrypted = expirationDateTimeEncrypted;
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
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(ccvEncrypted);
		result = prime * result + Arrays.hashCode(codeEncrypted);
		result = prime * result + Arrays.hashCode(expirationDateTimeEncrypted);
		result = prime * result + Objects.hash(address, firstName, lastName);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentData other = (PaymentData) obj;
		return Objects.equals(address, other.address) && Arrays.equals(ccvEncrypted, other.ccvEncrypted)
				&& Arrays.equals(codeEncrypted, other.codeEncrypted)
				&& Arrays.equals(expirationDateTimeEncrypted, other.expirationDateTimeEncrypted)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}
	public PaymentData(byte[] codeEncrypted, byte[] ccvEncrypted, byte[] expirationDateTimeEncrypted, String firstName, String lastName, Address address) {
		super();
		this.codeEncrypted = codeEncrypted;
		this.ccvEncrypted = ccvEncrypted;
		this.expirationDateTimeEncrypted = expirationDateTimeEncrypted;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}
	public PaymentData() {
		super();
	}
	
}
