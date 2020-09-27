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
var loginOptionSubmitButton = document.getElementById("login-option-submit-button");
var loginOptionRegisterButton = document.getElementById("login-option-forgot-button");
var loginOptionCancelButton = document.getElementById("login-option-register-button");


var forgotOptionContainer = document.getElementById("forgot-option-container");
var forgotOptionSendCodeButton = document.getElementById("forgot-option-send-code-button");
var forgotOptionCancelButton = document.getElementById("forgot-option-cancel-button");


var enterCodeOptionContainer = document.getElementById("enter-code-option-container");
var enterCodeOptionEnterCodeButton = document.getElementById("enter-code-option-enter-code-button");
var enterCodeOptionCancelButton = document.getElementById("enter-code-option-cancel-button");

var sendMessageContainer = document.getElementById("send-message-container");
var sendMessageSendButton = document.getElementById("send-message-send-button");
var sendMessageTextArea = document.getElementById("send-message-text-area");



document.getElementById("submitter").addEventListener("click",function(){
    // same as onclick, keeps the JS and HTML separate
    didClickIt = true;
});