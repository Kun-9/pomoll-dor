package kun.pomondor.service.time;

import kun.pomondor.repository.time.Time;
import kun.pomondor.repository.time.TimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TimeServiceImpl implements TimeService {

	private final TimeRepository timeRepository;

	@Override
	public void saveTime(long memberId, Time time) {
		timeRepository.saveTime(memberId, time);
	}

	@Override
	public List<Time> findAllTimes(long memberId) {
		return timeRepository.findAllTimes(memberId);
	}

	@Override
	public List<Time> findTimesByDate(Long memberId, LocalDate date) {
		return timeRepository.findTimesByDate(memberId, date);
	}

	@Override
	public int findAccumTimeByDate(Long memberId, LocalDate date) {
		return timeRepository.findAccumTimeByDate(memberId, date);
	}
}
