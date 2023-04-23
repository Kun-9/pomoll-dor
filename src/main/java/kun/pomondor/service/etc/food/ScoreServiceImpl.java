package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.score.Score;
import kun.pomondor.repository.etc.food.score.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

	private final ScoreRepository scoreRepository;

	@Override
	public Score findScoreByCommentId(Long commentId) {
		return null;
	}

	@Override
	public List<Score> findScoreByPostId(Long commentId) {
		return null;
	}

	@Override
	public List<Score> findScoreByMembrId(Long commentId) {
		return null;
	}

	@Override
	public Float getAverageRateByPost(Long postId) {
		return scoreRepository.getAverageRateByPost(postId);
	}

	@Override
	public Map<Long, Float> getAllAverageRate() {
		return scoreRepository.getAllAverageRate();
	}
}
