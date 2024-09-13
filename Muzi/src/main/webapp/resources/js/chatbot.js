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
        url: "http://localhost:5001" + endpoint,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({contents:data}),
        xhrFields: {
            withCredentials: true  // 쿠키 전송 활성화
        },
        success: function (response) {
            // 서버 응답을 채팅창에 추가
            var chatbox = $("#chatbot-messages");
            var botMessage = $("<div></div>").addClass("bot-message").html("챗봇: " + response);
            chatbox.append(botMessage);

            // 채팅창을 아래로 스크롤
            chatbox.scrollTop(chatbox.prop("scrollHeight"));
        },
        error: function (xhr, status, error) {
            console.error("Error: " + error);
        }
    });
}

const observer = new MutationObserver(function(mutationsList, observer) {
    mutationsList.forEach(mutation => {
        if (mutation.type === 'childList') {
            // 비동기로 추가된 요소 중 '.result-container' 클래스가 있는지 확인
            const newElements = mutation.target.querySelectorAll('.result-container');
            newElements.forEach(element => {
                if (element.classList.contains('processed')) {
                    return;  // 이미 'processed' 클래스가 있으면 리턴
                }

                getImage($(element));  // 새로 추가된 요소에 함수 적용
                element.classList.add('processed')
            });
        }
    });
});

// 감시할 대상 설정
const config = { childList: true, subtree: true };

// 감시할 노드 설정 (예: body 태그)
const targetNode = document.body;
observer.observe(targetNode, config);

function getImage(element) {
    
    var image_id = element.find('.main-image').val();
    console.log('image_id:', image_id);  // 확인
    
    if (image_id == null) {
        console.log("No image_id found");
        return;
    }

    fetch("http://localhost:8080/get-image?id=" + image_id)
        .then(response => response.blob())  // Blob 형식으로 응답 받음
        .then(imageBlob => {
            const imageObjectURL = URL.createObjectURL(imageBlob);
            const img = document.createElement('img');

            img.src = imageObjectURL;
            img.alt = 'Image from MySQL';
            img.style.width = '100px';
            img.style.height = '100px';

            element.append(img);
        })
        .catch(error => {
            console.error('Error fetching image:', error);
        });
}

// Enter 키로 메시지 전송
function checkEnter(event) {
    if (event.key === "Enter") {
        sendMessage();
    }
}