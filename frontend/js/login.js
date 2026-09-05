const loginForm = document.getElementById("loginForm");
const errorMessage = document.getElementById("errorMessage");

loginForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    errorMessage.textContent = "";

    try {

        const response = await fetch(
            "http://localhost:8080/api/auth/login",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    username: username,
                    password: password
                })
            }
        );

        if (!response.ok) {
            throw new Error("Invalid username or password");
        }

        const user = await response.json();

        console.log("Login successful:", user);

        /*
         * Store the logged-in user's information.
         */
        sessionStorage.setItem(
            "pcrmsUser",
            JSON.stringify(user)
        );

        /*
         * Redirect based on role.
         */
        if (user.role === "ADMIN") {

            window.location.href = "admin/dashboard.html";

        } else if (user.role === "INSPECTOR") {

            window.location.href = "inspector/dashboard.html";

        } else if (user.role === "CLERK") {

            window.location.href = "clerk/dashboard.html";

        } else {

            errorMessage.textContent =
                "Unknown user role.";

        }

    } catch (error) {

        console.error(error);

        errorMessage.textContent =
            "Login failed. Please check your username and password.";
    }
});