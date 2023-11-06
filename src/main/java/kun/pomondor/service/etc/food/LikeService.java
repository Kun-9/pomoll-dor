package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.member.MemberMin;

import java.util.List;

public interface LikeService {
	void likeComment(Long memberId, Long commentId);

	void likePost(Long memberId, Long postId);

	void cancelLikePost(Long memberId, Long postId);

	List<MemberMin> findLikeMembers(Long postId);

	List<FoodPost> findLikePost(Long memberId);

	Boolean isLike(Long memberId, Long postId);

	int getLikeCnt(Long postId);
}
