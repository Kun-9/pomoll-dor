package kun.pomondor.repository.group;

import kun.pomondor.repository.etc.food.post.GroupMember;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

	private final JdbcTemplate template;

	public GroupRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public Long createGroup(Group group) {
		String sql = "insert ALL " +
				"    INTO group_list (id, member_id, group_name, created_date, group_info, group_status, picture) " +
				"        VALUES (group_seq.nextval, ?, ?, sysdate, ?, ?, ?) " +
				"    INTO group_member (group_id, member_id, created_date, status) " +
				"        VALUES (group_seq.currval, ?, sysdate, '수락') " +
				"SELECT * from dual ";

//		template.update(sql, group.getAdminId(), group.getGroupName(), group.getInfo(), status, group.getPicture(), group.getAdminId());

		KeyHolder keyHolder = new GeneratedKeyHolder(); // KeyHolder 객체 생성

		template.update(con -> { // Lambda를 이용한 ConnectionCallback 생성
			PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"}); // PreparedStatement 생성, 자동 생성 키("id") 반환 요청
			ps.setLong(1, group.getAdminId());
			ps.setString(2, group.getGroupName());
			ps.setString(3, group.getInfo());
			ps.setInt(4, group.isJoinStatus() ? 1:0);
			ps.setString(5, group.getPicture());
			ps.setLong(6, group.getAdminId());
			return ps;
		}, keyHolder);

		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	@Override
	public Group findGroupById(Long groupId) {
		String sql = "SELECT id, member_id, group_name, created_date, group_info, group_status, picture " +
				"FROM group_list " +
				"WHERE id = ? ";

		List<Group> result = template.query(sql, (rs, rowNum) -> new Group(
				rs.getLong("member_id"),
				rs.getLong("id"),
				rs.getString("group_name"),
				rs.getString("group_info"),
				rs.getInt("group_status") == 1,
				rs.getString("picture")
		), groupId);

		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<GroupMember> findGroupMembers(Long groupId) {
		String sql = "SELECT m.id, m.username, m.picture, to_char(gm.created_date, 'yyyy/mm/dd') reg_date " +
				"FROM group_member gm " +
				"JOIN member m ON gm.member_id = m.id " +
				"WHERE group_id = ? ";

		return template.query(sql, (rs, rowNum) -> new GroupMember(
						rs.getLong("id"),
						rs.getString("username"),
						rs.getString("picture"),
						rs.getString("reg_date")
				), groupId
		);
	}

	@Override
	public List<Group> findManagingGroups(Long memberId) {
		String sql = "SELECT id, member_id, group_name, created_date, group_info, group_status, picture " +
				"FROM group_list " +
				"WHERE MEMBER_ID = ?";

		return template.query(sql, (rs, rowNum) -> new Group(
				rs.getLong("member_id"),
				rs.getLong("id"),
				rs.getString("group_name"),
				rs.getString("group_info"),
				rs.getInt("group_status") == 1,
				rs.getString("picture")
		), memberId);
	}

	@Override
	public int getManagingGroupCnt(Long memberId) {
		String sql = "SELECT count(*) cnt " +
				"FROM group_list " +
				"WHERE member_id = ? ";
		List<Integer> result = template.query(sql, (rs, rowNum) -> rs.getInt("cnt"), memberId);
		return result.isEmpty() ? 0 : result.get(0);
	}

	@Override
	public int getMemberCnt(Long groupId) {
		String sql = "SELECT count(*) cnt " +
				"FROM group_member " +
				"WHERE group_id = ? ";
		List<Integer> result = template.query(sql, (rs, rowNum) -> rs.getInt("cnt"), groupId);
		return result.isEmpty() ? 0 : result.get(0);
	}

	@Override
	public void sendJoinRequest(Long memberId, Long groupId) {
		String sql = "insert into group_member (group_id, member_id, created_date, status) " +
				"VALUES (?, ?, created_date, '대기')";
		template.update(sql, groupId, memberId);
	}

	@Override
	public void rejectRequest(Long rejectingMemberId, Long groupId) {
		String sql = "DELETE FROM group_member " +
				"WHERE member_id = ? AND group_id = ?";
		template.update(sql, rejectingMemberId, groupId);
	}

	@Override
	public void acceptRequest(Long acceptMemberId, Long groupId) {
		String sql = "UPDATE group_member SET status = '수락' " +
				"WHERE member_id = ? AND group_id = ?";
		template.update(sql, acceptMemberId, groupId);
	}

	@Override
	public void deleteGroup(Long memberId, Long groupId) {

	}

	@Override
	public List<Group> findAllGroup() {
		String sql = "SELECT id, member_id, group_name, created_date, group_info, group_status, picture " +
				"FROM group_list";

		return template.query(sql, (rs, rowNum) -> new Group(
				rs.getLong("member_id"),
				rs.getLong("id"),
				rs.getString("group_name"),
				rs.getString("group_info"),
				rs.getInt("group_status") == 1,
				rs.getString("picture")
		));
	}

	@Override
	public Boolean isAdmin(Long adminId, Long groupId) {
		String sql = "SELECT count(*) cnt " +
				"FROM group_list " +
				"WHERE member_id = ? AND id = ? ";
		List<Integer> result = template.query(sql, (rs, rowNum) -> rs.getInt("cnt"), adminId, groupId);
		return !result.isEmpty() && result.get(0) == 1;
	}

	@Override
	public List<Group> joinedGroup(Long memberId) {
		String sql = "SELECT gl.id, gl.member_id, group_name, group_info, group_status, picture " +
				"FROM group_member gm " +
				"JOIN group_list gl ON gm.group_id = gl.id " +
				"WHERE gm.member_id = ? AND status = '수락' ";

		return template.query(sql, (rs, rowNum) -> new Group(
				rs.getLong("member_id"),
				rs.getLong("id"),
				rs.getString("group_name"),
				rs.getString("group_info"),
				rs.getInt("group_status") == 1,
				rs.getString("picture")
		), memberId);
	}

	// 승인 없이 가입 여부
	@Override
	public Boolean getGroupStatus(Long groupId) {
		String sql = "SELECT group_status AS status " +
				"FROM group_list " +
				"WHERE id = ? ";
		List<Integer> result = template.query(sql, (rs, rowNum) -> rs.getInt("status"), groupId);

		return !result.isEmpty() && result.get(0) == 1;
	}

	@Override
	public void setGroupPicture(Long groupId, String URI) {
		String sql = "UPDATE group_list SET picture = ? WHERE id = ? ";
		template.update(sql, URI, groupId);
	}
}
