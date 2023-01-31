package home.hello.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Todo {
	@EmbeddedId
	private TodoId id;
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "USER_NAME")
	private Member user;
	@Enumerated(EnumType.STRING)
	private Activity activity;
	private String uploadedFileName;
	private String convertedFileName;
	private String description;
	@Override
	public String toString() {
		return "DailyJob [id=" + id.getDate() + "//" + id.getSequence() + ", user=" + user + ", activity=" + activity + ", description=" + description
				+ "]";
	}
	
}
