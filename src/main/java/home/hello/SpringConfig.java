package home.hello;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import home.hello.repository.FileInfoRepository;
import home.hello.repository.JpaFileInfoRepository;
import home.hello.repository.JpaTodoRepository;
import home.hello.repository.TodoRepository;
import home.hello.service.FileUploadService;
import home.hello.service.TodoServiceImpl;
import jakarta.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private final DataSource datasource;
    private final EntityManager em;
    public SpringConfig(DataSource datasource, EntityManager em) {
        this.datasource = datasource;
        this.em = em;
    }

    @Bean
    public TodoRepository todoRepository() {
        return new JpaTodoRepository(em);
    }

    @Bean
    public TodoServiceImpl todoService() {
        return new TodoServiceImpl(todoRepository());
    }

    @Bean
    public FileInfoRepository fileInfoRepository() {
        return new JpaFileInfoRepository(em);
    }
    @Bean
    public FileUploadService fileUploadService() {
        return new FileUploadService(fileInfoRepository());
    }
}
