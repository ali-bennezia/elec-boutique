package fr.alib.elec_boutique.entities;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import fr.alib.elec_boutique.dtos.inbound.ProductInboundDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(indexes = { @Index(columnList = "name, description, tags") })
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = true)
	private String tags;
	@Column(nullable = false, precision = 12, scale = 2)
	private Float price;
	private List<String> medias = new ArrayList<String>();
	
	@ManyToOne(targetEntity = User.class)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, medias, name, price, tags, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(medias, other.medias) && Objects.equals(name, other.name)
				&& Objects.equals(price, other.price) && Objects.equals(tags, other.tags)
				&& Objects.equals(user, other.user);
	}
	
	public void applyDTO(ProductInboundDTO dto)
	{
		this.name = dto.getName();
		this.description = dto.getDescription();
		this.tags = dto.getTags();
		this.price = dto.getPrice();
	}
	
	public Product(ProductInboundDTO dto, List<String> medias) {
		super();
		this.applyDTO(dto);
		this.setMedias(medias);
	}
	
	public Product(Long id, String name, String description, String tags, Float price, List<String> medias, User user) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.medias = medias;
		this.user = user;
	}

	public Product() {
		super();
	}

	

	
}
