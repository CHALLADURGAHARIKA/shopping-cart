<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="shoppingcart.Item" %>
<%@ page import="shoppingcart.ProductDAO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart Items</title>
<style>
 .cards-container {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around; /* Adjust as needed */
    }
    .card {
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        transition: 0.3s;
        width: 200px;
        margin: 10px;
    }
    .card:hover {
        box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
    }
    .container {
        padding: 2px 16px;
    }
    .btn-plus, .btn-minus {
        padding: 5px 10px;
        cursor: pointer;
        border: none;
        background-color: #4caf50;
        color: white;
        border-radius: 3px;
        font-size: 16px;
        margin: 5px;
    }
    .btn-remove {
        padding: 5px 10px;
        cursor: pointer;
        border: none;
        background-color: #f44336;
        color: white;
        border-radius: 3px;
        font-size: 16px;
        margin: 5px;
    }
    .btn-buy {
        padding: 8px 16px;
        cursor: pointer;
        border: none;
        background-color: #007bff;
        color: white;
        border-radius: 3px;
        font-size: 16px;
        margin-top: 20px;
    }
</style>
</head>
<body>
    <h1>Cart Items</h1>
    <div class="cards-container">
        <% 
            List<Item> items = (List<Item>)request.getAttribute("items");
            if(items != null) {
                for(Item item : items) {
        %>
        <div class="card">
            <div class="container">
                <h4><b><%= item.getProductName() %></b></h4>
                <p>Product ID: <%= item.getProductId() %></p>
                <p>Quantity: <span id="quantity_<%= item.getProductId() %>"><%= item.getCount() %></span></p>
                <button class="btn-plus" onclick="incrementQuantity('<%= item.getProductId() %>')">+</button>
                <button class="btn-minus" onclick="decrementQuantity('<%= item.getProductId() %>')">-</button>
                <button class="btn-remove" onclick="removeItem('<%= item.getProductId() %>')">Remove</button>
            </div>
        </div>
        <% 
                }
            }
        %>
    </div>
 <!-- Checkout Button -->
    <button class="btn-buy" onclick="checkout()">Checkout</button>
    <script>
        function incrementQuantity(productId) {
            var quantityElement = document.getElementById('quantity_' + productId);
            var currentQuantity = parseInt(quantityElement.innerText);
            quantityElement.innerText = currentQuantity + 1;
        }

        function decrementQuantity(productId) {
            var quantityElement = document.getElementById('quantity_' + productId);
            var currentQuantity = parseInt(quantityElement.innerText);
            if (currentQuantity > 0) {
                quantityElement.innerText = currentQuantity - 1;
            }
        }
        

        function removeItem(productId) {
            // Send AJAX request to remove the item from the database
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "RemoveItemServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // Refresh the page after removing the item
                        location.reload();
                    } else {
                        alert("Failed to remove item.");
                    }
                }
            };
            xhr.send("productId=" + encodeURIComponent(productId));
        }
        
        // Checkout function
        function checkout() {
            // Send AJAX request to CheckoutServlet
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "CheckoutServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // Redirect to checkout.jsp after successful checkout
                        window.location.href = "checkout.jsp";
                    } else {
                        var errorMessage = xhr.responseText; // Get error message from response
                        alert("Error: " + errorMessage);
                    }
                }
            };
            xhr.send();
        }

    </script>
</body>
</html>
