package kun.pomondor.web.controller.statistics;

import kun.pomondor.service.time.TimeService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class StatisticsUtils {
	public static LocalDate getSunday(LocalDate currentDate) {
		return currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
	}

	public static String[] getWeekTimeStr(TimeService timeService, Long memberId, LocalDate sundayDate) {
		String[] week_time_str = new String[7];
		for (int i = 0; i < 7; i++) {
			int accumTime = timeService.findAccumTimeByDate(memberId, sundayDate.plusDays(i));
			int hour = accumTime / 60 / 60;
			int minute = (accumTime / 60) % 60;
			String str;
			if (accumTime / 60 / 60 > 0) {
				// 시간이 1 이상인 경우
				str = hour + "h " + minute + "m";
			} else {
				str = minute + "m";
			}
			week_time_str[i] = str;
		}
		return week_time_str;
	}

	public static String getTimeStr(int todayTime) {
		int hour = todayTime / 60 / 60;
		int minute = (todayTime / 60) % 60;
		int second = todayTime % 60;
		return hour + "h " + minute + "m " + second + "s";
	}

	public static Double[] getWeekAccumTime(LocalDate sundayDate, TimeService timeService, Long memberId) {
		Double[] week_time = new Double[7];

		for (int i = 0; i < 7; i++) {
			int accumTime = timeService.findAccumTimeByDate(memberId, sundayDate.plusDays(i));
			week_time[i] = Double.parseDouble(accumTime / 60 / 60 + "." + ((accumTime / 60) % 60));
		}

		return week_time;
	}
}
