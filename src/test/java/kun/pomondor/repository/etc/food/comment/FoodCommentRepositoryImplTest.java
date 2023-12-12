package kun.pomondor.repository.etc.food.comment;

import kun.pomondor.repository.etc.food.score.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodCommentRepositoryImplTest {

	@Autowired
	FoodCommentRepository foodCommentRepository;

	@Test
	void create() {
//		FoodComment object = new FoodComment(
//				19L,
//				101L,
//				"맛있음",
//				null,
//				new Score(4, 5, 5, "짜장면")
//		);
//		foodCommentRepository.createComment(object);
	}

	@Test
	void findByPostId() {

		List<FoodComment> comments = foodCommentRepository.findCommentsByPostId(101L);

		for (FoodComment comment : comments) {
			System.out.println(comment);
		}



	}

	@Test
	void findCommentsByPostId() {
		List<FoodComment> commentsByPostId = foodCommentRepository.findCommentsByPostId(143L);

		for (FoodComment foodComment : commentsByPostId) {
			System.out.println(foodComment);
		}
	}
}