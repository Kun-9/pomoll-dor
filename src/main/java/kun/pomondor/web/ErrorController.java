package kun.pomondor.web;

import kun.pomondor.web.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/error")
@RequiredArgsConstructor
public class ErrorController {

    @GetMapping("/1")
//    @ResponseStatus(value = )
    public void errorPage(HttpServletResponse response) throws IOException {
        throw new CustomException("hello exception");
    }

    @GetMapping("/2")
//    @ResponseStatus(value = )
    public void errorPage2(HttpServletResponse response) throws IOException {
        throw new RuntimeException();
    }
}
