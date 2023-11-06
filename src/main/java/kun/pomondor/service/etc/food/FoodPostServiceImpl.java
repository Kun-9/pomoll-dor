package kun.pomondor.service.etc.food;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.etc.food.post.FoodPostForCreate;
import kun.pomondor.repository.etc.food.post.FoodPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodPostServiceImpl implements FoodPostService {

	private final FoodPostRepository foodPostRepository;

	@Override
	public Long createPost(FoodPostForCreate foodPost) {
		return foodPostRepository.createPost(foodPost);
	}

	@Override
	public void editPost(FoodPost foodPost) {
		foodPostRepository.editPost(foodPost);
	}

	@Override
	public int deletePost(Long memberId, Long postId) {
		return foodPostRepository.deletePost(memberId, postId);
	}

	@Override
	public List<FoodPost> findPostsByMemberId(Long memberId) {
		return foodPostRepository.findPostsByMemberId(memberId);
	}

	@Override
	public FoodPost findPostsByPostId(Long postId) {
		return foodPostRepository.findPostsByPostId(postId);
	}

	@Override
	public List<FoodPost> findAllPosts() {
		return foodPostRepository.findAllPosts();
	}

	@Override
	public List<FoodPost> findPartialPosts(int startRow, int endRow) {
		return foodPostRepository.findPartialPosts(startRow, endRow);
	}

	@Override
	public void registPicture(Long postId, String path) {
		foodPostRepository.registPicture(postId, path);
	}
}
