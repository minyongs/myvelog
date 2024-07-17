$(document).ready(function() {
    $('#username').on('blur', function() {
        var username = $(this).val();
        $.ajax({
            url: '/checkUsername',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ username: username }),
            success: function(response) {
                if (response.exists) {
                    $('#usernameError').show();
                } else {
                    $('#usernameError').hide();
                }
            }
        });
    });

    $('#email').on('blur', function() {
        var email = $(this).val();
        $.ajax({
            url: '/checkEmail',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: email }),
            success: function(response) {
                if (response.exists) {
                    $('#emailError').show();
                } else {
                    $('#emailError').hide();
                }
            }
        });
    });

    $('#signupForm').on('submit', function(event) {
        var password = $('#password').val();
        var confirmPassword = $('#confirmPassword').val();

        if (password !== confirmPassword) {
            $('#passwordError').show();
            event.preventDefault();
        } else {
            $('#passwordError').hide();
        }
    });
});
