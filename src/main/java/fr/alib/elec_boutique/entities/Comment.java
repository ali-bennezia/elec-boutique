package fr.alib.elec_boutique.entities;

import java.util.Objects;

import fr.alib.elec_boutique.dtos.inbound.CommentInboundDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table
@Entity
public class Comment {

	private Long id;
	private String title;
	private String content;
	private User author;
	@Column(nullable = false, precision = 1, scale = 0)
	private Integer note;
	@ManyToOne(targetEntity = Product.class)
	private Product product;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(author, content, id, note, product, title);
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
				&& Objects.equals(id, other.id) && Objects.equals(note, other.note)
				&& Objects.equals(product, other.product) && Objects.equals(title, other.title);
	}
	
	public void applyDTO(CommentInboundDTO dto)
	{
		this.setTitle(dto.getTitle());
		this.setContent(dto.getContent());
		this.setNote(dto.getNote());
	}
	public Comment(Long id, String title, String content, User author, Integer note, Product product) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.note = note;
		this.product = product;
	}
	public Comment(CommentInboundDTO dto, User author, Product product)
	{
		this.applyDTO(dto);
		this.setAuthor(author);
		this.setProduct(product);
	}
	public Comment() {
		super();
	}
	
	
	
	
}
