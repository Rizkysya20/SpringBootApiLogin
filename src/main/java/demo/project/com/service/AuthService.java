package demo.project.com.service;

import demo.project.com.dto.ApiResponse;
import demo.project.com.entity.User;
import demo.project.com.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ApiResponse(false, "Email already registered");
        }

        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);

        userRepository.save(user);

        return new ApiResponse(true, "Register success");
    }

    public ApiResponse login(User user) {

        var existing = userRepository.findByEmail(user.getEmail());

        if (existing.isEmpty()) {
            return new ApiResponse(false, "User not found");
        }

        if (BCrypt.checkpw(user.getPassword(), existing.get().getPassword())) {
            return new ApiResponse(true, "Login success");
        }

        return new ApiResponse(false, "Wrong password");
    }
}
