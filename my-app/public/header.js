document.addEventListener('DOMContentLoaded', function() {
    const jwt = localStorage.getItem('jwtToken');
    if (jwt) {
        const payloadBase64 = jwt.split('.')[1];
        try {
            const payloadJson = atob(payloadBase64);
            const payload = JSON.parse(payloadJson);
            const username = payload.sub || "User";
            const role = payload.role || "N/A";

            const headerDiv = document.createElement("div");
            headerDiv.id = "header";
            headerDiv.style.cssText = `
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                background: #333;
                color: #fff;
                padding: 10px 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 4px rgba(0,0,0,0.3);
                z-index: 1000;
                box-sizing: border-box;
                overflow: visible;
            `;

            headerDiv.innerHTML = `
                <div>Welcome, <strong>${username}</strong> (Role: ${role})</div>
                <button id="logout-btn" style="
                    padding: 5px 10px;
                    border: none;
                    background: #e53935;
                    color: #fff;
                    border-radius: 4px;
                    cursor: pointer;
                ">Logout</button>
            `;

            document.body.insertBefore(headerDiv, document.body.firstChild);
            
            const loginLink = document.querySelector('.login-link');
            if (loginLink) {
                loginLink.style.display = "none";
            }
            
            document.getElementById("logout-btn").addEventListener('click', function() {
                localStorage.removeItem('jwtToken');
                window.location.href = 'login.html';
            });
        } catch (err) {
            console.error("Failed to decode JWT:", err);
        }
    }
});