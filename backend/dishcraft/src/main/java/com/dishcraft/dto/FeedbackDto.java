package com.dishcraft.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Feedback DTO
 */
@Data
public class FeedbackDto {
    private Long id;
    private Long recipeId;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;
}
