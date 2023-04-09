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

        Member loginMember = memberService.findById(memberId);
        model.addAttribute("member", loginMember);

        List<Time> times = timeService.findAllTimes(memberId);

        model.addAttribute("times", times);

        return "user-time-statistics";

    }

}
