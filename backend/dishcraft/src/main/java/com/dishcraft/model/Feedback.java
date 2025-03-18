package com.dishcraft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The Feedback model stores user reviews and ratings for recipes.
 * It associates a user with a recipe along with a numeric rating, an optional comment, and a submission timestamp.
 */
@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    /**
     * Unique identifier for the feedback entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The rating provided by the user.
     * Expected to be within a defined range (e.g., 1 to 5).
     */
    @Column(nullable = false)
    private int rating;

    /**
     * The comment or review text provided by the user.
     */
    @Column(length = 1000)
    private String comment;

    /**
     * The timestamp indicating when the feedback was submitted.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Reference to the user who provided the feedback.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Reference to the recipe for which the feedback is given.
     */
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
}
