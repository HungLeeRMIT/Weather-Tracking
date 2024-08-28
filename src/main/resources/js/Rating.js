
document.addEventListener('DOMContentLoaded', function () {
    // Query selectors
    const ratingButtons = document.querySelectorAll('.rating-card__ratings button');
    const submitButton = document.querySelector('.rating-card__btn');
    const ratingCardFront = document.querySelector('.rating-card__front');
    const ratingCardBack = document.querySelector('.rating-card__back');
    const ratingResultValue = document.querySelector('.rating-card__result--value');

    // Variable to store the selected rating
    let selectedRating = 0;

    // Add event listeners to rating buttons
    ratingButtons.forEach(button => {
    button.addEventListener('click', function() {
        selectedRating = this.textContent;
        
        // Remove 'selected' class from all buttons and then add it to the clicked one
        ratingButtons.forEach(btn => btn.classList.remove('selected'));
        this.classList.add('selected');
    });
});

    // Add click event listener to the submit button
    submitButton.addEventListener('click', function() {
        if (selectedRating === 0) {
            alert('Please select a rating before submitting.');
            return;
        }

        // Display the selected rating in the rating-card__back section
        ratingResultValue.textContent = selectedRating;

        // Hide the front card and show the back card
        ratingCardFront.style.display = 'none';
        ratingCardBack.style.display = 'block';
    });
});


