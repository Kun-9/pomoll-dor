//package kun.pomondor.web;
//
//import kun.pomondor.repository.member.Member;
//import kun.pomondor.repository.member.MemberRepository1;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//@RequiredArgsConstructor
//public class AdminDataInit {
//
//    private final MemberRepository1 memberRepository1;
//
//    @PostConstruct
//    public void init() {
//        Member admin = new Member("admin@naver.com", "admin", "admin");
//        memberRepository1.save(admin);
//    }
//}
