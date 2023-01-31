package home.hello.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class FileInfo {
    @Id
    private String id;
    private String filePath;
    private String realFilename;
}