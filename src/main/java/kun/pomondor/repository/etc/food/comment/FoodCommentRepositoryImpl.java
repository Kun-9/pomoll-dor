package kun.pomondor.repository.etc.food.comment;

import kun.pomondor.repository.etc.food.score.Score;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class FoodCommentRepositoryImpl implements FoodCommentRepository {

	private final JdbcTemplate template;

	public FoodCommentRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void createRatingComment(FoodComment foodComment) {
		String sql = "CALL create_comment_procedure(?,?,?,?,?,?,?,?)";

		template.update(sql,
				foodComment.getWriterId(),
				foodComment.getBoardId(),
				foodComment.getContent(),
				foodComment.getPicture(),
				foodComment.getScore().getMenuName(),
				foodComment.getScore().getTaste(),
				foodComment.getScore().getPrice(),
				foodComment.getScore().getDistance()
		);
	}

	@Override
	public void createNomalComment(FoodComment foodComment) {
		String sql = "CALL create_comment_procedure(?,?,?,?,?,?,?,?)";

		template.update(sql,
				foodComment.getWriterId(),
				foodComment.getBoardId(),
				foodComment.getContent(),
				foodComment.getPicture(),
				null,
				null,
				null,
				null
		);
	}

	@Override
	public void editComment(FoodComment foodComment) {

	}

	@Override
	public void deleteComment(Long memberId, Long commentId) {
		String sql = "CALL delete_comment_procedure(?, ?)";
		template.update(sql, memberId, commentId);
	}

	@Override
	public List<FoodComment> findCommentsById(Long memberId) {
		return null;
	}

	@Override
	public List<FoodComment> findCommentsByPostId(Long postId) {
		String sql = "SELECT cm.id, member_id, board_id, username, created_date, content, menu, cm.picture, taste, price, distance, TRUNC((taste + price + distance)/3,1) avr " +
				"FROM food_review_comment cm " +
				"JOIN comment_score cs ON cm.id = cs.id " +
				"JOIN member m ON cm.member_id = m.id " +
				"WHERE board_id = ? " +
				"ORDER BY created_date DESC ";

		return template.query(sql, (rs, rowNum) -> new FoodComment(
				rs.getLong("id"),
				rs.getLong("member_id"),
				rs.getString("username"),
				rs.getLong("board_id"),
				rs.getTimestamp("created_date").toLocalDateTime(),
				rs.getString("content"),
				rs.getString("picture"),
				new Score(
						rs.getLong("id"),
						rs.getFloat("taste"),
						rs.getFloat("price"),
						rs.getFloat("distance"),
						rs.getFloat("avr"),
						rs.getString("menu")
				)
		), postId);
	}

	@Override
	public List<FoodComment> findAllComments() {
		return null;
	}
}
