package kun.pomondor.repository.etc.food.like;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberMin;

import java.util.List;

public interface LikeRepository {
	void likeComment(Long memberId, Long commentId);

	void likePost(Long memberId, Long postId);

	void cancelLikePost(Long memberId, Long postId);

	List<MemberMin> findLikeMembers(Long postId);

	List<FoodPost> findLikePost(Long memberId);

	Boolean isLike(Long memberId, Long postId);
}
