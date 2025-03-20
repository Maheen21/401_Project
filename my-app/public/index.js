document.addEventListener('DOMContentLoaded', () => {
  // Check if a JWT exists and adjust UI if needed.
  const jwt = localStorage.getItem('jwtToken');
  if (jwt) {
    const loginLink = document.querySelector('.login-link');
    if (loginLink) {
      loginLink.style.display = 'none';
    }
  }

  // Tab-switching logic.
  const tabButtons = document.querySelectorAll('.tab-button');
  const contentSections = document.querySelectorAll('.content');

  tabButtons.forEach(button => {
    button.addEventListener('click', () => {
      // Remove active classes from all buttons and content sections.
      tabButtons.forEach(btn => btn.classList.remove('active'));
      contentSections.forEach(section => section.classList.remove('active'));

      // Activate the clicked tab and its corresponding content.
      button.classList.add('active');
      const target = button.getAttribute('data-tab');
      const activeSection = document.getElementById(target);
      activeSection.classList.add('active');

      // When the "recipes" tab is clicked, call fetchRecipes() from recipe.js.
      if (target === 'recipes') {
        fetchRecipes();
      } 
      // When the "ingredients" tab is activated, fetch the ingredients list.
      else if (target === 'ingredients') {
        fetchIngredients();
      }
    });
  });

  // Since the ingredients tab is the default active tab, fetch the list on page load.
  fetchIngredients();
});