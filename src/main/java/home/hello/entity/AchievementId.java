package home.hello.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
// @Embeddable
public class AchievementId {
    @Column
    private String title;
    @Column
    private int level;
}
