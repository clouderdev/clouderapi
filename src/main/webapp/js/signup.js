$('#signup').click(function() {
  var emailId = $('#emailid').val();
  var firstName = $('#firstname').val();
  var lastName = $('#lastname').val();
  var username = $('#username').val();
  var signupData = JSON.stringify({
    emailId: emailId,
    firstName: firstName,
    lastName: lastName,
    username: username
  });
  ajax('api/signup', 'POST', signupData, 'application/json', toPasswordPage);
});

function toPasswordPage(data) {
  if (data.status.code === 201) {
    setMessage('message', 'Details saved. Enter password on next page.',
            '#00FF00');
  } else {
    setMessage('message', 'Error!! Die bitch !!', '#FF0000');
  }
  setTimeout(function() {
    location.href = 'signup2.html';
  }, 4000)
}
function savePasswordFlow() {
  getPublicKey
}