package com.dishcraft.config;

import com.dishcraft.model.User;
import com.dishcraft.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class SetupDefaultAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SetupDefaultAdmin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminUser = userRepository.findByUsername("admin");
        if (adminUser.isEmpty()) {
            User rootUser = new User();
            rootUser.setUsername("admin");
            rootUser.setEmail("admin@example.com");
            rootUser.setPassword(passwordEncoder.encode("admin123"));
            rootUser.setRole("root");
            userRepository.save(rootUser);
            System.out.println("Default root user created.");
        } else {
            System.out.println("Root user already exists.");
        }
    }
}