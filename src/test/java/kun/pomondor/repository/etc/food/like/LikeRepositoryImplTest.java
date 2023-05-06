package kun.pomondor.repository.etc.food.like;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.member.MemberMin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeRepositoryImplTest {

	@Autowired
	LikeRepository likeRepository;

//	@Test
//	void like() {
//		likeRepository.likePost(19L, 101L);
//	}
//
//	@Test
//	void cancelLike() {
//		likeRepository.cancelLikePost(19L, 101L);
//	}
//
//	@Test
//	void findByPostId() {
//		List<MemberMin> likeMembers = likeRepository.findLikeMembers(101L);
//		for (MemberMin likeMember : likeMembers) {
//			System.out.println(likeMember);
//		}
//	}
//
//	@Test
//	void findByMemberId() {
//		List<FoodPost> likePosts = likeRepository.findLikePost(19L);
//
//		for (FoodPost likePost : likePosts) {
//			System.out.println(likePost);
//		}
//
//	}
}