document.addEventListener('DOMContentLoaded', () => {
  // ----- Tab-switching Logic -----
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

  // ----- Global Mapping -----
  window.ingredientsMap = {};

  // Fetch ingredients for recipe datalist (used by Add Recipe form)
  async function loadIngredients() {
    try {
      const token = localStorage.getItem('jwtToken');
      const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients/all', {
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

  // ----- Recipe Tab Logic -----
  const ingredientListDiv = document.getElementById('ingredientList');
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

  // Recipe Form Submission Logic
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
      const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/recipes', {
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

  window.allRecipes = [];

  function renderRecipes(recipes) {
    const recipesListDiv = document.getElementById('recipesList');
    recipesListDiv.innerHTML = "";
    recipes.forEach(recipe => {
      const recipeItem = document.createElement('div');
      recipeItem.classList.add('recipe-item');
      recipeItem.setAttribute('data-recipe', JSON.stringify(recipe));
      recipeItem.innerHTML = `
        <h4>${recipe.name}</h4>
        <p>Cooking Time: ${recipe.cookingTime} minutes</p>
        <p>${recipe.instruction}</p>
        <div class="controls">
          <button class="edit-btn">Edit</button>
          <button class="delete-btn">Delete</button>
        </div>
      `;
      recipesListDiv.appendChild(recipeItem);
    });
  }
  
  const fetchRecipesBtn = document.getElementById('fetchRecipesBtn');
  if (fetchRecipesBtn) {
    fetchRecipesBtn.addEventListener('click', async () => {
      try {
        const token = localStorage.getItem('jwtToken');
        const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/recipes/all', {
          method: 'GET',
          headers: {
            'Accept': 'application/json',
            'Authorization': token ? `Bearer ${token}` : ''
          }
        });
        if (response.ok) {
          const recipes = await response.json();
          window.allRecipes = recipes; // Save globally for search filtering
          renderRecipes(recipes);
        } else {
          alert("Failed to fetch recipes.");
        }
      } catch (error) {
        alert("Error fetching recipes.");
      }
    });
  }

  const recipeSearchBar = document.getElementById('recipeSearchBar');
  if (recipeSearchBar) {
    recipeSearchBar.addEventListener('input', function(e) {
      const searchText = e.target.value.trim().toLowerCase();
      const filtered = window.allRecipes.filter(recipe => recipe.name.toLowerCase().includes(searchText));
      renderRecipes(filtered);
    });
  }

  // Delegate events for Recipe edit, cancel, save, and delete
  const recipesListDiv = document.getElementById('recipesList');
  if (recipesListDiv) {
    recipesListDiv.addEventListener('click', function(e) {
      const recipeItem = e.target.closest('.recipe-item');
      if (!recipeItem) return;
      
      // Edit Recipe
      if (e.target.classList.contains('edit-btn')) {
        const recipeData = recipeItem.getAttribute('data-recipe');
        let recipeObj = {};
        try {
          recipeObj = JSON.parse(recipeData);
        } catch(err) {
          console.error("Error parsing recipe data:", err);
          return;
        }
        recipeItem.innerHTML = `
          <textarea class="edit-json" style="width:100%;height:150px;">${JSON.stringify(recipeObj, null, 2)}</textarea>
          <div class="controls">
            <button class="save-btn">Save</button>
            <button class="cancel-btn">Cancel</button>
          </div>
        `;
      }
      // Cancel Recipe Editing
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
          </div>
        `;
      }
      // Save Recipe Edits
      else if (e.target.classList.contains('save-btn')) {
        const textarea = recipeItem.querySelector('.edit-json');
        let updatedRecipe;
        try {
          updatedRecipe = JSON.parse(textarea.value);
        } catch(err) {
          alert("Invalid JSON. Please fix errors.");
          return;
        }
        (async () => {
          try {
            const token = localStorage.getItem('jwtToken');
            const response = await fetch(`https://dishcraft-api-414213457313.us-central1.run.app/api/recipes/${updatedRecipe.id}`, {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': token ? `Bearer ${token}` : ''
              },
              body: JSON.stringify(updatedRecipe)
            });
            if (response.ok) {
              alert("Recipe updated successfully!");
              recipeItem.setAttribute('data-recipe', JSON.stringify(updatedRecipe));
              recipeItem.innerHTML = `
                <h4>${updatedRecipe.name}</h4>
                <p>Cooking Time: ${updatedRecipe.cookingTime} minutes</p>
                <p>${updatedRecipe.instruction}</p>
                <div class="controls">
                  <button class="edit-btn">Edit</button>
                  <button class="delete-btn">Delete</button>
                </div>
              `;
            } else {
              alert("Failed to update recipe.");
            }
          } catch (error) {
            alert("Error updating recipe.");
          }
        })();
      }
      // Delete Recipe
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
            const response = await fetch(`https://dishcraft-api-414213457313.us-central1.run.app/api/recipes/${recipeObj.id}`, {
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

  // ----- Ingredients Tab Logic -----
  window.allIngredients = [];
  
  // Function to render ingredients (filtered array)
  function renderIngredients(ingredients) {
    const ingredientsListDiv = document.getElementById('ingredientsList');
    ingredientsListDiv.innerHTML = "";
    ingredients.forEach(ingredient => {
      const ingredientItem = document.createElement('div');
      ingredientItem.classList.add('ingredient-item');
      ingredientItem.setAttribute('data-ingredient', JSON.stringify(ingredient));
      ingredientItem.innerHTML = `
        <h4>${ingredient.name}</h4>
        <p>Category: ${ingredient.category}</p>
        <p>Description: ${ingredient.description}</p>
        <p>Rank: ${ingredient.rank}</p>
        <p>Dietary Restrictions: ${ingredient.dietaryRestrictions.map(d => d.name).join(", ")}</p>
        <div class="controls">
          <button class="edit-ingredient-btn">Edit</button>
          <button class="delete-ingredient-btn">Delete</button>
        </div>
      `;
      ingredientsListDiv.appendChild(ingredientItem);
    });
  }

  // Function to load ingredients for management (GET /api/ingredients/all)
  window.loadIngredientsForManagement = async function() {
    try {
      const token = localStorage.getItem('jwtToken');
      const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients/all', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Authorization': token ? `Bearer ${token}` : ''
        }
      });
      if (response.ok) {
        const ingredients = await response.json();
        window.allIngredients = ingredients; // store globally
        renderIngredients(ingredients);
      } else {
        alert("Failed to fetch ingredients.");
      }
    } catch (error) {
      alert("Error fetching ingredients.");
    }
  };
  
  // Add search filter listener for the ingredients search bar
  const searchBar = document.getElementById('ingredientSearchBar');
  if (searchBar) {
    searchBar.addEventListener('input', function(e) {
      const searchText = e.target.value.trim().toLowerCase();
      const filtered = window.allIngredients.filter(ing => ing.name.toLowerCase().includes(searchText));
      renderIngredients(filtered);
    });
  }
  
  // Delegate events for Ingredients tab
  const ingredientsListDiv = document.getElementById('ingredientsList');
  if (ingredientsListDiv) {
    ingredientsListDiv.addEventListener('click', function(e) {
      const ingredientItem = e.target.closest('.ingredient-item');
      if (!ingredientItem) return;
      
      // Edit Ingredient
      if (e.target.classList.contains('edit-ingredient-btn')) {
        const ingredientData = ingredientItem.getAttribute('data-ingredient');
        let ingredientObj = {};
        try {
          ingredientObj = JSON.parse(ingredientData);
        } catch(err) {
          console.error("Error parsing ingredient data:", err);
          return;
        }
        ingredientItem.innerHTML = `
          <textarea class="edit-ingredient-json" style="width:100%;height:150px;">${JSON.stringify(ingredientObj, null, 2)}</textarea>
          <div class="controls">
            <button class="save-ingredient-btn">Save</button>
            <button class="cancel-ingredient-btn">Cancel</button>
          </div>
        `;
      }
      // Cancel Editing Ingredient
      else if (e.target.classList.contains('cancel-ingredient-btn')) {
        const ingredientData = ingredientItem.getAttribute('data-ingredient');
        let ingredientObj = {};
        try {
          ingredientObj = JSON.parse(ingredientData);
        } catch(err) {
          console.error("Error parsing ingredient data:", err);
          return;
        }
        ingredientItem.innerHTML = `
          <h4>${ingredientObj.name}</h4>
          <p>Category: ${ingredientObj.category}</p>
          <p>Description: ${ingredientObj.description}</p>
          <p>Rank: ${ingredientObj.rank}</p>
          <p>Dietary Restrictions: ${ingredientObj.dietaryRestrictions.map(d => d.name).join(", ")}</p>
          <div class="controls">
            <button class="edit-ingredient-btn">Edit</button>
            <button class="delete-ingredient-btn">Delete</button>
          </div>
        `;
      }
      // Save Edited Ingredient
      else if (e.target.classList.contains('save-ingredient-btn')) {
        const textarea = ingredientItem.querySelector('.edit-ingredient-json');
        let updatedIngredient;
        try {
          updatedIngredient = JSON.parse(textarea.value);
        } catch(err) {
          alert("Invalid JSON. Please fix errors.");
          return;
        }
        (async () => {
          try {
            const token = localStorage.getItem('jwtToken');
            const response = await fetch(`https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients/${updatedIngredient.id}`, {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': token ? `Bearer ${token}` : ''
              },
              body: JSON.stringify(updatedIngredient)
            });
            if (response.ok) {
              alert("Ingredient updated successfully!");
              ingredientItem.setAttribute('data-ingredient', JSON.stringify(updatedIngredient));
              ingredientItem.innerHTML = `
                <h4>${updatedIngredient.name}</h4>
                <p>Category: ${updatedIngredient.category}</p>
                <p>Description: ${updatedIngredient.description}</p>
                <p>Rank: ${updatedIngredient.rank}</p>
                <p>Dietary Restrictions: ${updatedIngredient.dietaryRestrictions.map(d => d.name).join(", ")}</p>
                <div class="controls">
                  <button class="edit-ingredient-btn">Edit</button>
                  <button class="delete-ingredient-btn">Delete</button>
                </div>
              `;
            } else {
              alert("Failed to update ingredient.");
            }
          } catch (error) {
            alert("Error updating ingredient.");
          }
        })();
      }
      // Delete Ingredient
      else if (e.target.classList.contains('delete-ingredient-btn')) {
        if (!confirm("Are you sure you want to delete this ingredient?")) return;
        const ingredientData = ingredientItem.getAttribute('data-ingredient');
        let ingredientObj = {};
        try {
          ingredientObj = JSON.parse(ingredientData);
        } catch(err) {
          console.error("Error parsing ingredient data:", err);
          return;
        }
        (async () => {
          try {
            const token = localStorage.getItem('jwtToken');
            const response = await fetch(`https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients/${ingredientObj.id}`, {
              method: 'DELETE',
              headers: {
                'Authorization': token ? `Bearer ${token}` : ''
              }
            });
            if (response.ok) {
              alert("Ingredient deleted successfully!");
              ingredientItem.remove();
            } else {
              alert("Failed to delete ingredient.");
            }
          } catch (error) {
            alert("Error deleting ingredient.");
          }
        })();
      }
    });
  }

  // ----- Add Ingredient Form Logic -----
  const addIngredientForm = document.getElementById('addIngredientForm');
  if (addIngredientForm) {
    addIngredientForm.addEventListener('submit', async function(e) {
      e.preventDefault();
      const name = document.getElementById('ingredient-name').value;
      const category = document.getElementById('ingredient-category').value;
      const description = document.getElementById('ingredient-description').value;
      const rank = document.getElementById('ingredient-rank').value;
      let dietaryRestrictions = [];
      document.querySelectorAll('.dietary-restriction-checkbox').forEach(cb => {
        if (cb.checked) {
          dietaryRestrictions.push({
            id: parseInt(cb.value, 10),
            name: cb.getAttribute('data-name')
          });
        }
      });
      const newIngredient = {
        name: name,
        category: category,
        description: description,
        rank: rank,
        dietaryRestrictions: dietaryRestrictions
      };
      try {
        const token = localStorage.getItem('jwtToken');
        const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/ingredients', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': token ? `Bearer ${token}` : ''
          },
          body: JSON.stringify(newIngredient)
        });
        if (response.ok) {
          alert("Ingredient added successfully!");
          addIngredientForm.reset();
          loadIngredientsForManagement();
        } else {
          alert("Failed to add ingredient.");
        }
      } catch (error) {
        alert("Error adding ingredient.");
      }
    });
  }
});