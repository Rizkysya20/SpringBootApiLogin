package demo.project.com.service;

import demo.project.com.entity.User;
import demo.project.com.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String register(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            return "Email sudah terdaftar";
        }

        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);

        userRepository.save(user);

        return "Register berhasil";
    }

    public String login(User user){

        var existing = userRepository.findByEmail(user.getEmail());

        if(existing.isEmpty()){
            return "User tidak terbaca";
        }

        if(BCrypt.checkpw(user.getPassword(), existing.get().getPassword())){
            return "Login sukses";
        }

        return "Password salah mas";
    }
}

