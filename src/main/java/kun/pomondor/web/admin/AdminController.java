package kun.pomondor.web.admin;


import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;

    @GetMapping("/index")
    public String adminPageForm(Model model) {

        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);

        return "admin-page";
    }


}

