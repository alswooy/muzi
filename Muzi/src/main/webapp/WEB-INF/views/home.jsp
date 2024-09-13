<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<c:set var="loginOutLink" value="${sessionScope.c_email==null ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${sessionScope.c_email==null ? '로그인' : '로그아웃'}"/>
<c:set var="mypageLink" value="${sessionScope.c_email==null ? '' : '/mypage'}"/>
<c:set var="mypage" value="${sessionScope.c_email==null ? '' : '마이페이지'}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="/css/chatbot.css">
    <style>
       .slider {
          box-sizing: border-box;
       }

       .slider {
          position: relative;
          width: 100%;
          max-width: 600px;
          margin: auto;
          overflow: hidden;
       }

       .slides {
          display: flex;
          transition: transform 0.5s ease-in-out;
       }

       .slides img {
          width: 100%;
          max-width: 600px;
       }

       .prev, .next {
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
          background-color: rgba(0, 0, 0, 0.5);
          color: white;
          border: none;
          padding: 10px;
          cursor: pointer;
       }

       .prev {
          left: 10px;
       }

       .next {
          right: 10px;
       }
    </style>
</head>
<body>
<%@ include file="header.jspf" %>
<div class="slider">
    <div class="slides">
       <img src="/img/image1.png" alt="Image 1">
       <img src="/img/image2.png" alt="Image 2">
       <img src="/img/image3.png" alt="Image 3">
    </div>
    <button class="prev">&#10094;</button>
    <button class="next">&#10095;</button>
</div>
<a class="cart_link" id="logoutLink" href="<c:url value='${loginOutLink}'/>">${loginOut}</a>
<a class="cart_link" id="mypage" href="<c:url value='${mypageLink}'/>">${mypage}</a>
<!-- 챗봇 열기 버튼 -->
<button class="open-chatbot-btn" onclick="toggleChatbot()">💬</button>
<!-- 챗봇 모달창 -->
<div class="chatbot-container" id="chatbot">
    <div class="chatbot-header">
       <span>Chatbot</span>
       <button class="close-btn" onclick="toggleChatbot()">X</button>
    </div>

    <!-- 카테고리 표시 -->
    <div class="chatbot-category" id="chatbot-category">
       선택한 카테고리: 없음
    </div>

    <!-- 채팅 메시지 표시 영역 -->
    <div class="chatbot-messages" id="chatbot-messages">
       <div class="bot-message">안녕하세요! 무엇을 도와드릴까요?</div>
    </div>

    <!-- 사용자 입력 필드 및 전송 버튼 -->
    <div class="chatbot-input">
       <input type="text" id="userInput" placeholder="메시지를 입력하세요..." onkeypress="checkEnter(event)">
       <button onclick="sendMessage()">보내기</button>
    </div>

    <!-- 버튼들 (공지사항, FAQ, 주문, 상품) -->
    <div class="chatbot-buttons" style="padding: 10px; display: flex; justify-content: space-around;">
       <button onclick="sendCategory('일상대화', '/')">일상대화</button>
       <button onclick="sendCategory('공지사항', '/notice')">공지사항</button>
       <button onclick="sendCategory('FAQ', '/faq')">FAQ</button>
       <button onclick="sendCategory('주문', '/order')">주문</button>
       <button onclick="sendCategory('상품', '/product')">상품</button>
    </div>
</div>

<script src="/js/chatbot.js"></script>
<script>
    $(document).ready(function() {
       let currentSlide = 0;
       const $slides = $('.slides');
       const totalSlides = $('.slides img').length;

       function showSlide(index) {
          if (index < 0) {
             currentSlide = totalSlides - 1;
          } else if (index >= totalSlides) {
             currentSlide = 0;
          } else {
             currentSlide = index;
          }
          const offset = -currentSlide * 100 + '%';
          $slides.css('transform', 'translateX(' + offset + ')');
       }

       // Next button click
       $('.next').click(function() {
          showSlide(currentSlide + 1);
       });

       // Prev button click
       $('.prev').click(function() {
          showSlide(currentSlide - 1);
       });

       // Automatically move to the next slide every 1 second (1000 ms)
       setInterval(function() {
          showSlide(currentSlide + 1);
       }, 1000); // 1초마다 자동 슬라이드
    });

</script>
</body>
</html>
