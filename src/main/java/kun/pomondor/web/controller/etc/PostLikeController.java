package kun.pomondor.web.controller.etc;

import kun.pomondor.service.etc.food.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostLikeController {

	private final LikeService likeService;


}
