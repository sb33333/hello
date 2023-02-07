package home.hello.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import home.hello.entity.FileInfoVO;
import home.hello.entity.Todo;
import home.hello.entity.TodoId;
import home.hello.entity.TodoKey;
import home.hello.entity.TodoStatus;
import home.hello.entity.TodoVO;
import home.hello.repository.TodoRepository;

@Transactional
public class TodoServiceImpl {
	
	private TodoRepository repository;
	public TodoServiceImpl(TodoRepository repository) {
		this.repository = repository;
	}

	public TodoVO newTodo(TodoVO vo) {
		TodoKey key = repository.selectTodoKey(vo.getDate());
		Todo todo = new Todo();
		todo.setId(key.createTodoId());
		todo.setTitle(vo.getTitle());
		todo.setDescription(vo.getDescription());
		todo.setStatus(TodoStatus.STARTED);
		repository.insertTodo(todo);
		repository.saveTodoKey(key);
		return TodoVO.createTodoVO(todo);
	}
	public List<TodoVO> todoList(LocalDate date) {
		List<Todo> search;
		if (date == null) {
			search = repository.selectTodos();
		} else {
			search = repository.selectTodosByDate(date);
		}
		return search.stream().map(TodoVO::createTodoVO).collect(Collectors.toList());
	}
	public TodoVO findTodo(TodoId id) {
		return repository
				.selectTodoById(id)
				.map(TodoVO::createTodoVO)
				.orElseThrow(IllegalArgumentException::new);
	}

	public TodoVO addFileToTodo(TodoVO todo, FileInfoVO fileInfo) {
		Todo entity = repository.selectTodoById(todo.createTodoId()).orElseThrow(IllegalArgumentException::new);
		entity.attachFile(fileInfo.createFileInfoEntity());
		repository.insertTodo(entity);
		return TodoVO.createTodoVO(entity);
	}

	public boolean deleteTodo(TodoId id) {
		return repository.deleteTodo(id);
	}

	public List<FileInfoVO> retrieveFileInfoList(TodoId id) {
		Todo saved = repository.selectTodoById(id).orElseThrow(IllegalArgumentException::new);
		return saved.getAttachedFiles().stream().map(FileInfoVO::createFileInfoVO).collect(Collectors.toList());
	}

	public TodoVO updateTodoStatus(TodoId id, TodoStatus status) {
		Todo saved = repository.selectTodoById(id).orElseThrow(IllegalArgumentException::new);
		saved.setStatus(status);
		repository.insertTodo(saved);
		return TodoVO.createTodoVO(saved);
	}

	public boolean updateTodoContent(TodoId id, TodoVO vo) {
		Todo saved = repository.selectTodoById(id).orElseThrow(IllegalArgumentException::new);
		if (saved != null) {
			if (StringUtils.hasLength(vo.getDescription())) {
				saved.setDescription(vo.getDescription());
			}
			if (StringUtils.hasLength(vo.getTitle())) {
				saved.setTitle(vo.getTitle());
			}
			repository.insertTodo(saved);
			return true;
		} else {
			return false;
		}
	}
}
