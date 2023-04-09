package kun.pomondor.repository.time;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class Time {
	Long timeId;
	LocalDateTime startDate;
	LocalDateTime endDate;
	int time;

	public Time(LocalDateTime startDate, int time) {
		this.startDate = startDate;
		this.time = time;
	}

	public Time(Long timeId, LocalDateTime startDate, LocalDateTime endDate, int time) {
		this.timeId = timeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.time = time;
	}

}
