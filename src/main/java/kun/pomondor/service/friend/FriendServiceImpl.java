package kun.pomondor.service.friend;

import kun.pomondor.repository.friend.FriendRepository;
import kun.pomondor.repository.friend.FriendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

	private final FriendRepository friendRepository;

	@Override
	public List<FriendRequest> findFriends(Long memberId) {
		return friendRepository.findFriendListById(memberId, "수락");
	}

	@Override
	public List<FriendRequest> findReceiveRequest(Long memberId) {
		return friendRepository.findFriendListById(memberId, "대기");
	}

	@Override
	public List<FriendRequest> findRejectingRequest(Long memberId) {
		return friendRepository.findFriendListById(memberId, "거절");
	}

	@Override
	public List<FriendRequest> findSentRequestById(Long memberId) {
		return friendRepository.findSentRequestById(memberId);
	}

	@Override
	public int sendFriendRequest(Long memberId, Long targetId) {
		return friendRepository.sendFriendRequest(memberId, targetId);
	}

	@Override
	public int acceptFriendRequest(Long memberId, Long targetId) {
		return friendRepository.acceptFriendRequest(memberId, targetId);
	}

	@Override
	public int rejectFriendRequest(Long memberId, Long targetId) {
		return friendRepository.rejectFriendRequest(memberId, targetId);
	}

	@Override
	public int deleteFriend(Long memberId, Long targetId) {
		return friendRepository.deleteFriend(memberId, targetId);
	}

	@Override
	public String getStatus(Long memberId, Long targetId) {
		return friendRepository.getStatus(memberId, targetId);
	}
}
