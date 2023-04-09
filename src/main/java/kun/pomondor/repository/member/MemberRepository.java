package kun.pomondor.repository.member;

import java.util.List;

public interface MemberRepository {
	Member join(Member member);

	Member findById(Long id);

	List<Member> findAll();

	Member findByEmail(String email);

	int deleteMember(long userId);
}


