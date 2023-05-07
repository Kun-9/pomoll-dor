package kun.pomondor.repository.etc.food.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor
public class FoodPost {
	private Long postId;
	private String restaurantName;
	private Long writerId;
	private String content;
	private LocalDateTime createdDate;
	private String picture;
	private Double distance;
	private int likeCnt;

	public FoodPost() {
	}

	public FoodPost(Long postId, String restaurantName, Long writerId, String content, LocalDateTime createdDate, String picture, Double distance) {
		this.postId = postId;
		this.restaurantName = restaurantName;
		this.writerId = writerId;
		this.content = content;
		this.createdDate = createdDate;
		this.picture = picture;
		this.distance = distance;
	}
}
