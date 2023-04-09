package kun.pomondor.service.member;

import kun.pomondor.repository.member.Member;

import java.util.List;

public interface MemberService {
	Member join(Member member);

	Member findById(Long id);

	List<Member> findAll();

	Member findByEmail(String email);

	Member login(String email, String password);
}
