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
//		String sql = "INSERT INTO member (member_key, email, password, username) " +
//				"VALUES (member_id_seq.nextval, ?, ?, ?)";

		String sql = "CALL CREATE_MEMBER(?, ?, ?, ?)";

//		template.update(sql, member.getEmail(),
//				member.getPassword(),
//				member.getUsername());

		template.execute(sql,
				(CallableStatementCallback<Void>) cs -> {
					cs.setString(1, " ");
					cs.setString(2, member.getEmail());
					cs.setString(3, member.getPassword());
					cs.setString(4, member.getUsername());
					cs.execute();
					return null;
				}
		);

		return member;
	}

	@Override
	public Member findById(Long id) {
		String sql = "SELECT id, email, password, username FROM member WHERE id = ?";
		List<Member> results = getMembers(sql, id);

		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public List<Member> findAll() {
		String sql = "SELECT member_key as id, email, password, username FROM member";

		return getMembers(sql);
	}

	@Override
	public Member findByEmail(String email) {
		String sql = "SELECT id, email, password, username FROM member WHERE email = ?";
		List<Member> results = getMembers(sql, email);
		return results.isEmpty() ? null : results.get(0);
	}

	private static Member getMember(ResultSet rs) throws SQLException {
		return new Member(
				rs.getLong("id"),
				rs.getString("email"),
				rs.getString("username"),
				rs.getString("password")
		);
	}

	private List<Member> getMembers(String sql, Object param) {
		return template.query(sql, (rs, rowNum) -> getMember(rs), param);
	}

	private List<Member> getMembers(String sql) {
//		List<Member> results = template.query(sql, new RowMapper<Member>() {
//			@Override
//			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
//				return getMember(rs);
//			}
//		});
//		return results;

		return template.query(sql, (rs, rowNum) -> getMember(rs));
	}

	@Override
	public int deleteMember(long userId) {
		return 0;
	}
}
