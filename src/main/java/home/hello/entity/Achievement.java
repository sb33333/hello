package home.hello.entity;

import lombok.Getter;
import lombok.Setter;

// @Entity
@Getter
@Setter
public class Achievement {
	// @EmbeddedId
	private AchievementId id;
	// @Enumerated(EnumType.STRING)
	private Activity activity;
	private Member user;
	private int level;
	private int goal;
	private int count;
}
