package fr.alib.elec_boutique.dtos.outbound;

import java.util.Objects;

import fr.alib.elec_boutique.entities.Invoice;

public class InvoiceOutboundDTO {
	private Long id;
	private UserOutboundDTO user;
	private ProductOutboundDTO product;
	private Float price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserOutboundDTO getUser() {
		return user;
	}
	public void setUser(UserOutboundDTO user) {
		this.user = user;
	}
	public ProductOutboundDTO getProduct() {
		return product;
	}
	public void setProduct(ProductOutboundDTO product) {
		this.product = product;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, price, product, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InvoiceOutboundDTO other = (InvoiceOutboundDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(price, other.price)
				&& Objects.equals(product, other.product) && Objects.equals(user, other.user);
	}
	public InvoiceOutboundDTO(Invoice invoice)
	{
		this.setId(invoice.getId());
		this.setUser(new UserOutboundDTO( invoice.getUser() ));
		this.setProduct(new ProductOutboundDTO(invoice.getProduct()));
		this.setPrice(invoice.getPrice());
	}
	public InvoiceOutboundDTO(Long id, UserOutboundDTO user, ProductOutboundDTO product, Float price) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.price = price;
	}
	public InvoiceOutboundDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
