package home.hello.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import home.hello.entity.Todo;
import home.hello.entity.TodoId;
import home.hello.entity.TodoKey;

public interface TodoRepository {

	public TodoKey selectTodoKey(LocalDate date);
	public TodoKey saveTodoKey(TodoKey key);
	public Todo insertTodo(Todo todo);
	public Optional<Todo> selectTodo(Todo todo);
	public Optional<Todo> selectTodoById(TodoId id);
	public List<Todo> selectTodosByDate(LocalDate date);
	public List<Todo> selectTodos();
	public boolean deleteTodo(TodoId id);

}
