document.addEventListener('DOMContentLoaded', () => {
    // ------------------ Login Form Handler ------------------
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            // Get login form field values; using the username field as the usernameOrEmail
            const usernameOrEmail = loginForm.querySelector('input[name="username"]').value;
            const password = loginForm.querySelector('input[name="password"]').value;
            
            // Prepare payload with updated key names
            const payload = { usernameOrEmail, password };
    
            try {
                const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/auth/login', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                
                if (response.ok) {
                    const data = await response.json();
                    // Assuming the returned JSON has a field called 'token'
                    localStorage.setItem('jwtToken', data.token);
                    alert('Login successful!');
                    // Redirect to the main page.
                    window.location.href = 'index.html';
                } else {
                    const text = await response.text();
                    console.error('Login failed (raw response):', text);
                    alert('Login failed: ' + text);
                }
            } catch (error) {
                console.error('Error during login:', error);
                alert('Login error: ' + error.message);
            }
        });
    }
    
    // ------------------ Registration Form Handler ------------------
    const registerForm = document.querySelector('#register-container form');
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            // Get registration form field values
            const email = registerForm.querySelector('input[name="email"]').value;
            const username = registerForm.querySelector('input[name="username"]').value;
            const password = registerForm.querySelector('input[name="password"]').value;
            // For now, dietaryRestrictions is an empty array.
            const dietaryRestrictions = [];
            
            // Prepare payload for registration
            const payload = { username, email, password, dietaryRestrictions };
            
            try {
                const response = await fetch('https://dishcraft-api-414213457313.us-central1.run.app/api/auth/register', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });
                
                if (response.ok) {
                    alert('Registration successful!');
                    showLogin();
                } else {
                    const text = await response.text();
                    console.error('Registration failed (raw response):', text);
                    alert('Registration failed: ' + text);
                }
            } catch (error) {
                console.error('Error during registration:', error);
                alert('Registration error: ' + error.message);
            }
        });
    }
});
    
// ------------------ Toggle Functions ------------------
function showRegister() {
    document.getElementById("login-container").style.display = "none";
    document.getElementById("register-container").style.display = "block";
}
    
function showLogin() {
    document.getElementById("register-container").style.display = "none";
    document.getElementById("login-container").style.display = "block";
}