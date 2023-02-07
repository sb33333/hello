package home.hello.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import home.hello.entity.Todo;
import home.hello.entity.TodoId;
import home.hello.entity.TodoKey;
import jakarta.persistence.EntityManager;

public class JpaTodoRepository implements TodoRepository{
	private EntityManager em;
	public JpaTodoRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Todo> selectTodos() {
		return em.createQuery("select t from Todo t", Todo.class)
		.getResultList();
	}
	@Override
	public List<Todo> selectTodosByDate(LocalDate date) {
		List<Todo> result = em.createQuery("select t from Todo t where t.date = :date", Todo.class)
		.setParameter("date", date)
		.getResultList();
		return result;
	}
	@Override
	public Optional<Todo> selectTodoById(TodoId id) {
		Todo result = em.find(Todo.class, id);
		return Optional.ofNullable(result);
	}
	@Override
	public Todo insertTodo(Todo todo) {
		em.persist(todo);
		return todo;
	}

	@Override
	public TodoKey selectTodoKey(LocalDate date) {
		TodoKey dateNumber = em.find(TodoKey.class, date);
		if (dateNumber == null) {
			dateNumber = new TodoKey();
			dateNumber.setDate(LocalDate.now());
			dateNumber.setSequence(0);
		} else {
			dateNumber.setSequence(dateNumber.getSequence() + 1);
		}
		return dateNumber;
	}
	
	@Override
	public TodoKey saveTodoKey(TodoKey key) {
		em.persist(key);
		return key;
	}

	@Override
	public Optional<Todo> selectTodo(Todo todo) {
		return Optional.ofNullable(em.find(Todo.class, todo.getId()));
	}

	@Override
	public boolean deleteTodo(TodoId id) {
		Todo target = em.find(Todo.class, id);
		if (target != null) {
			em.remove(target);
			return true;
		}
		return false;
	}
	
}
