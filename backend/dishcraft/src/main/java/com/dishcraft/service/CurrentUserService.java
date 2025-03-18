package com.dishcraft.service;

import com.dishcraft.model.User;

/**
 * Service interface for retrieving the currently authenticated user's details.
 */
public interface CurrentUserService {

    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return the authenticated User entity.
     * @throws IllegalStateException if no user is currently authenticated.
     */
    User getCurrentUser();
}
