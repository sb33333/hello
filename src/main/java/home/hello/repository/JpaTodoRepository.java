package home.hello.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import home.hello.entity.Todo;
import home.hello.entity.TodoId;
import home.hello.entity.DateNumberSelector;
import jakarta.persistence.EntityManager;

public class JpaTodoRepository implements TodoRepository{
	private EntityManager em;
	public JpaTodoRepository(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<Todo> findAll() {
		return em.createQuery("select job from DailyJob job", Todo.class)
		.getResultList();
	}
	@Override
	public List<Todo> findByDate(LocalDate date) {
		List<Todo> result = em.createQuery("select job from DailyJob job where job.date = :date", Todo.class)
		.setParameter("date", date)
		.getResultList();
		return result;
	}
	@Override
	public Optional<Todo> findById(TodoId id) {
		Todo result = em.find(Todo.class, id);
		return Optional.ofNullable(result);
	}
	@Override
	public Todo save(LocalDate dateKey, Todo job) {
		DateNumberSelector key = selectNewSequenceNumber(dateKey);
		job.setId(key.createDailyJobId());
		em.persist(job);
		return job;
	}

	@Override
	public DateNumberSelector selectNewSequenceNumber(LocalDate date) {
		DateNumberSelector dateNumber = em.find(DateNumberSelector.class, LocalDate.now());
		if (dateNumber == null) {
			dateNumber = new DateNumberSelector();
			dateNumber.setDate(LocalDate.now());
			dateNumber.setSequence(0);
			em.persist(dateNumber);
		} else {
			dateNumber.setSequence(dateNumber.getSequence() + 1);
			em.persist(dateNumber);
		}
		return dateNumber;
	}
	
}
