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

	public FoodPost() {
	}

	public FoodPost(Long postId, String restaurantName, Long loginMember, String content, Double distance) {

	}
}
