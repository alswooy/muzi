<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chatbot</title>


</head>
<body>




<script>
    let endpoint = "/"
    // 챗봇 열고 닫는 함수
    function toggleChatbot() {
        var chatbot = $("#chatbot");
        if (chatbot.css("display") === "none" || chatbot.css("display") === "") {
            chatbot.css("display", "flex");
        } else {
            chatbot.css("display", "none");
        }
    }

    // 메시지 전송 함수
    function sendMessage() {
        var userInput = $("#userInput");
        var message = userInput.val().trim();

        // 입력값이 있는지 확인
        if (message === "") return;

        // 사용자 메시지를 채팅창에 추가
        var chatbox = $("#chatbot-messages");
        var userMessage = $("<div></div>").addClass("user-message").text("사용자: " + message);
        chatbox.append(userMessage);

        // 채팅창을 아래로 스크롤
        chatbox.scrollTop(chatbox.prop("scrollHeight"));

        // 입력창 초기화
        userInput.val("");

        // 서버로 메시지 전송 (기본 경로로 AJAX 요청)
        sendAjaxRequest(endpoint, message);
    }

    // 카테고리 선택 시 요청 보내는 함수
    function sendCategory(category, url) {
        $("#chatbot-category").text("선택한 카테고리: " + category);
        endpoint = url;
        // 사용자 선택을 채팅창에 추가
        var chatbox = $("#chatbot-messages");
        var userMessage = $("<div></div>").addClass("user-message").text("사용자: " + category + "을(를) 선택했습니다.");
        chatbox.append(userMessage);

        // 채팅창을 아래로 스크롤
        chatbox.scrollTop(chatbox.prop("scrollHeight"));

    }

    // jQuery로 AJAX 요청 함수
    function sendAjaxRequest(endpoint, data) {
        $.ajax({
            url: "http://localhost:5000" + endpoint,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({contents:data}),
            success: function (response) {
                // 서버 응답을 채팅창에 추가
                var chatbox = $("#chatbot-messages");
                var botMessage = $("<div></div>").addClass("bot-message").text("챗봇: " + response);
                chatbox.append(botMessage);

                // 채팅창을 아래로 스크롤
                chatbox.scrollTop(chatbox.prop("scrollHeight"));
            },
            error: function (xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    }

    // Enter 키로 메시지 전송
    function checkEnter(event) {
        if (event.key === "Enter") {
            sendMessage();
        }
    }
</script>
</body>
</html>