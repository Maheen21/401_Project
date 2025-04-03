package com.dishcraft.mapper;

import com.dishcraft.dto.FavoriteDto;
import com.dishcraft.model.Favorite;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping between Favorite entities and DTOs.
 */
public class FavoriteMapperUtil {

    /**
     * Convert a Favorite entity to a DTO.
     *
     * @param favorite the Favorite entity to convert
     * @return the resulting FavoriteDto
     */
    public static FavoriteDto toDto(Favorite favorite) {
        if (favorite == null) return null;
        
        FavoriteDto dto = new FavoriteDto();
        dto.setId(favorite.getId());
        dto.setUserId(favorite.getUser().getId());
        dto.setRecipeId(favorite.getRecipe().getId());
        dto.setRecipeName(favorite.getRecipe().getName());
        dto.setRecipeImageUrl(favorite.getRecipe().getImageUrl());
        dto.setCreatedAt(favorite.getCreatedAt());
        
        return dto;
    }

    /**
     * Convert a list of Favorite entities to a list of DTOs.
     *
     * @param favorites the list of Favorite entities to convert
     * @return the resulting list of FavoriteDtos
     */
    public static List<FavoriteDto> toDtoList(List<Favorite> favorites) {
        if (favorites == null) return null;
        return favorites.stream()
                .map(FavoriteMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Create a new Favorite entity from user and recipe.
     *
     * @param user the User entity
     * @param recipe the Recipe entity
     * @return a new Favorite entity
     */
    public static Favorite createEntity(User user, Recipe recipe) {
        if (user == null || recipe == null) return null;
        
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setRecipe(recipe);
        
        return favorite;
    }
}