package kun.pomondor.repository.time;

import java.time.LocalDate;
import java.util.List;

public interface TimeRepository {
	void saveTime(Long memberId, Time time);

	List<Time> findAllTimes(Long memberId);

	List<Time> findTimesByDate(Long memberId, LocalDate date);

	int findAccumTimeByDate(Long memberId, LocalDate date);
}
