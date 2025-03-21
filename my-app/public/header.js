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
  let userRole = "";
  
  if (jwt) {
    // If token exists, decode and show user info with a Logout button.
    try {
      const payloadBase64 = jwt.split('.')[1];
      const payloadJson = atob(payloadBase64);
      const payload = JSON.parse(payloadJson);
      const username = payload.sub || "User";
      userRole = payload.role || "N/A";
      
      headerContent = `
        <div>
          Welcome, <strong>${username}</strong> (Role: ${userRole})
          ${userRole.toLowerCase() === 'root' ? `<button id="admin-btn" style="${buttonStyle} background: #007BFF; color: #fff; margin-left: 10px;">Admin</button>` : ""}
        </div>
        <div>
          <button id="home-btn" style="${buttonStyle} background: #4CAF50; color: #fff; margin-right: 10px;">Home</button>
          <button id="auth-btn" style="${buttonStyle} background: #e53935; color: #fff;">Logout</button>
        </div>
      `;
      
    } catch (err) {
      console.error("Failed to decode JWT:", err);
      // Fallback for invalid token: show guest interface.
      headerContent = `
        <div>Welcome, Guest</div>
        <div>
          <button id="home-btn" style="${buttonStyle} background: #4CAF50; color: #fff; margin-right: 10px;">Home</button>
          <button id="auth-btn" style="${buttonStyle} background: #468966; color: #fff;">Login</button>
        </div>
      `;
    }
  } else {
    // If no token exists, show login button.
    headerContent = `
      <div>Welcome, Guest</div>
      <div>
        <button id="home-btn" style="${buttonStyle} background: #4CAF50; color: #fff; margin-right: 10px;">Home</button>
        <button id="auth-btn" style="${buttonStyle} background: #468966; color: #fff;">Login</button>
      </div>
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
  
  // Set up the action for the admin button if it exists.
  const adminBtn = document.getElementById("admin-btn");
  if (adminBtn) {
    adminBtn.addEventListener('click', function() {
      window.location.href = 'admin.html'; // Adjust this URL to your admin page.
    });
  }
  
  // Set up the Home button to navigate to the homepage.
  const homeBtn = document.getElementById("home-btn");
  if (homeBtn) {
    homeBtn.addEventListener('click', function() {
      window.location.href = 'index.html'; // Adjust this URL to your home page if needed.
    });
  }
});