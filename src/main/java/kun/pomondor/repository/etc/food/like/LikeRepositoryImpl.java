package kun.pomondor.repository.etc.food.like;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberMin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

	JdbcTemplate template;

	public LikeRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void likeComment(Long memberId, Long commentId) {

	}

	@Override
	public void likePost(Long memberId, Long postId) {
		String sql = "INSERT INTO post_like (member_id, board_id) VALUES (?, ?)";
		template.update(sql, memberId, postId);
	}

	@Override
	public void cancelLikePost(Long memberId, Long postId) {
		String sql = "DELETE FROM post_like WHERE member_id = ? AND board_id = ?";
		template.update(sql, memberId, postId);
	}

	@Override
	public List<MemberMin> findLikeMembers(Long postId) {
		String sql = "SELECT m.id, username, picture " +
				"FROM post_like p " +
				"JOIN member m ON m.id = p.member_id " +
				"WHERE board_id = ? ";

		return template.query(sql, (rs, rowNum) -> new MemberMin(
				rs.getLong("id"),
				rs.getString("username"),
				rs.getString("picture")
		), postId);
	}

	@Override
	public List<FoodPost> findLikePost(Long memberId) {
		String sql = "SELECT frb.id, restaurant_name, frb.member_id, content, distance, picture, created_date " +
				"FROM post_like pl " +
				"JOIN food_review_board frb ON frb.id = pl.board_id " +
				"WHERE frb.member_id = ?";
		return template.query(sql, (rs, rowNum) -> getFoodPost(rs), memberId);
	}

	private static FoodPost getFoodPost(ResultSet rs) throws SQLException {
		return new FoodPost(
				rs.getLong("id"),
				rs.getString("restaurant_name"),
				rs.getLong("member_id"),
				rs.getString("content"),
				rs.getTimestamp("created_date").toLocalDateTime(),
				rs.getString("picture"),
				rs.getDouble("distance")
		);
	}

	@Override
	public Boolean isLike(Long memberId, Long postId) {
		String sql = "SELECT count(*) cnt FROM post_like WHERE member_id = ? and board_id = ?";
		List<Integer> cnt = template.query(sql, (rs, rowNum) -> rs.getInt("cnt"), memberId, postId);
		return cnt.get(0) != 0;
	}
}
