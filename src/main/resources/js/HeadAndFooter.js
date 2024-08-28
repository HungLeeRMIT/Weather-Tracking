    
    // Script for Navbar Interaction
document.addEventListener('DOMContentLoaded', function() {
  // Function to toggle navbar menu
  function toggleMenu() {
      document.querySelector('.nav-links').classList.toggle('active');
  }

  // Add click event listener to menu for responsive design
  document.querySelector('.responsive-menu').addEventListener('click', toggleMenu);

  // Function to remove 'active' class from all navbar links
  function removeAllActive() {
      document.querySelectorAll('.nav-links a').forEach(link => {
          link.classList.remove('active');
      });
  }

  // Add 'active' class to the navbar link that matches current page
  const currentLocation = window.location.href;
  document.querySelectorAll('.nav-links a').forEach(link => {
      if (link.href === currentLocation) {
          removeAllActive();
          link.classList.add('active');
      }
      // Add click event listener to each link
      link.addEventListener('click', function() {
          removeAllActive();
          this.classList.add('active');
      });
  });

  // Function to toggle the user dropdown menu
  function toggleUserDropdown() {
      document.getElementById("userDropdown").classList.toggle("show");
  }

  // Add click event listener to user icon for dropdown
  document.querySelector('.user-avatar-img').addEventListener('click', toggleUserDropdown);

  // Close the dropdown if the user clicks outside of it
  window.onclick = function(event) {
      if (!event.target.matches('.user-avatar-img')) {
          var dropdowns = document.getElementsByClassName("user-dropdown-content");
          for (var i = 0; i < dropdowns.length; i++) {
              var openDropdown = dropdowns[i];
              if (openDropdown.classList.contains('show')) {
                  openDropdown.classList.remove('show');
              }
          }
      }
  };

  // User Authentication Check
  var isUserLoggedIn = localStorage.getItem('isUserLoggedIn');
  var userDropdown = document.getElementById('userDropdown');
  if (isUserLoggedIn === 'true') {
      userDropdown.innerHTML = `
          <a href="User.html">Account</a>
          <a href="#" onclick="signOut()">Sign Out</a>
      `;
  }
});

// Function to handle sign out
function signOut() {
  localStorage.setItem('isUserLoggedIn', 'false');
  window.location.href = '/';
}
