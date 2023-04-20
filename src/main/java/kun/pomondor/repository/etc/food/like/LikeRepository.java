package kun.pomondor.repository.etc.food.like;

public interface LikeRepository {
	void likeComment(Long memberId, Long CommentId);

	void likePost(Long memberId, Long commentId);

	void createScore(Long memberId, Long PostId, Double score);
}
