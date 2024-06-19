package fr.alib.elec_boutique.dtos.outbound;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.security.crypto.encrypt.BytesEncryptor;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.utils.EncryptionUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CardOutboundDTO {
	private Long id;
	@Size(min=13, max=19)
	private String partialCode;
	@NotNull
	private Long expirationDateTime;

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
	
	@Override
	public int hashCode() {
		return Objects.hash(expirationDateTime, id, partialCode);
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
		return Objects.equals(expirationDateTime, other.expirationDateTime) && Objects.equals(id, other.id)
				&& Objects.equals(partialCode, other.partialCode);
	}
	
	public CardOutboundDTO(Long id, @Size(min = 13, max = 19) String partialCode, @NotNull Long expirationDateTime) {
		super();
		this.id = id;
		this.partialCode = partialCode;
		this.expirationDateTime = expirationDateTime;
	}
	public CardOutboundDTO(Card card, BytesEncryptor encryptor, EncryptionUtils encryptionUtils) {
		super();
		this.id = card.getId();
		this.partialCode = new String( encryptor.decrypt( card.getCodeEncrypted() ), StandardCharsets.UTF_8 )
				.replaceAll(".{12}$", "XXXXXXXXXXXX");
		this.expirationDateTime = encryptionUtils.bytesToLong( encryptor.decrypt( card.getExpirationDateTimeEncrypted() ) );
	}
	public CardOutboundDTO() {
		super();
	}
}
