$(document).ready(function() {
    
	$(".pager_maxreturned_selector").change(function() {
		//alert("selected: " + $(this).val());
		document.location.href = $(this).val();
	});

});