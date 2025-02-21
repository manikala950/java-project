document.getElementById('loginForm').addEventListener('submit', function(event) {
     event.preventDefault(); // Prevent the default form submission

     // Redirect to booking page after form submission
     window.location.href = './form.html';
   });
   