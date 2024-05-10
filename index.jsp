<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="empcrud.Employee" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Employee Management System</title>

    <style>
body{
    background-color: #f0f0f0; /* Light gray */
}

form label {
    display: inline-block;
    width: 150px;
}

form input[type="text"] {
    width: 250px;
    margin-bottom: 10px;
    box-sizing: border-box;
}

button {
    padding: 10px 20px;
    cursor: pointer;
    background-image: linear-gradient(to right, #4caf50, #2196F3); /* Green to blue gradient */
    color: white;
    border: none;
    border-radius: 5px;
    margin-right: 10px;
    margin-bottom:10px;
}

button:hover {
    background-color: #45a049;
}

table {
    border-collapse: collapse;
    width: 100%;
}

th{
    background-color: #2196F3; /* Blue */
}

th, td {
    padding: 8px;
    text-align: left;
}

tr:nth-child(even) {
    background-color: #e7f2f7; /* Light blue */
}

tr:hover {
    background-color: #ffd700; /* Orange to pink gradient */
}


    </style>

</head>

<body>

    <h2 style="text-align:center">Employee CRUD</h2>

    <form action="OperationsController" method="post">

        <label for="Empid">Employee ID:</label>

        <input type="text" id="Empid" name="Empid"><br><br>

        <label for="Empname">Employee Name:</label>

        <input type="text" id="Empname" name="Empname"><br><br>

        <label for="job">Job:</label>

        <input type="text" id="job" name="job"><br><br>

        <label for="Department">Department:</label>

        <input type="text" id="Department" name="Department"><br><br>

        <label for="Sal">Salary:</label>

        <input type="text" id="Sal" name="Sal"><br><br>
        <label for="Email">Email:</label>
<input type="text" id="Email" name="Email"><br><br>

<label for="HireDate">Hire Date:</label>
<input type="date" id="HireDate" name="HireDate" max="<%= java.time.LocalDate.now() %>"><br><br>
        

        <button id="first" type="button">first</button>

        <button id="last" type="button">last</button>

        <button type="submit" name="action" value="add">Add</button>

        <button type="submit" name="action" value="delete">Delete</button>

        <button type="submit" name="action" value="update">Update</button>

        <button id="nextButton" type="button">Next</button>

    <button type="button" id="prevbutton">Previous</button>

    </form>

    

    <br><br>

    <!-- Table to display employee records -->

    <table id="employeeTable" border="1">

        <tr>

            <th>Employee ID</th>

            <th>Employee Name</th>

            <th>Job</th>

            <th>Department</th>

            <th>Salary</th>
            <th>email</th>
            <th>hire date</th>

        </tr>

        <%@ page import="empcrud.Employee" %>

        <% 

            List<Employee> emp1 = (List<Employee>) request.getAttribute("ed");

            if (emp1 != null) {

                for (Employee emp2 : emp1) {

        %>

        <tr id="data">

            <td><%= emp2.getEmpid() %></td>

            <td><%= emp2.getEmp_name() %></td>

            <td><%= emp2.getJob() %></td>

            <td><%= emp2.getDept()%></td>

            <td><%= emp2.getSalary() %></td>
            <td><%= emp2.getEmailid()%></td>

            <td><%= emp2.getHire_date() %></td>

        </tr>

        <%

                }

            }

        %>

    </table>

    <script>

        var rowIndex = 1;

        var index = 1; 

        var rows; 



        document.addEventListener("DOMContentLoaded", function() {

            var table = document.getElementById("employeeTable");

            rows = table.getElementsByTagName("tr");



            function updateFormFields(row, rowIndex) {

                var cells = row.getElementsByTagName("td");

                document.getElementsByName("Empname")[0].value = cells[1].innerHTML;

                document.getElementsByName("Empid")[0].value = cells[0].innerHTML;

                document.getElementsByName("job")[0].value = cells[2].innerHTML;

                document.getElementsByName("Department")[0].value = cells[3].innerHTML;

                document.getElementsByName("Sal")[0].value = cells[4].innerHTML;
                document.getElementsByName("Email")[0].value = cells[5].innerHTML;
                document.getElementsByName("HireDate")[0].value = cells[6].innerHTML;
                index = rowIndex; 

            }



            for (var i = 1; i < rows.length; i++) {

                rows[i].addEventListener("click", function() {

                    rowIndex = Array.prototype.indexOf.call(rows, this);

                    updateFormFields(this, rowIndex); 

                });

            }



            function moveToNext() {

                if (rowIndex < rows.length - 1) {

                    rowIndex++;

                    updateFormFields(rows[rowIndex], rowIndex);

                }

            }



            function moveToPrev() {

                if (rowIndex > 1) {

                    rowIndex--;

                    updateFormFields(rows[rowIndex], rowIndex);

                }

            }

            

            document.getElementById("first").addEventListener("click", function() {

                rowIndex = 1;

                updateFormFields(rows[rowIndex], rowIndex);

            });



            

            document.getElementById("last").addEventListener("click", function() {

                rowIndex = rows.length - 1; 

                updateFormFields(rows[rowIndex], rowIndex);

            });

            document.getElementById("prevbutton").addEventListener("click", function() {

                moveToPrev();

                console.log('Backward');

            });



            document.getElementById("nextButton").addEventListener("click", function() {

                moveToNext();

                console.log('Forward');

            });

        });

    </script>

</body>

</html>