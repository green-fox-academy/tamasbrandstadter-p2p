function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic', function (message) {
            console.log(message);
            window.location.reload();
        });
    });
}

connect();

// function sendMessage() {
//     stompClient.send("/", {}, JSON.stringify({'username': $("#message.username").val(),
//                                             'text':$("#message.text").val()}));
// }
//
// function showMessage(message) {
//     $("#greetings").append("<tr><td>" + message + "</td></tr>");
// }
//
// function sendName() {
//     stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
// }


// setTimeout(function(){
//     window.location.reload(1);
// }, 5000);