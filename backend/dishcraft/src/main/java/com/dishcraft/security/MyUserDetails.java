package com.dishcraft.security;

import com.dishcraft.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Add the user's role as a GrantedAuthority with ROLE_ prefix
        // Spring Security expects roles to have this prefix for hasRole() function
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        
        // You can add additional authorities based on specific business rules if needed
        
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Custom method to retrieve the role from the wrapped User entity.
    // Updated to return Role enum instead of String
    public com.dishcraft.model.Role getRole() {
        return user.getRole();
    }
}