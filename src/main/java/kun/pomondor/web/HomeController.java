package kun.pomondor.web;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String index(@SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                        @SessionAttribute(value = SessionConst.LOGIN_LEVEL, required = false) Integer loginLevel,
                        Model model) {

        if (memberId == null) {
            model.addAttribute("member", new Member());
            model.addAttribute("loginInfo", SessionConst.NOT_LOGIN);
            return "index";
        }

        Member loginMember = memberRepository.findById(memberId);

        model.addAttribute("member", loginMember);
        model.addAttribute("loginInfo", loginLevel);
        return "index";
    }
}
