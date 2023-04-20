package kun.pomondor.web.controller.etc;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.etc.food.post.FoodPostForCreate;
import kun.pomondor.repository.member.Member;
import kun.pomondor.service.etc.food.FoodPostService;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("etc/food")
public class FoodReviewController {
	private final MemberService memberService;
	private final FoodPostService foodPostService;

	private final String path = "https://kun-buket-test.s3.ap-northeast-2.amazonaws.com/pomondor/post-img/";

	@GetMapping
	public String foodReviewIndex(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember,
			Model model) {
		Member member = memberService.findById(loginMember);
		List<FoodPost> posts = foodPostService.findAllPosts();
		model.addAttribute("member", member);
		model.addAttribute("posts", posts);
		return "extra/food";
	}

	@GetMapping("post")
	public String postRestaurantForm(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember,
			Model model
	) {
		Member member = memberService.findById(loginMember);
		model.addAttribute("member", member);
		model.addAttribute("foodPostForm", new FoodPost());
		model.addAttribute("formType", "post");
		return "extra/restaurant-form";
	}

	@PostMapping("post-test")
	public String postPicture(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember,
			HttpServletRequest request
	) throws IOException, ServletException {

		String restaurantName = request.getParameter("restaurantName");
		Double distance = Double.valueOf(request.getParameter("distance"));
		String content = request.getParameter("content");
		String formType = request.getParameter("formType");

		log.info("name = {}, distance = {}, content = {}, formType = {}",
				restaurantName, distance, content, formType);

		Long postId;

		if (formType.equals("post")) {
			log.info("등록");
			FoodPostForCreate foodPost = new FoodPostForCreate(restaurantName, loginMember, content, distance);
			postId = foodPostService.createPost(foodPost);

			Part part = request.getPart("picture");
			String fileName = part.getSubmittedFileName();

			// 데이터 읽고 파일 저장, 파일 이미지 이름 설정
			if (!fileName.isBlank()) {
//				String path = getAbsolutePath();
				String absolutePath = saveImg(postId, part, path);
				foodPostService.registPicture(postId, absolutePath);
			} else {
				// 사진파일이 없을 때
				foodPostService.registPicture(postId, null);
			}
		} else {
			// 수정 폼
			log.info("수정");
			postId = Long.valueOf(request.getParameter("postId"));
			FoodPost foodPost = new FoodPost();
			foodPost.setPostId(postId);
			foodPost.setRestaurantName(restaurantName);
			foodPost.setContent(content);
			foodPost.setDistance(distance);

			log.info("foodPost 객체 = {}", foodPost);

			foodPostService.editPost(foodPost);

			Part part = request.getPart("picture");
			String fileName = part.getSubmittedFileName();
			// 데이터 읽고 파일 저장, 파일 이미지 이름 설정
			if (!fileName.isBlank()) {
//				String path = getAbsolutePath();
				File directory = new File(path);
				Optional<File> first = Arrays.stream(Objects.requireNonNull(directory.listFiles((dir, name) -> name.split("\\.")[0].equals(fileName)))).findFirst();
				// 기존 파일이 있다면 삭제
				first.ifPresent(File::delete);
				// 이미지 저장
				String absolutePath = saveImg(postId, part, path);
				foodPostService.registPicture(postId, absolutePath);
			} else {
				// 사진파일이 없을 때, 기존 파일 삭제는 구현 안함
//				foodPostService.registPicture(postId, null);
			}
		}

		return "redirect:/etc/food/post/" + postId;
	}

	private static String getAbsolutePath() throws FileNotFoundException {
		return ResourceUtils.getFile("classpath:static/picture/").getAbsolutePath();
	}

	private static String saveImg(Long postId, Part part, String path) throws IOException {
		String type = part.getSubmittedFileName().split("\\.")[1];
		String fullPath = path + postId + "." + type;
		String absolutePath = "/picture/" + postId + "." + type;
		part.write(fullPath);
		log.info("파일 저장 fullPath={}", fullPath);
		return absolutePath;
	}

	@GetMapping("post/{postId}")
	public String viewPost(
			@PathVariable Long postId,
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember,
			Model model) {

		Member member = memberService.findById(loginMember);
		FoodPost post = foodPostService.findPostsByPostId(postId);

		model.addAttribute("member", member);
		model.addAttribute("post", post);

		return "extra/restaurant";
	}

	@PostMapping(value = "post/{postId}/delete")
	public String deletePost(
			@PathVariable Long postId,
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember) throws FileNotFoundException {

		int result = foodPostService.deletePost(loginMember, postId);

		String fileName = String.valueOf(postId);

		String path = getAbsolutePath();
		File directory = new File(path);

		Optional<File> first = Arrays.stream(Objects.requireNonNull(directory.listFiles((dir, name) -> name.split("\\.")[0].equals(fileName)))).findFirst();
		first.ifPresent(File::delete);


		return "redirect:/etc/food";
	}

	@GetMapping(value = "post/{postId}/edit")
	public String editPost(
			@PathVariable Long postId,
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginId,
			Model model){
		FoodPost post = foodPostService.findPostsByPostId(postId);
		Long writerId = post.getWriterId();
		if (!writerId.equals(loginId)) {
			return "redirect:/etc/food";
		}

		Member member = memberService.findById(loginId);

		model.addAttribute("member", member);
		model.addAttribute("foodPostForm", post);
		model.addAttribute("formType", "edit");

		return "extra/restaurant-form";
	}

}
