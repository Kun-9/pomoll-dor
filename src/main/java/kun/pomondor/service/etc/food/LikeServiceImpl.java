package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.like.LikeRepository;
import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.member.MemberMin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

	private final LikeRepository likeRepository;

	@Override
	public void likeComment(Long memberId, Long commentId) {
	}

	@Override
	public void likePost(Long memberId, Long postId) {
		likeRepository.likePost(memberId, postId);
	}

	@Override
	public void cancelLikePost(Long memberId, Long postId) {
		likeRepository.cancelLikePost(memberId, postId);
	}

	@Override
	public List<MemberMin> findLikeMembers(Long postId) {
		return likeRepository.findLikeMembers(postId);
	}

	@Override
	public List<FoodPost> findLikePost(Long memberId) {
		return likeRepository.findLikePost(memberId);
	}

	@Override
	public Boolean isLike(Long memberId, Long postId) {
		return likeRepository.isLike(memberId, postId);
	}
}
