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
                <th>without gst</th>
                <th>Product GST</th>
                <th>Actual Amount</th>
                <th>GST percentage</th>
                <th>shipping</th>
            </tr>
        </thead>
        <tbody>
            <% 
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            int totalAmount = 0 ;
            double ship=100;
            try {
                connection = YourDatabaseConnection.getConnection(); // Implement your database connection method
                String query = "SELECT c.product_id, c.product_name, COUNT(*) AS quantity, p.productprice, " +
                        "COUNT(*) * p.productprice AS total_price, g.gst " +
                        "FROM carts181 c " +
                        "JOIN product181 p ON c.product_id = p.productid " +
                        "JOIN gst181 g ON p.producthsn = g.producthsn " +
                        "GROUP BY c.product_id, c.product_name, p.productprice, g.gst";
                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");
                    int productPrice = resultSet.getInt("total_price");
                    int gst = resultSet.getInt("gst");
                    int originalPrice = quantity * productPrice;
                    double totalPrice = (int) ((double) (quantity * productPrice) / (1 + ((double)gst / 100)));
                  
                    double gstp =( (double)originalPrice -  (totalPrice))/100;
                    totalAmount += originalPrice; 
                  //  double shipproduct=(totalPrice/originalPrice)*ship;
            %>
            <tr>                
                <td><%= productId %></td>
                <td><%= productName %></td>
                <td><%= quantity %></td>
                <td>$<%= productPrice %></td>
                <td>$<%= originalPrice %></td>
                <td>$<%= gst %></td>
                <td>$<%= totalPrice %></td>
                <td>$<%= gstp %></td>
                <td><%= (totalPrice/totalAmount)*ship %></td>
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
                <td>$<%= totalAmount %></td>
            </tr>
            
              <%
              double shippingCharges =100;

           // Query to fetch shipping charges based on the total amount
          String shippingQuery = "SELECT ship_value FROM shipping181 WHERE " + totalAmount + " BETWEEN ship_from AND ship_to";

           try {
               connection = YourDatabaseConnection.getConnection(); // Implement your database connection method
               statement = connection.prepareStatement(shippingQuery);
              // statement.setInt(1, totalAmount);
             
               resultSet = statement.executeQuery();
               System.out.println("ksks-------------"+shippingCharges);
               
               if (resultSet.next()) {
                   shippingCharges = resultSet.getInt("ship_value");
                   System.out.println(resultSet.getInt("ship_value"));
                   System.out.println("ks------------"+shippingCharges);
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

           double finalBill = totalAmount + shippingCharges;
           System.out.println("bnn"+totalAmount);
           System.out.println("jjj"+shippingCharges);
           System.out.println("kj"+finalBill);

    %>
   <!--   <tr class="total-row">
        <td colspan="5" style="text-align: right;"><strong>Shipping Charges:</strong></td>
        <td>$<%= shippingCharges %></td>
    </tr>
    <tr class="total-row">
        <td colspan="5" style="text-align: right;"><strong>Final Bill:</strong></td>
        <td>$<%= finalBill %></td>
    </tr>
        </tbody>        
    </table>    -->
 
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
