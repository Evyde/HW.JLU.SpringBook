package ng.hanfe.springbook.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginView {
    @GetMapping
    public String login() {
        return "login.html";
    }
}
