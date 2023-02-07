package home.hello;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import home.hello.entity.Todo;
import home.hello.entity.Member;
import home.hello.service.TodoServiceImpl;

@SpringBootTest
public class IntegratedTest {
    
    @Autowired
    private TodoServiceImpl service;
    
    Member mockUser = new Member();

    @BeforeEach
    public void init() {
        mockUser.setName("testUser");
    }
    @Test
    public void test1() {
        Todo job = new Todo();
        job.setDescription("Test");
        Todo result = service.newTodo(LocalDate.now(), job);

        System.out.println(">>>>: " + result.toString());
    }
}
