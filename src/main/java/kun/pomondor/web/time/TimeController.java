package kun.pomondor.web.time;

import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("time")
public class TimeController {

    private final MemberRepository memberRepository;

    @ResponseBody
    @PostMapping("/save-time")
    public String saveTime(@RequestParam int time, HttpServletRequest request, HttpServletResponse response) {
//        Object userId = session.getAttribute("userId");

        Cookie[] cookies = request.getCookies();

        Cookie userIdCookie = Arrays.stream(cookies).filter(c -> c.equals("userId")).findFirst().orElse(null);
        assert userIdCookie != null;
        String userId = userIdCookie.getValue();

        if (userId != null) {
            int accumTime = memberRepository.saveUserTime(Long.parseLong(String.valueOf(userId)), time);
            log.info("time = {}, userId = {}, accumTime = {}", time, userId, accumTime);
            return "ok";
        } else {
            return "fail";
        }
    }
}
