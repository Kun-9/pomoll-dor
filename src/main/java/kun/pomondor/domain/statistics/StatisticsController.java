package kun.pomondor.domain.statistics;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/basic/statistics")
public class StatisticsController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/info")
    public String statisticsForm(Model model, HttpServletRequest request) {
        Object sessionId = request.getSession().getAttribute("userId");
        if (sessionId != null) {
            Member member = memberRepository.findById(Long.parseLong(String.valueOf(sessionId)));
            model.addAttribute("member", member);

            return "user-time-statistics";
        } else {
            return "redirect:/basic/home";
        }

    }

}
