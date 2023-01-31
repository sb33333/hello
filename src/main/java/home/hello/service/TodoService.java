package home.hello.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import home.hello.entity.Todo;
import home.hello.entity.TodoId;
import home.hello.repository.TodoRepository;

@Transactional
public class TodoService {
	
	private TodoRepository repository;
	public TodoService(TodoRepository repository) {
		this.repository = repository;
	}

	public Todo newDailyJob(LocalDate date, Todo job) {
		return repository.save(date, job);
	}
	public List<Todo> findDailyJobs() {
		return repository.findAll();
	}
	public List<Todo> findDailyJobs(LocalDate date) {
		return repository.findByDate(date);
	}
	public Optional<Todo> findDailyJob(TodoId id) {
		return repository.findById(id);
	}


}
