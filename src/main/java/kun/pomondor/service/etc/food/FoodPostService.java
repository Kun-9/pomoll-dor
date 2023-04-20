package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.etc.food.post.FoodPostForCreate;

import java.util.List;

public interface FoodPostService {
	Long createPost(FoodPostForCreate foodPost);

	void editPost(FoodPost foodPost);

	int deletePost(Long memberId, Long postId);

	List<FoodPost> findPostsByMemberId(Long memberId);

	FoodPost findPostsByPostId(Long postId);

	List<FoodPost> findAllPosts();

	void registPicture(Long postId, String path);
}
