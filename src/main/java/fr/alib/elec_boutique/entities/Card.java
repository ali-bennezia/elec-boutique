package fr.alib.elec_boutique.entities;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.security.crypto.encrypt.BytesEncryptor;

import fr.alib.elec_boutique.dtos.duplex.AddressDTO;
import fr.alib.elec_boutique.dtos.inbound.CardInboundDTO;
import fr.alib.elec_boutique.entities.embedded.Address;
import fr.alib.elec_boutique.entities.embedded.PaymentData;
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
	@Embedded
	private PaymentData paymentData;
	@ManyToOne(targetEntity = User.class)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public PaymentData getPaymentData() {
		return paymentData;
	}

	public void setPaymentData(PaymentData paymentData) {
		this.paymentData = paymentData;
	}

	public byte[] getCodeEncrypted() {
		return this.getPaymentData().getCodeEncrypted();
	}

	public void setCodeEncrypted(byte[] codeEncrypted) {
		this.getPaymentData().setCcvEncrypted(codeEncrypted);
	}

	public byte[] getCcvEncrypted() {
		return this.getPaymentData().getCcvEncrypted();
	}

	public void setCcvEncrypted(byte[] ccvEncrypted) {
		this.getPaymentData().setCcvEncrypted(ccvEncrypted);
	}

	public byte[] getExpirationDateTimeEncrypted() {
		return this.getPaymentData().getExpirationDateTimeEncrypted();
	}

	public void setExpirationDateTimeEncrypted(byte[] expirationDateTimeEncrypted) {
		this.getPaymentData().setExpirationDateTimeEncrypted(expirationDateTimeEncrypted);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Address getAddress() {
		return this.getPaymentData().getAddress();
	}

	public void setAddress(Address address) {
		this.getPaymentData().setAddress(address);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, paymentData, user);
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
		return Objects.equals(id, other.id) && Objects.equals(paymentData, other.paymentData)
				&& Objects.equals(user, other.user);
	}

	public void applyDTO(CardInboundDTO dto, BytesEncryptor encryptor, EncryptionUtils encryptionUtils)
	{
		this.setCodeEncrypted( encryptor.encrypt(dto.getCode().getBytes(StandardCharsets.UTF_8) ) );
		this.setCcvEncrypted( encryptor.encrypt( dto.getCcv().getBytes(StandardCharsets.UTF_8) ) );
		this.setExpirationDateTimeEncrypted( encryptor.encrypt( encryptionUtils.longToBytes( dto.getExpirationDateTime() ) ) );
		this.setAddress(new Address( dto.getAddress() ));	
	}
	
	public void applyPatchDTO(CardInboundDTO dto, BytesEncryptor encryptor, EncryptionUtils encryptionUtils)
	{
		if (dto.getCode() != null) this.setCodeEncrypted( encryptor.encrypt(dto.getCode().getBytes(StandardCharsets.UTF_8) ) );
		if (dto.getCcv() != null) this.setCcvEncrypted( encryptor.encrypt( dto.getCcv().getBytes(StandardCharsets.UTF_8) ) );
		if (dto.getExpirationDateTime() != null) this.setExpirationDateTimeEncrypted( encryptor.encrypt( encryptionUtils.longToBytes( dto.getExpirationDateTime() ) ) );
		if (dto.getAddress() != null) this.setAddress(new Address( dto.getAddress() ));	
	}
	
	public Card(Long id, byte[] codeEncrypted, byte[] ccvEncrypted, byte[] expirationDateTimeEncrypted, User user,
			Address address) {
		super();
		this.id = id;
		this.setCodeEncrypted(codeEncrypted);
		this.setCcvEncrypted(ccvEncrypted);
		this.setExpirationDateTimeEncrypted(expirationDateTimeEncrypted);
		this.user = user;
		this.setAddress(address);
	}

	public Card(CardInboundDTO dto, BytesEncryptor encryptor, EncryptionUtils encryptionUtils, User user ) 
	{
		this.applyDTO(dto, encryptor, encryptionUtils);
		this.setUser(user);
	}
	public Card() {
		super();
	}
	
}
