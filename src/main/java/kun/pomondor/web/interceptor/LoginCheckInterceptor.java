package kun.pomondor.web.interceptor;

import kun.pomondor.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("로그인 체크 인터셉터: {}", requestURI);
            log.info("로그인 미인증 사용자 요청");

            response.sendRedirect("/member/login?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}
