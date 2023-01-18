package kun.pomondor.web.login;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class LoginController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginMember(@ModelAttribute Member member, Model model, HttpServletRequest request, HttpServletResponse response) {

//        HttpSession session = request.getSession();

        String email = member.getEmail();
        String password = member.getPassword();

        Member loginMember = memberRepository.login(email, password);
        if (loginMember != null ) {
            log.info("로그인 성공");
            Cookie cookie = new Cookie("userId", String.valueOf(member.getId()));
            response.addCookie(cookie);

        } else {
            log.info("로그인 실패");
        }

        return "redirect:/home";
    }
}
