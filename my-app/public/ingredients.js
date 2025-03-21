let selectedIngredients = []; //to save them before sending to api

async function fetchIngredients() {
  try {
    // Get JWT from localStorage (if available)
    const token = localStorage.getItem('jwtToken');

    // Replace with your actual backend endpoint for ingredients.
    const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients/all', {
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

      const submitButton = document.createElement('button');
      submitButton.id = 'submit-ingredients';
      submitButton.classList.add('btn');
      submitButton.textContent = 'Submit Ingredients';
      ingredientsContainer.appendChild(submitButton);  // Add the button to the DOM

      // Add event listener to the submit button
      submitButton.addEventListener('click', handleSubmitIngredients);

    } else {
      console.error('Failed to fetch ingredients:', response.statusText);
    }
  } catch (error) {
    console.error("Error fetching ingredients:", error);
  }
}

// function handleSubmitIngredients() {
//   // Get all selected ingredients (checkboxes)
//   const selectedIngredients = [];
//   const checkboxes = document.querySelectorAll('.ingredient-checkbox:checked');

//   checkboxes.forEach(checkbox => {
//     const ingredientId = checkbox.value;
//     selectedIngredients.push({
//       id: ingredientId,
//       name: checkbox.nextElementSibling.querySelector('strong').textContent
//     });
//   });

//   if (selectedIngredients.length > 0) {
//     // Create a JSON object with the selected ingredients
//     const ingredientsJSON = JSON.stringify(selectedIngredients, null, 2);

//     // Log the JSON (or send it to the backend)
//     console.log(ingredientsJSON);

//     // Optionally, you can download the JSON file
//     const blob = new Blob([ingredientsJSON], { type: 'application/json' });
//     const link = document.createElement('a');
//     link.href = URL.createObjectURL(blob);
//     link.download = 'selected_ingredients.json';
//     link.click();
//   } else {
//     alert('No ingredients selected.');
//   }

//   //TODO: make new api endpoint to put submitted ingredients in
//   fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/submit-ingredients', {
//     method: 'POST',
//     headers: {
//       'Content-Type': 'application/json',
//       'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
//     },
//     body: JSON.stringify(selectedIngredients)
//   })
//   .then(response => response.json())
//   .then(data => {
//     alert('Ingredients submitted successfully!');
//     switchToRecipesTab();
//   })
//   .catch(error => {
//     console.error('Error submitting ingredients:', error);
//     alert('Error submitting ingredients.');
//     switchToRecipesTab();
//   });
// }

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
      // Store ingredients in localStorage
      localStorage.setItem('selectedIngredients', JSON.stringify(selectedIngredients));

      // Switch to Recipes tab
      switchToRecipesTab();
  } else {
      alert('No ingredients selected.');
  }
}



function switchToRecipesTab() {
  const tabButtons = document.querySelectorAll('.tab-button');
  const contentSections = document.querySelectorAll('.content');

  // Remove 'active' class from all tabs and sections
  tabButtons.forEach(btn => btn.classList.remove('active'));
  contentSections.forEach(section => section.classList.remove('active'));

  // Find and activate the Recipes tab and its content
  const recipesTabButton = document.querySelector('.tab-button[data-tab="recipes"]');
  const recipesContent = document.getElementById('recipes');

  if (recipesTabButton && recipesContent) {
    recipesTabButton.classList.add('active');
    recipesContent.classList.add('active');
  }

  // Fetch recipes after switching tabs
  fetchRecipes();
}



