var username;
var password;

$('#login').click(function() {
  username = $('#username').val();
  password = $('#password').val();

  $.jCryption.getKeys('api/public/key?username=' + username, function(keys) {
    $.jCryption.encrypt(password, keys, function(encryptedPassword) {
      var loginData = {
        username: username,
        password: encryptedPassword
      };
      $.ajax({
        url: 'api/public/login',
        method: 'POST',
        data: JSON.stringify(loginData),
        contentType: 'application/json',
        success: function(data) {
          if (data.status.code = 200) {
            console.log(data.response);
          }
        }
      });
    });
  });
});
