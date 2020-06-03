<%@page import="com.model.employeeModel"%>
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
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript">
            window.onload = function () {

                var test = <%=(Boolean) request.getAttribute("passModal")%>;
                if (test === true) {
                    $("#passModal").modal('show');
                } else {
                    $("#passModal").modal('hide');
                }
            };
        </script>
    </head>

    <body style="background-image: url(&quot;assets/img/mike-kenneally-TD4DBagg2wE-unsplash.jpg&quot;);">

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
        <!-- Security Modal -->
        <div class="modal fade" id="passModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center font-weight-bold">Your Password has been Updated.</h4>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
        <%--Start of Page Design --%>
        <div id="wrapper">
            <div id="sidebar-wrapper" style="width: 200px;">
                <ul class="sidebar-nav" style="width: 150px;color: rgb(51,24,0);">           
                    <li> 
                        <a class="text-left" data-bs-hover-animate="pulse" href="checkPosition" style="width: 220px;margin-top: 30px; margin-left: -20px ;color: rgb(153,153,153);"><i class="fas fa-home"></i> Home Page </a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="Manage_Accounts.jsp" style="width: 220px;margin-top: 30px; margin-left: -20px ;color: rgb(153,153,153);"><i class="fas fa-users-cog"></i> Manage Accounts</a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="Create_Account.jsp" style="width: 220px;margin-top: 30px; margin-left: -20px ;color: rgb(153,153,153);"><i class="fas fa-user-plus"></i> Create Accounts</a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="OW_Change_Pass.jsp" style="width: 220px;margin-top: 30px; margin-left: -20px ;color: rgb(153,153,153);"><i class="fas fa-key"></i> Change Password</a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="OW_Security_Questions.jsp" style="width: 220px;margin-top: 30px; margin-left: -20px ;color: rgb(153,153,153);"><i class="fas fa-key"></i> Security Questions</a>
                    <li>
                    </li>
                    <li style="width: 150px;"> 
                        <form action="logoutUser" method="POST">
                            <button class="text-left btn btn-outline-secondary text-white-50" data-bs-hover-animate="pulse" type="submit" style="width: 170px;margin-top: 30px;">Logout</button>
                        </form>              
                    </li>  
                </ul>
            </div>
        </div>
        <div class="container full-height">
            <div class="row flex center v-center full-height">
                <div class="col-8 col-sm-4" style="opacity: 0.91;">
                    <div class="form-box" style="background-color: #ffffff;opacity: 0.91;">
                        <form action="updateUser" method="POST">
                            <fieldset><img id="avatar" class="avatar round" src="assets/img/lock.jpg" style="width: 225px;height: 200px;">  
                                <script>
                                    var check = function ()
                                    {
                                        if (document.getElementById('password').value === document.getElementById('confirm_password').value)
                                        {
                                            document.getElementById('message').style.color = 'green';
                                            document.getElementById('message').style.fontWeight = 'bold';
                                            document.getElementById('message').innerHTML = 'Matching';
                                        } else
                                        {
                                            document.getElementById('message').style.color = 'red';
                                            document.getElementById('message').style.fontWeight = 'bold';
                                            document.getElementById('message').innerHTML = 'Not Matching';
                                        }
                                    }
                                </script>
                                <input class="form-control" type="password" id="password" name="password" placeholder="Password" required="" onkeyup='check();' />
                                <input class="form-control" type="password" id="confirm_password" name="changePass" placeholder="Confirm Password" required="" onkeyup='check();' />
                                <span id='message'></span>
                                <button class="btn btn-primary btn-block" type="submit" style="background-color: rgb(136,57,0);">Change Password</button>
                            </fieldset>
                        </form>
                    </div>
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