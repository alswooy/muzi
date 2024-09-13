<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>Product Form</title>
</head>
<body>
    <form id="productForm">
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

    <div class="container">
        <h1>동적 데이터 추가 폼</h1>
        <div id="key-value-container">
            <div class="key-value-pair">
                <input type="text" class="key-input" placeholder="키 입력">
                <div class="value-container">
                    <input type="text" class="value-input" placeholder="값 입력">
                    <button class="add-value-btn">값 추가</button>
                </div>
                <button class="remove-btn">제거</button>
            </div>
        </div>
        <button id="add-key-btn">키 추가</button>
        <button id="submit-btn">데이터 제출</button>
        <pre id="output"></pre>
    </div>


        <button type="submit">Submit</button>
    </form>

        <form action="/multi" method="POST" enctype="multipart/form-data">
            <input type="file" name="multipartFile" accept="image/*" required>
            <button type="submit">Upload</button>
        </form>


    <script>
document.getElementById('productForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    // Create a FormData object from the form
    const formData = new FormData(this);

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
            jsonObject[key] = false;
        }
    });

    handleFormSubmit();
    console.log(jsonObject);

    // Send AJAX request

    const combinedData = {
        ...jsonObject,
        ...data
    };
    console.log(combinedData);

    fetch('/product/add-product', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(combinedData)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
        alert('Product added successfully!');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred while adding the product.');
    });
});

// Prevent form submission on key-value operations
document.getElementById('add-key-btn').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent default form submission

    const keyValueContainer = document.getElementById('key-value-container');
    const newKeyValuePair = document.createElement('div');
    newKeyValuePair.classList.add('key-value-pair');
    newKeyValuePair.innerHTML = `
        <input type="text" class="key-input" placeholder="키 입력">
        <div class="value-container">
            <input type="text" class="value-input" placeholder="값 입력">
            <button class="add-value-btn">값 추가</button>
        </div>
        <button class="remove-btn">제거</button>
    `;
    keyValueContainer.appendChild(newKeyValuePair);
});

document.body.addEventListener('click', function(event) {
    if (event.target.classList.contains('add-value-btn')) {
        event.preventDefault(); // Prevent default form submission

        const valueInput = event.target.previousElementSibling;
        const valueContainer = event.target.parentElement;

        // 새로운 값 입력 필드 생성
        const newValueInput = document.createElement('input');
        newValueInput.type = 'text';
        newValueInput.classList.add('value-input');
        newValueInput.placeholder = '값 입력';

        // 값 추가 버튼 생성
        const newAddValueBtn = document.createElement('button');
        newAddValueBtn.classList.add('add-value-btn');
        newAddValueBtn.innerText = '값 추가';

        valueContainer.appendChild(newValueInput);
        valueContainer.appendChild(newAddValueBtn);
    }
});

document.body.addEventListener('click', function(event) {
    if (event.target.classList.contains('remove-btn')) {
        event.preventDefault(); // Prevent default form submission

        const keyValueDiv = event.target.parentElement;
        keyValueDiv.remove();
    }
});
    let data = {};

document.getElementById('submit-btn').addEventListener('click', handleFormSubmit);

function handleFormSubmit() {
    event.preventDefault(); // Prevent default form submission

    data = {};

    const keyValuePairs = document.querySelectorAll('.key-value-pair');
    keyValuePairs.forEach(pair => {
        const key = pair.querySelector('.key-input').value;
        const values = Array.from(pair.querySelectorAll('.value-container .value-input'))
            .map(input => input.value)
            .filter(value => value);

        if (key) {
            data[key] = values;
        }
    });

    // Log the data object for debugging
    Object.keys(data).forEach(key => {
        console.log(`Key: ${key}, Value: ${data[key]}`);
        // Example output: Key: 1, Value: ['2']
    });

    document.getElementById('output').innerText = JSON.stringify(data, null, 2);

    const jsondata = JSON.stringify(data);
    console.log(jsondata);
}
    </script>

</body>
</html>
