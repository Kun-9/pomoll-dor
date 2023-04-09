package kun.pomondor.repository.time;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
public class TimeRepositoryImpl implements TimeRepository {

	private final JdbcTemplate template;

	public TimeRepositoryImpl(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveTime(Long memberId, Time time) {
		String sql = "INSERT INTO time (ID, MEMBER_ID, SUBSTANTIAL_TIME, START_DATE, END_DATE) " +
				"VALUES (time_id_seq.nextval, ?, ?, ?, sysdate) ";

		template.update(sql,
				memberId,
				time.getTime(),
				time.getStartDate());

	}

	@Override
	public List<Time> findAllTimes(Long memberId) {
		String sql = "SELECT * \n" +
				"FROM time \n" +
				"WHERE member_id = ? " +
				"ORDER BY id";

		return getTimes(sql, memberId);
	}

	@Override
	public List<Time> findTimesByDate(Long memberId, LocalDate date) {
		String sql = "SELECT * FROM time \n" +
				"WHERE member_id = ? \n" +
				"AND (start_date >= ? AND start_date < ?) ";

		return getTimes(sql, memberId, date, date.plusDays(1));
	}

	@Override
	public int findAccumTimeByDate(Long memberId, LocalDate date) {
		String sql = "SELECT sum(substantial_time) accumtime FROM time \n" +
				"WHERE member_id = ? \n" +
				"AND (start_date >= ? AND start_date < ?)";

		List<Integer> results = template.query(sql, (rs, rowNum) -> rs.getInt("accumtime"), memberId, date, date.plusDays(1));

		return results.isEmpty() ? 0 : results.get(0);
	}

	private static Time getTime(ResultSet rs) throws SQLException {
		return new Time(
				rs.getLong("id"),
				rs.getTimestamp("start_date").toLocalDateTime(),
				rs.getTimestamp("end_date").toLocalDateTime(),
				rs.getInt("substantial_time")
		);
	}

	private List<Time> getTimes(String sql, Object param) {
		return template.query(sql, (rs, rowNum) -> getTime(rs), param);
	}

	private List<Time> getTimes(String sql, Object param1, Object parma2, Object param3) {
		return template.query(sql, (rs, rowNum) -> getTime(rs), param1, parma2, param3);
	}
}
