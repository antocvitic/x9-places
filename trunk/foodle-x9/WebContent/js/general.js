$(document).ajaxError(function(e, xhr, settings, exception) {
	error = "ajax error: " + exception + "\n" + settings.url + "\n" + xhr
	alert(error);
	showMessage({status: "Error", text: error.replace("\n", "<br/>\n")});
});

