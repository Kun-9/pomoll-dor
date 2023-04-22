package kun.pomondor.web.controller.etc;

import kun.pomondor.repository.etc.food.post.FoodPost;
import kun.pomondor.repository.etc.food.post.FoodPostForCreate;
import kun.pomondor.repository.member.Member;
import kun.pomondor.service.etc.food.FoodPostService;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import kun.pomondor.web.controller.s3.S3Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("etc/food")
public class FoodReviewController {
	private final MemberService memberService;
	private final FoodPostService foodPostService;
	private final S3Handler s3Handler;

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

	@PostMapping("post")
	public String postToBoard(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember,
			HttpServletRequest request) {
		MultipartRequest multipartRequest = (MultipartRequest) request;
		Long postId;

		// form의 컨텐츠 값 할당
		String restaurantName = request.getParameter("restaurantName");
		Double distance = Double.valueOf(request.getParameter("distance"));
		String content = request.getParameter("content");

		log.info("등록");
		FoodPostForCreate foodPost = new FoodPostForCreate(restaurantName, loginMember, content, distance);
		postId = foodPostService.createPost(foodPost);

		// postId.확장자로 s3에 저장
		MultipartFile[] files = new MultipartFile[1];

		String ext = validateImg(multipartRequest, files);
		if (ext == null) {
			// 사진파일이 없을 때 경로값 null
			foodPostService.registPicture(postId, null);
			return "redirect:/etc/food/post/" + postId;
		}
		String saveFileName = postId + "." + ext;

		try {
			// s3에 저장 후 이미지 경로 리턴
			String path = saveImgToS3(files, saveFileName);

			// 이미지 경로를 db board table의 picture col에 할당
			foodPostService.registPicture(postId, path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "redirect:/etc/food/post/" + postId;
	}

	private String saveImgToS3(MultipartFile[] files, String saveFileName) throws Exception {
		List<String> imgPathList = s3Handler.upload(files, saveFileName);
		String path = imgPathList.get(0);
		return path;
	}

	@PostMapping("post/{postId}/edit")
	public String postPicture(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginId,
			HttpServletRequest request, @PathVariable Long postId) {
		MultipartRequest multipartRequest = (MultipartRequest) request;

		// form의 컨텐츠 값 할당
		String restaurantName = request.getParameter("restaurantName");
		Double distance = Double.valueOf(request.getParameter("distance"));
		String content = request.getParameter("content");

		FoodPost editPost = new FoodPost();
		editPost.setPostId(postId);
		editPost.setRestaurantName(restaurantName);
		editPost.setDistance(distance);
		editPost.setContent(content);
		editPost.setWriterId(loginId);

		foodPostService.editPost(editPost);

		MultipartFile[] files = new MultipartFile[1];
		// 이미지 존재여부 및 이미지 파일 검증, 타입 반환
		String ext = validateImg(multipartRequest, files);

		// 수정폼에 업로드 이미지가 없을 시 반환
		if (ext == null) {
			return "redirect:/etc/food/post/" + postId;
		}
		String saveFileName = postId + "." + ext;

		// 수정폼에 업로드 이미지가 있을 때
		// 기존 파일 삭제 메소드
		deleteImg(postId);

		try {
			String path = saveImgToS3(files, saveFileName);
			foodPostService.registPicture(postId, path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "redirect:/etc/food/post/" + postId;
	}

	// multipartFile에 파일이 포함되어 있는지 확인하고 이미지 파일인지 검증, 이미지 파일이라면 타입 리턴
	private String validateImg(MultipartRequest multipartRequest, MultipartFile[] files) {
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String s : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(s);
			files[0] = multipartFile;

			String[] contentType = multipartFile.getContentType().split("/");
			// 이미지 파일이 아니라면 리턴
			if (!contentType[0].equals("image")) {
				return null;
			}
			// 이미지 타입 리턴
			return contentType[1];
		}
		return null;
	}

	@GetMapping("post/{postId}")
	public String viewPost(
			@PathVariable Long postId,
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember,
			Model model) {

		Member member = memberService.findById(loginMember);
		FoodPost post = foodPostService.findPostsByPostId(postId);
		Long writerId = post.getWriterId();
		Member writerMember = memberService.findById(writerId);

		model.addAttribute("writerMember", writerMember);
		model.addAttribute("member", member);
		model.addAttribute("post", post);

		return "extra/restaurant";
	}

	@PostMapping(value = "post/{postId}/delete")
	public String deletePost(
			@PathVariable Long postId,
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember) throws FileNotFoundException {

		deleteImg(postId);
		int result = foodPostService.deletePost(loginMember, postId);

		return "redirect:/etc/food";
	}

	private void deleteImg(Long postId) {
		FoodPost post = foodPostService.findPostsByPostId(postId);
		String picURI = post.getPicture();

		if (picURI != null) {
			String[] split = picURI.split("\\.");
			String ext = split[split.length - 1];
			String fileName = postId + "." + ext;
			s3Handler.fileDelete(fileName);
		}
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
