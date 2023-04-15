//package kun.pomondor.repository.friend;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class FriendRepositoryImplTest {
//
//	@Autowired
//	FriendRepository friendRepository;
//
//	@Test
//	void sendFriendRequest() {
//		Long sendId = 19L;
//		Long receiverId = 20L;
//
//		int i = friendRepository.sendFriendRequest(sendId, receiverId);
//
//		assertThat(i).isEqualTo(1);
//	}
//
//	@Test
//	void findFriendListById() {
//		Long memberId = 20L;
//
//		List<FriendRequest> list = friendRepository.findFriendListById(memberId, "대기");
//
//		System.out.println(list.get(0));
//	}
//
//	@Test
//	void acceptFriendRequest() {
//		Long memberId = 20L;
//		Long senderId = 19L;
//
//		int i = friendRepository.acceptFriendRequest(memberId, senderId);
//		System.out.println(i);
//	}
//
//	@Test
//	void rejectRequest() {
//		Long memberId = 20L;
//		Long senderId = 19L;
//
//		friendRepository.rejectFriendRequest(memberId, senderId);
//	}
//
//}