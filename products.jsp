<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="shoppingcart.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            position: relative;
        }
        h1 {
            color: #333;
            text-align: center;
            margin-top: 20px;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-around;
        }
        .card {
            width: 300px;
            margin: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .card-header {
            background-color: #f2f2f2;
            padding: 12px;
            text-align: center;
            font-weight: bold;
        }
        .card-body {
            padding: 12px;
        }
        .card-footer {
            padding: 12px;
            text-align: center;
            position: relative;
        }
        .btn-add-to-cart {
            background-color: #4caf50;
            color: #fff;
            border: none;
            padding: 8px 12px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .btn-add-to-cart:hover {
            background-color: #45a049;
        }
          img {
            width: 100%;
            height: 200px; /* Fixed height for images */
            object-fit: cover; /* Ensure images cover the entire container */
        }
        .check-cart-button {
          position: absolute;
            top: 20px; /* Adjust as needed */
            right: 20px; /* Adjust as needed */
        }
    </style>
</head>
<body>
    <h1>Online Store</h1>
    
       
         <!-- Category dropdown -->
        <form action="products" method="get">
            <label for="category">Category:</label>
            <select id="category" name="category">
                <option value="" selected disabled>Select category</option>
                <option value="11">furniture</option>
                <option value="22">electronics</option>
                <option value="33">kids</option>
            </select>
            <br><br>
          <!-- Price-wise search option -->
    <div style="text-align: center;">
        <form action="products" method="get">
            <label for="minPrice">Min Price:</label>
            <input type="number" id="minPrice" name="minPrice" min="0">
            <label for="maxPrice">Max Price:</label>
            <input type="number" id="maxPrice" name="maxPrice" min="0">
            <button type="submit">Search</button>
        </form>
    </div>
    <% 
		if(session.getAttribute("username")==null)
		{
			
		%>
			<div id="login">
				<a href="login.jsp">login<i class="fa fa-sign-in"></i></a>
			</div>
			<div id="signup">
				<a href="signup.html">signup<i class="fa fa-user-plus" aria-hidden="true"></i></a>
			</div>
		<%
			}
			else
			{
		%>
		<div>
		welcome <%=session.getAttribute("username") %>
		<input type="text"id="custid" value=<%=session.getAttribute("id") %> name="id" hidden>
		</div>
		<%
			}
		%>
    <div class="container">
        <% 
            List<Product> products = (List<Product>)request.getAttribute("products");
            if(products != null) {
                for(Product product : products) {
        %>
        <div class="card">
            <div class="card-header"><%= product.getProductName() %></div>
            <div class="card-body">
             <img src="<%= product.getImage() %>" alt="<%= product.getProductName() %> Image" style="width: 100%;">
                <p><strong>Product ID:</strong> <%= product.getProductId() %></p>
                <p><strong>Price:</strong> $<%= product.getProductPrice() %></p>
                <p><strong>HSN:</strong> <%= product.getProductHsn() %></p>
            </div>
            <div class="card-footer">
                <form action="addToCart" method="post">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="hidden" name="productName" value="<%= product.getProductName() %>">
                    <label for="pincode">Enter Pincode:</label>
       				<input type="text" id="pincode" name="pincode">
                    <button type="submit" class="btn-add-to-cart" >Add to Cart</button>
      </form>
            </div>
        </div>
        <% 
                }
            }
        %>
    </div>
    <div  class="check-cart-button">
        <form action="login.jsp" method="get">
            <button type="submit">Check Cart Items</button>
        </form>
    </div>
    
</body>
</html>
