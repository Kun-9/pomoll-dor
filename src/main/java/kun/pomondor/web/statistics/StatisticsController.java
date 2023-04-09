package kun.pomondor.web.statistics;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository1;
import kun.pomondor.repository.time.Time;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.service.time.TimeService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final MemberService memberService;
    private final TimeService timeService;

    @GetMapping("/info")
    public String statisticsForm(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            Model model, HttpServletRequest request) {
        if (memberId == null) {
            return "redirect:/home";
        }

        LocalDate currentDate = LocalDate.now();
        Member loginMember = memberService.findById(memberId);

//        List<Time> timesByToday = timeService.findTimesByDate(memberId, LocalDate.now());

        int todayTime = timeService.findAccumTimeByDate(memberId, currentDate);

        int hour = todayTime / 60 / 60;
        int minute = (todayTime / 60) % 60;
        int second = todayTime % 60;

        // 현재 날짜

        // 현재 날짜가 속한 주의 일요일
        LocalDate sundayDate = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        Double[] week_time = new Double[7];

        for (int i = 0; i < 7; i++) {
            int accumTime = timeService.findAccumTimeByDate(memberId, sundayDate.plusDays(i));
            week_time[i] = Double.parseDouble(accumTime / 60 / 60 + "." + ((accumTime / 60) % 60) / 10);
        }

        String timeStr = hour + "h " + minute + "m " + second + "s";

        List<Time> timesByWeek;

        model.addAttribute("member", loginMember);

        List<Time> times = timeService.findAllTimes(memberId);

        model.addAttribute("times", times);
        model.addAttribute("timeStr", timeStr);
        model.addAttribute("week_time", week_time);
        model.addAttribute("login_info");

        return "user-time-statistics";
    }



}
