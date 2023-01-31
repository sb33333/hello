package home.hello.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import home.hello.entity.Todo;
import home.hello.entity.TodoId;
import home.hello.entity.DateNumberSelector;

public interface TodoRepository {

	public DateNumberSelector selectNewSequenceNumber(LocalDate date);
	public Todo save(LocalDate dateKey, Todo job);
	public Optional<Todo> findById(TodoId id);
	public List<Todo> findAll();
	public List<Todo> findByDate(LocalDate date);

}
