function ajax(url, method, data, contentType, success) {
  $.ajax({
    url: url,
    method: method,
    data: data,
    contentType: contentType,
    success: success
  });
}

function setMessage(elementId, message, color) {
  $('#' + elementId).css("color", color);
  $('#' + elementId).html(message);
}