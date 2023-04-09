package kun.pomondor.repository.time;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class TimeRepositoryImplTest {

	@Autowired
	TimeRepository timeRepository;

	@Test
	void saveTime() {
		// given
		Long memberId = 11L;

		LocalDateTime start = LocalDateTime.now().plusMinutes(-60);

		Time time = new Time(start, 60);

		// when
		timeRepository.saveTime(memberId, time);

		// then

	}

	@Test
	void getAccumTimeById() {
		List<Time> list = timeRepository.getAccumTimeById(11L);

		for (Time time : list) {
			System.out.println(time);
		}
	}
}