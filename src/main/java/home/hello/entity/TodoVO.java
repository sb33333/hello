package home.hello.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class TodoVO {
	private LocalDate date;
	private int sequence;
	private String userName;
	private String title;
	private String description;
	private TodoStatus status;
	private List<FileInfoVO> attachedFiles;

	public static TodoVO createTodoVO (Todo entity) {
		TodoVO vo = new TodoVO();
		TodoId id = entity.getId();
		vo.setDate(id.getDate());
		vo.setSequence(id.getSequence());
		// vo.setUserName(entity.getMember().getName());
		vo.setStatus(entity.getStatus());
		vo.setTitle(entity.getTitle());
		vo.setDescription(entity.getDescription());
		vo.setAttachedFiles(entity.getAttachedFiles().stream().map(FileInfoVO::createFileInfoVO).collect(Collectors.toList()));
		return vo;
	}
	public TodoId createTodoId() {
		TodoId id = new TodoId();
		id.setDate(this.date);
		id.setSequence(this.sequence);
		return id;
	}
}
