package com.dishcraft.service.impl;

import com.dishcraft.dto.FeedbackDto;
import com.dishcraft.mapper.FeedbackMapperUtil;
import com.dishcraft.model.Feedback;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repository.FeedbackRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.repository.UserRepository;
import com.dishcraft.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of FeedbackService.
 * Handles creation, retrieval, update, and deletion of feedback for recipes.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, 
                              UserRepository userRepository,
                              RecipeRepository recipeRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    /**
     * Creates new feedback.
     */
    @Override
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        User user = null;
        Recipe recipe = null;
        
        if (feedbackDto.getUserId() != null) {
            user = userRepository.findById(feedbackDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + feedbackDto.getUserId()));
        }
        
        if (feedbackDto.getRecipeId() != null) {
            recipe = recipeRepository.findById(feedbackDto.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + feedbackDto.getRecipeId()));
        }
        
        Feedback feedback = FeedbackMapperUtil.toEntity(feedbackDto, user, recipe);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return FeedbackMapperUtil.toDto(savedFeedback);
    }

    /**
     * Retrieves feedback by ID.
     */
    @Override
    public FeedbackDto getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        return FeedbackMapperUtil.toDto(feedback);
    }

    /**
     * Retrieves all feedback for a given recipe.
     */
    @Override
    public List<FeedbackDto> getFeedbackByRecipeId(Long recipeId) {
        List<Feedback> feedbackList = feedbackRepository.findByRecipeId(recipeId);
        return FeedbackMapperUtil.toDtoList(feedbackList);
    }

    /**
     * Updates feedback.
     */
    @Override
    public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) {
        Feedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        User user = null;
        Recipe recipe = null;
        
        if (feedbackDto.getUserId() != null) {
            user = userRepository.findById(feedbackDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + feedbackDto.getUserId()));
        }
        
        if (feedbackDto.getRecipeId() != null) {
            recipe = recipeRepository.findById(feedbackDto.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + feedbackDto.getRecipeId()));
        }

        FeedbackMapperUtil.updateEntityFromDto(feedbackDto, existingFeedback, user, recipe);
        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
        return FeedbackMapperUtil.toDto(updatedFeedback);
    }

    /**
     * Deletes feedback.
     */
    @Override
    public void deleteFeedback(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        feedbackRepository.delete(feedback);
    }
}
