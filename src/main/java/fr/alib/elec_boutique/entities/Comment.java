package fr.alib.elec_boutique.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import fr.alib.elec_boutique.dtos.inbound.CommentInboundDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Table
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	@ManyToOne(targetEntity = User.class)
	private User author;
	@Column(nullable = false, precision = 1, scale = 0)
	private Integer note;
	@ManyToOne(targetEntity = Product.class)
	private Product product;
	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Integer getNote() {
		return note;
	}
	public void setNote(Integer note) {
		this.note = note;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, content, createdAt, id, note, product, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(author, other.author) && Objects.equals(content, other.content)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(note, other.note) && Objects.equals(product, other.product)
				&& Objects.equals(title, other.title);
	}
	public void applyDTO(CommentInboundDTO dto)
	{
		this.setTitle(dto.getTitle());
		this.setContent(dto.getContent());
		this.setNote(dto.getNote());
	}
	public Comment(Long id, String title, String content, User author, Integer note, Product product, Timestamp createdAt) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.note = note;
		this.product = product;
		this.createdAt = createdAt;
	}
	public Comment(CommentInboundDTO dto, User author, Product product)
	{
		this.applyDTO(dto);
		this.setAuthor(author);
		this.setProduct(product);
		this.setCreatedAt(Timestamp.from(Instant.now()));
	}
	public Comment() {
		super();
	}
	
	
	
	
}
