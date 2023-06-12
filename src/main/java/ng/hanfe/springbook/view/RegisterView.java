package ng.hanfe.springbook.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterView {
    @GetMapping
    public String register() {
        return "register.html";
    }
}
