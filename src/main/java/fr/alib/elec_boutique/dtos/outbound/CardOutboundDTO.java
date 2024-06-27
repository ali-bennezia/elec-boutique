package fr.alib.elec_boutique.dtos.outbound;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.security.crypto.encrypt.BytesEncryptor;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.utils.EncryptionUtils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CardOutboundDTO {
	private Long id;
	@Size(min=13, max=19)
	private String partialCode;
	@NotNull
	private Long expirationDateTime;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPartialCode() {
		return partialCode;
	}
	public void setPartialCode(String partialCode) {
		this.partialCode = partialCode;
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

	@Override
	public int hashCode() {
		return Objects.hash(expirationDateTime, firstName, id, lastName, partialCode);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardOutboundDTO other = (CardOutboundDTO) obj;
		return Objects.equals(expirationDateTime, other.expirationDateTime)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(partialCode, other.partialCode);
	}
	public CardOutboundDTO(Long id, @Size(min = 13, max = 19) String partialCode, @NotNull Long expirationDateTime,
			@NotEmpty String firstName, @NotEmpty String lastName) {
		super();
		this.id = id;
		this.partialCode = partialCode;
		this.expirationDateTime = expirationDateTime;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public CardOutboundDTO(Card card, BytesEncryptor encryptor, EncryptionUtils encryptionUtils) {
		super();
		this.id = card.getId();
		this.partialCode = new String( encryptor.decrypt( card.getPaymentData().getCodeEncrypted() ), StandardCharsets.UTF_8 )
				.replaceAll(".{12}$", "XXXXXXXXXXXX");
		this.expirationDateTime = encryptionUtils.bytesToLong( encryptor.decrypt( card.getPaymentData().getExpirationDateTimeEncrypted() ) );
		this.firstName = card.getPaymentData().getFirstName();
		this.lastName = card.getPaymentData().getLastName();
	}
	public CardOutboundDTO() {
		super();
	}
}
