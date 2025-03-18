package com.dishcraft.repository;

import com.dishcraft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/*
 * UserRepository
 *  - Manages data access for the User entity.
 *  - By extending JpaRepository, it provides methods such as save, findById, findAll, deleteById, etc.
 *  - You can also define custom queries, e.g., finding a user by username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query to find a user by username.
    Optional<User> findByUsername(String username);
}