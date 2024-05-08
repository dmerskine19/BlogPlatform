document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');

    // Event listener for login form submission
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the form from submitting normally
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            if (username && password) {
                console.log('Login attempt with:', username, password);
                
                // Send AJAX request to log the user in
                fetch('/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({username: username, password: password})
                })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to log in');
                    }
                })
                .then(data => {
                    console.log('Login successful:', data);
                    // Redirect to another page or handle the logged-in state
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error logging in: ' + error.message);
                });
            } else {
                alert('Please enter both username and password.');
            }
        });
    }

    // Event listener for registration form submission
    if (registerForm) {
        registerForm.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the form from submitting normally
            const username = document.getElementById('reg-username').value;
            const password = document.getElementById('reg-password').value;
            const email = document.getElementById('reg-email').value;
            if (username && password && email) {
                console.log('Registration attempt with:', username, email);
                
                // Send AJAX request to register the user
                fetch('/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({username: username, password: password, email: email})
                })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to register');
                    }
                })
                .then(data => {
                    console.log('Registration successful:', data);
                    // Handle successful registration, e.g., show a success message
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error registering user: ' + error.message);
                });
            } else {
                alert('Please complete all fields.');
            }
        });
    }
});
