package kun.pomondor.repository.etc.food.comment;

import kun.pomondor.repository.etc.food.score.Score;
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
	private String content;
	private String picture;
	private Score score;

	public FoodComment(Long writerId, Long boardId, String content, String picture, Score score) {
		this.writerId = writerId;
		this.boardId = boardId;
		this.content = content;
		this.picture = picture;
		this.score = score;
	}
}
