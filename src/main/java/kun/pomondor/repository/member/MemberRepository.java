package kun.pomondor.repository.member;

import java.util.List;

public interface MemberRepository {
	Member join(Member member);

	Member findById(Long id);

	List<Member> findAll();

	Member findByEmail(String email);

	/**
	 * @param keyword 검색할 문자 입력
	 * @return 하나라도 일치시 출력 (대소문자 구분 X)
	 */
	List<Member> findByUsername(String keyword);

	int deleteMember(long userId);

	int getRenameCnt(long userId);

	boolean validUsernameExist(String username);

	void subtractRenameCnt(long userId);

	int changeName(long userId, String username);

	void setProfileImg(long userId, String path);

	void deleteProfileImg(long userId);
}


