### 🧪 Backend Testing Approach

Instead of implementing automated unit or integration tests, our team validated the backend functionality using **Swagger UI** provided by SpringDoc.

Swagger UI allowed us to:
- Interactively test each REST endpoint (GET, POST, PUT, DELETE)
- Verify request/response formats and status codes
- Simulate real-world client behavior without building a frontend
- Quickly iterate and debug API behavior during development

We used Swagger to validate the following endpoints:
- `/api/recipes` for CRUD operations
- `/api/ingredients` for search and creation
- `/api/recipes/search` for ingredient-based filtering
- `/api/auth/register` and `/api/auth/login` for authentication
and more

This manual testing approach enabled us to ensure that the backend logic and database interactions were functioning correctly.

> While no formal JUnit or integration tests were written, Swagger UI was actively used as a functional test tool throughout the development process.

### 🐞 Circular Reference Issue in User ↔ Dietary Restriction ↔ Ingredient Mapping

While testing our API responses in Swagger UI, we encountered a circular reference issue when fetching user data (`/api/auth/users` or `/api/auth/profile`). The problem stemmed from the entity relationships between `User`, `DietaryRestriction`, and `RestrictedIngredient`.

Specifically:
- Each `User` had a list of `DietaryRestriction` objects.
- Each `DietaryRestriction` referenced one or more `RestrictedIngredient` entries.
- These ingredients, in turn, referenced back to the original `User`, either directly or through their restriction mappings.

This created an infinite loop during JSON serialization:
`User → DietaryRestriction → Ingredient → User → ...`

To fix this issue, we:
- Introduced dedicated **DTOs** that exclude back-references to `User`

This experience highlighted the importance of **separating entity structure from API response models**, and showed how Swagger UI helped uncover deep structural issues early in development.

### 🔍 Swagger-Driven Debugging Example – DTO Exposure Fix

While testing the user registration and retrieval endpoints via Swagger UI, we noticed that the API was returning the entire `User` entity, including sensitive fields such as `password`.

This issue arose because the controller was returning the `User` entity directly, instead of using a `UserResponseDto`. As a result, sensitive information was unintentionally exposed in the API response.

Swagger made this immediately visible by showing the full response structure. We used this visibility to:
- Identify the exposed fields (`password`, `roles`, etc.)
- Refactor the controller and service layers to use `UserRequestDto` and `UserResponseDto`
- Ensure that only safe and intended fields (`id`, `username`, `email`) are included in API responses

This example highlights the importance of API documentation and testing tools like Swagger in catching security and design issues before deployment.



