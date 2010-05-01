/** Remove placeholder
 * Removes the placeholder when the object is selected (gets focus).
 * Changes passwords back to password-type.
 * 
 * @param inputObject	The object with the placeholder
 */
function removePlaceholder(inputObject)
{
	
	if (inputObject.name=="password") {
		inputObject.type="password";
	}
	if (inputObject.value==(inputObject.name.substr(0, 1).toUpperCase() + inputObject.name.substr(1))) {
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
		inputObject.value=inputObject.name.substr(0, 1).toUpperCase() + inputObject.name.substr(1);
		inputObject.style.color='gray';
		
		if (inputObject.name=="password") {
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
	document.login_form.username.focus();
	document.login_form.username.blur();
	document.login_form.password.focus();
	document.login_form.password.blur();
}