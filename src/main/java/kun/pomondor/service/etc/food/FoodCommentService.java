package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.comment.FoodComment;

import java.util.List;

public interface FoodCommentService {

	void createComment(FoodComment foodComment);

	void editComment(FoodComment foodComment);

	void deleteComment(Long memberId, Long commentId);

	List<FoodComment> findCommentsById(Long memberId);

	List<FoodComment> findCommentsByPostId(Long postId);

}
