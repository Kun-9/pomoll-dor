package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.score.Score;

import java.util.List;
import java.util.Map;

public interface ScoreService {
	Score findScoreByCommentId(Long commentId);

	List<Score> findScoreByPostId(Long commentId);

	List<Score> findScoreByMembrId(Long commentId);

	Float getAverageRateByPost(Long postId);

	Map<Long, Float> getAllAverageRate();
}
