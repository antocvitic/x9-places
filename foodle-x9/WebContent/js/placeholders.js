
function getPlaceholderText(inputObject) {
	return inputObject.name.substr(0, 1).toUpperCase() + inputObject.name.substr(1).replace("_", " ")
}

/** Remove placeholder
 * Removes the placeholder when the object is selected (gets focus).
 * Changes passwords back to password-type.
 * 
 * @param inputObject	The object with the placeholder
 */
function removePlaceholder(inputObject)
{
	if ($(inputObject).hasClass("password_placeholder")) {
		inputObject.type="password";
	}
	if (inputObject.value==getPlaceholderText(inputObject)) {
		inputObject.value='';
		inputObject.style.color='black';
	}
}

/** Add placeholder
 * If the input object is empty it ads a placeholder to describe what to insert.
 * Changes passwords into text to be able to display the placeholder
 * 
 * @param inputObject	The object that gets the placeholder
 */
function addPlaceholder(inputObject)
{
	if (inputObject.value=='') {
		inputObject.value=getPlaceholderText(inputObject);
		inputObject.style.color='gray';
		
		if ($(inputObject).hasClass("password_placeholder")) {
			inputObject.type='text';
		}
	}
}

/** Load placeholders
 * Focuses and Blurs the input fields in the header so that the placeholders get put in place.
 * This is because input object doesn't have the onLoad property.
 */
function loadPlaceholders()
{
	$('.placeholder').each(function(i){
		this.focus();
		this.blur();
	});
	// TODO: add the onFocus / onBlur functions here instead of when declaring each object
}

/**
 * This is to disable any placeholders before submitting a form. Requires that the form has an
 * id
 */
$(document).ready(function() {
	$("form").submit(function() {
	    $("#" + this.id + " .placeholder").each(function(i) {
			removePlaceholder(this);
		});
	    return true;
	})
});


$(document).ready(loadPlaceholders);