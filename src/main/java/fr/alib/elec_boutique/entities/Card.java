package fr.alib.elec_boutique.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class Card {

	@Id
	private Long id;
	@Column(nullable = false)
	private String code_encrypted;
	@Column(nullable = false)
	private String ccv_encrypted;
	@Column(nullable = false)
	private String expiration_date_time_encrypted;
	@ManyToOne(targetEntity = User.class)
	private User user;
	@Embedded
	private fr.alib.elec_boutique.entities.embedded.Address address;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode_encrypted() {
		return code_encrypted;
	}
	public void setCode_encrypted(String code_encrypted) {
		this.code_encrypted = code_encrypted;
	}
	public String getCcv_encrypted() {
		return ccv_encrypted;
	}
	public void setCcv_encrypted(String ccv_encrypted) {
		this.ccv_encrypted = ccv_encrypted;
	}
	public String getExpiration_date_time_encrypted() {
		return expiration_date_time_encrypted;
	}
	public void setExpiration_date_time_encrypted(String expiration_date_time_encrypted) {
		this.expiration_date_time_encrypted = expiration_date_time_encrypted;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public fr.alib.elec_boutique.entities.embedded.Address getAddress() {
		return address;
	}
	public void setAddress(fr.alib.elec_boutique.entities.embedded.Address address) {
		this.address = address;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, ccv_encrypted, code_encrypted, expiration_date_time_encrypted, id, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Objects.equals(address, other.address) && Objects.equals(ccv_encrypted, other.ccv_encrypted)
				&& Objects.equals(code_encrypted, other.code_encrypted)
				&& Objects.equals(expiration_date_time_encrypted, other.expiration_date_time_encrypted)
				&& Objects.equals(id, other.id) && Objects.equals(user, other.user);
	}

	
	
}
