package kun.pomondor.web.controller.group;

import kun.pomondor.domain.util.MyFileUploadUtil;
import kun.pomondor.repository.etc.food.post.FoodPostForCreate;
import kun.pomondor.repository.etc.food.post.GroupMember;
import kun.pomondor.repository.group.Group;
import kun.pomondor.repository.member.Member;
import kun.pomondor.repository.member.MemberRepository1;
import kun.pomondor.service.group.GroupService;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {

    private final MemberService memberService;
    private final GroupService groupService;
    private final MyFileUploadUtil myFileUploadUtil;

    @GetMapping("/index")
    public String groupPageIndex(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginId,
            Model model) {
        Member member = memberService.findById(loginId);
        List<Group> groups = groupService.joinedGroup(loginId);

        model.addAttribute("member", member);
        model.addAttribute("groups", groups);

        return "group/group-page";
    }

    @GetMapping("/post")
    public String createGroupForm(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginId,
            Model model) {
        Member member = memberService.findById(loginId);
        model.addAttribute("member", member);
        return "group/create-group";
    }

    @PostMapping("/post")
    public String createGroup(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) Long loginId,
            HttpServletRequest request, Model model) {

        Member member = memberService.findById(loginId);
        model.addAttribute("member", member);



        MultipartRequest multipartRequest = (MultipartRequest) request;

        // form의 컨텐츠 값 할당
        String groupName = request.getParameter("name");
        String info = request.getParameter("info");
        boolean joinStatus = Boolean.parseBoolean(request.getParameter("joinStatus"));

        Group group = new Group(loginId, groupName, info, joinStatus);

        log.info("그룹 생성 : {}" ,group);

//        postId = foodPostService.createPost(foodPost);
        Long groupId = groupService.createGroup(group);

        // postId.확장자로 s3에 저장
        MultipartFile[] files = new MultipartFile[1];

        String ext = myFileUploadUtil.validateImg(multipartRequest, files);
        if (ext == null) {
            // 사진파일이 없을 때 경로값 null
            groupService.setGroupPicture(groupId, null);
            return "redirect:/group/" + groupId;
        }
        String saveFileName = UUID.randomUUID() + "." + ext;

        try {
            // s3에 저장 후 이미지 경로 리턴
            String path = myFileUploadUtil.saveRestaurantPostImgToS3(files, saveFileName);

            // 이미지 경로를 db board table의 picture col에 할당
            groupService.setGroupPicture(groupId, path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/group/" + groupId;
    }

    @GetMapping("search")
    public String searchGroup() {

        return "/group/group-search";
    }

    @GetMapping("/{groupId}")
    public String groupPageView(
            @PathVariable Long groupId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER) Long loginId,
            Model model) {

        Member member = memberService.findById(loginId);
        Group group = groupService.findGroupById(groupId);
        List<GroupMember> members = groupService.findGroupMembers(groupId);
        int cnt = members.size();

        model.addAttribute("member", member);
        model.addAttribute("group", group);
        model.addAttribute("cnt", cnt);
        model.addAttribute("members", members);

        return "/group/group-detail";
    }
}
