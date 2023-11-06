package kun.pomondor.repository.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Slf4j
@Repository
public class MemberRepositoryImpl implements MemberRepository {
//	private final NamedParameterJdbcTemplate template;
	private final JdbcTemplate template;

	public MemberRepositoryImpl(DataSource dataSource) {
//		this.template = new NamedParameterJdbcTemplate(dataSource);
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public Member join(Member member) {
		String sql = "INSERT INTO member (ID, EMAIL, PASSWORD, USERNAME) " +
				"VALUES (member_id_seq.nextval, ?, ?, ?)";

		template.update(sql, member.getEmail(),
				member.getPassword(),
				member.getUsername());

		return member;
	}

	@Override
	public Member findById(Long id) {
		String sql = "SELECT id, email, password, username, picture FROM member WHERE id = ?";
		List<Member> results = getMembers(sql, id);

		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public List<Member> findAll() {
		String sql = "SELECT id, email, password, username, picture FROM member";
		return getMembers(sql);
	}

	@Override
	public Member findByEmail(String email) {
		String sql = "SELECT id, email, password, username, picture FROM member WHERE email = ?";
		List<Member> results = getMembers(sql, email);
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public List<Member> findByUsername(String keyword) {
		String sql = "SELECT id, email, username, PASSWORD, PICTURE FROM member WHERE INSTR(UPPER(username), UPPER(?)) > 0";
		return template.query(sql, (rs, rowNum) -> getMember(rs), keyword);
	}

	@Override
	public int deleteMember(long userId) {
		return 0;
	}

	@Override
	public int getRenameCnt(long userId) {
		String sql = "SELECT RENAME_CNT FROM MEMBER WHERE ID = ? ";
		List<Integer> renameCnt = template.query(sql, (rs, rowNum) -> rs.getInt("rename_cnt"), userId);
		return renameCnt.isEmpty() ? null : renameCnt.get(0);
	}

	@Override
	public boolean validUsernameExist(String username) {
		String sql = "SELECT count(*) cnt FROM member WHERE username = ?";
		List<Integer> cnt = template.query(sql, (rs, rowNum) -> rs.getInt("cnt"), username);
		return cnt.get(0) != 0;
	}

	@Override
	public void subtractRenameCnt(long userId) {
		String sql = "UPDATE member SET rename_cnt = rename_cnt - 1 WHERE id = ?";
		template.update(sql, userId);
	}

	@Override
	public int changeName(long userId, String username) {
		String sql = "UPDATE member SET username = ? WHERE id = ?";
		return template.update(sql, username, userId);
	}

	@Override
	public void setProfileImg(long userId, String path) {
		String sql = "UPDATE MEMBER SET PICTURE = ? WHERE id = ?";
		template.update(sql, path, userId);
	}

	@Override
	public void deleteProfileImg(long userId) {
		String sql = "UPDATE MEMBER SET PICTURE = null WHERE id = ?";
		template.update(sql, userId);
	}

	private List<Member> getMembers(String sql, Object param) {
		return template.query(sql, (rs, rowNum) -> getMember(rs), param);
	}

	private List<Member> getMembers(String sql) {
		return template.query(sql, (rs, rowNum) -> getMember(rs));
	}

	private static Member getMember(ResultSet rs) throws SQLException {
		return new Member(
				rs.getLong("id"),
				rs.getString("email"),
				rs.getString("username"),
				rs.getString("password"),
				rs.getString("picture")
		);
	}
}
