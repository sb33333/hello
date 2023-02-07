package home.hello.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Todo {
	@EmbeddedId
	private TodoId id;
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "USER_NAME")
	private Member member;
	private String title = "제목";
	private String description = "설명";
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	private TodoStatus status;
	@OneToMany @JoinTable(name = "TODO_ATTACHED", joinColumns = {@JoinColumn(name = "DATE"), @JoinColumn(name = "SEQUENCE")}, inverseJoinColumns = @JoinColumn(name = "ID"))
	private List<FileInfo> attachedFiles = new ArrayList<>();

	public void attachFile(FileInfo fileInfo) {
		attachedFiles.add(fileInfo);
	}
	public void removeFile(FileInfo fileInfo) {
		if (attachedFiles.contains(fileInfo)) {
			attachedFiles.remove(fileInfo);
		}
	}
	public List<FileInfo> getAttachedFiles() {
		return new ArrayList<FileInfo>(this.attachedFiles);
	}
}
