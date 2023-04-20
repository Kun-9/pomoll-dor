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
		String sql = "DELETE food_review_board WHERE id = ? AND member_id = ?";
		return  template.update(sql, postId, memberId);
	}

	@Override
	public List<FoodPost> findPostsByMemberId(Long memberId) {
		String sql = "SELECT id, restaurant_name, member_id, content, distance, picture, created_date FROM food_review_board WHERE member_id = ?";
		return template.query(sql, (rs, rowNum) -> getFoodPost(rs), memberId);
	}

	@Override
	public FoodPost findPostsByPostId(Long postId) {
		String sql = "SELECT id, restaurant_name, member_id, content, distance, picture, created_date FROM food_review_board WHERE id = ?";
		List<FoodPost> result = template.query(sql, (rs, rowNum) -> getFoodPost(rs), postId);
		return result.isEmpty() ? null : result.get(0);
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
	public List<FoodPost> findAllPosts() {
		String sql = "SELECT * FROM food_review_board ORDER BY created_date DESC";
		return template.query(sql, (rs, rowNum) -> getFoodPost(rs));
	}

	@Override
	public void registPicture(Long postId, String path) {
		String sql = "UPDATE food_review_board SET picture = ? WHERE id = ?";
		template.update(sql, path, postId);
	}
}
