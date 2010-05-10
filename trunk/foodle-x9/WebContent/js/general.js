$(document).ajaxError(function(e, xhr, settings, exception) {
	error = "ajax error: " + exception + "\n" + settings.url + "\n" + xhr
	alert(error);
	showMessage( {
		status : "Error",
		text : error.replace("\n", "<br/>\n")
	});
});

function imposeMaxLength(textArea, maxLength) {
	// less than, as returning true will okay insertion of a new character
	return (textArea.value.length < maxLength);
}
