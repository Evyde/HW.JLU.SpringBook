package ng.hanfe.springbook.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    private UserRepository repository;

    private record LoginParam(
            String username,
            String password
    ) {
    }

    public LoginController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response, HttpSession session) throws IOException {
        session.invalidate();
        response.sendRedirect("/");
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginParam param, HttpSession session) {
        logger.info("Login request: " + param);
        User tempUser = repository.findByUsername(param.username);
        if (tempUser != null) {
            if (tempUser.getPassword().equals(param.password)) {
                String redirectUrl;
                logger.info(String.valueOf(tempUser.getRole()));
                switch (tempUser.getRole()) {
                    case USER: {
                        redirectUrl = "/";
                        break;
                    }
                    case ADMIN: {
                        redirectUrl = "/";
                        break;
                    }
                    default: redirectUrl = "/login";
                }
                logger.info(redirectUrl);

                session.setAttribute("username", tempUser.getUsername());

                return Map.of(
                        "redirect", redirectUrl,
                        "message", "Hello!"
                );
            } else {
                return Map.of(
                        "redirect", "/login",
                        "message", "User password wrong!"
                );
            }

        } else {
            return Map.of(
                    "redirect", "/login",
                    "message", "User does not exists!"
            );
        }
    }
}
