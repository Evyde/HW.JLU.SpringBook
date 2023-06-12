package ng.hanfe.springbook.controller;

import jakarta.validation.Valid;
import ng.hanfe.springbook.model.Role;
import ng.hanfe.springbook.model.User;
import ng.hanfe.springbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private record RegisterParam(
            String username,
            String password,
            Role role
    ) {}
    private UserRepository repository;
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    public RegisterController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Map<String, String> register(@Valid @RequestBody RegisterParam param) {
        User user = repository.findByUsername(param.username);
        if (user == null) {
            logger.info("Creating user with username [" + param.username + "] and password [" + param.password + "].");
            repository.save(new User(
                    param.username,
                    param.password,
                    param.role
            ));
            return Map.of(
                    "message", "success",
                    "redirect", "/login"
            );
        }
        return Map.of(
                "message", "error",
                "redirect", "/register"
        );
    }
}
