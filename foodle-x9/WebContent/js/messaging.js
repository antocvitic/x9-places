function showMessage(msgObject, dontSlide) {
	if (msgObject.status == "ok") {
		$("#msg").removeClass("msg_error");
		$("#msg").addClass("msg_msg");
	} else {
		$("#msg").removeClass("msg_msg");
		$("#msg").addClass("msg_error");
	}
	$("#msg #msg_content").html(msgObject.text);
	if (dontSlide) {
		$("#msg").show();
	} else {
		$("#msg").slideDown();
	}
}

function closeMessageBox() {
	//$("#msg").hide();
	//$("#msg").fadeOut("slow", function(){$("#msg").hide();});
	$("#msg").slideUp();
}