var ws;

init = function () {
    ws = new WebSocket("ws://localhost:8080/AdminWebapp/log");
    ws.onopen = function (event) {
    };
    ws.onmessage = function (event) {
        var $textarea = document.getElementById("messages");
        $textarea.value = $textarea.value + event.data + "\n";
        console.log("onmessage: " + event.data)
    };
    ws.onclose = function (event) {
    }
};

function sendTextMessage() {
    var messageField = document.getElementById("message");
    var userNameField = document.getElementById("username");
    var message = userNameField.value + ":" + messageField.value;
    sendCommonMessage("msg", message);
    messageField.value = '';
}

function sendCommonMessage(command, text) {
    var message = "{" +
        "command:'"+command+"',"+
        "text:'"+text+"'"+
        "}";
    console.log("send: " + message);
    ws.send(message);
}