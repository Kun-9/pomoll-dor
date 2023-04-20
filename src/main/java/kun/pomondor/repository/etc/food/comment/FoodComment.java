package kun.pomondor.repository.etc.food.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor
public class FoodComment {
	private Long commentId;
	private Long writerId;
	private Long boardId;
	private LocalDateTime createdDate;
	private String menu;
	private Double score;
	private String content;
	private String picture;
}
