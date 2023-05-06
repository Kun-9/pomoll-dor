package kun.pomondor.service.member;

import kun.pomondor.repository.member.Member;

import java.util.List;

public interface MemberService {
	Member join(Member member);

	Member findById(Long id);

	List<Member> findAll();

	Member findByEmail(String email);

	Member login(String email, String password);

	List<Member> findByUsername(String keyword);

	int getRenameCnt(long userId);

	boolean validUsernameExist(String username);

	void subtractRenameCnt(long userId);

	int changeName(long userId, String username);

	void setProfileImg(long userId, String path);

	void deleteProfileImg(long userId);
}
