package com.dishcraft.repository;

import com.dishcraft.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FeedbackRepository are responsible for accessing and managing Feedback entities.
 * besides CRUD operations provided by JpaRepository, custom queries can be defined to
 * retrieve feedbacks by recipeId or userId.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    /**
     * get all feedbacks for a specific recipe
     *
     * @param recipeId the id of the recipe
     * @return the list of feedbacks for the recipe
     */
    List<Feedback> findByRecipeId(Long recipeId);

    /**
     * get all feedbacks for a specific user
     *
     * @param userId the id of the user
     * @return the list of feedbacks for the user
     */
    List<Feedback> findByUserId(Long userId);
}
