package kun.pomondor.web.controller.statistics;

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

import static kun.pomondor.web.controller.statistics.StatisticsUtils.*;

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
        Member loginMember = memberService.findById(memberId);

        LocalDate currentDate = LocalDate.now();

        // 오늘 누적 시간
        int todayTime = timeService.findAccumTimeByDate(memberId, currentDate);

        // 초 -> 시 분 초 문자열
        String timeStr = getTimeStr(todayTime);

        // 현재 날짜가 속한 주의 일요일
        LocalDate sundayDate = getSunday(currentDate);

        Double[] week_time = getWeekAccumTime(sundayDate, timeService, memberId);

        String[] week_time_str = getWeekTimeStr(timeService, memberId, sundayDate);


        model.addAttribute("member", loginMember);

        List<Time> times = timeService.findAllTimes(memberId);

        // 해당 아이디의 모든 타임 객체
        model.addAttribute("times", times);
        // 오늘 누적 시간 문자열
        model.addAttribute("timeStr", timeStr);
        // 최근 일주일의 요일별 시간
        model.addAttribute("week_time", week_time);
        // 최근 일주일의 요일별 시간 문자열
        model.addAttribute("week_time_str", week_time_str);

        return "user-time-statistics";
    }


}
