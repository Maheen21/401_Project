package com.dishcraft.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;
 



/*
 * Recipe class that stores the core information of a recipe
 * this model includes the recipe name, the list of ingredients, description, and image url
 * and manage the relationship between the recipe and the ingredients through the RecipeIngredient class
 */
@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    /*
     * the id of the recipe
     * this is the primary key of the recipe
     */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    /*
     * the name of the recipe
     * this is a required field
     * and not null field
     */
   @Column(nullable = false)
    private String name;

    /*
     * the simple description of the recipe
     */
    @Column(length = 500)
    private String description;

    /*
     * instruction of the recipe
     * to store the long text of the instruction
     * it will use LOB (Large Object) data type
     */
    @Lob
    private String instruction;

    /*
     * estimated time to cook the recipe
     * in minutes
     */
    @Column(name = "cooking_time", nullable = false)
    private Integer cookingTime;

    /*
     * the image url of the recipe (may be null)
     * 
     */
    @Column(name = "image_url")
    private String imageUrl;

    /*
     * the list of ingredients of the recipe
     * this is a many to many relationship
     * between the recipe and the ingredient
     * 
     * the relationship is managed by the RecipeIngredient class
     */
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipeIngredients;

      public void addIngredient(RecipeIngredient ri) {
        recipeIngredients.add(ri);
        ri.setRecipe(this);
    }
    
    public void removeIngredient(RecipeIngredient ri) {
        recipeIngredients.remove(ri);
        ri.setRecipe(null);
    }



    
}
