<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Product-Home</title>
	    <style>
            .page-list {
                list-style-type: none;
                padding: 0;
            }
            .page-list li {
                display: inline;
                margin: 5px;
            }
            .hidden {
                display: none;
            }
        </style>


<script>
    let productPage = 0;
    let limit = 10;
    const baseUrl = '';
    const visiblePageCount = 10; // 표시할 페이지 수
function getProducts(productPage, limit) {
fetch(baseUrl + 'list?page=' + encodeURIComponent(productPage) + '&limit=' + encodeURIComponent(limit))
    .then(response => response.json())
    .then(responseData => {
        console.log(responseData);
        const productList = document.getElementById('productList');

        // Clear the existing content
        productList.innerHTML = '';

        // Create the table and header row
        const table = document.createElement('table');
        table.className = 'product-table'; // Add a class for styling

        const headerRow = document.createElement('tr');
        const headers = ['상품 번호', '상품 이름', '상품 가격'];

        headers.forEach(headerText => {
            const th = document.createElement('th');
            th.textContent = headerText;
            headerRow.appendChild(th);
        });

        table.appendChild(headerRow);

        const products = responseData.dto || [];
        console.log(products);

        products.forEach(product => {
            const row = document.createElement('tr');

            // 상품 번호
            const productNumberCell = document.createElement('td');
            const numberLink = document.createElement('a');
            numberLink.href = baseUrl + 'detail?productNumber=' + encodeURIComponent(product.productNumber);
            numberLink.textContent = product.productNumber;
            productNumberCell.appendChild(numberLink);

            // 상품 이름
            const nameLink = document.createElement('a');
            nameLink.href = baseUrl + 'detail?productNumber=' + encodeURIComponent(product.productNumber);
            const productNameCell = document.createElement('td');
            nameLink.textContent = product.productName;
            productNameCell.appendChild(nameLink);

            // 상품 가격
            const priceLink= document.createElement('a');
            priceLink.href = baseUrl + 'detail?productNumber=' + encodeURIComponent(product.productNumber);
            const productPriceCell = document.createElement('td');
            priceLink.textContent = product.productPrice;
            productPriceCell.appendChild(priceLink);


            row.appendChild(productNumberCell);
            row.appendChild(productNameCell);
            row.appendChild(productPriceCell);

            table.appendChild(row);
        });

        productList.appendChild(table);
    })
    .catch(error => console.error('Error fetching product list:', error));
}
fetchPages(limit)
getProducts(productPage, limit);


        function setLimit(newLimit) {
            limit = newLimit;
            fetchPages(limit)
            getProducts(productPage, limit);
        }

        document.addEventListener('DOMContentLoaded', () => {

            document.getElementById('limit-10').addEventListener('click', () => setLimit(10));
            document.getElementById('limit-20').addEventListener('click', () => setLimit(20));
            document.getElementById('limit-50').addEventListener('click', () => setLimit(50));
        });


        function fetchPages() {
            fetch(baseUrl + 'pages?limit=' + encodeURIComponent(limit))
                .then(response => response.json())
                .then(data => {
                    const pageList = document.getElementById('pageList');
                    pageList.innerHTML = ''; // 기존 콘텐츠 제거

                    const pages = data.dto || [];
                    const totalPages = pages.length;

                    // 페이지 표시 범위 계산
                    const startPage = Math.max(productPage - Math.floor(visiblePageCount / 2), 0);
                    const endPage = Math.min(startPage + visiblePageCount - 1, totalPages - 1);

                    pages.forEach((page, index) => {
                        if (index >= startPage && index <= endPage) {
                            const listItem = document.createElement('li');
                            const pageLink = document.createElement('a');
                            pageLink.href = '#';
                            pageLink.textContent = page + 1;
                            pageLink.dataset.page = page;
                            pageLink.className = page === productPage ? 'current' : ''; // 현재 페이지 링크에 스타일 적용
                            pageLink.addEventListener('click', (event) => {
                                event.preventDefault();
                                productPage = parseInt(event.target.dataset.page, 10); // 현재 페이지 업데이트
                                getProducts(productPage, limit); // 선택한 페이지의 제품 목록 가져오기
                                fetchPages(); // 페이지 링크 업데이트
                            });
                            listItem.appendChild(pageLink);
                            pageList.appendChild(listItem);
                        }
                    });

                })
                .catch(error => console.error('Error fetching pages:', error));
        }

</script>
</head>
<body>
    <div class="toggle-container">
        <button id="limit-10">10 개씩 보기</button>
        <button id="limit-20">20 개씩 보기</button>
        <button id="limit-50">50 개씩 보기</button>
    </div>


    <h1>Product List</h1>
    <ul id="productList"></ul>

    <h1>Product Pages</h1>
    <ul id="pageList" class="page-list"></ul>

</body>
</html>
