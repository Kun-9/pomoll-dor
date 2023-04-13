package kun.pomondor.service.friend;

import kun.pomondor.repository.friend.FriendRequest;

import java.util.List;

public interface FriendService {
	List<FriendRequest> findFriends(Long memberId);

	List<FriendRequest> findReceiveRequest(Long memberId);

	List<FriendRequest> findRejectingRequest(Long memberId);

	List<FriendRequest> findSentRequestById(Long memberId);

	int sendFriendRequest(Long memberId, Long targetId);

	int acceptFriendRequest(Long memberId, Long targetId);

	int rejectFriendRequest(Long memberId, Long targetId);

	int deleteFriend(Long memberId, Long targetId);

	String getStatus(Long memberId, Long targetId);

	int cancelRequest(Long memberId, Long targetId);
}
