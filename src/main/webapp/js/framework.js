var keys;
var encryptedUser = "";
var encryptedPassword = "";

/**
 * Default call back for all ajax post requests
 * 
 * @param data
 */
function defaultCallback(data) {
	try {
		// asdfs
	} catch (e) {
	}
}

/**
 * Custom Post function which generates time in each request and adds some
 * security parameters on each request
 * 
 * @param url
 * @param parameters
 * @param callback
 * @param dataType
 */
function sendAjaxRequest(url, parameters, callback, dataType) {
	if (url != undefined && url != null) {
		if (url.indexOf("?") == -1) {
			url = url + "?1=1";
		} else {
			url = url + "&1=1";
		}
	}

	try {
		var mydate = new Date();
		url = url + "&uniqueTimestamp=" + mydate.getFullYear()
				+ mydate.getMonth() + mydate.getDay() + mydate.getHours()
				+ mydate.getSeconds() + mydate.getUTCMilliseconds();
	} catch (e) {
	}

	// url= url + "&mediUser="+ mediUser+"&mediPass="+mediEncryptedPassword;

	// $('#processing-form').dialog('open');
	if (callback) {
		$.post(url, parameters, function(data) {
			// $('#processing-form').dialog('close');
			eval(callback(data));
		});
	} else {
		$.post(url, parameters, defaultCallback);
	}
}

/**
 * Get Security keys from server so that we can encrypt request in future
 */
function getKeys() {
	$.jCryption.getKeys("EncryptionServlet?generateKeypair=true", function(
			receivedKeys) {
		keys = receivedKeys;
	});
}

/**
 * Called on Login Button clicked
 */
function onLoginButtonClicked() {
	var user = $("#login_user").val();
	var password = $("#login_password").val();
	$.jCryption.encrypt(user, keys, function(encrypted) {
		encryptedUser = encrypted;
		$.jCryption.encrypt(password, keys, function(encryptedPasswd) {
			encryptedPassword = encryptedPasswd;
			/**
			 * As both userName and password are encrypted now Submit login
			 */
			submitLoginRequest();
		});
	});
}

/**
 * Submit Login request
 */
function submitLoginRequest() {
	sendAjaxRequest("LoginServlet", {
		username : encryptedUser,
		password : encryptedPassword
	}, function(data) {
		if (data.length > 0) {
			$("#login_status").empty();
			$("#login_status").append(data);
		}
	});
}
