<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${faqDto.faq_title} FAQ</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
            margin-bottom: 20px;
        }
        .faq-title {
            font-size: 24px;
            margin-bottom: 10px;
        }
        .faq-meta {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: #555;
            margin-bottom: 20px;
        }
        .faq-meta div {
            margin-right: 20px;
        }
        .faq-content {
            font-size: 16px;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        .faq-closing {
            font-size: 14px;
            font-weight: bold;
            color: #333;
            margin-top: 20px;
        }
        .additional-info {
            margin-top: 40px;  /* Adds more space before this section */
            font-size: 14px;
            color: #555;
        }
        .button-group {
            display: flex;
            justify-content: space-between;
            margin-top: 20px; /* Adds space between the message and the button */
        }
        .back-button, .edit-button, .delete-button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .back-button:hover, .edit-button:hover, .delete-button:hover {
            background-color: #0056b3;
        }
        .edit-button {
            margin-right: 10px; /* Space between edit and delete buttons */
        }
        .delete-button {
            background-color: #dc3545;
        }
        .delete-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2 class="faq-title">${faqDto.faq_title}</h2>
    </div>

    <div class="faq-meta">
        <div><strong>분류유형:</strong> ${faqDto.categoryName}</div>
        <div><strong>작성자:</strong> ${faqDto.faq_writer}</div>
        <div><strong>작성일:</strong> ${faqDto.formattedRegDate}</div>
    </div>

    <div class="faq-content">
        ${faqDto.faq_content}
    </div>

    <div class="faq-closing">
        ${faqDto.faq_closing}
    </div>

    <!-- 버튼 그룹 -->
    <div class="button-group">
        <button class="back-button" onclick="location.href='${pageContext.request.contextPath}/faq'">목록으로 돌아가기</button>
        <div>
            <button class="edit-button" onclick="location.href='${pageContext.request.contextPath}/faq/edit?faq_no=${faqDto.faq_no}'">수정</button>
            <button class="delete-button" onclick="deleteFaq(${faqDto.faq_no})">삭제</button>
        </div>
    </div>
</div>


<%-- JavaScript로 작성한 코드 --%>
<script>
    function deleteFaq(faq_no) {
        if (confirm("FAQ를 삭제하시겠습니까?")) {
            fetch(`/faq/remove?faq_no=${faqDto.faq_no}`, {
                method: 'DELETE'  // DELETE 메서드로 요청
            })
                .then(response => {
                    if (response.ok) {  // 응답 상태가 200-299 범위일 때
                        return response.text();  // 응답 본문을 텍스트로 변환
                    } else {
                        throw new Error("FAQ 삭제 실패했습니다.");  // 상태 코드가 실패를 나타내면 오류 발생
                    }
                })
                .then(message => {
                    alert(message);  // 성공 메시지 표시
                    window.location.href = `/faq`;  // FAQ 목록 페이지로 리다이렉트
                })
                .catch(error => {
                    console.error('Error:', error);  // 콘솔에 오류 출력
                    alert("오류가 발생했습니다. 다시 시도해 주세요.");  // 사용자에게 오류 메시지 표시
                });
        }
    }
</script>
​





</body>
</html>