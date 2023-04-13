package kun.pomondor.repository.friend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FriendRepositoryImpl implements FriendRepository {

	JdbcTemplate template;

	public FriendRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public List<FriendRequest> findFriendListById(Long memberId, String status) {
		String sql = "SELECT sender_id, receiver_id, status " +
				"FROM friend " +
				"WHERE receiver_id = ? " +
				"AND status = ? ";
		return getFriendList(memberId, status, sql);
	}

	@Override
	public List<FriendRequest> findSentRequestById(Long memberId) {
		String sql = "SELECT sender_id, receiver_id, status " +
				"FROM friend " +
				"WHERE sender_id = ? " +
				"AND status = '대기' ";

		return getFriendList(memberId, sql);
	}

	@Override
	public int sendFriendRequest(Long memberId, Long targetId) {
		String sql = "INSERT INTO friend (sender_id, receiver_id, status) VALUES (?, ?, '대기')";
		return template.update(sql, memberId, targetId);
	}

	@Override
	public int acceptFriendRequest(Long memberId, Long targetId) {
		String sql = "CALL accept_friend_request(?, ?)";
		return template.update(sql, targetId, memberId);
	}

	@Override
	public int rejectFriendRequest(Long memberId, Long targetId) {
		String sql = "UPDATE friend SET status ='거절' WHERE sender_id = ? AND receiver_id = ?";
		return template.update(sql, targetId, memberId);
	}

	@Override
	public int deleteFriend(Long memberId, Long targetId) {
		String sql = "CALL DELETE_FRIEND(?, ?)";
		template.update(sql, memberId, targetId);
		return 0;
	}

	private List<FriendRequest> getFriendList(Long memberId, String status, String sql) {
		return template.query(sql, (rs, rowNum) -> new FriendRequest(
				rs.getLong("sender_id"),
				rs.getLong("receiver_id"),
				rs.getString("status")
		), memberId, status);
	}

	private List<FriendRequest> getFriendList(Long memberId, String sql) {
		return template.query(sql, (rs, rowNum) -> new FriendRequest(
				rs.getLong("sender_id"),
				rs.getLong("receiver_id"),
				rs.getString("status")
		), memberId);
	}

	@Override
	public int cancelRequset(Long memberId, Long targetId) {
		String sql = "DELETE friend WHERE sender_id = ? AND receiver_id = ?";
		return template.update(sql, memberId, targetId);
	}

	@Override
	public String getStatus(Long memberId, Long targetId) {
		String sql = "SELECT status FROM friend WHERE sender_id = ? AND receiver_id = ?";
		List<String> status = template.query(sql,
				(rs, rowNum) -> rs.getString("status")
				, memberId, targetId);
		return status.isEmpty() ? null : status.get(0);
	}
}
