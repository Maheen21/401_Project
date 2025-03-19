package com.dishcraft.config;

import com.dishcraft.model.User;
import com.dishcraft.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.Random;

@Component
public class SetupDefaultAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Random random = new Random();
    
    public SetupDefaultAdmin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminUser = userRepository.findByUsername("admin");
        if (adminUser.isEmpty()) {
            User rootUser = new User();
            // Generate a random 6-digit number between 100000 and 999999.
            long generatedId = random.nextInt(900000) + 100000;
            //rootUser.setId(generatedId); // Make sure your User entity doesn't override this value with auto-generation.
            
            rootUser.setUsername("admin");
            rootUser.setEmail("admin@example.com");
            rootUser.setPassword(passwordEncoder.encode("admin123"));
            rootUser.setRole("root");
            userRepository.save(rootUser);
            System.out.println("Default root user created with id: " + generatedId);
        } else {
            System.out.println("Root user already exists.");
        }
    }
}