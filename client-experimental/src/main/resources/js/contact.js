/**
 * Validate all contact form elements to make sure they have valid inputs
 */
function validateContactForm() {
    let statusElement = document.querySelector(".status");

    //Validate Name
    var name = document.getElementById("name").value;
    if (name == "") {
        statusElement.innerHTML = "Name cannot be empty";
        return false;
    }

    //Validate Email
    var email = document.getElementById("email").value;
    if (email == "" || !validateEmail(email)) {
        statusElement.innerHTML = "Invalid email";
        return false;
    }

    //Validate Phone Number
    var phoneNumber = document.getElementById("phonenumber").value;
    if (phoneNumber == "" || isNaN(phoneNumber) || phoneNumber.length != 10) {
        statusElement.innerHTML = "Invalid phone number";
        return false;
    }

    //Validate Zip Code
    var zipCode = document.getElementById("zipcode").value;
    if (zipCode == "" || isNaN(zipCode) || zipCode.length != 5) {
        statusElement.innerHTML = "Invalid zip code";
        return false;
    }

    //Validate Bip Code
    var bipCode = document.getElementById("bipcode").value;
    if (bipCode == "" || isNaN(bipCode) || bipCode.length != 5) {
        statusElement.innerHTML = "Invalid bip code";
        return false;
    }

    //Validate Description
    var description = document.getElementById("description").value;
    if (description == "") {
        statusElement.innerHTML = "Invalid description";
        return false;
    }
    statusElement.innerHTML = "";
    return true;
}

//https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
/**
 * Validate whether the string is a valid email address
 * @param {string} email 
 */
function validateEmail(email) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}