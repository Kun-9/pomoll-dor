package kun.pomondor.repository.etc.food.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class FoodPostRepositoryImpl implements FoodPostRepository {

	JdbcTemplate template;

	public FoodPostRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}


	@Override
	public Long createPost(FoodPostForCreate foodPost) {
		String sql = "INSERT INTO food_review_board (id, restaurant_name, member_id, distance, content) " +
				"VALUES (food_review_board_seq.nextval, ?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder(); // KeyHolder 객체 생성

		template.update(con -> { // Lambda를 이용한 ConnectionCallback 생성
			PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"}); // PreparedStatement 생성, 자동 생성 키("id") 반환 요청
			ps.setString(1, foodPost.getRestaurantName());
			ps.setLong(2, foodPost.getWriterId());
			ps.setDouble(3, foodPost.getDistance());
			ps.setString(4, foodPost.getContent());
			return ps;
		}, keyHolder);

		return keyHolder.getKey().longValue();
	}

	@Override
	public void editPost(FoodPost foodPost) {
		String sql = "UPDATE food_review_board SET restaurant_name = ?, content = ?, distance = ? WHERE id = ?";
		template.update(sql, foodPost.getRestaurantName(), foodPost.getContent(), foodPost.getDistance(), foodPost.getPostId());
	}

	@Override
	public int deletePost(Long memberId, Long postId) {
		String sql = "CALL delete_post_procedure(?,?)";
		return  template.update(sql, postId, memberId);
	}

	@Override
	public List<FoodPost> findPostsByMemberId(Long memberId) {
		String sql = "SELECT id, restaurant_name, member_id, content, distance, picture, created_date FROM food_review_board WHERE member_id = ?";
		return template.query(sql, (rs, rowNum) -> getFoodPost(rs), memberId);
	}

	@Override
	public FoodPost findPostsByPostId(Long postId) {
		String sql = "SELECT id, restaurant_name, member_id, content, distance, picture, created_date, NVL(cnt, 0) AS like_cnt " +
				"FROM food_review_board f " +
				"LEFT JOIN (SELECT board_id, count(member_id) cnt FROM post_like GROUP BY board_id) l ON f.id = l.board_id " +
				"WHERE id = ? ";
		List<FoodPost> result = template.query(sql, (rs, rowNum) -> getFoodPost(rs), postId);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<FoodPost> findAllPosts() {
		String sql = "SELECT id, restaurant_name, f.member_id, content, distance, picture, created_date, NVL(like_cnt, 0) AS like_cnt, NVL(comment_cnt, 0) AS comment_cnt " +
				"FROM food_review_board f " +
				"LEFT JOIN (SELECT board_id, count(id) comment_cnt FROM food_review_comment GROUP BY board_id) c ON f.id = c.board_id " +
				"LEFT JOIN (SELECT board_id, count(member_id) like_cnt FROM post_like GROUP BY board_id) l ON f.id = l.board_id " +
				"ORDER BY created_date DESC";

//		return template.query(sql, (rs, rowNum) -> new FoodPost(
//				rs.getLong("id"),
//				rs.getString("restaurant_name"),
//				rs.getLong("member_id"),
//				rs.getString("content"),
//				rs.getTimestamp("created_date").toLocalDateTime(),
//				rs.getString("picture"),
//				rs.getDouble("distance"),
//				rs.getInt("like_cnt"),
//				rs.getInt("comment_cnt")
//		));
		return null;
	}

	@Override
	public List<FoodPost> findPartialPosts(int startRow, int endRow) {
		String sql = "SELECT * " +
				"FROM (SELECT t.*, rownum AS rn " +
				"      FROM (SELECT id, " +
				"                   restaurant_name, " +
				"                   f.member_id, " +
				"                   content, " +
				"                   distance, " +
				"                   picture, " +
				"                   created_date, " +
				"                   NVL(like_cnt, 0)    AS like_cnt, " +
				"                   NVL(comment_cnt, 0) AS comment_cnt, " +
				"                   r.avr AS avr_rating " +
				"            FROM food_review_board f " +
				"                     LEFT JOIN (SELECT board_id, COUNT(id) comment_cnt FROM food_review_comment GROUP BY board_id) c " +
				"                               ON f.id = c.board_id " +
				"                     LEFT JOIN (SELECT board_id, COUNT(member_id) like_cnt FROM post_like GROUP BY board_id) l " +
				"                               ON f.id = l.board_id " +
				"                     LEFT JOIN (SELECT board_id, TRUNC(AVG((taste + price + distance))/3,1) avr " +
				"                                   FROM food_review_comment cm " +
				"                                   JOIN comment_score cs ON cm.id = cs.id " +
				"                                   GROUP BY board_id) r " +
				"                               ON f.id = r.board_id " +
				"            ORDER BY created_date DESC) t " +
				"      WHERE rownum <= :endRow) " +
				"WHERE rn > :startRow";

		Object[] params = new Object[]{endRow, startRow};

		return template.query(sql, params, (rs, rowNum) -> new FoodPost(
				rs.getLong("id"),
				rs.getString("restaurant_name"),
				rs.getLong("member_id"),
				rs.getString("content"),
				rs.getTimestamp("created_date").toLocalDateTime(),
				rs.getString("picture"),
				rs.getDouble("distance"),
				rs.getInt("like_cnt"),
				rs.getInt("comment_cnt"),
				rs.getFloat("avr_rating")
		));
	}

	@Override
	public void registPicture(Long postId, String path) {
		String sql = "UPDATE food_review_board SET picture = ? WHERE id = ?";
		template.update(sql, path, postId);
	}

	private static FoodPost getFoodPost(ResultSet rs) throws SQLException {
		return new FoodPost(
				rs.getLong("id"),
				rs.getString("restaurant_name"),
				rs.getLong("member_id"),
				rs.getString("content"),
				rs.getTimestamp("created_date").toLocalDateTime(),
				rs.getString("picture"),
				rs.getDouble("distance"),
				rs.getInt("like_cnt")
		);
	}
}
