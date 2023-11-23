package kun.pomondor.web.controller.member;

import kun.pomondor.domain.util.MyFileUploadUtil;
import kun.pomondor.repository.etc.food.post.FoodPostForCreate;
import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository;
import kun.pomondor.service.member.MemberService;
import kun.pomondor.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/profile/change")
public class MemberInfoChangeController {

	private final MemberService memberService;
	private final MyFileUploadUtil myFileUploadUtil;

	// 식당 사진 s3 저장 경로
	final String filePath = "pomondor/profile-img";

	@GetMapping
	public String infoSettingForm(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginMember, Model model) {

		Member member = memberService.findById(loginMember);
		int renameCnt = memberService.getRenameCnt(loginMember);

		model.addAttribute("member", member);
		model.addAttribute("renameCnt", renameCnt);

		return "user/user-change-info";
	}

	@ResponseBody
	@GetMapping("username")
	public boolean validUsernameExist(@RequestParam String username) {
		boolean exist = memberService.validUsernameExist(username);
		return !exist;
	}

	@PostMapping("username")
	public String subtractRenameCnt(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginId,
			@RequestParam String username) {
		String query = "";
		int result = memberService.changeName(loginId, username);
		if (result == 1) {
			memberService.subtractRenameCnt(loginId);
			query = "?result=true";
		} else {
			query = "?result=false";
		}
		return "redirect:/member/profile/change" + query;
	}


	@PostMapping("profile-img")
	public String postProfileImg(
			@SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginId,
			HttpServletRequest request) {
		MultipartRequest multipartRequest = (MultipartRequest) request;

		// postId.확장자로 s3에 저장
		MultipartFile[] files = new MultipartFile[1];

		String ext = myFileUploadUtil.validateImg(multipartRequest, files);

		// 수정폼에 업로드 이미지가 없을 시
		if (ext == null) {
			memberService.setProfileImg(loginId, "/picture/default.png");
			return "redirect:/member/profile/change";
		}

		String saveFileName = loginId + "." + ext;

		try {
			// s3에 저장 후 이미지 경로 리턴
			String path = myFileUploadUtil.saveProfileImgToS3(files, saveFileName);

			// 이미지 경로를 db board table의 picture col에 할당
			memberService.setProfileImg(loginId, saveFileName);

			log.info("{} : 프로파일 이미지 변경", loginId);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return "redirect:/member/profile/change";
	}
}
