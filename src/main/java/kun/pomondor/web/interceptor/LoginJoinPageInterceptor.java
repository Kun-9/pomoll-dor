package kun.pomondor.web.interceptor;

import kun.pomondor.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginJoinPageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            log.info("[LoginJoinInterceptor] 이미 로그인된 사용자");
            response.sendRedirect("/home");
            return false;
        }

        return true;
    }
}
