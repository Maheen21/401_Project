package com.dishcraft.service.impl;

import com.dishcraft.dto.FeedbackDto;
import com.dishcraft.model.Feedback;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repository.FeedbackRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    private Feedback mockFeedback;
    private FeedbackDto mockFeedbackDto;
    private User mockUser;
    private Recipe mockRecipe;
    private LocalDateTime mockTimestamp;

    @BeforeEach
    void setUp() {
        // Setup mock timestamp
        mockTimestamp = LocalDateTime.now();
        
        // Setup mock user
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
        
        // Setup mock recipe
        mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setName("Test Recipe");
        
        // Setup mock feedback
        mockFeedback = new Feedback();
        mockFeedback.setId(1L);
        mockFeedback.setRating(5);
        mockFeedback.setComment("Great recipe!");
        mockFeedback.setTimestamp(mockTimestamp);
        mockFeedback.setUser(mockUser);
        mockFeedback.setRecipe(mockRecipe);
        
        // Setup mock feedback DTO
        mockFeedbackDto = new FeedbackDto();
        mockFeedbackDto.setId(1L);
        mockFeedbackDto.setRating(5);
        mockFeedbackDto.setComment("Great recipe!");
        mockFeedbackDto.setTimestamp(mockTimestamp);
        mockFeedbackDto.setUserId(1L);
        mockFeedbackDto.setRecipeId(1L);
    }

    @Test
    void testGetFeedbackById() {
        // Given
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(mockFeedback));
        
        // When
        FeedbackDto result = feedbackService.getFeedbackById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(mockFeedback.getId(), result.getId());
        assertEquals(mockFeedback.getRating(), result.getRating());
        assertEquals(mockFeedback.getComment(), result.getComment());
        assertEquals(mockFeedback.getTimestamp(), result.getTimestamp());
        assertEquals(mockFeedback.getUser().getId(), result.getUserId());
        assertEquals(mockFeedback.getRecipe().getId(), result.getRecipeId());
    }

    @Test
    void testCreateFeedback() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(mockRecipe));
        when(feedbackRepository.save(any(Feedback.class))).thenAnswer(invocation -> {
            Feedback savedFeedback = invocation.getArgument(0);
            savedFeedback.setId(1L); // Simulate ID generation
            return savedFeedback;
        });
        
        // When
        FeedbackDto result = feedbackService.createFeedback(mockFeedbackDto);
        
        // Then
        assertNotNull(result);
        assertEquals(mockFeedbackDto.getRating(), result.getRating());
        assertEquals(mockFeedbackDto.getComment(), result.getComment());
        assertEquals(mockUser.getId(), result.getUserId());
        assertEquals(mockRecipe.getId(), result.getRecipeId());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }

    @Test
    void testGetFeedbackByRecipeId() {
        // Given
        List<Feedback> feedbacks = List.of(mockFeedback);
        when(feedbackRepository.findByRecipeId(1L)).thenReturn(feedbacks);
        
        // When
        List<FeedbackDto> results = feedbackService.getFeedbackByRecipeId(1L);
        
        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(mockFeedback.getRating(), results.get(0).getRating());
        assertEquals(mockFeedback.getComment(), results.get(0).getComment());
    }

    @Test
    void testUpdateFeedback() {
        // Given
        when(feedbackRepository.findById(1L)).thenReturn(Optional.of(mockFeedback));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(mockRecipe));
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockFeedback);
        
        // Update the feedback rating and comment
        mockFeedbackDto.setRating(4);
        mockFeedbackDto.setComment("Updated comment");
        
        // When
        FeedbackDto result = feedbackService.updateFeedback(1L, mockFeedbackDto);
        
        // Then
        assertNotNull(result);
        assertEquals(4, result.getRating());
        assertEquals("Updated comment", result.getComment());
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }
}