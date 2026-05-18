package demo.project.com.controller;


import demo.project.com.entity.User;
import demo.project.com.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fauth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/regist")
    public String register (@RequestBody User user){
        return authService.register(user);
    }

    @RequestMapping("/login")
    public String login (@RequestBody User user){
        return authService.login(user);
    }
}
