$(document).ajaxError(function(e, xhr, settings, exception) {
	alert("ajax error: " + exception + "\n" + settings.url + "\n" + xhr);
});