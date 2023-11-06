package kun.pomondor.web;

import kun.pomondor.repository.member.Member;
import kun.pomondor.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

//    private final MemberRepository1 memberRepository1;
    private final MemberService memberService;

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


        Member loginMember = memberService.findById(memberId);

        model.addAttribute("member", loginMember);
        model.addAttribute("loginInfo", loginLevel);
        return "index";
    }

    @ResponseBody
    @PostMapping("/ex")
    public String ex(@RequestParam Object start) {
        log.info("start = {}", start);
        return "ok";
    }
}
