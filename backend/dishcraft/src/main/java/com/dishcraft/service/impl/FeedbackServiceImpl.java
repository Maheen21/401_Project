package com.dishcraft.service.impl;

import com.dishcraft.dto.FeedbackDto;
import com.dishcraft.model.Feedback;
import com.dishcraft.repository.FeedbackRepository;
import com.dishcraft.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of FeedbackService.
 * Handles creation, retrieval, update, and deletion of feedback for recipes.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Creates new feedback.
     */
    @Override
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = modelMapper.map(feedbackDto, Feedback.class);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return modelMapper.map(savedFeedback, FeedbackDto.class);
    }

    /**
     * Retrieves feedback by ID.
     */
    @Override
    public FeedbackDto getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        return modelMapper.map(feedback, FeedbackDto.class);
    }

    /**
     * Retrieves all feedback for a given recipe.
     */
    @Override
    public List<FeedbackDto> getFeedbackByRecipeId(Long recipeId) {
        List<Feedback> feedbackList = feedbackRepository.findByRecipeId(recipeId);
        return feedbackList.stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Updates feedback.
     */
    @Override
    public FeedbackDto updateFeedback(Long id, FeedbackDto feedbackDto) {
        Feedback existingFeedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        existingFeedback.setRating(feedbackDto.getRating());
        existingFeedback.setComment(feedbackDto.getComment());
        // Optionally update other fields such as timestamp if needed

        Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
        return modelMapper.map(updatedFeedback, FeedbackDto.class);
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
