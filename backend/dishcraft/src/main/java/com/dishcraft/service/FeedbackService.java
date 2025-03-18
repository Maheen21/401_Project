package com.dishcraft.service;

import com.dishcraft.dto.FeedbackDto;
import java.util.List;

/**
 * FeedbackService defines the business logic for managing feedback on recipes.
 */
public interface FeedbackService {

    /**
     * Create new feedback for a recipe.
     *
     * @param feedbackDto the feedback details to be created
     * @return the created FeedbackDto
     */
    FeedbackDto createFeedback(FeedbackDto feedbackDto);

    /**
     * Retrieve feedback by its ID.
     *
     * @param id the unique identifier of the feedback
     * @return the FeedbackDto for the given ID
     */
    FeedbackDto getFeedbackById(Long id);

    /**
     * Retrieve all feedback for a specific recipe.
     *
     * @param recipeId the ID of the recipe
     * @return a list of FeedbackDto associated with the recipe
     */
    List<FeedbackDto> getFeedbackByRecipeId(Long recipeId);

    /**
     * Update existing feedback.
     *
     * @param id the unique identifier of the feedback to update
     * @param feedbackDto the feedback details to update
     * @return the updated FeedbackDto
     */
    FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto);

    /**
     * Delete a feedback by its ID.
     *
     * @param id the unique identifier of the feedback to delete
     */
    void deleteFeedback(Long id);
}
