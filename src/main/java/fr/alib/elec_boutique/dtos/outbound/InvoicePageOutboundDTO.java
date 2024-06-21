package fr.alib.elec_boutique.dtos.outbound;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import fr.alib.elec_boutique.entities.Invoice;

public class InvoicePageOutboundDTO {
	private Integer pageIndex;
	private Integer totalPagesAmount;
	private Integer pageElementCount;
	private List<InvoiceOutboundDTO> content;
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getTotalPagesAmount() {
		return totalPagesAmount;
	}
	public void setTotalPagesAmount(Integer totalPagesAmount) {
		this.totalPagesAmount = totalPagesAmount;
	}
	public Integer getPageElementCount() {
		return pageElementCount;
	}
	public void setPageElementCount(Integer pageElementCount) {
		this.pageElementCount = pageElementCount;
	}
	public List<InvoiceOutboundDTO> getContent() {
		return content;
	}
	public void setContent(List<InvoiceOutboundDTO> content) {
		this.content = content;
	}
	@Override
	public int hashCode() {
		return Objects.hash(content, pageElementCount, pageIndex, totalPagesAmount);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoicePageOutboundDTO other = (InvoicePageOutboundDTO) obj;
		return Objects.equals(content, other.content) && Objects.equals(pageElementCount, other.pageElementCount)
				&& Objects.equals(pageIndex, other.pageIndex)
				&& Objects.equals(totalPagesAmount, other.totalPagesAmount);
	}
	public InvoicePageOutboundDTO(Page<Invoice> invoicePage)
	{
		this.setPageIndex(invoicePage.getNumber());
		this.setTotalPagesAmount(invoicePage.getTotalPages());
		this.setPageElementCount(invoicePage.getSize());
		this.setContent(invoicePage.getContent().stream().map(p->{
			return new InvoiceOutboundDTO(p);
		}).collect(Collectors.toList()));
	}
	public InvoicePageOutboundDTO(Integer pageIndex, Integer totalPagesAmount, Integer pageElementCount,
			List<InvoiceOutboundDTO> content) {
		super();
		this.pageIndex = pageIndex;
		this.totalPagesAmount = totalPagesAmount;
		this.pageElementCount = pageElementCount;
		this.content = content;
	}
	public InvoicePageOutboundDTO() {
		super();
	}
	
}
