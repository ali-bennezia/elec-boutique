package fr.alib.elec_boutique.dtos.inbound;

import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CommentInboundDTO {
	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
	@Min(0)
	@Max(5)
	private Integer note;
	
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
	public Integer getNote() {
		return note;
	}
	public void setNote(Integer note) {
		this.note = note;
	}
	@Override
	public int hashCode() {
		return Objects.hash(content, note, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentInboundDTO other = (CommentInboundDTO) obj;
		return Objects.equals(content, other.content) && Objects.equals(note, other.note)
				&& Objects.equals(title, other.title);
	}
	public CommentInboundDTO(@NotEmpty String title, @NotEmpty String content, @Min(0) @Max(5) Integer note) {
		super();
		this.title = title;
		this.content = content;
		this.note = note;
	}
	public CommentInboundDTO() {
		super();
	}
}
