package com.dishcraft.service.impl;

import com.dishcraft.dto.FavoriteDto;
import com.dishcraft.dto.RecipeDto;
import com.dishcraft.mapper.FavoriteMapperUtil;
import com.dishcraft.mapper.RecipeMapperUtil;
import com.dishcraft.model.Favorite;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repository.FavoriteRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.service.CurrentUserService;
import com.dishcraft.service.FavoriteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the FavoriteService interface.
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;
    private final CurrentUserService currentUserService;

    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, 
                              RecipeRepository recipeRepository, 
                              CurrentUserService currentUserService) {
        this.favoriteRepository = favoriteRepository;
        this.recipeRepository = recipeRepository;
        this.currentUserService = currentUserService;
    }

    @Override
    @Transactional
    public FavoriteDto addFavorite(Long recipeId) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        // Check if already favorited
        if (favoriteRepository.existsByUserAndRecipe(currentUser, recipe)) {
            throw new RuntimeException("Recipe already in favorites");
        }

        Favorite favorite = FavoriteMapperUtil.createEntity(currentUser, recipe);
        favorite = favoriteRepository.save(favorite);
        return FavoriteMapperUtil.toDto(favorite);
    }

    @Override
    @Transactional
    public void removeFavorite(Long recipeId) {
        User currentUser = currentUserService.getCurrentUser();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));

        favoriteRepository.deleteByUserAndRecipe(currentUser, recipe);
    }

    @Override
    public List<FavoriteDto> getCurrentUserFavorites() {
        User currentUser = currentUserService.getCurrentUser();
        List<Favorite> favorites = favoriteRepository.findByUser(currentUser);
        return FavoriteMapperUtil.toDtoList(favorites);
    }

    @Override
    public boolean isFavorite(Long recipeId) {
        try {
            User currentUser = currentUserService.getCurrentUser();
            Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
            if (recipe == null) return false;
            
            return favoriteRepository.existsByUserAndRecipe(currentUser, recipe);
        } catch (Exception e) {
            // If there's any error (like no logged-in user), return false
            return false;
        }
    }

    @Override
    public List<FavoriteDto> getUserFavorites(Long userId) {
        // This would typically need authorization checks but that's omitted for brevity
        // In a real app, ensure users can only access other users' favorites if permitted
        User currentUser = currentUserService.getCurrentUser();
        
        // Simple authorization check - only allow if requesting own favorites
        if (!currentUser.getId().equals(userId)) {
            throw new RuntimeException("Not authorized to view other users' favorites");
        }
        
        List<Favorite> favorites = favoriteRepository.findByUser(currentUser);
        return FavoriteMapperUtil.toDtoList(favorites);
    }

    @Override
    public List<RecipeDto> getFavoriteRecipes() {
        User currentUser = currentUserService.getCurrentUser();
        
        List<Favorite> favorites = favoriteRepository.findByUser(currentUser);
        
        if (favorites.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Recipe> recipes = favorites.stream()
                .map(Favorite::getRecipe)
                .collect(Collectors.toList());
        
        return RecipeMapperUtil.toDtoList(recipes);
    }
}