package kun.pomondor.repository.etc.food.comment;

import java.util.List;

public interface FoodCommentRepository {
	void createRatingComment(FoodComment foodComment);

	void createNomalComment(FoodComment foodComment);

	void editComment(FoodComment foodComment);

	void deleteComment(Long memberId, Long commentId);

	List<FoodComment> findCommentsById(Long memberId);

	List<FoodComment> findCommentsByPostId(Long postId);

	List<FoodComment> findAllComments();
}
