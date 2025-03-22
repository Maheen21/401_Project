document.addEventListener('DOMContentLoaded', () => {
    const recipeDetails = document.querySelector('.recipe-details');
    const ingredientsList = document.querySelector('.ingredients-list');
    const submitIngredientsButton = document.getElementById('submit-ingredients');
    const backButton = document.getElementById('back-button');
  
    // Get the recipe ID from the URL parameter
    const urlParams = new URLSearchParams(window.location.search);
    const recipeId = urlParams.get('recipeId');
  
    if (!recipeId) {
      alert("No recipe ID found in the URL.");
      return;
    }
  
    // Fetch and display the recipe details
    async function fetchRecipeDetails() {
      try {
        const token = localStorage.getItem('jwtToken');
        const response = await fetch(`https://dishcraft-api-414213457313.us-central1.run.app/api/recipes/${recipeId}`, {
          method: 'GET',
          headers: {
            'Accept': '*/*',
            'Authorization': token ? `Bearer ${token}` : ''
          }
        });
  
        if (response.ok) {
          const recipe = await response.json();
          console.log("API Response:", recipe); // Log the full API response
          displayRecipeDetails(recipe);
  
          // Check if the recipeIngredients field exists and is not empty
          if (recipe.recipeIngredients && recipe.recipeIngredients.length > 0) {
            displayIngredients(recipe.recipeIngredients);
          } else {
            console.warn("No ingredients found in the recipe.");
            console.warn("Recipe Object:", recipe); // Log the recipe object for debugging
          }
        } else {
          console.error('Failed to fetch recipe details:', response.statusText);
        }
      } catch (error) {
        console.error("Error fetching recipe details:", error);
      }
    }
  
    // Display the recipe details
    function displayRecipeDetails(recipe) {
      recipeDetails.innerHTML = `
        <h2>${recipe.name}</h2>
        <img src="${recipe.imageUrl}" alt="${recipe.name}" />
        <p>${recipe.description}</p>
      `;
    }
  
    // Display the ingredients as checkboxes
    function displayIngredients(ingredients) {
      const ul = document.createElement('ul');
      ingredients.forEach(ingredient => {
        const li = document.createElement('li');
        
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = `ingredient-${ingredient.id}`;
        checkbox.value = ingredient.id;
  
        const label = document.createElement('label');
        label.htmlFor = `ingredient-${ingredient.id}`;
        label.textContent = ingredient.name;
  
        li.appendChild(checkbox);
        li.appendChild(label);
        ul.appendChild(li);
      });
  
      ingredientsList.appendChild(ul);
    }

    async function sendToLLM(recipe, ingredientsList) {
        const selectedIngredients = [];
        const checkboxes = ingredientsList.querySelectorAll('input[type="checkbox"]:checked');
      
        checkboxes.forEach(checkbox => {
          selectedIngredients.push({
            id: checkbox.value,
            name: checkbox.nextElementSibling.textContent // Get the ingredient name from the label
          });
        });
      
        if (selectedIngredients.length === 0) {
          alert("Please select at least one ingredient!");
          return;
        }
      
        const requestData = {
          recipe: {
            id: recipe.id,
            name: recipe.name,
            description: recipe.description
          },
          ingredients: selectedIngredients
        };
      
        console.log("Sending data to LLM:", requestData);
      
        try {
          const response = await fetch('https://dishcraft-llm-k6snsjrexa-uc.a.run.app/start', { // Adjust the LLM API URL
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              input: `Given these ingredients: ${selectedIngredients.map(i => i.name).join(', ')}, and this recipe: ${recipe.name} (${recipe.description}), suggest ingredient substitutions and recipe improvements.`
            })
          });
      
          if (response.ok) {
            const result = await response.json();
            console.log("LLM Response:", result);
      
            // Store the LLM response in localStorage
            localStorage.setItem('llmResponse', JSON.stringify(result));
      
            // Redirect to output.html
            window.location.href = 'output.html';
          } else {
            console.error('Error contacting LLM:', response.statusText);
            alert('Failed to fetch suggestions.');
          }
        } catch (error) {
          console.error("Error:", error);
          alert("Error communicating with LLM.");
        }
      }
  
    // Handle submission of selected ingredients
    submitIngredientsButton.addEventListener('click', () => {
        const selectedIngredients = [];
        const checkboxes = ingredientsList.querySelectorAll('input[type="checkbox"]:checked');
      
        checkboxes.forEach(checkbox => {
          selectedIngredients.push({
            id: checkbox.value,
            name: checkbox.nextElementSibling.textContent // Get the ingredient name from the label
          });
        });
      
        if (selectedIngredients.length === 0) {
          alert("Please select at least one ingredient!");
          return;
        }
      
        // Fetch the full recipe details (if not already available)
        const recipe = {
          id: recipeId, // recipeId is from the URL parameter
          name: document.querySelector('.recipe-details h2').textContent,
          description: document.querySelector('.recipe-details p').textContent
        };
      
        // Call sendToLLM with the recipe and selected ingredients
        sendToLLM(recipe, ingredientsList);
      });
  
    // Add event listener for the back button
    backButton.addEventListener('click', () => {
      window.location.href = 'selection.html';
    });
  
    // Fetch recipe details when the page loads
    fetchRecipeDetails();
  });