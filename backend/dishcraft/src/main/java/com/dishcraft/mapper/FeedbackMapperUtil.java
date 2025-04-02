package com.dishcraft.mapper;

import com.dishcraft.dto.FeedbackDto;
import com.dishcraft.model.Feedback;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackMapperUtil {

    public static FeedbackDto toDto(Feedback entity) {
        if (entity == null) return null;

        FeedbackDto dto = new FeedbackDto();
        dto.setId(entity.getId());
        dto.setRating(entity.getRating());
        dto.setComment(entity.getComment());
        dto.setTimestamp(entity.getTimestamp());
        
        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }
        
        if (entity.getRecipe() != null) {
            dto.setRecipeId(entity.getRecipe().getId());
        }
        
        return dto;
    }

    public static List<FeedbackDto> toDtoList(List<Feedback> entities) {
        if (entities == null) return new ArrayList<>();
        
        return entities.stream()
                .map(FeedbackMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    public static Feedback toEntity(FeedbackDto dto, User user, Recipe recipe) {
        if (dto == null) return null;

        Feedback entity = new Feedback();
        entity.setId(dto.getId());
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
        entity.setTimestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now());
        entity.setUser(user);
        entity.setRecipe(recipe);
        
        return entity;
    }

    public static void updateEntityFromDto(FeedbackDto dto, Feedback entity, User user, Recipe recipe) {
        if (dto == null || entity == null) return;

        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
        
        if (dto.getTimestamp() != null) {
            entity.setTimestamp(dto.getTimestamp());
        }
        
        if (user != null) {
            entity.setUser(user);
        }
        
        if (recipe != null) {
            entity.setRecipe(recipe);
        }
    }
}