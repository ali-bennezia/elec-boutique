package fr.alib.elec_boutique.dtos.outbound;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.encrypt.BytesEncryptor;

import fr.alib.elec_boutique.entities.Card;
import fr.alib.elec_boutique.utils.EncryptionUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CardOutboundDTO {
	@Size(min=13, max=19)
	private String partialCode;
	@NotNull
	private Long expirationDateTime;
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
	public CardOutboundDTO(@Size(min = 13, max = 19) String partialCode, @NotNull Long expirationDateTime) {
		super();
		this.partialCode = partialCode;
		this.expirationDateTime = expirationDateTime;
	}
	public CardOutboundDTO(Card card, BytesEncryptor encryptor, EncryptionUtils encryptionUtils) {
		super();
		this.partialCode = new String( encryptor.decrypt( card.getCodeEncrypted() ), StandardCharsets.UTF_8 )
				.replaceAll(".{12}$", "XXXXXXXXXXXX");
		this.expirationDateTime = encryptionUtils.bytesToLong( encryptor.decrypt( card.getExpirationDateTimeEncrypted() ) );
	}
	public CardOutboundDTO() {
		super();
	}
	
	
}
