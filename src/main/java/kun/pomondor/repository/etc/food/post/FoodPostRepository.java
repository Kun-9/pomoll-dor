package kun.pomondor.repository.etc.food.post;

import java.util.List;

public interface FoodPostRepository {
	Long createPost(FoodPostForCreate foodPost);

	void editPost(FoodPost foodPost);

	int deletePost(Long memberId, Long postId);

	List<FoodPost> findPostsByMemberId(Long memberId);

	FoodPost findPostsByPostId(Long postId);

	List<FoodPost> findAllPosts();

	void registPicture(Long postId, String path);
}
