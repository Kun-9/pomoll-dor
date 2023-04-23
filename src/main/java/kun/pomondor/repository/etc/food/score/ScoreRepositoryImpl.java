package kun.pomondor.repository.etc.food.score;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScoreRepositoryImpl implements ScoreRepository {

	JdbcTemplate template;

	public ScoreRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

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
		String sql = "SELECT TRUNC(AVG((taste + price + distance))/3,1) avr " +
				"FROM food_review_comment cm " +
				"JOIN comment_score cs ON cm.id = cs.id " +
				"WHERE board_id = ?";
		List<Float> result = template.query(sql, (rs, rowNum) -> rs.getFloat("avr"), postId);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public Map<Long, Float> getAllAverageRate() {
		String sql = "SELECT board_id, TRUNC(AVG((taste + price + distance))/3,1) avr " +
				"FROM food_review_comment cm " +
				"JOIN comment_score cs ON cm.id = cs.id " +
				"GROUP BY board_id ";

		return template.query(sql, rs -> {
			Map<Long, Float> resultMap = new HashMap<>();
			while (rs.next()) {
				Long boardId = rs.getLong("board_id");
				Float avr = rs.getFloat("avr");
				resultMap.put(boardId, avr);
			}
			return resultMap;
		});
	}
}
