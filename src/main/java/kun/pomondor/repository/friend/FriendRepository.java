package kun.pomondor.repository.friend;

import java.util.List;

public interface FriendRepository {

	List<FriendRequest> findFriendListById(Long memberId, String status);

	List<FriendRequest> findSentRequestById(Long memberId);

	int sendFriendRequest(Long memberId, Long targetId);

	int acceptFriendRequest(Long memberId, Long targetId);

	int rejectFriendRequest(Long memberId, Long targetId);

	int deleteFriend(Long memberId, Long targetId);

	int cancelRequset(Long memberId, Long targetId);

	String getStatus(Long memberId, Long targetId);
}
