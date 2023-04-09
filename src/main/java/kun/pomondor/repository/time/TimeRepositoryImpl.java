package kun.pomondor.repository.time;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "INSERT INTO time (time_id, member_key, accum_time, start_date, end_date) " +
				"VALUES (time_id_seq.nextval, ?, ?, ?, sysdate) ";

		template.update(sql,
				memberId,
				time.getTime(),
				time.getStartDate());

	}

	@Override
	public List<Time> getAccumTimeById(Long memberId) {
		String sql = "SELECT * \n" +
				"FROM time \n" +
				"WHERE member_key = ? " +
				"ORDER BY time_id";

		return getTimes(sql, memberId);
	}

	private static Time getTime(ResultSet rs) throws SQLException {
		return new Time(
				rs.getLong("time_id"),
				rs.getTimestamp("start_date").toLocalDateTime(),
				rs.getTimestamp("end_date").toLocalDateTime(),
				rs.getInt("accum_time")
		);
	}

	private List<Time> getTimes(String sql, Object param) {
		return template.query(sql, (rs, rowNum) -> getTime(rs), param);
	}
}
