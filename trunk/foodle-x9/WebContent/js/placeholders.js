function removePlaceholder(inputObject)
{
	//alert('remove');
	if (inputObject.name=="password") {
		inputObject.type="password";
	}
	if (inputObject.value==(inputObject.name.substr(0, 1).toUpperCase() + inputObject.name.substr(1))) {
		inputObject.value='';
		inputObject.style.color='black';
	}
	
}

function addPlaceholder(inputObject)
{
	//alert('add');
	if (inputObject.value=='') {
		inputObject.value=inputObject.name.substr(0, 1).toUpperCase() + inputObject.name.substr(1);
		inputObject.style.color='gray';
		
		if (inputObject.name=="password") {
			inputObject.type='text';
		}
	}
}

function loadPlaceholders()
{
	document.login_form.username.focus();
	document.login_form.username.blur();
	document.login_form.password.focus();
	document.login_form.password.blur();
}