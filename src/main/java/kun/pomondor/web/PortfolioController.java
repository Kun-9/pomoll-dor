package kun.pomondor.web;

import kun.pomondor.repository.member.Member;
import kun.pomondor.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PortfolioController {

    @GetMapping("/portfolio")
    public String portfolio() {
        return "portfolio/portfolio";
    }

    @GetMapping("/portfolio/sauce-printer")
    public String portfolioSaucePrinter() {
        return "portfolio/projects/sauce_printer";
    }

}
