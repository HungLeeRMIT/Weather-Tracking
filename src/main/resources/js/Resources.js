function toggleInfo(event, cardElement) {
  // Prevent the function from running if the click is not on 'Read More' or 'Read Less' link
  if (!event.target.matches('.read-more') && !event.target.matches('.read-less')) {
      return;
  }

  const additionalInfo = cardElement.querySelector('.additional-info');
  const readMoreLink = cardElement.querySelector('.read-more');
  const readLessLink = cardElement.querySelector('.read-less');

  if (additionalInfo.style.display === 'none' || !additionalInfo.style.display) {
      additionalInfo.style.display = 'block';
      readMoreLink.style.display = 'none';
      readLessLink.style.display = 'block';
  } else {
      additionalInfo.style.display = 'none';
      readMoreLink.style.display = 'block';
      readLessLink.style.display = 'none';
  }
}



document.addEventListener('DOMContentLoaded', function() {
  function startDownload(buttonId, statusId, progressId) {
    var downloadButton = document.getElementById(buttonId);
    var downloadStatus = document.getElementById(statusId);
    var progressBar = document.getElementById(progressId);
    var percentageText = downloadStatus.querySelector('span');

    downloadButton.hidden = true;
    downloadStatus.hidden = false;

    let progress = 0;
    let intervalId = setInterval(function() {
      progress += 10; // Increase for faster fill
      percentageText.textContent = progress + '%';
      progressBar.style.width = progress + '%';

      if (progress >= 100) {
        clearInterval(intervalId);
        percentageText.textContent = 'Download complete';
        progressBar.style.width = '100%'; // Ensure full fill

        // Trigger the download of the file here
        // This will not open the file explorer but will download the file to the default download folder
        const link = document.createElement('a');
        link.href = 'src/main/resources/night-drive'; // You need to replace this with the actual file path
        link.download = 'resources.html'; // Replace with the actual file name and extension
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }
    }, 500); // Update every half second
  }

  // Attach event listeners to each download button
  const downloadButtons = document.querySelectorAll('.download-btn');
  downloadButtons.forEach(button => {
    button.addEventListener('click', function() {
      const card = button.closest('.card-masterclass');
      const statusId = card.querySelector('.download-status').id;
      const progressId = card.querySelector('.progress').id;

      startDownload(button.id, statusId, progressId);
    });
  });
});
