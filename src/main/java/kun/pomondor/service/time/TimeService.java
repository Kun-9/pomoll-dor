package kun.pomondor.service.time;

import kun.pomondor.repository.time.Time;

import java.time.LocalDate;
import java.util.List;

public interface TimeService {
	void saveTime(long memberId, Time time);

	List<Time> findAllTimes(long memberId);

	List<Time> findTimesByDate(Long memberId, LocalDate date);

	int findAccumTimeByDate(Long memberId, LocalDate date);

}
