<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="shoppingcart.YourDatabaseConnection" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f0f0f0;
    }

    h1 {
        text-align: center;
        margin-top: 20px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    th, td {
        padding: 8px;
        text-align: center;
        border-bottom: 1px solid #ddd;
    }

    th {
        background-color: #f2f2f2;
    }

    tr:hover {
        background-color: #f5f5f5;
    }

    .total-row td {
        background-color: #f2f2f2;
        font-weight: bold;
        text-align: center;
    }
     .proceed-btn {
        margin-top: 20px;
        text-align: center;
    }
</style>
</head>
<body>
    <h1>Order Details</h1>
    <table>
        <thead>
            <tr>             
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Product Price</th>
                <th>discount</th>
                <th>Product GST</th>
                <th>Actual Amount</th>
                <th>total product price</th>
                <th>shipping value</th>
               
            </tr>
        </thead>
        <tbody>
            <% 
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            int totalAmount = 0 ;
            int tpq=0;
            double ship=100;
            int total=0;
           double totalshipcharges=0;
           int quantity=0;
           int  productId=0;
            try {
                connection = YourDatabaseConnection.getConnection(); // Implement your database connection method
                String query = "SELECT c.product_id, c.product_name, COUNT(*) AS quantity, " +
                        "p.productprice, COUNT(*) * p.productprice AS total_price, " +
                        "g.gst, d.discount " +
                        "FROM carts181 c " +
                        "JOIN product181 p ON c.product_id = p.productid " +
                        "JOIN gst181 g ON p.producthsn = g.producthsn " +
                        "JOIN dproduct181 d ON p.productid = d.productid " +
                        "GROUP BY c.product_id, c.product_name, p.productprice, g.gst, d.discount";

                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    productId = resultSet.getInt("product_id");
                    String productName = resultSet.getString("product_name");
                     quantity = resultSet.getInt("quantity");
                    int productPrice = resultSet.getInt("productprice");
                    int gst = resultSet.getInt("gst");
                    int discount=resultSet.getInt("discount");
                    int originalPrice = quantity * productPrice;
                    double totalPrice =  ((double) ( productPrice) / (1 + ((double)gst / 100)));
                    double dis=totalPrice*((double)discount/100);
                    double actualPrice=totalPrice-dis;
                    double gstp =( (double)productPrice -  (totalPrice));
                   double productgst=((actualPrice*gstp)/totalPrice);
                   //System.out.println("jhssdhdghj"+productgst);
                   total=(int)(actualPrice+productgst);
                   tpq=(int)(actualPrice+productgst)*quantity;
                   totalAmount+=tpq;
                   double shipproduct=((double)total/totalAmount)* ship;
                   double gship=(shipproduct*((double)gst/100))*quantity;
                   totalshipcharges+=gship;
                 
            %>
            <tr>                
                <td><%= productId %></td>
                <td><%= productName %></td>
                <td><%= quantity %></td>
                <td>$<%= productPrice %></td>
                <td>$<%= dis %></td>
                <td>$<%= productgst %></td>
                <td>$<%= actualPrice %></td>
                <td>$<%=total %></td>
                <td><%=gship %></td>
                
            </tr>
            <% 
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle database errors appropriately
            } finally {
                // Close resources
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (statement != null)
                        statement.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            %>
              <tr  class="total-row">
                <td colspan="8" style="text-align: right;"><strong>Total Amount:</strong></td>
                <td>$<%=totalAmount %></td>
            </tr>
            
              <%
              double shippingCharges =0;

           // Query to fetch shipping charges based on the total amount
          String shippingQuery = "SELECT ship_value FROM shipping181 WHERE ? BETWEEN ship_from AND ship_to";

           try {
               connection = YourDatabaseConnection.getConnection(); // Implement your database connection method
               statement = connection.prepareStatement(shippingQuery);
              statement.setInt(1,tpq);
             
               resultSet = statement.executeQuery();
              
               
               if (resultSet.next()) {
                   shippingCharges = resultSet.getInt("ship_value");
                // System.out.println(shippingCharges);
                  
               }
           } catch (SQLException e) {
               e.printStackTrace(); // Handle database errors appropriately
           } finally {
                // Close resources
                try {
                    if (resultSet != null)
                        resultSet.close();
                    if (statement != null)
                        statement.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

           double finalBill = totalAmount+ shippingCharges+ totalshipcharges;
          
          

    %>
   <tr class="total-row">
        <td colspan="8" style="text-align: right;"><strong>Shipping Charges:</strong></td>
        <td>$<%= shippingCharges %></td>
    </tr>
    <tr class="total-row">
        <td colspan="8" style="text-align: right;"><strong>Final Bill:</strong></td>
        <td>$<%= finalBill %></td>
    </tr>
        </tbody>        
    </table>   
  <form>
        <input type="text" id="coupon" placeholder="Enter Coupon Code">
        <button type="button" onClick='applyCoupon()'>Apply Coupon</button>
    </form>
    

  <script>

    function applyCoupon() {
        var couponCode = document.getElementById("coupon").value;
        var finalBill = <%= finalBill %>;
      
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4) {
                if (this.status == 200) {
                    var response = this.responseText;
                    if (response === "success") {
                    	
                        alert("Coupon applied successfully!");
                        window.location.reload();
                    } else {
                        alert("Coupon application failed: " + response); // Display error message
                    }
                } else {
                    alert("Coupon application failed. Please try again. Error: " + xhttp.statusText); // Display HTTP status text
                }
            }
        };
        xhttp.open("POST", "ApplyCouponServlet", true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send("couponCode=" + encodeURIComponent(couponCode) + "&finalBill=" + finalBill );
    }
</script>
  
    
    
   <form action="https://www.example.com/payment/success/" method="POST">
<script
    src="https://checkout.razorpay.com/v1/checkout.js"
    data-key="rzp_test_c3lZVlCRJQVBEX" 
    data-amount="29935" 
    data-currency="INR"
    data-order_id="order_CgmcjRh9ti2lP7"
    data-buttontext="Pay with Razorpay"
    data-name="Acme Corp"
    data-description="A Wild Sheep Chase is the third novel by Japanese author Haruki Murakami"
    data-image="https://example.com/your_logo.jpg"
    data-prefill.name="Gaurav Kumar"
    data-prefill.email="gaurav.kumar@example.com"
    data-theme.color="#F37254">
</script>
<input type="hidden" custom="Hidden Element" name="hidden"/>
</form> 
</body>
</html>
