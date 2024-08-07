package kun.pomondor.repository.etc.food.score;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface ScoreRepository {
	Score findScoreByCommentId(Long commentId);

	List<Score> findScoreByPostId(Long commentId);

	List<Score> findScoreByMembrId(Long commentId);

	Float getAverageRateByPost(Long postId);

	Map<Long, Float> getAllAverageRate();
}
