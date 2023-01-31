package home.hello.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Embeddable
public class TodoId {
    @Column
    private LocalDate date;
    @Column
    private int sequence;
}
