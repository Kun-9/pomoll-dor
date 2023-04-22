package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.comment.FoodComment;
import kun.pomondor.repository.etc.food.comment.FoodCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodCommentServiceImpl implements FoodCommentService {

	private final FoodCommentRepository foodCommentRepository;


	@Override
	public void createComment(FoodComment foodComment) {
		foodCommentRepository.createComment(foodComment);
	}

	@Override
	public void editComment(FoodComment foodComment) {
		foodCommentRepository.editComment(foodComment);
	}

	@Override
	public void deleteComment(Long memberId, Long commentId) {
		foodCommentRepository.deleteComment(memberId, commentId);
	}

	@Override
	public List<FoodComment> findCommentsById(Long memberId) {
		return foodCommentRepository.findCommentsById(memberId);
	}

	@Override
	public List<FoodComment> findCommentsByPostId(Long postId) {
		return foodCommentRepository.findCommentsByPostId(postId);
	}

}
