document.addEventListener('DOMContentLoaded', () => {
    // Check if a JWT exists and if so, decode and add the Edit Recipes button for root users.
    const jwt = localStorage.getItem('jwtToken');
    if (jwt) {
      try {
        const payloadBase64 = jwt.split('.')[1];
        const payloadJson = atob(payloadBase64);
        const payload = JSON.parse(payloadJson);
        const role = (payload.role || "").toLowerCase();
        
        // If the user role is "root", create and add the Edit Recipes button.
        if (role === 'root') {
          const editButton = document.createElement("button");
          editButton.textContent = "Edit Recipes";
          editButton.className = "btn edit-btn"; // You can style this using CSS.
          
          // Redirect to the edit interface when clicked.
          editButton.addEventListener('click', () => {
            window.location.href = 'edit-recipes.html'; // Adjust the destination as needed.
          });
          
          // Insert the button at the top within the recipes container.
          const recipesContainer = document.getElementById("recipes");
          if (recipesContainer) {
            recipesContainer.insertBefore(editButton, recipesContainer.firstChild);
          }
        }
      } catch (error) {
        console.error("Failed to decode JWT:", error);
      }
    }
  });
  
  // Function to fetch recipes from the backend and display them in the recipes tab.
  // This function is called by index.js when the "Recipes" tab is activated.
  async function fetchRecipes() {
    try {
      // Replace with your actual backend endpoint.
      const token = localStorage.getItem('jwtToken');

      const response = await fetch('http://localhost:8080/api/recipes/all', {
        method: 'GET',
        headers: {
          'Accept': '*/*',
          // Add the Authorization header if token is available
          'Authorization': token ? `Bearer ${token}` : ''
        }
      });
      
        if (response.ok) {
        const data = await response.json();
        const recipesContainer = document.getElementById('recipes');
        
        // Clear any existing recipe content and add the header.
        recipesContainer.innerHTML = `
            <h2>Recipes</h2>
            <p>Here are the available dishes.</p>
        `;
        
        // Append each recipe (customize the markup as needed).
        data.forEach(recipe => {
          const recipeDiv = document.createElement('div');
          recipeDiv.classList.add('recipe-item');
          recipeDiv.innerHTML = `<h3>${recipe.name}</h3><p>${recipe.description}</p>`;
          recipesContainer.appendChild(recipeDiv);
        });
      } else {
        console.error('Failed to fetch recipes:', response.statusText);
      }
    } catch (error) {
      console.error("Error fetching recipes:", error);
    }
  }