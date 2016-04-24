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
  localStorage.setItem('currentUser', signupData);
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
  var userData = localStorage.getItem('currentUser');
  if (userData !== null) {
    var username = JSON.parse(userData).username;
    $('#username').val(username);
  }
}

$('#savepassword').click(function() {
  
});