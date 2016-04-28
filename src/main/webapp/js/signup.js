var emailId;
var firstName;
var lastName;
var username;
var password;
var signupData;

$('#signup').click(function() {
  emailId = $('#emailid').val();
  firstName = $('#firstname').val();
  lastName = $('#lastname').val();
  username = $('#username').val();
  password = $('#password').val();
  signupData = JSON.stringify({
    emailId: emailId,
    firstName: firstName,
    lastName: lastName,
    username: username
  });
  $.ajax({
    url: 'api/signup',
    method: 'POST',
    data: signupData,
    contentType: 'application/json',
    success: function(data) {
      if (data.status.code === 201) {
        $.jCryption.getKeys('api/key?username=' + username, function(keys) {
          $.jCryption.encrypt(password, keys, function(encryptedPassword) {
            var passwdData = {
              username: username,
              password: encryptedPassword
            };
            $.ajax({
              url: 'api/signup/password',
              method: 'POST',
              data: JSON.stringify(passwdData),
              contentType: 'application/json',
              success: function(data) {
                if (data.status.code = 200) {
                  alert("Signup done");
                }
              }
            });
          });
        });
      } else {
        $('#message').css('color', 'FF0000');
        $('#message').html('Error !! Die Bitch !!');
      }
    }

  });
});
