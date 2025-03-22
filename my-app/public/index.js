document.addEventListener('DOMContentLoaded', () => {
  const recipeList = document.querySelector('.recipe-list');

  // Fetch the list of recipes from the backend
  async function fetchRecipes() {
    try {
      const token = localStorage.getItem('jwtToken');
      const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/recipes/all', {
        method: 'GET',
        headers: {
          'Accept': '*/*',
          'Authorization': token ? `Bearer ${token}` : ''
        }
      });

      if (response.ok) {
        const recipes = await response.json();
        displayRecipeList(recipes);
      } else {
        console.error('Failed to fetch recipes:', response.statusText);
      }
    } catch (error) {
      console.error("Error fetching recipes:", error);
    }
  }

  // Display the list of recipes
  function displayRecipeList(recipes) {
    recipeList.innerHTML = ''; // Clear any existing content
    recipes.forEach(recipe => {
      const recipeButton = document.createElement('button');
      recipeButton.textContent = recipe.name;
      recipeButton.addEventListener('click', () => {
        // Redirect to recipe-details.html with the recipe ID as a URL parameter
        window.location.href = `recipe-details.html?recipeId=${recipe.id}`;
      });
      recipeList.appendChild(recipeButton);
    });
  }

  // Fetch recipes when the page loads
  fetchRecipes();
});