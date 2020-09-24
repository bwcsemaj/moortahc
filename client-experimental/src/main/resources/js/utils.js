'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var password = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];



// let request = new XMLHttpRequest();
// request.open("POST", "http://localhost:8080/a/authenticate2?emailaddress=a&password=password");
// request.send();
// console.log("HELLO");
// request.onload = () => {
//     console.log(request);
// };




    // $.ajax({
    //     url: 'http://localhost:8080/resourceserver/protected-no-scope',
    //     type: 'GET',
    //     contentType: 'application/json'
    //     headers: {
    //        'Authorization': 'Bearer <token>'
    //     },
    //     success: function (result) {
    //         // CallBack(result);
    //     },
    //     error: function (error) {

    //     }
    //  });

function test(){
    return fetch("http://localhost:8082/a/", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({ emailAddress: "a", password: "password" })
    })
        .then(response => {
            return response.headers.get("Authorization").split(" ")[1];
        })
        .then(headers => {
            console.log(headers);
        });
}

usernamePage.classList.add('hidden');
chatPage.classList.remove('hidden');

var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);

stompClient.connect({}, onConnected, onError);
    username = document.querySelector('#name').value.trim();
    password = document.querySelector('#password').value.trim();


    if (username) {
        fetch("https://localhost:8080/a",
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify({ emailAddress: "a", password: "password" })
            })
            .then(response => {
                return response.headers;
            })
            .then(headers => {
                console.log(headers);
            });


        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }


function connect(event) {
    username = document.querySelector('#name').value.trim();
    password = document.querySelector('#password').value.trim();

    if (username) {
        fetch("https://localhost:8080/a",
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                method: "POST",
                body: JSON.stringify({ emailAddress: "a", password: "password" })
            })
            .then(response => {
                return response.headers;
            })
            .then(headers => {
                console.log(headers);
            });


        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({ sender: username, type: 'JOIN' })
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
