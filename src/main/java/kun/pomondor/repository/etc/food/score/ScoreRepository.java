package kun.pomondor.repository.etc.food.score;

import java.util.List;

public interface ScoreRepository {
	Score findScoreByCommentId(Long commentId);

	List<Score> findScoreByPostId(Long commentId);

	List<Score> findScoreByMembrId(Long commentId);

	float getAverageRateByPost(Long postId);
}
