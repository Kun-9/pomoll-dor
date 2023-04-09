package kun.pomondor.web.group;

import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository1;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final MemberService memberService;

    @GetMapping("/index")
    public String groupPageIndex(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            Model model) {
        Member member = memberService.findById(memberId);
        model.addAttribute("member", member);
        return "group/group-page";
    }
}
