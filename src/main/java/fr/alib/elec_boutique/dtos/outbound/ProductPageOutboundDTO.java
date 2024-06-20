package fr.alib.elec_boutique.dtos.outbound;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import fr.alib.elec_boutique.entities.Product;

public class ProductPageOutboundDTO {
	private Integer pageIndex;
	private Integer totalPagesAmount;
	private Integer pageElementCount;
	private List<ProductOutboundDTO> content;
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
	public List<ProductOutboundDTO> getContent() {
		return content;
	}
	public void setContent(List<ProductOutboundDTO> content) {
		this.content = content;
	}
	@Override
	public int hashCode() {
		return Objects.hash(pageElementCount, pageIndex, totalPagesAmount);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPageOutboundDTO other = (ProductPageOutboundDTO) obj;
		return Objects.equals(pageElementCount, other.pageElementCount) && Objects.equals(pageIndex, other.pageIndex)
				&& Objects.equals(totalPagesAmount, other.totalPagesAmount);
	}
	public ProductPageOutboundDTO(Page<Product> productPage)
	{
		this.setPageIndex(productPage.getNumber());
		this.setTotalPagesAmount(productPage.getTotalPages());
		this.setPageElementCount(productPage.getSize());
		this.setContent(productPage.getContent().stream().map(p->{
			return new ProductOutboundDTO(p);
		}).collect(Collectors.toList()));
	}
	public ProductPageOutboundDTO(Integer pageIndex, Integer totalPagesAmount, Integer pageElementCount,
			List<ProductOutboundDTO> content) {
		super();
		this.pageIndex = pageIndex;
		this.totalPagesAmount = totalPagesAmount;
		this.pageElementCount = pageElementCount;
		this.content = content;
	}
	public ProductPageOutboundDTO() {
		super();
	}
	
	
}
