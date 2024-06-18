package fr.alib.elec_boutique.entities;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.security.crypto.encrypt.BytesEncryptor;

import fr.alib.elec_boutique.dtos.inbound.CardInboundDTO;
import fr.alib.elec_boutique.entities.embedded.Address;
import fr.alib.elec_boutique.utils.EncryptionUtils;
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
	private byte[] codeEncrypted;
	@Column(nullable = false)
	private byte[] ccvEncrypted;
	@Column(nullable = false)
	private byte[] expirationDateTimeEncrypted;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return Objects.equals(address, other.address) && Objects.equals(ccvEncrypted, other.ccvEncrypted)
				&& Objects.equals(codeEncrypted, other.codeEncrypted)
				&& Objects.equals(expirationDateTimeEncrypted, other.expirationDateTimeEncrypted)
				&& Objects.equals(id, other.id) && Objects.equals(user, other.user);
	}
	
	public Card(Long id, byte[] codeEncrypted, byte[] ccvEncrypted, byte[] expirationDateTimeEncrypted, User user,
			Address address) {
		super();
		this.id = id;
		this.codeEncrypted = codeEncrypted;
		this.ccvEncrypted = ccvEncrypted;
		this.expirationDateTimeEncrypted = expirationDateTimeEncrypted;
		this.user = user;
		this.address = address;
	}

	public Card(CardInboundDTO dto, BytesEncryptor encryptor, EncryptionUtils encryptionUtils ) 
	{
		this.setCodeEncrypted( encryptor.encrypt(dto.getCode().getBytes(StandardCharsets.UTF_8) ) );
		this.setCcvEncrypted( encryptor.encrypt( dto.getCcv().getBytes(StandardCharsets.UTF_8) ) );
		this.setExpirationDateTimeEncrypted( encryptor.encrypt( encryptionUtils.longToBytes( dto.getExpirationDateTime() ) ) );
	}
	public Card() {
		super();
	}
	
}
