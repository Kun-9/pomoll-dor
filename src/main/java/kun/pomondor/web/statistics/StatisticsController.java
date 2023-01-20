package kun.pomondor.web.statistics;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final MemberRepository memberRepository;

    @GetMapping("/info")
    public String statisticsForm(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            Model model, HttpServletRequest request) {
        if (memberId == null) {
            return "redirect:/home";
        }

        Member loginMember = memberRepository.findById(memberId);
        model.addAttribute("member", loginMember);

        return "user-time-statistics";

    }

}
