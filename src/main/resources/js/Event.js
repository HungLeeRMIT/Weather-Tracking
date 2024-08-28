 //script for Event card selection
        document.addEventListener('DOMContentLoaded', function() {
            // Function to handle card side selection
            function selectCardSide(side) {
                localStorage.setItem('selectedCardSide', side); // Store selection
            }
    
            // Get the card elements
            var cardFront = document.querySelector('.card-front');
            var cardBack = document.querySelector('.card-back');
    
            // Set event listeners for card sides
            cardFront.addEventListener('click', function() {
                selectCardSide('front');
            });
    
            cardBack.addEventListener('click', function() {
                selectCardSide('back');
            });
    
            // Event listener for "Choose Payment" buttons
            var choosePaymentButtons = document.querySelectorAll('.link');
            choosePaymentButtons.forEach(function(button) {
                button.addEventListener('click', function() {
                    // Check if a card side has been selected
                    var selectedCardSide = localStorage.getItem('selectedCardSide');
                    if (!selectedCardSide) {
                        alert('Please select a card side first.');
                    } else {
                        // Redirect to Payment.html with the selected card side
                        window.location.href = 'Payment.html?cardSide=' + selectedCardSide;
                    }
                });
            });
        });
