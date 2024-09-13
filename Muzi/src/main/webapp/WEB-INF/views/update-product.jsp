<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>
    <title>Product Form</title>
</head>

<body>
    <form id="productForm" method="POST">
        <label for="productPrice">Product Price:</label>
        <input type="number" id="productPrice" name="productPrice" required><br><br>

        <label for="productName">Product Name:</label>
        <input type="text" id="productName" name="productName" required><br><br>

        <label for="newItem">New Item:</label>
        <input type="checkbox" id="newItem" name="newItem"><br><br>

        <label for="postingStatus">Posting Status:</label>
        <input type="checkbox" id="postingStatus" name="postingStatus"><br><br>

        <label for="discountable">Discountable:</label>
        <input type="checkbox" id="discountable" name="discountable"><br><br>

        <label for="notice">Notice:</label>
        <input type="text" id="notice" name="notice"><br><br>

        <label for="productCode">Product Code:</label>
        <input type="text" id="productCode" name="productCode"><br><br>

        <label for="deliveryFee">Delivery Fee:</label>
        <input type="number" id="deliveryFee" name="deliveryFee" required><br><br>

        <label for="privateProduct">Private Product:</label>
        <input type="checkbox" id="privateProduct" name="privateProduct"><br><br>



        <button type="submit">Submit</button>
    </form>

    <div class="container">
        <h1>옵션</h1>
        <button id="add-key-btn">키 추가</button>
        <div id="key-value-container">
        </div>
        <button id="submit-btn">데이터 제출</button>
        <pre id="output"></pre>
    </div>


    <form action="/multi" method="POST" enctype="multipart/form-data">
        <input type="file" name="multipartFile" accept="image/*" required>
        <button type="submit">Upload</button>
        <div id="image-container"></div>
    </form>


    <script>
        const originData = {};
        fetch(`${baseUrl}info?productNumber=${productNumber}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                originData['data'] = data;
                addForm(data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });

        function addForm(data) {
            document.getElementById('productPrice').value = data.productDto.productPrice;
            document.getElementById('productName').value = data.productDto.productName;
            document.getElementById('newItem').checked = data.productDto.newItem;
            document.getElementById('postingStatus').checked = data.productDto.postingStatus;
            document.getElementById('discountable').checked = data.productDto.discountable;
            document.getElementById('notice').value = data.productDto.notice;
            document.getElementById('productCode').value = data.productDto.productCode;
            document.getElementById('deliveryFee').value = data.productDto.deliveryFee;
            document.getElementById('privateProduct').checked = data.productDto.privateProduct;

            const optionNames = Object.keys(data.options);


            addKey(null, data.options);

        }




        // Prevent form submission on key-value operations
        document.getElementById('add-key-btn').addEventListener('click', addNewKey);

        function addNewKey(event) {
            const optionTypes = originData.data.optionTypes;

            if (event) {
                event.preventDefault(); // Prevent default form submission
            }
            const keyValueContainer = document.getElementById('key-value-container');
            const newKeyValuePair = document.createElement('div');
            newKeyValuePair.classList.add('key-value-pair');
            newKeyValuePair.innerHTML = `
            <button class="remove-key-btn">제거</button>
            <br>
            <select class="option-type-select"></select>
            <input type="text" class="key-input" placeholder="키 입력" value=>
            <input type="checkbox" class="required-value" name="required-"">
            <label for="required-">필수 옵션</label>
            <div class="value-container">
                <button class="add-value-btn">값 추가</button>
                <br>
            </div>
            <br><br><br>
        `;

                        // optionTypes 배열을 사용해 select에 옵션 추가
                const selectElement = newKeyValuePair.querySelector('.option-type-select');

                optionTypes.forEach((type) => {
                    const option = document.createElement('option');
                    option.value = type;
                    option.text = type;
                    selectElement.appendChild(option);
                });


            keyValueContainer.appendChild(newKeyValuePair);
        }

        function validateOptionRequired(options) {
            for (let i = 0; i < options.length; i++) {
                console.log(options[i].required)
                if (options[i].required == false) {
                    return 'checked';
                }
            }
            return 'checked';
        }

        function addKey(event, options) {
            const optionTypes = originData.data.optionTypes;
            if (event) {
                event.preventDefault(); // Prevent default form submission
            }

            console.log(options);
            const optionNames = Object.keys(options);

            for (let i = 0; i < optionNames.length; i++) {
                let optionName = optionNames[i];
                console.log(optionName);
                let optionDetails = options[optionName];
                console.log(optionDetails);

                const keyValueContainer = document.getElementById('key-value-container');
                const newKeyValuePair = document.createElement('div');
                newKeyValuePair.classList.add('key-value-pair');

                // 기본 구조 생성
                newKeyValuePair.innerHTML = `
            <button class="remove-key-btn">제거</button>
            <br>
            <select class="option-type-select"></select>
            <input type="text" class="key-input" placeholder="키 입력" value=` + optionName + `>
            <input type="checkbox" class="required-value" name="required-`+ optionName+`"` + validateOptionRequired(optionDetails)+`>
            <label for="required-"`+optionName+`>필수 옵션</label>
            <div class="value-container">
                <button class="add-value-btn">값 추가</button>
                <br>
            </div>
            <br><br><br>
        `;

                // optionTypes 배열을 사용해 select에 옵션 추가
                const selectElement = newKeyValuePair.querySelector('.option-type-select');

                optionTypes.forEach((type) => {
                    const option = document.createElement('option');
                    option.value = type;
                    option.text = type;
                    console.log(optionDetails[0].optionType);
                    console.log(option.value);
                    if (type == optionDetails[0].optionType) {
                        option.selected = true;
                    }
                    selectElement.appendChild(option);
                });

                const valueContainer = newKeyValuePair.querySelector('.value-container');
                // optionDetails 배열의 각 요소를 input 태그로 추가
                optionDetails.forEach((detail) => {
                    const value = document.createElement('div');
                    const newValueInput = document.createElement('input');
                    const deleteValue = document.createElement('button');
                    const status = document.createElement('input');
                    const statusLabel = document.createElement('label');
                    const optionId = document.createElement('input');

                    newValueInput.type = 'text';
                    newValueInput.classList.add('value-input');
                    newValueInput.placeholder = '값 입력';
                    newValueInput.value = detail.optionDetail; // 배열의 각 값을 input의 value로 설정

                    deleteValue.innerText = "삭제";
                    deleteValue.classList.add('remove-value-btn');

                    status.type = "checkbox";
                    status.name = "status";
                    status.classList.add("status-value");
                    status.id = `status-` + detail.optionNumber; // 고유 ID 설정
                    status.checked = detail.status; // detail의 상태 값을 체크 여부로 설정


                    // Label 설정
                    statusLabel.htmlFor = status.id; // label의 for 속성은 체크박스 ID와 동일하게 설정
                    statusLabel.innerText = "공개"; // Label의 텍스트


                    optionId.value = detail.optionNumber;
                    optionId.readOnly = true;
                    optionId.type = 'hidden';
                    optionId.classList.add('value-id');

                    value.appendChild(newValueInput);
                    value.appendChild(deleteValue);
                    value.appendChild(status);
                    value.appendChild(statusLabel);
                    value.appendChild(optionId);
                    value.appendChild(document.createElement('br'));
                    valueContainer.appendChild(value);
                });

                keyValueContainer.appendChild(newKeyValuePair);
            }
        }


        document.body.addEventListener('click', function (event) {
            if (event.target.classList.contains('add-value-btn')) {
                event.preventDefault(); // Prevent default form submission

                const valueInput = event.target.previousElementSibling;
                const valueContainer = event.target.parentElement;



                // 새로운 값 입력 필드 생성
                const value = document.createElement('div');
                const newValueInput = document.createElement('input');
                const newLine = document.createElement('br');
                const deleteValue = document.createElement('button');
                const status = document.createElement('input');
                const statusLabel = document.createElement('label');

                value.classList.add("option-value-container");

                newValueInput.type = 'text';
                newValueInput.classList.add('value-input');
                newValueInput.placeholder = '값 입력';

                deleteValue.innerText = "삭제 ";
                deleteValue.classList.add('remove-btn');

                status.type = "checkbox";
                status.name = "status";
                status.classList.add("status-value");

                // Label 설정
                statusLabel.htmlFor = status.id; // label의 for 속성은 체크박스 ID와 동일하게 설정
                statusLabel.innerText = "공개"; // Label의 텍스트




                value.appendChild(newValueInput);
                value.appendChild(deleteValue);
                value.appendChild(status);
                value.appendChild(statusLabel);
                value.appendChild(newLine);
                valueContainer.appendChild(value);

            }
        });

        document.body.addEventListener('click', function (event) {
            if (event.target.classList.contains('remove-key-btn')) {
                event.preventDefault(); // Prevent default form submission
                const keyValueDiv = event.target.parentElement;
                console.log(keyValueDiv.querySelector('.key-input').value);
                let optionDeleteRequestDto = {};
                optionDeleteRequestDto['productNumber'] = `${productNumber}`
                optionDeleteRequestDto['optionName'] = keyValueDiv.querySelector('.key-input').value;

                console.log(optionDeleteRequestDto);
                fetch(`${baseUrl}delete-option`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(optionDeleteRequestDto)
                    }

                ).then(response => {
                    if (!response.ok) {
                        throw new Error('Server error');
                    }
                    return response.json();
                });
                console.log(keyValueDiv.querySelector('.value-id').value)
            }
        });

        document.body.addEventListener('click', function (event) {
            if (event.target.classList.contains('remove-value-btn')) {
                event.preventDefault(); // Prevent default form submission

                const keyValueDiv = event.target.parentElement;

                if (event.target.parentElement.querySelector('.value-id').value == 0) {
                    console.log(event.target.parentElement.querySelector('.value-id').value)
                    fetch(`${baseUrl}delete-option-value?optionNumber=` + keyValueDiv.querySelector('.value-id')
                            .value, {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                            }

                        )
                        .then(response => response.json())
                        .then(data => console.log(data))
                    console.log(keyValueDiv.querySelector('.value-id').value)

                }
            }
        })

        document.getElementById('productForm').addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission
            formData = handleFormSubmit();
            fetch(
                `${baseUrl}update-product`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body:JSON.stringify(formData)
            }
            )
            console.log(formData);
        });

        document.getElementById('submit-btn').addEventListener('click', handleFormSubmit);

        function handleFormSubmit() {
            let data = {
                options: []
            };

            const keyValuePairs = document.querySelectorAll('.key-value-pair');
            keyValuePairs.forEach(pair => {
                const key = pair.querySelector('.key-input').value; // optionName
                const valueContainers = Array.from(pair.querySelectorAll(
                    '.value-container > div')); // 각각의 value-container 내부의 div들 가져오기

                valueContainers.forEach(container => {
                    const valueInput = container.querySelector('.value-input').value; // optionDetail
                    const optionId = container.querySelector('.value-id') ?.value; // optionNumber

                    // status-value 체크박스 값을 가져옴 (각 container 별로)
                    const status = container.querySelector('.status-value') ?.checked || false;

                    // required-value 체크박스 값을 가져옴 (각 container 별로)
                    const required = container.parentElement.parentElement.querySelector('.required-value') ?.checked || false;

                    const optionType = container.parentElement.parentElement.querySelector(
                        '.option-type-select').value;
                    console.log(optionType);

                    if (key && valueInput) {
                        data.options.push({
                            optionName: key,
                            optionDetail: valueInput,
                            optionNumber: optionId, // value-id 값을 optionNumber로 추가
                            status: status, // 체크박스의 checked 상태 추가
                            required: required, // 체크박스의 checked 상태 추가
                            optionType: optionType
                        });
                    }
                });
            });

            // 디버깅용으로 data 객체 로그 출력
            console.log(JSON.stringify(data, null, 2));

            ;
            // 출력 부분
            document.getElementById('output').innerText = JSON.stringify(data, null, 2);
            return toJson(data.options);
        }

        function toJson(options) {

        
        const formData = new FormData(document.getElementById('productForm'));

        // Create an empty object to hold form data
        const jsonObject = {};

        // Iterate over formData and build the JSON object
        formData.forEach((value, key) => {
            if (key.endsWith('[]')) {
                // Handle array types if needed
                const fieldName = key.slice(0, -2);
                if (!jsonObject[fieldName]) {
                    jsonObject[fieldName] = [];
                }
                jsonObject[fieldName].push(value);
            } else {
                jsonObject[key] = value;
            }
        });

        // Convert checkbox values to boolean
        Object.keys(jsonObject).forEach(key => {
            if (jsonObject[key] === 'on') {
                jsonObject[key] = true;
            } else if (jsonObject[key] === '') {
                jsonObject[key] = null;
            }
        });
        jsonObject.productOptions = options;
        jsonObject.productNumber = new URLSearchParams(window.location.search).get('productNumber');
        return jsonObject;
    }
    </script>

</body>

</html>