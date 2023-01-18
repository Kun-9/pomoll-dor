package kun.pomondor.web.statistics;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String statisticsForm(Model model, HttpServletRequest request) {

        Arrays.stream(request.getCookies()).forEach(c -> log.info("cookie ={}",c.getValue()));
        Cookie userIdCookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("userId"))
                .findFirst().orElse(null);




        if (userIdCookie != null) {

            String userId = userIdCookie.getValue();
            Member member = memberRepository.findById(Long.parseLong(userId));
            model.addAttribute("member", member);

            return "user-time-statistics";
        } else {
            return "redirect:/basic/home";
        }

    }

}
