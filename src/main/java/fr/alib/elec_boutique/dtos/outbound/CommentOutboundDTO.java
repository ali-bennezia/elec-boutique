package fr.alib.elec_boutique.dtos.outbound;

import java.util.Objects;

import fr.alib.elec_boutique.entities.Comment;

public class CommentOutboundDTO {
	private Long id;
	private String title;
	private String content;
	private UserOutboundDTO author;
	private Integer note;
	private Long createdAtTime;
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
	public UserOutboundDTO getAuthor() {
		return author;
	}
	public void setAuthor(UserOutboundDTO author) {
		this.author = author;
	}
	public Integer getNote() {
		return note;
	}
	public void setNote(Integer note) {
		this.note = note;
	}
	public Long getCreatedAtTime() {
		return createdAtTime;
	}
	public void setCreatedAtTime(Long createdAtTime) {
		this.createdAtTime = createdAtTime;
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, content, createdAtTime, id, note, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentOutboundDTO other = (CommentOutboundDTO) obj;
		return Objects.equals(author, other.author) && Objects.equals(content, other.content)
				&& Objects.equals(createdAtTime, other.createdAtTime) && Objects.equals(id, other.id)
				&& Objects.equals(note, other.note) && Objects.equals(title, other.title);
	}
	public CommentOutboundDTO( Comment comment )
	{
		this.setId(comment.getId());
		this.setTitle(comment.getTitle());
		this.setContent(comment.getContent());
		this.setAuthor( new UserOutboundDTO( comment.getAuthor() ) );
		this.setNote( comment.getNote() );
		this.setCreatedAtTime( comment.getCreatedAt().getTime() );
	}
	public CommentOutboundDTO(Long id, String title, String content, UserOutboundDTO author, Integer note,
			Long createdAtTime) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.note = note;
		this.createdAtTime = createdAtTime;
	}
	public CommentOutboundDTO() {
		super();
	}
	
	
}
