package kun.pomondor.repository.etc.food.comment;

import kun.pomondor.repository.etc.food.post.FoodPost;

import java.util.List;

public interface FoodCommentRepository {
	void createComment(FoodComment foodComment);

	void editComment(FoodComment foodComment);

	void deleteComment(Long memberId, Long commentId);

	List<FoodComment> findCommentsById(Long memberId);

	List<FoodComment> findCommentsByPostId(Long postId);

	List<FoodComment> findAllComments();
}
