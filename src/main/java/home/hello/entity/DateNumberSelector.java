package home.hello.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter@Setter
public class DateNumberSelector {
    @Column@Id
    private LocalDate date;
    @Column
    private int sequence;

    public TodoId createDailyJobId() {
        TodoId id = new TodoId();
        id.setDate(this.date);
        id.setSequence(this.sequence);
        return id;
    }
}
