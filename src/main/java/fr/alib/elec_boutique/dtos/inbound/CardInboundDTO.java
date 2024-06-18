package fr.alib.elec_boutique.dtos.inbound;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CardInboundDTO {
	@Size(min=13, max=19)
	private String code;
	@Size(min=3, max=3)
	private String ccv;
	@NotNull
	private Long expirationDateTime;
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
	public CardInboundDTO(@Size(min = 13, max = 19) String code, @Size(min = 3, max = 3) String ccv,
			@NotNull Long expirationDateTime) {
		super();
		this.code = code;
		this.ccv = ccv;
		this.expirationDateTime = expirationDateTime;
	}
}
