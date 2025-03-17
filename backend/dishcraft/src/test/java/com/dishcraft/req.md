# Dishcraft Backend Test Requirements

## Overview
This document outlines the testing requirements for the Dishcraft backend application. The goal is to ensure that all functionalities are thoroughly validated through unit and integration tests. Developer C is responsible for writing and maintaining these tests.

## Objectives
- Verify the correctness and robustness of the business logic in service classes.
- Ensure the proper functioning of REST API endpoints exposed by controllers.
- Validate the mapping between entities and DTOs.
- Test authentication and security mechanisms where applicable.
- Cover both positive and negative test scenarios.

## Testing Scope

### 1. Unit Tests
- **Service Layer:**  
  Test individual methods in services (e.g., RecipeService, IngredientService, FeedbackService) by mocking repository dependencies.
- **Mapping Logic:**  
  Test the correctness of DTO to entity and entity to DTO conversions (using ModelMapper or custom mapping logic).

### 2. Integration Tests
- **Controller Endpoints:**  
  Verify that API endpoints (e.g., RecipeController, IngredientController, FeedbackController) function as expected. Use MockMvc or RestAssured to simulate HTTP requests.
- **Repository Interactions:**  
  Use an in-memory database (e.g., H2) to test repository queries and interactions in an end-to-end scenario.
- **Security Integration:**  
  Test that endpoints requiring authentication return the proper status codes (e.g., 401 Unauthorized when no token is provided).

## Detailed Test Requirements

### RecipeService Tests
- **Create Recipe:**  
  - Validate that a recipe is created correctly when given valid input.
  - Ensure that required fields are validated and missing or invalid inputs trigger errors.
- **Get Recipe by ID:**  
  - Verify that the correct recipe is returned for a valid ID.
  - Test the behavior when a recipe with the specified ID does not exist.
- **Get All Recipes with Pagination:**  
  - Confirm that pagination parameters (page, size, sorting) work correctly.
  - Ensure that the returned page contains the expected number of items.
- **Update Recipe:**  
  - Check that updates to a recipe are correctly applied.
  - Validate error handling when trying to update a non-existent recipe.
- **Delete Recipe:**  
  - Ensure that a recipe is properly deleted.
  - Verify that related data (if applicable) is handled according to cascading rules.
- **Search Recipes:**  
  - Test "all" mode: Only recipes containing all selected ingredients are returned.
  - Test "any" mode: Recipes containing at least one selected ingredient are returned, with MAIN ingredients prioritized.
  - Validate that dietary restrictions filtering is applied correctly based on the current user’s profile.

### IngredientService Tests
- **Get Ingredient by ID:**  
  - Confirm that the correct ingredient is returned for a valid ID.
  - Test error handling for an invalid or non-existent ID.
- **Search Ingredients by Name:**  
  - Validate that the search is case-insensitive and returns the expected results.
  - Test edge cases such as empty search strings or no matching ingredients.

### FeedbackService Tests
- **Create Feedback:**  
  - Ensure that feedback is created correctly and associated with the correct recipe and user.
- **Get Feedback by ID:**  
  - Verify that feedback is retrieved correctly by its ID.
  - Test behavior when feedback with the specified ID does not exist.
- **Get Feedback for a Recipe:**  
  - Confirm that all feedback for a given recipe is returned.
- **Update Feedback:**  
  - Validate that feedback updates are correctly applied.
  - Test error handling for updating non-existent feedback.
- **Delete Feedback:**  
  - Ensure that feedback is deleted properly and that the system reflects the deletion.

### Controller Tests (Integration)
- **RecipeController:**  
  - Test the create, retrieve, update, delete, and search endpoints.
  - Validate proper HTTP status codes and response content.
- **IngredientController:**  
  - Test endpoints for retrieving an ingredient by ID and searching by name.
- **FeedbackController:**  
  - Test endpoints for creating, retrieving, updating, and deleting feedback.

## Test Environment and Tools
- **Unit Testing Framework:** JUnit 5
- **Integration Testing:** Spring Boot Test with MockMvc
- **Mocking Framework:** Mockito (for mocking dependencies in unit tests)
- **In-Memory Database:** H2 (for repository and integration tests)
- **API Testing:** Postman or RestAssured (for end-to-end API testing)

## Additional Requirements
- All test cases should cover both typical and edge case scenarios.
- Ensure that tests are clearly named and commented, explaining the purpose of each test.
- Aim for high code coverage for all business-critical functionalities.
- Document any assumptions or limitations in the test cases.

## Deliverables
- Complete test code in the `src/test/java/com/dishcraft/` directory.
- A summary test report detailing test coverage, key test cases, and any issues found.
- Regular updates during code reviews to ensure tests are maintained as the application evolves.

---