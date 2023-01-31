package home.hello;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import home.hello.entity.DateNumberSelector;
import jakarta.persistence.EntityManager;

@DataJpaTest
class HelloApplicationTests {

	@Autowired
	private EntityManager em;

	@Test
	void contextLoads() {
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
		System.out.println(">>>> " + dateNumber.toString());
		Assertions.assertNotNull(dateNumber);
	}

}
