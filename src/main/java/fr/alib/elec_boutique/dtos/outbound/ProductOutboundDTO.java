package fr.alib.elec_boutique.dtos.outbound;

import java.util.List;
import java.util.Objects;

import fr.alib.elec_boutique.entities.Product;

public class ProductOutboundDTO {
	private Long id;
	private String name;
	private String description;
	private String tags;
	private Float price;
	private List<String> medias;
	private UserOutboundDTO user;
	private Long createdAtTime;
	private Float averageNote;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<String> getMedias() {
		return medias;
	}
	public void setMedias(List<String> medias) {
		this.medias = medias;
	}
	public UserOutboundDTO getUser() {
		return user;
	}
	public void setUser(UserOutboundDTO user) {
		this.user = user;
	}
	public Long getCreatedAtTime() {
		return createdAtTime;
	}
	public void setCreatedAtTime(Long createdAtTime) {
		this.createdAtTime = createdAtTime;
	}
	public Float getAverageNote() {
		return averageNote;
	}
	public void setAverageNote(Float averageNote) {
		this.averageNote = averageNote;
	}
	@Override
	public int hashCode() {
		return Objects.hash(averageNote, createdAtTime, description, id, medias, name, price, tags, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductOutboundDTO other = (ProductOutboundDTO) obj;
		return Objects.equals(averageNote, other.averageNote) && Objects.equals(createdAtTime, other.createdAtTime)
				&& Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(medias, other.medias) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(tags, other.tags)
				&& Objects.equals(user, other.user);
	}
	public ProductOutboundDTO(Long id, String name, String description, String tags, Float price, List<String> medias,
			UserOutboundDTO user, Long createdAtTime, Float averageNote) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.medias = medias;
		this.user = user;
		this.createdAtTime = createdAtTime;
		this.averageNote = averageNote;
	}
	public ProductOutboundDTO(Product product) {
		super();
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.tags = product.getTags();
		this.price = product.getPrice();
		this.medias = product.getMedias();
		this.user = new UserOutboundDTO( product.getUser() );
		this.createdAtTime = product.getCreatedAt().getTime();
		this.averageNote = product.getAverageNote().floatValue();
	}
	public ProductOutboundDTO() {
		super();
	}

	
	
}
