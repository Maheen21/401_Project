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
async function fetchRecipes() {
  try {
    // Replace with your actual backend endpoint.
    const token = localStorage.getItem('jwtToken');
    const response = await fetch('http://localhost:8080/api/recipes/all', {
      method: 'GET',
      headers: {
        'Accept': '*/*',
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
      
      data.forEach(recipe => {
        const recipeButton = document.createElement('button');
        recipeButton.classList.add('recipe-button');

        // Create separate elements for name and description
        const recipeName = document.createElement('span');
        recipeName.classList.add('recipe-name');
        recipeName.textContent = recipe.name;

        const recipeDescription = document.createElement('span');
        recipeDescription.classList.add('recipe-description');
        recipeDescription.textContent = recipe.description;

        // Append both elements to the button
        recipeButton.appendChild(recipeName);
        recipeButton.appendChild(recipeDescription);

        // Add click event listener
        recipeButton.addEventListener('click', () => handleRecipeClick(recipe));

        // Append the button to the container.
        recipesContainer.appendChild(recipeButton);
      });
    } else {
      console.error('Failed to fetch recipes:', response.statusText);
    }
  } catch (error) {
    console.error("Error fetching recipes:", error);
  }
}

function handleRecipeClick(recipe) {
  // Retrieve ingredients from localStorage
  const selectedIngredients = JSON.parse(localStorage.getItem('selectedIngredients'));

  if (!selectedIngredients || selectedIngredients.length === 0) {
    alert("Please select ingredients first!");
    return;
  }

  const selectedRecipe = recipe;
  console.log("Selected Recipe:", selectedRecipe);

  // Send both ingredients and recipe to the backend
  submitIngredientsAndRecipe(selectedIngredients, selectedRecipe);
}

async function submitIngredientsAndRecipe(selectedIngredients, selectedRecipe) {
  const requestData = {
    ingredients: selectedIngredients,
    recipe: selectedRecipe
  };

  console.log("Sending data to LLM:", requestData);

  try {
    const response = await fetch('http://localhost:5000/start', { // Adjust the LLM API URL
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        input: `Given these ingredients: ${selectedIngredients.map(i => i.name).join(', ')}, and this recipe: ${selectedRecipe.name} (${selectedRecipe.description}), suggest ingredient substitutions and recipe improvements.`
      })
    });

    if (response.ok) {
      const result = await response.json();
      console.log("LLM Response:", result);
      alert("LLM Response Received: " + result.response);
      // Store the LLM response in localStorage
      localStorage.setItem('llmResponse', JSON.stringify(result.response));

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



// let selectedRecipe = null; //store to send to api

// document.addEventListener('DOMContentLoaded', () => {
//     // Check if a JWT exists and if so, decode and add the Edit Recipes button for root users.
//     const jwt = localStorage.getItem('jwtToken');
//     if (jwt) {
//       try {
//         const payloadBase64 = jwt.split('.')[1];
//         const payloadJson = atob(payloadBase64);
//         const payload = JSON.parse(payloadJson);
//         const role = (payload.role || "").toLowerCase();
        
//         // If the user role is "root", create and add the Edit Recipes button.
//         if (role === 'root') {
//           const editButton = document.createElement("button");
//           editButton.textContent = "Edit Recipes";
//           editButton.className = "btn edit-btn"; // You can style this using CSS.
          
//           // Redirect to the edit interface when clicked.
//           editButton.addEventListener('click', () => {
//             window.location.href = 'edit-recipes.html'; // Adjust the destination as needed.
//           });
          
//           // Insert the button at the top within the recipes container.
//           const recipesContainer = document.getElementById("recipes");
//           if (recipesContainer) {
//             recipesContainer.insertBefore(editButton, recipesContainer.firstChild);
//           }
//         }
//       } catch (error) {
//         console.error("Failed to decode JWT:", error);
//       }
//     }
//   });
  
//   // Function to fetch recipes from the backend and display them in the recipes tab.
//   // This function is called by index.js when the "Recipes" tab is activated.
//   async function fetchRecipes() {
//     try {
//       // Replace with your actual backend endpoint.
//       const token = localStorage.getItem('jwtToken');

//       const response = await fetch('http://localhost:8080/api/recipes/all', {
//         method: 'GET',
//         headers: {
//           'Accept': '*/*',
//           // Add the Authorization header if token is available
//           'Authorization': token ? `Bearer ${token}` : ''
//         }
//       });
      
//         if (response.ok) {
//         const data = await response.json();
//         const recipesContainer = document.getElementById('recipes');
        
//         // Clear any existing recipe content and add the header.
//         recipesContainer.innerHTML = `
//             <h2>Recipes</h2>
//             <p>Here are the available dishes.</p>
//         `;
        
//         // Append each recipe (customize the markup as needed).
//         // data.forEach(recipe => {
//         //   const recipeDiv = document.createElement('div');
//         //   recipeDiv.classList.add('recipe-item');
//         //   recipeDiv.innerHTML = `<h3>${recipe.name}</h3><p>${recipe.description}</p>`;
//         //   recipesContainer.appendChild(recipeDiv);
//         // });
//         data.forEach(recipe => {
//           const recipeButton = document.createElement('button');
//           recipeButton.classList.add('recipe-button');

//           // Create separate elements for name and description
//           const recipeName = document.createElement('span');
//           recipeName.classList.add('recipe-name');
//           recipeName.textContent = recipe.name;

//           const recipeDescription = document.createElement('span');
//           recipeDescription.classList.add('recipe-description');
//           recipeDescription.textContent = recipe.description;

//           // Append both elements to the button
//           recipeButton.appendChild(recipeName);
//           recipeButton.appendChild(recipeDescription);

//           // Add click event listener
//           recipeButton.addEventListener('click', () => handleRecipeClick(recipe));

//           // Append the button to the container.
//           recipesContainer.appendChild(recipeButton);
//         });
//       } else {
//         console.error('Failed to fetch recipes:', response.statusText);
//       }
//     } catch (error) {
//       console.error("Error fetching recipes:", error);
//     }
  

// //   function handleRecipeClick(recipe) {
// //     console.log("Recipe clicked:", recipe);
// //     alert(`You selected: ${recipe.name}\n\nDescription: ${recipe.description}`);
// // }

//   // function handleRecipeClick(recipe) {
//   //   if (selectedIngredients.length === 0) {
//   //       alert("Please select ingredients first!");
//   //       return;
//   //   }

//   //   selectedRecipe = recipe;
//   //   console.log("Selected Recipe:", selectedRecipe);

//   //   // Send selected ingredients and recipe to the backend
//   //   submitIngredientsAndRecipe();
//   // }

//   function handleRecipeClick(recipe) {
//     // Retrieve ingredients from localStorage
//     const selectedIngredients = JSON.parse(localStorage.getItem('selectedIngredients'));

//     if (!selectedIngredients || selectedIngredients.length === 0) {
//         alert("Please select ingredients first!");
//         return;
//     }

//     const selectedRecipe = recipe;
//     console.log("Selected Recipe:", selectedRecipe);

//     // Send both ingredients and recipe to the backend
//     submitIngredientsAndRecipe(selectedIngredients, selectedRecipe);
//   }


//   async function fetchIngredientDetails(id) {
//     const response = await fetch(`/api/ingredients/${id}`);
//     if (!response.ok) {
//       throw new Error('Ingredient not found');
//     }
//     const ingredient = await response.json();
//     return ingredient;
//   }
  
//   // async function submitIngredientsAndRecipe(selectedIngredients, selectedRecipe) {
//   //   try {
//   //     // Fetch full ingredient details for each selected ingredient
//   //     const updatedIngredients = await Promise.all(selectedIngredients.map(async (ingredient) => {
//   //       const fullIngredient = await fetchIngredientDetails(ingredient.id);
//   //       return {
//   //         ...ingredient,
//   //         name: fullIngredient.name,
//   //         category: fullIngredient.category,
//   //         description: fullIngredient.description,
//   //         rank: fullIngredient.rank,
//   //         dietaryRestrictions: fullIngredient.dietaryRestrictions
//   //       };
//   //     }));
  
//   //     // Prepare the data to be sent
//   //     const requestData = {
//   //       ingredients: updatedIngredients,
//   //       recipe: selectedRecipe
//   //     };
  
//   //     console.log("Submitting:", requestData);
  
//   //     // Create an object containing both the selected ingredients and the selected recipe
//   //     const data = {
//   //       ingredients: updatedIngredients,
//   //       recipe: selectedRecipe
//   //     };
  
//   //     // Log the data to the console for debugging
//   //     console.log("Ingredients and Recipe Data:", JSON.stringify(data, null, 2));
  
//   //     // Optionally, download the data as a JSON file for verification
//   //     const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
//   //     const link = document.createElement('a');
//   //     link.href = URL.createObjectURL(blob);
//   //     link.download = 'ingredients_and_recipe.json'; // Set the filename for the download
//   //     link.click();
  
//   //     // Submit the data to the backend
//   //     const response = await fetch('http://localhost:8080/api/submit-selection', {
//   //       method: 'POST',
//   //       headers: {
//   //         'Content-Type': 'application/json',
//   //         'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
//   //       },
//   //       body: JSON.stringify(requestData)
//   //     });
  
//   //     if (response.ok) {
//   //       alert('Ingredients and Recipe submitted successfully!');
//   //     } else {
//   //       console.error('Error submitting data:', response.statusText);
//   //       alert('Failed to submit selection.');
//   //     }
//   //   } catch (error) {
//   //     console.error("Error:", error);
//   //     alert("Error submitting selection.");
//   //   }
//   // }

//   async function submitIngredientsAndRecipe(selectedIngredients, selectedRecipe) {
//     const requestData = {
//         ingredients: selectedIngredients,
//         recipe: selectedRecipe
//     };

//     console.log("Sending data to LLM:", requestData);

//     try {
//         const response = await fetch('http://localhost:5000/start', { // Adjust the LLM API URL
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({
//                 input: `Given these ingredients: ${selectedIngredients.map(i => i.name).join(', ')}, and this recipe: ${selectedRecipe.name} (${selectedRecipe.description}), suggest ingredient substitutions and recipe improvements.`
//             })
//         });

//         if (response.ok) {
//             const result = await response.json();
//             console.log("LLM Response:", result);
//             alert("LLM Response Received: " + result.reply);
//         } else {
//             console.error('Error contacting LLM:', response.statusText);
//             alert('Failed to fetch suggestions.');
//         }
//     } catch (error) {
//         console.error("Error:", error);
//         alert("Error communicating with LLM.");
//     }
//   }

  

//   // async function submitIngredientsAndRecipe(selectedIngredients, selectedRecipe) {
//   //   const requestData = {
//   //       ingredients: selectedIngredients,
//   //       recipe: selectedRecipe
//   //   };

//   //   console.log("Submitting:", requestData);

//   //   // Create an object containing both the selected ingredients and the selected recipe
//   //   const data = {
//   //     ingredients: selectedIngredients,
//   //     recipe: selectedRecipe
//   //   };

//   //   // Log the data to the console for debugging
//   //   console.log("Ingredients and Recipe Data:", JSON.stringify(data, null, 2));

//   //   // Optionally, download the data as a JSON file for verification
//   //   const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
//   //   const link = document.createElement('a');
//   //   link.href = URL.createObjectURL(blob);
//   //   link.download = 'ingredients_and_recipe.json'; // Set the filename for the download
//   //   link.click();

//   //   try {
//   //       const response = await fetch('http://localhost:8080/api/submit-selection', {
//   //           method: 'POST',
//   //           headers: {
//   //               'Content-Type': 'application/json',
//   //               'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`
//   //           },
//   //           body: JSON.stringify(requestData)
//   //       });

//   //       if (response.ok) {
//   //           alert('Ingredients and Recipe submitted successfully!');
//   //       } else {
//   //           console.error('Error submitting data:', response.statusText);
//   //           alert('Failed to submit selection.');
//   //       }
//   //   } catch (error) {
//   //       console.error("Error:", error);
//   //       alert("Error submitting selection.");
//   //   }
//   // }

// }