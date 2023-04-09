package kun.pomondor.service.time;

import kun.pomondor.repository.time.Time;

import java.util.List;

public interface TimeService {
	void saveUserTime(long memberId, Time time);

	List<Time> findAllTimes(long memberId);

	List<Time> findTimesByDate();


}
