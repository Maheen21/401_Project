document.addEventListener('DOMContentLoaded', () => {
    // Example: Check if a JWT exists and adjust UI if needed.
    const jwt = localStorage.getItem('jwtToken');
    if (jwt) {
      // For example: hide the login link if the user is logged in.
      const loginLink = document.querySelector('.login-link');
      if (loginLink) {
        loginLink.style.display = 'none';
      }
      // You could also load a header or additional info here.
    }
  });