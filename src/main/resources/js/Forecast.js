window.switchChannel = function(channel) {
    // Iterate through all channels and stop any playing videos
    for (let i = 1; i <= 10; i++) {
        const div = document.getElementById('hiddenDiv' + i);
        if (div) {
            const iframe = div.querySelector('iframe');
            if (iframe) {
                // Stop the video from playing by resetting the src attribute
                iframe.src = iframe.src.split('?')[0];
            }
            // Hide the div by default
            div.style.display = 'none';
        }
    }

    // Show the selected channel
    const selectedDiv = document.getElementById('hiddenDiv' + channel);
    if (selectedDiv) {
        const iframe = selectedDiv.querySelector('iframe');
        if (iframe) {
            // Append '?autoplay=1' to the src to start the video
            iframe.src += '?autoplay=1';
            selectedDiv.style.display = 'block';
        }
    }
};
