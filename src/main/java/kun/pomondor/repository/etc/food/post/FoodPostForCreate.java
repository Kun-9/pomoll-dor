package kun.pomondor.repository.etc.food.post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter @Setter @ToString @AllArgsConstructor
public class FoodPostForCreate {
	private String restaurantName;
	private Long writerId;
	private String content;
	private Double distance;

	public FoodPostForCreate() {
	}
}
