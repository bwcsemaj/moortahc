'use strict';


//CENTER CONTAINER NODES
var submitForgotCancelContainer = document.getElementById("submit-forgot-cancel-container");
var submitForgotCancelUserName = document.getElementById("submit-forgot-cancel-username");
var submitForgotCancelUserPassword = document.getElementById("submit-forgot-cancel-password");

var sendCodeEmailContainer = document.getElementById("send-code-email-container");
var sendCodeEmailEmail = document.getElementById("send-code-email-email");

var chatMessagesContainer = document.querySgetElementByIdelector("chat-messages-container");
var chatMessagesListGroup = document.getElementById("chat-messages-list-group");

// BOTTOM CONTAINER NODES
var loginRegisterContainer = document.getElementById("login-option-container");
var forgotOptionContainer = document.getElementById("forgot-option-container");
var enterCodeOptionContainer = document.getElementById("enter-code-option-container");
var sendMessageContainer = document.getElementById("enter-code-option-container");

var ctext=document.getElementById("c").value;



document.getElementById("submitter").addEventListener("click",function(){
    // same as onclick, keeps the JS and HTML separate
    didClickIt = true;
});