package kun.pomondor.repository.etc.food.score;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Score {
	private Long commentId;
	private Float taste;
	private Float price;
	private Float distance;
	private Float average;
	private String menuName;

	public Score(Long commentId, float taste, float price, float distance, float average, String menuName) {
		this.commentId = commentId;
		this.taste = taste;
		this.price = price;
		this.distance = distance;
		this.average = average;
		this.menuName = menuName;
	}

	public Score(float taste, float price, float distance, String menuName) {
		this.taste = taste;
		this.price = price;
		this.distance = distance;
		this.menuName = menuName;
	}
}
