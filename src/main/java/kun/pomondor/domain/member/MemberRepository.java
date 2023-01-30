package kun.pomondor.domain.member;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemberRepository {

    private static long sequence = 0L;
    private static Map<Long, Member> store = new HashMap<>();

    public Member save(Member member) {
        member.setId(++sequence);
        long id = member.getId();
        store.put(id, member);
        return member;
    }
    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
    public Member findByEmail(String email) {
        Iterator<Member> iterator = findAll().iterator();
        if (iterator.hasNext()) {
            Member member = iterator.next();
            String extractedEmail = member.getEmail();
            if (extractedEmail.equals(email)) return member;
        }
        return null;
    }

    public Member login(String email, String password) {
        Member memberByEmail = findByEmail(email);
        if (memberByEmail != null && memberByEmail.getPassword().equals(password)) {
            return memberByEmail;
        }
        return null;
    }

    public int saveUserTime(long userId, int time) {
        Member member = findById(userId);
        int accumTime = member.getAccumTime();
        accumTime += time;
        member.setAccumTime(accumTime);

        return accumTime;
    }

}
