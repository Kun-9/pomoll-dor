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

@Slf4j
@Controller
@RequestMapping("/basic/statistics")
public class StatisticsController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/{userId}/info")
    public String statisticsForm(@PathVariable long userId, Model model) {
        Member member = memberRepository.findById(userId);
        model.addAttribute("member", member);

        return "user-time-statistics";
    }

}
