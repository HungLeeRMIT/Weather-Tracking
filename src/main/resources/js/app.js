$(document).ready(function() {
    // Initially hide state and city inputs
    $('.main-state, .main-city').hide();

    $('.main-btn').click(function() {
        $('.search-description').slideToggle(100);
    });

    $('.search-description li').click(function() {
        var target = $(this).text();
        var toRemove = 'By ';
        var newTarget = target.replace(toRemove, '').toLowerCase();
        $(".search-large").html(newTarget);
        $('.search-description').hide();
        $('.main-input').hide();
        
        if (newTarget === 'country' || newTarget === 'state' || newTarget === 'city') {
            $('.main-' + newTarget).show();
        } else {
            $('.main-country').show(); // Show country by default
        }
    });

    $('#main-submit-mobile').click(function() {
        $('#main-submit').trigger('click');
    });

    $(window).resize(function() {
        replaceMatches();
    });

    function replaceMatches() {
        var width = $(window).width();
        if (width < 516) {
            $('.main-location').attr('value', 'City or postal code');
        } else {
            $('.main-location').attr('value', 'Search by city or postal code');
        }
    }

    replaceMatches();

    function clearText(thefield) {
        if (thefield.defaultValue == thefield.value) {
            thefield.value = "";
        }
    }

    function replaceText(thefield) {
        if (thefield.value == "") {
            thefield.value = thefield.defaultValue;
        }
    }
});