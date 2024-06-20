package fr.alib.elec_boutique.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.alib.elec_boutique.dtos.inbound.ProductInboundDTO;
import fr.alib.elec_boutique.entities.Comment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
	@Column(nullable = false, precision = 12)
	private Float price;
	private List<String> medias = new ArrayList<String>();
	
	@ManyToOne(targetEntity = User.class)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;
	@Column(nullable = false, precision = 2, scale = 1)
	private BigDecimal averageNote;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "product_comment_id", referencedColumnName = "id")
	private List<Comment> comments = new ArrayList<Comment>();

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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public BigDecimal getAverageNote() {
		return averageNote;
	}

	public void setAverageNote(BigDecimal averageNote) {
		this.averageNote = averageNote;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(averageNote, comments, createdAt, description, id, medias, name, price, tags, user);
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
		return Objects.equals(averageNote, other.averageNote) && Objects.equals(comments, other.comments)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(medias, other.medias)
				&& Objects.equals(name, other.name) && Objects.equals(price, other.price)
				&& Objects.equals(tags, other.tags) && Objects.equals(user, other.user);
	}

	public void applyDTO(ProductInboundDTO dto)
	{
		this.name = dto.getName();
		this.description = dto.getDescription();
		this.tags = dto.getTags();
		this.price = dto.getPrice();
	}
	
	public void applyPatchDTO(ProductInboundDTO dto)
	{
		if (dto.getName() != null) this.setName(dto.getName());
		if (dto.getDescription() != null) this.setDescription(dto.getDescription());
		if (dto.getTags() != null) this.setTags(dto.getTags());
		if (dto.getPrice() != null) this.setPrice(dto.getPrice());
	}
	
	public Product(ProductInboundDTO dto, List<String> medias, List<Comment> comments) 
	{
		super();
		this.applyDTO(dto);
		this.setMedias(medias);
		this.setCreatedAt( Timestamp.from(Instant.now()) );
		this.setAverageNote(new BigDecimal(0));
		this.setComments(comments);
	}

	public Product(Long id, String name, String description, String tags, Float price, List<String> medias, User user,
			Timestamp createdAt, BigDecimal averageNote, List<Comment> comments) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.medias = medias;
		this.user = user;
		this.createdAt = createdAt;
		this.averageNote = averageNote;
		this.comments = comments;
	}

	public Product() {
		super();
	}

	

	
}
