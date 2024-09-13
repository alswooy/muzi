<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Product-Home</title>
    <style>
        .toggle-content {
            display: none;
        }
        .toggle-button {
            cursor: pointer;
            color: blue;
            text-decoration: underline;
        }
    </style>
    <script>
        function toggleContent(id) {
            var content = document.getElementById(id);
            var isVisible = content.style.display === 'block';
            content.style.display = isVisible ? 'none' : 'block';
        }
    </script>
</head>
<body>
<%@ include file="header.jspf" %>
<link rel="stylesheet" href="/css/productDetail.css" />
<h1>Product Details</h1>
<c:if test="${not empty product}">
    <table border="1">
        <tr>
            <th>상품 번호</th>
            <td>${product.productNumber}</td>
        </tr>
        <tr>
            <th>상품 이름</th>
            <td>${product.productName}</td>
        </tr>
        <tr>
            <th>상품 가격</th>
            <td>${product.productPrice}</td>
        </tr>
        <tr>
            <th>배송비</th>
            <td>${product.deliveryFee}</td>
        </tr>
    </table>
</c:if>
<form id="cartForm" method="post">
    <c:forEach var="option" items="${productOption}">
        <label>${option.key} : </label>
        <select id="option_${option.key}" name="option_${option.key}" onchange="updateProductOption()">
            <c:forEach var="detail" items="${option.value}">
                <option value="${detail.optionDetail}">${detail.optionDetail}</option>
            </c:forEach>
        </select>
        <br>
    </c:forEach>
    <!-- hidden 필드 -->
    <input type="hidden" id="productOption" name="productOption" value="">
    <br><br>
    <!-- 수량 입력 -->
    <label for="quantity">수량:</label>
    <input type="number" id="quantity" name="productCnt" min="1" value="1" required>
    <input type="hidden" id="productNumber" name="productNo" value="${product.productNumber}">
    <input type="hidden" id="productPrice" name="productPrice" value="${product.productPrice}">
    <input type="hidden" id="orderType" name="orderType" value="바로구매">
    <th>배송비</th>
    <c:set var="totalAmount" value="${product.productPrice * 2}" />
    <c:choose>
        <c:when test="${totalAmount >= 30000}">
            <input type="hidden" name="productDeliveryPrice" value="0" readonly>
            <span>0원</span>
        </c:when>
        <c:otherwise>
            <input type="hidden" name="productDeliveryPrice" value="${product.deliveryFee}" readonly>
            <span>${product.deliveryFee}원</span>
        </c:otherwise>
    </c:choose>    <br><br>
    <!-- 제출 버튼 -->
    <button type="submit" id="cartAdd">장바구니</button>
    <button type="submit" id="order">바로구매</button>

</form>

<form id="updateProductForm" method="get" action="update-product">
    <!-- 상품 번호를 숨겨진 필드로 설정 -->
    <input type="hidden" id="productNumber" name="productNumber" value="${product.productNumber}">
    <!-- 수정 버튼 -->
    <button type="submit" id="updateProductButton">상품 수정</button>
</form>
<c:forEach var="entry" items="${attribute}">
    <c:if test = "${entry.key==0}">
        <h2>상품 예시 이미지</h2>
    </c:if>
    <c:if test = "${entry.key==1}">
        <h2>상품 상세 이미지</h2>
    </c:if>
    <ul>
        <c:forEach var="picture" items="${entry.value}">
            <li>
                <img src="${picture.pictureUrl}" alt="${picture.productDetailTitle}" />
                <p>ID: ${picture.pictureId}</p>
            </li>
        </c:forEach>
    </ul>
</c:forEach>
<script>
    function updateProductOption() {
        // 선택된 옵션들을 담을 배열
        let selectedOptions = [];
        // 모든 select 요소를 반복하면서 선택된 값을 배열에 추가
        document.querySelectorAll('select[id^="option_"]').forEach(function(select) {
            selectedOptions.push(select.value);
        });
        // 선택된 옵션들을 콤마(,)로 구분하여 결합
        let combinedValue = selectedOptions.join(', ');
        // hidden 필드의 value 값 설정
        document.getElementById('productOption').value = combinedValue;
    }
    // 페이지 로드 시, 혹은 옵션 변경 시마다 실행되도록 설정
    window.onload = updateProductOption;
    $(document).ready(function() {
        // 에러 메시지가 있을 경우 경고창 표시 후 로그인 페이지로 리다이렉트
        <c:if test="${not empty errorMessage}">
        alert("${errorMessage}");
        window.location.href = "/login"; // 로그인 페이지로 이동
        </c:if>
        // 장바구니 버튼 클릭 시
        $("#cartAdd").click(function() {
            $("#cartForm").attr("action", "/cart/add").submit();
            alert("장바구니에 담았습니다.");
        });
        // 바로구매 버튼 클릭 시
        $("#order").click(function() {
            if ($("#errorMessage").length === 0) { // 에러 메시지가 없을 때만 실행
                if (confirm("정말 구매하시겠습니까??") == true) { // 확인
                    $("#cartForm").attr("action", "/cart/order").submit();
                } else { // 취소
                    return false;
                }
            }
        });
    });
</script>
</body>
</html>