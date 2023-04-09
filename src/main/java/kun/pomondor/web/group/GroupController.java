package kun.pomondor.web.group;

import kun.pomondor.repository.member.MemberRepository1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final MemberRepository1 memberRepository1;

    @GetMapping("/index")
    public String groupPageIndex() {
        return "group/group-page";
    }
}
