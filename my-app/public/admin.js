document.addEventListener('DOMContentLoaded', () => {
  // Tab-switching logic
  const tabButtons = document.querySelectorAll('.tab-button');
  const contentSections = document.querySelectorAll('.content');

  tabButtons.forEach(button => {
    button.addEventListener('click', () => {
      tabButtons.forEach(btn => btn.classList.remove('active'));
      contentSections.forEach(section => section.classList.remove('active'));

      button.classList.add('active');
      const target = button.getAttribute('data-tab');
      const activeSection = document.getElementById(target);
      if (activeSection) {
        activeSection.classList.add('active');
      }
    });
  });

  // Global mapping: ingredient name => id
  window.ingredientsMap = {};

  // Fetch ingredients from the backend and populate the datalist for searchable dropdown
  async function loadIngredients() {
    try {
      const token = localStorage.getItem('jwtToken');
      const response = await fetch('http://localhost:8080/api/ingredients/all', {
        method: 'GET',
        headers: {
          'Accept': '*/*',
          'Authorization': token ? `Bearer ${token}` : ''
        }
      });
      if (response.ok) {
        const ingredients = await response.json();
        const datalist = document.getElementById('ingredientsDatalist');
        datalist.innerHTML = "";
        ingredients.forEach(ingredient => {
          const option = document.createElement('option');
          option.value = ingredient.name;
          datalist.appendChild(option);
          // Store mapping from ingredient name to id
          window.ingredientsMap[ingredient.name] = ingredient.id;
        });
      }
    } catch (error) {
      console.error("Error fetching ingredients:", error);
    }
  }
  
  loadIngredients();
  
  const ingredientListDiv = document.getElementById('ingredientList');
  
  // Handle clicks for add and remove buttons within the ingredient list
  ingredientListDiv.addEventListener('click', function(event) {
    // Add a new ingredient group (plus button)
    if (event.target && event.target.classList.contains('add-ingredient-btn')) {
      const ingredientGroup = event.target.closest('.ingredient-group');
      const newGroup = ingredientGroup.cloneNode(true);
      newGroup.querySelectorAll('input').forEach(input => {
        if (input.type === "checkbox") {
          input.checked = false;
        } else {
          input.value = "";
        }
      });
      ingredientListDiv.appendChild(newGroup);
    }
    
    // Remove an ingredient group (x button)
    if (event.target && event.target.classList.contains('remove-ingredient-btn')) {
      const ingredientGroup = event.target.closest('.ingredient-group');
      const allGroups = ingredientListDiv.querySelectorAll('.ingredient-group');
      if (allGroups.length > 1) {
        ingredientGroup.remove();
      } else {
        ingredientGroup.querySelectorAll('input').forEach(input => {
          if (input.type === "checkbox") {
            input.checked = false;
          } else {
            input.value = "";
          }
        });
      }
    }
  });
  
  // Prevent negative numbers in all number inputs
  document.addEventListener('input', function(e) {
    if (e.target && e.target.type === 'number') {
      const value = parseInt(e.target.value, 10);
      if (value < 0) {
        e.target.value = 0;
      }
    }
  });
  
  // Listen for the form submission to build and send the recipe payload
  const addRecipeForm = document.getElementById('addRecipeForm');
  addRecipeForm.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const recipeName = document.getElementById('recipe-name').value;
    const cookingTime = parseInt(document.getElementById('cooking-time').value, 10);
    const instruction = document.getElementById('instruction').value;
    const description = "";
    const imageUrl = "";
    
    const ingredientGroups = document.querySelectorAll('.ingredient-group');
    const recipeIngredients = [];
    ingredientGroups.forEach(group => {
      const nameInput = group.querySelector('.ingredient-name');
      const quantityInput = group.querySelector('.ingredient-quantity');
      const requiredCheckbox = group.querySelector('.ingredient-required');
      const ingredientName = nameInput.value.trim();
      const ingredientId = (window.ingredientsMap && window.ingredientsMap[ingredientName]) || null;
      const quantity = parseFloat(quantityInput.value);
      const isRequired = requiredCheckbox.checked;
      const unit = "";
      
      if (ingredientName !== "") {
        recipeIngredients.push({
          id: ingredientId,
          name: ingredientName,
          quantity: quantity,
          unit: unit,
          isRequired: isRequired
        });
      }
    });
    
    const recipe = {
      name: recipeName,
      description: description,
      instruction: instruction,
      cookingTime: cookingTime,
      recipeIngredients: recipeIngredients,
      imageUrl: imageUrl
    };
    
    try {
      const token = localStorage.getItem('jwtToken');
      const response = await fetch('http://localhost:8080/api/recipes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': token ? `Bearer ${token}` : ''
        },
        body: JSON.stringify([recipe])
      });
      if (response.ok) {
        alert("Recipe submitted successfully!");
        addRecipeForm.reset();
      } else {
        alert("Failed to submit recipe.");
      }
    } catch (error) {
      alert("Error submitting recipe.");
    }
  });

  // Fetch recipes from /api/recipes/all when the button is clicked
  const fetchRecipesBtn = document.getElementById('fetchRecipesBtn');
  if (fetchRecipesBtn) {
    fetchRecipesBtn.addEventListener('click', async () => {
      try {
        const token = localStorage.getItem('jwtToken');
        const response = await fetch('http://localhost:8080/api/recipes/all', {
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Authorization': token ? `Bearer ${token}` : ''
          }
        });
        if (response.ok) {
          const recipes = await response.json();
          const recipesListDiv = document.getElementById('recipesList');
          recipesListDiv.innerHTML = "";
          recipes.forEach(recipe => {
            const recipeItem = document.createElement('div');
            recipeItem.classList.add('recipe-item');
            // Store the full recipe JSON for later editing
            recipeItem.setAttribute('data-recipe', JSON.stringify(recipe));
            recipeItem.innerHTML = `
              <h4>${recipe.name}</h4>
              <p>Cooking Time: ${recipe.cookingTime} minutes</p>
              <p>${recipe.instruction}</p>
              <div class="controls">
                <button class="edit-btn">Edit</button>
                <button class="delete-btn">Delete</button>
              </div>`;
            recipesListDiv.appendChild(recipeItem);
          });
        } else {
          alert("Failed to fetch recipes.");
        }
      } catch (error) {
        alert("Error fetching recipes.");
      }
    });
  }

  // Delegate events for edit, cancel, and save within the recipes list
  const recipesListDiv = document.getElementById('recipesList');
  if (recipesListDiv) {
    recipesListDiv.addEventListener('click', function(e) {
      const recipeItem = e.target.closest('.recipe-item');
      if (!recipeItem) return;
      
      // Edit button clicked
      if (e.target.classList.contains('edit-btn')) {
        const recipeData = recipeItem.getAttribute('data-recipe');
        let recipeObj = {};
        try {
          recipeObj = JSON.parse(recipeData);
        } catch(err) {
          console.error("Error parsing recipe data:", err);
          return;
        }
        // Replace the recipe content with an editable textarea and Save/Cancel controls
        recipeItem.innerHTML = `
          <textarea class="edit-json" style="width:100%;height:150px;">${JSON.stringify(recipeObj, null, 2)}</textarea>
          <div class="controls">
            <button class="save-btn">Save</button>
            <button class="cancel-btn">Cancel</button>
          </div>`;
      }
      // Cancel editing: revert to the original display from stored data
      else if (e.target.classList.contains('cancel-btn')) {
        const recipeData = recipeItem.getAttribute('data-recipe');
        let recipeObj = {};
        try {
          recipeObj = JSON.parse(recipeData);
        } catch(err) {
          console.error("Error parsing recipe data:", err);
          return;
        }
        recipeItem.innerHTML = `
          <h4>${recipeObj.name}</h4>
          <p>Cooking Time: ${recipeObj.cookingTime} minutes</p>
          <p>${recipeObj.instruction}</p>
          <div class="controls">
            <button class="edit-btn">Edit</button>
            <button class="delete-btn">Delete</button>
          </div>`;
      }
      // Save the edited JSON and call the PUT endpoint to update the recipe
      else if (e.target.classList.contains('save-btn')) {
        const textarea = recipeItem.querySelector('.edit-json');
        let updatedRecipe;
        try {
          updatedRecipe = JSON.parse(textarea.value);
        } catch(err) {
          alert("Invalid JSON. Please fix errors.");
          return;
        }
        // Call the PUT endpoint to update the recipe using its id
        (async () => {
          try {
            const token = localStorage.getItem('jwtToken');
            const response = await fetch(`http://localhost:8080/api/recipe/${updatedRecipe.id}`, {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': token ? `Bearer ${token}` : ''
              },
              body: JSON.stringify(updatedRecipe)
            });
            if (response.ok) {
              alert("Recipe updated successfully!");
              // Update the saved data attribute and display updated info
              recipeItem.setAttribute('data-recipe', JSON.stringify(updatedRecipe));
              recipeItem.innerHTML = `
                <h4>${updatedRecipe.name}</h4>
                <p>Cooking Time: ${updatedRecipe.cookingTime} minutes</p>
                <p>${updatedRecipe.instruction}</p>
                <div class="controls">
                  <button class="edit-btn">Edit</button>
                  <button class="delete-btn">Delete</button>
                </div>`;
            } else {
              alert("Failed to update recipe.");
            }
          } catch (error) {
            alert("Error updating recipe.");
          }
        })();
      }
      // Delete functionality: call DELETE endpoint to remove the recipe
      else if (e.target.classList.contains('delete-btn')) {
        if (!confirm("Are you sure you want to delete this recipe?")) return;
        const recipeData = recipeItem.getAttribute('data-recipe');
        let recipeObj = {};
        try {
          recipeObj = JSON.parse(recipeData);
        } catch(err) {
          console.error("Error parsing recipe data:", err);
          return;
        }
        (async () => {
          try {
            const token = localStorage.getItem('jwtToken');
            const response = await fetch(`http://localhost:8080/api/recipes/${recipeObj.id}`, {
              method: 'DELETE',
              headers: {
                'Authorization': token ? `Bearer ${token}` : ''
              }
            });
            if (response.ok) {
              alert("Recipe deleted successfully!");
              recipeItem.remove();
            } else {
              alert("Failed to delete recipe.");
            }
          } catch (error) {
            alert("Error deleting recipe.");
          }
        })();
      }
    });
  }
});
