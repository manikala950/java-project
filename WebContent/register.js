
  document.getElementById("registerBtn").addEventListener("click", function (event) {
  event.preventDefault(); // Prevent form submission until validation

  const password = document.getElementById("password").value;
  const confirmPassword = document.getElementById("confirmPassword").value;
  const errorMessage = document.getElementById("error-message");

  // Define password validation rules
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  if (!passwordRegex.test(password)) {
    errorMessage.textContent = "Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a number, and a special character.";
    errorMessage.style.color = "red";
  } else if (password !== confirmPassword) {
    errorMessage.textContent = "Passwords do not match!";
    errorMessage.style.color = "red";
  } else {
    errorMessage.textContent = "Registration successful!";
    errorMessage.style.color = "green";
    // Here you can submit the form or proceed further
  }
});
   