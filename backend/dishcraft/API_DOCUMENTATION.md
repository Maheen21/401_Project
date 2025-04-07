# Dishcraft API Documentation

This document provides a comprehensive overview of all API endpoints available in the Dishcraft backend application.

## Table of Contents
- [Authentication](#authentication)
- [User Management](#user-management)
- [Recipes](#recipes)
- [Ingredients](#ingredients)
- [Favorites](#favorites)
- [Dietary Restrictions](#dietary-restrictions)

## Authentication

### Register User
- **URL**: `POST /api/auth/register`
- **Description**: Register a new user account
- **Request Body**:
  ```json
  {
    "username": "string",
    "email": "string",
    "password": "string"
  }
  ```
- **Notes**: 
  - ID will be auto-generated
  - Role will always be set to "USER" for security reasons
- **Response**: Created user information (id, username, email, role) with status code 201

### Login
- **URL**: `POST /api/auth/login`
- **Description**: Authenticate a user and receive a JWT token
- **Request Body**:
  ```json
  {
    "usernameOrEmail": "string",
    "password": "string"
  }
  ```
- **Response**: JWT token and user role with status code 200
  ```json
  {
    "token": "string",
    "role": "USER"
  }
  ```

### Refresh Token
- **URL**: `POST /api/auth/refresh`
- **Description**: Get a new JWT token using a refresh token
- **Request Body**:
  ```json
  {
    "refreshToken": "string"
  }
  ```
- **Response**: New JWT token and user role with status code 200

### Get All Users
- **URL**: `GET /api/auth/users`
- **Description**: For testing purposes - retrieve all user accounts
- **Response**: List of user accounts with status code 200

## Recipes

### Create Recipe
- **URL**: `POST /api/recipes`
- **Description**: Create a new recipe
- **Request Body**: Recipe details including name, description, instruction, cookingTime, imageUrl, and recipe ingredients
- **Response**: Created recipe details with status code 201

### Get Recipe by ID
- **URL**: `GET /api/recipes/{id}`
- **Description**: Retrieve a specific recipe by its ID
- **Path Parameter**: `id` - Recipe ID
- **Response**: Recipe details with status code 200

### Get All Recipes (Paginated)
- **URL**: `GET /api/recipes`
- **Description**: Retrieve all recipes with pagination
- **Query Parameters**: Pagination parameters (page, size, sort)
- **Response**: Paginated list of recipes with status code 200

### Get All Recipes (Non-Paginated)
- **URL**: `GET /api/recipes/all`
- **Description**: Retrieve all recipes without pagination
- **Response**: List of all recipes with status code 200

### Update Recipe
- **URL**: `PUT /api/recipes/{id}`
- **Description**: Update an existing recipe
- **Path Parameter**: `id` - Recipe ID
- **Request Body**: Updated recipe details
- **Response**: Updated recipe details with status code 200

### Delete Recipe
- **URL**: `DELETE /api/recipes/{id}`
- **Description**: Delete a recipe
- **Path Parameter**: `id` - Recipe ID
- **Response**: No content with status code 204

### Search Recipes
- **URL**: `GET /api/recipes/search`
- **Description**: Search for recipes based on selected ingredients and search mode. Results are filtered using the current user's dietary restrictions.
- **Query Parameters**:
  - `ingredientIds` - List of ingredient IDs to search by
  - `mode` - Search mode: "all" (must contain all ingredients) or "any" (contain any ingredient; MAIN ingredients prioritized)
- **Response**: List of recipes matching the search criteria with status code 200

### Filter Recipes by Dietary Restrictions
- **URL**: `GET /api/recipes/filter/dietary`
- **Description**: Filter recipes by dietary restrictions
- **Query Parameter**: `dietaryRestrictionIds` - List of dietary restriction IDs to filter by
- **Response**: List of recipes that match the dietary restrictions with status code 200

## Ingredients

### Get Ingredient by ID
- **URL**: `GET /api/ingredients/{id}`
- **Description**: Retrieve ingredient details by ID
- **Path Parameter**: `id` - Ingredient ID
- **Response**: Ingredient details with status code 200

### Search Ingredients by Name
- **URL**: `GET /api/ingredients/search`
- **Description**: Search for ingredients by name (case-insensitive)
- **Query Parameter**: `name` - Search keyword for ingredient names
- **Response**: List of ingredients matching the search criteria with status code 200

### Get All Ingredients
- **URL**: `GET /api/ingredients/all`
- **Description**: Retrieve all ingredients from the database
- **Response**: List of all ingredients with status code 200

### Create Ingredient
- **URL**: `POST /api/ingredients`
- **Description**: Create a new ingredient in the database
- **Request Body**: Ingredient details
- **Response**: Created ingredient details with status code 200

### Update Ingredient
- **URL**: `PUT /api/ingredients/{id}`
- **Description**: Update the ingredient details by its ID
- **Path Parameter**: `id` - Ingredient ID
- **Request Body**: Updated ingredient details
- **Response**: Updated ingredient details with status code 200

### Delete Ingredient
- **URL**: `DELETE /api/ingredients/{id}`
- **Description**: Delete an ingredient by its ID
- **Path Parameter**: `id` - Ingredient ID
- **Response**: No content with status code 204

### Filter Ingredients by Dietary Restrictions
- **URL**: `GET /api/ingredients/filter`
- **Description**: Filter ingredients by excluding those with the specified dietary restrictions
- **Query Parameter**: `dietaryRestrictionIds` - List of dietary restriction IDs to exclude
- **Response**: List of ingredients that do NOT have any of the specified dietary restrictions with status code 200

## Favorites

### Add to Favorites
- **URL**: `POST /api/favorites`
- **Description**: Add a recipe to the current user's favorites
- **Request Body**:
  ```json
  {
    "recipeId": 123
  }
  ```
- **Authentication**: Required (Bearer token)
- **Response**: Created favorite with status code 201

### Remove from Favorites
- **URL**: `DELETE /api/favorites/{recipeId}`
- **Description**: Remove a recipe from the current user's favorites
- **Path Parameter**: `recipeId` - Recipe ID to remove
- **Authentication**: Required (Bearer token)
- **Response**: No content with status code 204

### Get Current User's Favorites
- **URL**: `GET /api/favorites`
- **Description**: Get all favorite recipes for the current user
- **Authentication**: Required (Bearer token)
- **Response**: List of favorite recipes with status code 200

### Get Favorite Recipes
- **URL**: `GET /api/favorites/recipes`
- **Description**: Get all recipes that are favorited by the current user
- **Authentication**: Required (Bearer token)
- **Response**: List of recipe DTOs with status code 200

### Check if Recipe is Favorite
- **URL**: `GET /api/favorites/check/{recipeId}`
- **Description**: Check if a recipe is in the current user's favorites
- **Path Parameter**: `recipeId` - Recipe ID to check
- **Authentication**: Required (Bearer token)
- **Response**: Boolean value indicating if the recipe is a favorite with status code 200
  ```json
  {
    "isFavorite": true
  }
  ```

## Dietary Restrictions

### Get All Dietary Restrictions
- **URL**: `GET /api/dietary-restrictions`
- **Description**: Retrieve all dietary restrictions available in the system
- **Response**: List of all dietary restrictions with status code 200

### Get Current User's Dietary Restrictions
- **URL**: `GET /api/dietary-restrictions/current-user`
- **Description**: Retrieve the dietary restrictions of the currently authenticated user
- **Authentication**: Required (Bearer token)
- **Response**: List of the current user's dietary restrictions with status code 200

### Add Dietary Restriction to Current User
- **URL**: `POST /api/dietary-restrictions/current-user/{dietaryRestrictionId}`
- **Description**: Add a dietary restriction to the currently authenticated user's profile
- **Path Parameter**: `dietaryRestrictionId` - ID of the dietary restriction to add
- **Authentication**: Required (Bearer token)
- **Response**: The added dietary restriction with status code 201
- **Error Responses**:
  - 401: Not authenticated
  - 404: Dietary restriction not found

### Remove Dietary Restriction from Current User
- **URL**: `DELETE /api/dietary-restrictions/current-user/{dietaryRestrictionId}`
- **Description**: Remove a dietary restriction from the currently authenticated user's profile
- **Path Parameter**: `dietaryRestrictionId` - ID of the dietary restriction to remove
- **Authentication**: Required (Bearer token)
- **Response**: Success status with status code 200
  ```json
  {
    "removed": true
  }
  ```
- **Error Responses**:
  - 401: Not authenticated
  - 404: Dietary restriction not found in user's profile

### Check if User Has Dietary Restriction
- **URL**: `GET /api/dietary-restrictions/has/{dietaryRestrictionId}`
- **Description**: Check if the authenticated user has a specific dietary restriction
- **Path Parameter**: `dietaryRestrictionId` - ID of the dietary restriction to check
- **Authentication**: Required (Bearer token)
- **Response**: Boolean value indicating if the user has this dietary restriction with status code 200
  ```json
  {
    "has": true
  }
  ```
- **Error Responses**:
  - 401: Not authenticated