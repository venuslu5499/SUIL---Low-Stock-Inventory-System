<%@page import="com.model.inventoryModel"%>
<%@page import="com.model.itemModel"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>SE 1.1</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/css/Bootstrap-DataTables.css">
        <link rel="stylesheet" href="assets/css/Data-Table-1.css">
        <link rel="stylesheet" href="assets/css/Data-Table.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
        <link rel="stylesheet" href="assets/css/login-form-1.css">
        <link rel="stylesheet" href="assets/css/login-form.css">
        <link rel="stylesheet" href="assets/css/mloureiro1973-login.css">
        <link rel="stylesheet" href="assets/css/mloureiro1973-login-1.css">
        <link rel="stylesheet" href="assets/css/MUSA_button-label-1.css">
        <link rel="stylesheet" href="assets/css/MUSA_button-label.css">
        <link rel="stylesheet" href="assets/css/sidebar-1.css">
        <link rel="stylesheet" href="assets/css/Sidebar-Menu-1.css">
        <link rel="stylesheet" href="assets/css/Sidebar-Menu.css">
        <link rel="stylesheet" href="assets/css/sidebar.css">
        <link rel="stylesheet" href="assets/css/styles.css">
        <link rel="stylesheet" href="assets/js/bootstrap.js">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
        <style type="text/css"> 
            .btn-circle.btn-xl { 
                width: 400px; 
                height: 400px; 
                padding: 100px 16px; 
                border-radius: 200px; 
                font-size: 12px; 
                text-align: center; 
                outline: none !important;
                border: none;
            } 
            .vertical-center {
                vertical-align: middle;  
                height: 75%;
                display: flex;
                align-items: center;
            }
        </style>
    </head>

    <body style="background-image: url(&quot;assets/img/mike-kenneally-TD4DBagg2wE-unsplash1.jpg&quot;);background-color: transparent">

        <%
            if (session == null || session.getAttribute("inputUsername") == null) {
                response.sendRedirect("index.jsp");
            } else {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.addHeader("Cache-Control", "post-check=0, pre-check=0");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
            }
        %>  
        <nav class="navbar bg-dark text-left">
            <ul class="navbar-nav text-left">
                <li class="nav-item text-white font-weight-bold">
                    <h3 class="font-weight-bold" style="margin-left: -120%"> Suil Cafe Han Low Stock Inventory System </h3>
                </li>
            </ul>
        </nav>
        <div class="container-fluid vertical-center">    
            <div class="row">
                <div class="col-sm">
                    <a href="Inventory.jsp" type="submit" class="btn btn-default btn-circle btn-xl shadow-lg" style="margin-left: 55%"><i class="fas fa-dolly-flatbed" style="font-size: 180px;"></i><br><h2 class="font-weight-bold">Inventory</h2></a>
                </div>
                <div class="col-sm">
                    <a href="Manage_Accounts.jsp" class="btn btn-default btn-circle btn-xl shadow-lg" style="margin-left: 55%"><i class="fas fa-users" style="font-size: 180px;"></i><br><h2 class="font-weight-bold">Accounts</h2></a>
                </div>
                <div class="col-sm">
                    <a href="logoutUser" class="btn btn-default btn-circle btn-xl shadow-lg" type="submit"style="margin-left: 55%"><i class="fas fa-sign-out-alt font-weight-bold" style="font-size: 180px;"></i><br><h2 class="font-weight-bold">Logout</h2></a>                
                </div>

            </div>
        </div>
        <script src="assets/js/jquery.min.js"></script>
        <script src="../assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/bs-init.js"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
        <script src="assets/js/Bootstrap-DataTables.js"></script>
        <script src="assets/js/Sidebar-Menu.js"></script>
    </body>

</html>