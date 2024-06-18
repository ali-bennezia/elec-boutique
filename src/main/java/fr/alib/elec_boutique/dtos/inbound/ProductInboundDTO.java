package fr.alib.elec_boutique.dtos.inbound;

import java.util.Objects;

public class ProductInboundDTO {
	private String name;
	private String description;
	private String tags;
	private Float price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(description, name, price, tags);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInboundDTO other = (ProductInboundDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(tags, other.tags);
	}
	public ProductInboundDTO(String name, String description, String tags, Float price) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
	}
	public ProductInboundDTO() {
		super();
	}
	
}
