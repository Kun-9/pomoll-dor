package kun.pomondor.service.time;

import kun.pomondor.repository.time.Time;
import kun.pomondor.repository.time.TimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

	private final TimeRepository timeRepository;

	@Override
	public void saveUserTime(long memberId, Time time) {
		timeRepository.saveTime(memberId, time);
	}

	@Override
	public List<Time> findAllTimes(long memberId) {
		return timeRepository.getAccumTimeById(memberId);
	}

	@Override
	public List<Time> findTimesByDate() {

		return null;
	}
}
