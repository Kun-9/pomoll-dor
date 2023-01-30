package kun.pomondor.web;

import kun.pomondor.domain.member.Member;
import kun.pomondor.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminDataInit {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member admin = new Member("admin@naver.com", "admin", "admin");
        memberRepository.save(admin);
    }
}
