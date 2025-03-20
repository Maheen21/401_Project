async function fetchIngredients() {
  try {
    // Get JWT from localStorage (if available)
    const token = localStorage.getItem('jwtToken');

    // Replace with your actual backend endpoint for ingredients.
    const response = await fetch('http://localhost:8080/api/ingredients/all', {
      method: 'GET',
      headers: {
        'Accept': '*/*',
        // Add the Authorization header if token is available
        'Authorization': token ? `Bearer ${token}` : ''
      }
    });

    if (response.ok) {
      const data = await response.json();
      const ingredientsContainer = document.getElementById('ingredients');

      // Clear any existing content and add a header.
      ingredientsContainer.innerHTML = `
          <h2>Ingredients</h2>
          <p>Select an ingredient from the list below:</p>
          <div id="ingredientsList"></div>
      `;

      const ingredientsList = document.getElementById('ingredientsList');
      // Append each ingredient as a checkbox option.
      data.forEach(ingredient => {
        const ingredientDiv = document.createElement('div');
        ingredientDiv.classList.add('ingredient-item');
        // Add a bottom margin for spacing.
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
    } else {
      console.error('Failed to fetch ingredients:', response.statusText);
    }
  } catch (error) {
    console.error("Error fetching ingredients:", error);
  }
}