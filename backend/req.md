# Dishcraft Backend Requirements Document

## 1. Overview

Dishcraft is a backend system that enables users to register and authenticate via JWT, create and manage recipes, and search for recipes based on available ingredients. The system is designed to support a flexible, scalable architecture with environment-specific configurations (e.g., using an embedded H2 database in development and MySQL in production).

---

## 2. Core Functional Requirements

### 2.1 JWT-Based User Authentication

**Objective:**  
Implement a stateless authentication mechanism using JSON Web Tokens (JWT).

**Endpoints and Features:**

- **User Registration**
  - **Endpoint:** `POST /api/auth/register`
  - **Input:** User details such as username, email, password, and optional profile information (e.g., dietary restrictions).
  - **Output:** Success message or the created user’s information.

- **User Login**
  - **Endpoint:** `POST /api/auth/login`
  - **Input:** User credentials (username or email and password).
  - **Output:** A JWT access token (optionally accompanied by a refresh token).

- **Token Refresh**
  - **Endpoint:** `POST /api/auth/refresh`
  - **Input:** A valid refresh token.
  - **Output:** A new JWT access token.

- **Protected Endpoints**
  - Require the HTTP Authorization header formatted as `Bearer {token}` for endpoints that modify data (e.g., recipe creation, update, or deletion).

**Non-Functional Considerations:**
- Use a strong secret key for signing tokens.
- Configure appropriate token expiration times.
- Secure password storage using hashing algorithms like BCrypt.
- Ensure the authentication process is stateless to facilitate horizontal scaling.

---

### 2.2 Recipe Management

**Objective:**  
Allow authenticated users to create, read, update, and delete recipes.

**Recipe Entity and Features:**

- **Recipe Entity Fields:**
  - `id`, `name`, `description`, `instructions`, `cookingTime`, `imageUrl`, etc.
  - Relationship with ingredients via an intermediary entity (RecipeIngredient) that includes details such as quantity, unit, and whether the ingredient is required or optional.
  - Association with the creator (User), user feedback, and favorites.

- **API Endpoints:**
  - **List Recipes:**
    - **Endpoint:** `GET /api/recipes`
    - **Function:** Retrieve a paginated list of recipes with optional filtering (e.g., by category or creator).
  - **Get Recipe Details:**
    - **Endpoint:** `GET /api/recipes/{id}`
    - **Function:** Return detailed information about a specific recipe.
  - **Create Recipe:**
    - **Endpoint:** `POST /api/recipes`
    - **Input:** Recipe data including name, ingredients, instructions, etc.
    - **Restriction:** Only accessible to authenticated users.
  - **Update Recipe:**
    - **Endpoint:** `PUT /api/recipes/{id}`
    - **Restriction:** Only the creator or an administrator can modify the recipe.
  - **Delete Recipe:**
    - **Endpoint:** `DELETE /api/recipes/{id}`
    - **Restriction:** Only the creator or an administrator can delete the recipe.

---

### 2.3 Ingredient-Based Recipe Search and Ranking

**Objective:**  
Enable users to search for recipes by selecting available ingredients, with prioritization based on ingredient importance.

**Ingredient Model Enhancement:**
- **Additional Field:**  
  - `rank` (or `ingredientType`): Indicates the importance of an ingredient within a recipe.
  - **Example Values:** `"MAIN"` for core ingredients; `"SUB"` for secondary ingredients.

**API Endpoint and Behavior:**
- **Ingredient-Based Recipe Search**
  - **Endpoint:** `GET /api/recipes/search`
  - **Query Parameters:**
    - `ingredients`: A comma-separated list of ingredient identifiers (IDs or names).
    - `mode` (optional): Specifies search mode — `all` (return recipes containing all specified ingredients) or `any` (return recipes containing at least one). Default is `all`.
  - **Logic:**
    - When using mode `all`, return recipes that include every ingredient from the list.
    - When using mode `any`, assign a higher weight to recipes that include `"MAIN"` ingredients and sort results accordingly.
  - **Output:** A paginated and sorted list of recipes matching the search criteria.

**Non-Functional Considerations:**
- Optimize database queries with indexing and efficient JOIN operations.
- Design the API to allow future extensions (e.g., filtering by cooking time, calorie count, dietary restrictions).

---

## 3. Data Model Definitions

### 3.1 User
- **Role:** Manages user accounts and authentication.
- **Core Fields:**  
  - `id`: Unique identifier.
  - `username`, `email`, `password`.
- **Relationships:**  
  - Many-to-many with DietaryRestriction.
  - Many-to-many with Recipe for favorites.
  - One-to-many with Feedback.

### 3.2 DietaryRestriction
- **Role:** Defines various dietary constraints (e.g., Vegan, Gluten-Free).
- **Relationships:**  
  - Many-to-many with both User and Ingredient.

### 3.3 Recipe
- **Role:** Stores recipe information.
- **Core Fields:**  
  - `id`, `name`, `description`, `instructions`, `cookingTime`, `imageUrl`.
- **Relationships:**  
  - Many-to-many with Ingredient via RecipeIngredient.
  - Associated with User (creator), Feedback, and Favorites.

### 3.4 Ingredient
- **Role:** Represents ingredients used in recipes.
- **Core Fields:**  
  - `id`, `name`, `category`, `description`, `rank` (or `ingredientType` such as MAIN or SUB).
- **Relationships:**  
  - Self-referencing many-to-many for substitutes.
  - Many-to-many with DietaryRestriction.

### 3.5 RecipeIngredient (Join Entity)
- **Role:** Manages the many-to-many relationship between Recipe and Ingredient with additional metadata.
- **Core Fields:**  
  - References to Recipe and Ingredient.
  - `quantity`, `unit`, `isRequired` (boolean flag for required vs. optional).

### 3.6 Feedback
- **Role:** Stores user reviews and ratings for recipes.
- **Core Fields:**  
  - `id`, references to User and Recipe, `rating`, `comment`, and `timestamp`.

### 3.7 Favorite
- **Role:** Manages the association of users with their favorite recipes.
- **Implementation:**  
  - A many-to-many relationship between User and Recipe or a separate entity if additional metadata (e.g., favorite date) is required.

### 3.8 (Optional) RecipeStep
- **Role:** Represents step-by-step instructions for a recipe.
- **Core Fields:**  
  - `id`, `stepNumber`, `description`, and optional media links.

---

## 4. Additional Considerations

- **Environment Configuration:**
  - Use Spring Profiles to separate configurations.
    - **Development:** Use H2 embedded database.
    - **Production:** Use MySQL with proper connection settings.
- **Testing:**
  - Implement unit tests for authentication and recipe services.
  - Use integration tests (with tools like MockMvc or RestAssured) for API endpoints.
- **Documentation:**
  - Integrate Swagger (using springdoc-openapi) for automatic API documentation generation.
- **Security and Monitoring:**
  - Secure the application with Spring Security and proper JWT configuration.
  - Utilize Spring Boot Actuator for logging and health monitoring.

---

This document provides a solid foundation for the backend architecture of Dishcraft. It defines core models, functional endpoints, and non-functional requirements, ensuring the system is secure, scalable, and maintainable. As the project evolves, these requirements can be further refined and extended based on user feedback and new features.
