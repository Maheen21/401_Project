let selectedIngredients = []; // to save them before sending to api

async function fetchIngredients() {
  try {
    // Get JWT from localStorage (if available)
    const token = localStorage.getItem('jwtToken');

    // Replace with your actual backend endpoint for ingredients.
    const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients/all', {
      method: 'GET',
      headers: {
        'Accept': '*/*',
        'Authorization': token ? `Bearer ${token}` : ''
      }
    });

    if (response.ok) {
      const data = await response.json();
      const ingredientsContainer = document.getElementById('ingredients');

      // Clear any existing content and add a header.
      ingredientsContainer.innerHTML = `
          <h2>Ingredients</h2>
          <!-- New search result container will be inserted here -->
          <div id="searchResults"></div>
          <p>Select an ingredient from the list below:</p>
          <div id="ingredientsList"></div>
      `;

      // Get the ingredients list container.
      const ingredientsList = document.getElementById('ingredientsList');

      // Create and insert the submit button above the ingredients list.
      const submitButton = document.createElement('button');
      submitButton.id = 'submit-ingredients';
      submitButton.classList.add('btn');
      submitButton.textContent = 'Submit Ingredients';
      ingredientsContainer.insertBefore(submitButton, ingredientsList);

      // Append each ingredient as a checkbox option.
      data.forEach(ingredient => {
        const ingredientDiv = document.createElement('div');
        ingredientDiv.classList.add('ingredient-item');
        ingredientDiv.style.marginBottom = '10px';

        // Create a comma separated list of dietary restriction names.
        let restrictions = "";
        if (Array.isArray(ingredient.dietaryRestrictions)) {
          restrictions = ingredient.dietaryRestrictions.map(r => r.name).join(', ');
        }

        // Set innerHTML with a checkbox and label.
        ingredientDiv.innerHTML = `
          <input type="checkbox" class="ingredient-checkbox" id="ingredient-${ingredient.id}" value="${ingredient.id}">
          <label for="ingredient-${ingredient.id}">
            <strong>${ingredient.name}</strong> (${ingredient.category} - ${ingredient.rank})<br>
            ${ingredient.description}<br>
            <em>Dietary Restrictions:</em> ${restrictions}
          </label>
        `;

        ingredientsList.appendChild(ingredientDiv);
      });

      // Add event listener to the submit button.
      submitButton.addEventListener('click', handleSubmitIngredients);

    } else {
      console.error('Failed to fetch ingredients:', response.statusText);
    }
  } catch (error) {
    console.error("Error fetching ingredients:", error);
  }
}

// This function builds the search query URL using the selected ingredient ids and mode=any,
// and calls renderSearchResults() to display the recipes within the ingredients tab.
async function searchRecipes() {
  try {
    const token = localStorage.getItem('jwtToken');
    const selectedIngredientsData = localStorage.getItem('selectedIngredients');
    let url = 'https://dishcraft-api-414213457313.us-central1.run.app/api/recipes/search';
    if (selectedIngredientsData) {
      const selectedIngredients = JSON.parse(selectedIngredientsData);
      // Build the query string with multiple ingredientIds parameters.
      const queryParams = selectedIngredients.map(ing => `ingredientIds=${ing.id}`).join('&');
      url += `?${queryParams}&mode=any`;
    }
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Accept': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      }
    });
    if (response.ok) {
      const recipes = await response.json();
      // Render the recipes in the searchResults container.
      renderSearchResults(recipes);
    } else {
      alert('Failed to fetch recipes from search endpoint.');
    }
  } catch (error) {
    console.error('Error fetching recipes:', error);
  }
}

// Renders the search result recipes in a container at the top of the Ingredients tab.
function renderSearchResults(recipes) {
  let resultsContainer = document.getElementById('searchResults');
  if (!resultsContainer) {
    // Create the container if it doesn't exist.
    resultsContainer = document.createElement('div');
    resultsContainer.id = 'searchResults';
    // Insert it at the top of the ingredients container.
    const ingredientsContainer = document.getElementById('ingredients');
    ingredientsContainer.insertBefore(resultsContainer, ingredientsContainer.firstChild);
  }
  // Clear previous results.
  resultsContainer.innerHTML = '<h2>Recipe Results</h2>';
  if (recipes.length === 0) {
    resultsContainer.innerHTML += '<p>No recipes found matching the selected ingredients.</p>';
  } else {
    recipes.forEach(recipe => {
      const recipeDiv = document.createElement('div');
      recipeDiv.classList.add('recipe-item');
      recipeDiv.style.border = '1px solid #ccc';
      recipeDiv.style.padding = '10px';
      recipeDiv.style.marginBottom = '10px';
      recipeDiv.innerHTML = `
        <h4>${recipe.name}</h4>
        <p><strong>Cooking Time:</strong> ${recipe.cookingTime} minutes</p>
        <p>${recipe.instruction}</p>
      `;
      resultsContainer.appendChild(recipeDiv);
    });
  }
}

function handleSubmitIngredients() {
  const selectedIngredients = [];
  const checkboxes = document.querySelectorAll('.ingredient-checkbox:checked');

  checkboxes.forEach(checkbox => {
    const ingredientId = checkbox.value;
    selectedIngredients.push({
      id: ingredientId,
      name: checkbox.nextElementSibling.querySelector('strong').textContent
    });
  });

  if (selectedIngredients.length > 0) {
    // Store selected ingredients so we can build our search query.
    localStorage.setItem('selectedIngredients', JSON.stringify(selectedIngredients));
    // Instead of switching to the Recipes tab, fetch and show the results in the Ingredients tab.
    searchRecipes();
  } else {
    alert('No ingredients selected.');
  }
}