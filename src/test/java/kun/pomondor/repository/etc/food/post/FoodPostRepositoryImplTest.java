package kun.pomondor.repository.etc.food.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodPostRepositoryImplTest {

	@Autowired
	FoodPostRepository foodPostRepository;

	@Test
	void create() {
//		FoodPostForCreate downTowner = new FoodPostForCreate(
//				"다운타우너",
//				19L,
//				"수제버거집",
//				"hamburger.jpeg",
//				12.4
//		);
//		foodPostRepository.createPost(downTowner);

	}

	@Test
	void findAll() {
		List<FoodPost> allPosts = foodPostRepository.findAllPosts();

		for (FoodPost allPost : allPosts) {
			System.out.println(allPost);
		}
	}

	@Test
	void findPartialPosts() {
		List<FoodPost> allPosts = foodPostRepository.findPartialPosts(10, 20);
		// te
		for (FoodPost allPost : allPosts) {
			System.out.println(allPost);
		}
	}
}