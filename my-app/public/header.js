document.addEventListener('DOMContentLoaded', function() {
    // Create a header container that will always be displayed in the same fixed location.
    const headerDiv = document.createElement("div");
    headerDiv.id = "header";
    headerDiv.style.cssText = `
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      background: #8E2800;
      color: #fff;
      padding: 10px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-shadow: 0 2px 4px rgba(0,0,0,0.3);
      z-index: 1000;
      box-sizing: border-box;
    `;
    
    // Define button common styles
    const buttonStyle = `
      padding: 5px 10px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    `;
    
    let headerContent = "";
    const jwt = localStorage.getItem('jwtToken');
    
    if (jwt) {
      // If token exists, decode and show user info with a Logout button.
      try {
        const payloadBase64 = jwt.split('.')[1];
        const payloadJson = atob(payloadBase64);
        const payload = JSON.parse(payloadJson);
        const username = payload.sub || "User";
        const role = payload.role || "N/A";
        
        headerContent = `
          <div>Welcome, <strong>${username}</strong> (Role: ${role})</div>
          <button id="auth-btn" style="${buttonStyle} background: #e53935; color: #fff;">Logout</button>
        `;
      } catch (err) {
        console.error("Failed to decode JWT:", err);
        // Fallback for invalid token: show guest interface.
        headerContent = `
          <div>Welcome, Guest</div>
          <button id="auth-btn" style="${buttonStyle} background: #468966; color: #fff;">Login</button>
        `;
      }
    } else {
      // If no token exists, show login button.
      headerContent = `
        <div>Welcome, Guest</div>
        <button id="auth-btn" style="${buttonStyle} background: #468966; color: #fff;">Login</button>
      `;
    }
    
    headerDiv.innerHTML = headerContent;
    document.body.insertBefore(headerDiv, document.body.firstChild);
    
    // Set up the action for the auth button.
    document.getElementById("auth-btn").addEventListener('click', function() {
      if (jwt) {
        // If logged in, log out the user.
        localStorage.removeItem('jwtToken');
        window.location.href = 'login.html';
      } else {
        // Otherwise, direct the user to the login page.
        window.location.href = 'login.html';
      }
    });
  });