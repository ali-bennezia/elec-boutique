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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		this.setPaymentData(new PaymentData());
		this.getPaymentData().setCodeEncrypted( encryptor.encrypt(dto.getCode().getBytes(StandardCharsets.UTF_8) ) );
		this.getPaymentData().setCcvEncrypted( encryptor.encrypt( dto.getCcv().getBytes(StandardCharsets.UTF_8) ) );
		this.getPaymentData().setExpirationDateTimeEncrypted( encryptor.encrypt( encryptionUtils.longToBytes( dto.getExpirationDateTime() ) ) );
		this.getPaymentData().setFirstName(dto.getFirstName());
		this.getPaymentData().setLastName(dto.getLastName());
		this.getPaymentData().setAddress(new Address( dto.getAddress() ));	
	}
	
	public void applyPatchDTO(CardInboundDTO dto, BytesEncryptor encryptor, EncryptionUtils encryptionUtils)
	{
		if (this.paymentData == null) this.setPaymentData(new PaymentData());
		if (dto.getCode() != null) this.getPaymentData().setCodeEncrypted( encryptor.encrypt(dto.getCode().getBytes(StandardCharsets.UTF_8) ) );
		if (dto.getCcv() != null) this.getPaymentData().setCcvEncrypted( encryptor.encrypt( dto.getCcv().getBytes(StandardCharsets.UTF_8) ) );
		if (dto.getExpirationDateTime() != null) this.getPaymentData().setExpirationDateTimeEncrypted( encryptor.encrypt( encryptionUtils.longToBytes( dto.getExpirationDateTime() ) ) );
		if (dto.getFirstName() != null) this.getPaymentData().setFirstName( dto.getFirstName() );
		if (dto.getLastName() != null) this.getPaymentData().setFirstName( dto.getLastName() );
		if (dto.getAddress() != null) this.getPaymentData().setAddress(new Address( dto.getAddress() ));	
	}
	
	public Card(Long id, byte[] codeEncrypted, byte[] ccvEncrypted, byte[] expirationDateTimeEncrypted, User user, String firstName, String lastName,
			Address address) {
		super();
		this.id = id;
		this.setPaymentData(new PaymentData());
		this.getPaymentData().setCodeEncrypted(codeEncrypted);
		this.getPaymentData().setCcvEncrypted(ccvEncrypted);
		this.getPaymentData().setExpirationDateTimeEncrypted(expirationDateTimeEncrypted);
		this.user = user;
		this.getPaymentData().setFirstName(firstName);
		this.getPaymentData().setLastName(lastName);
		this.getPaymentData().setAddress(address);
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
