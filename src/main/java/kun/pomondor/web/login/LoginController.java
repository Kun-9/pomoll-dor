package kun.pomondor.web.login;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class LoginController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login-form";
    }


    @PostMapping("/login")
    public String loginMember(
            @Valid @ModelAttribute LoginForm loginForm,
            BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "login-form";
        }

        Member loginMember = memberRepository.login(loginForm.getEmail(), loginForm.getPassword());
        if (loginMember == null) {
            log.info("로그인 실패");
            return "index";
        }

        log.info("로그인 성공 email = {}", loginForm.getEmail());

        request.getSession(true).setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());

        return "redirect:/";
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(name -> log.info("session expire : name = {}, value = {}", name, session.getAttribute(name)) );
        session.invalidate();

        model.addAttribute("member", new Member());
        return "redirect:/home";
    }
}
