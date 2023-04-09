package kun.pomondor.repository.time;

import java.util.List;

public interface TimeRepository {
	void saveTime(Long memberId, Time time);

	List<Time> getAccumTimeById(Long memberId);

}
