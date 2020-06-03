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

                var test = <%=(Boolean) request.getAttribute("userModal")%>;
                if (test === true) {
                    $("#userModal").modal('show');
                } else {
                    $("#userModal").modal('hide');
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
        <!-- report Modal -->
        <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="font-weight-bold"> User Still Logged In</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center">The action can't be completed because the user is still logged in.</h4>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
        <%--Pop Up Window for Edit --%>
        <div class="container">
            <div class="modal fade" id="editModal" role="dialog">
                <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">      
                            <div class="col-8 col-sm-12">
                                <form action="updateUser" method="POST" id="editForm">
                                    <fieldset> 
                                        <select class="form-control" name="employee_position">
                                            <option value="" disabled selected>Position</option>
                                            <option value="Owner">Owner</option>
                                            <option value="Operations Manager">Operations Manager</option>
                                        </select><br>
                                        <input class="form-control" type="text" id="LName" name="LName" placeholder="Last Name"><br>
                                        <input class="form-control" type="text" id="FName" name="FName" placeholder="First Name"><br>
                                        <input class="form-control" type="text" id="username" name="username" placeholder="Username"><br>
                                        <button class="btn btn-primary btn-block" type="submit" id="employee_code" name="employee_code" value="" style="background-color: rgb(136,57,0);">Submit</button>
                                    </fieldset>
                                </form> 
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>   
        </div>
        <%--Pop Up Window for Delete --%>
        <div class="container">
            <div class="modal fade" id="deleteModal" role="dialog">
                <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body">      
                            <div class="col-8 col-sm-12">
                                <form action="deleteUser" method="POST" id="deleteForm">
                                    <fieldset>
                                        <center>
                                            <label for="Title"><h3>Delete the Selected Record?</h3></label><br>
                                            <button class="btn btn-danger" type="submit" id="employee_code" name="employee_code" value="">Submit</button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>  
                                        </center>
                                    </fieldset>
                                </form> 
                            </div>
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div>
                </div>
            </div>   
        </div>
        <%--JavaScript Functions for Pop Up Window --%>
        <script>
            $('#editModal').on('show.bs.modal', function (e) {
                var id = e.relatedTarget.id;
                var employee_code = document.getElementById(id).value;
                $(this).find('.modal-body').find('#editForm').find("#employee_code").attr("value", employee_code);
                $(this).find('.modal-header').html('<h4>Edit User Account: <b>Employee Code #' + employee_code + "</h4>");
            });

            $('#deleteModal').on('show.bs.modal', function (e) {
                var id = e.relatedTarget.id;
                var employee_code = document.getElementById(id).value;
                $(this).find('.modal-body').find('#deleteForm').find("#employee_code").attr("value", employee_code);
                $(this).find('.modal-header').html('<h4>Delete User Account: <b>Employee Code #' + employee_code + "</h4>");
            });
        </script>  
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
            <div class="page-content-wrapper">
                <div class="container-fluid" style="opacity: 0.91;">
                    <p class="lead text-left align-items-center align-content-center align-self-center" style="height: 20px;color: rgb(255,255,255);"></p>
                    <div class="row" style="margin: 0;width: 1200px;height: 700px;">
                        <div class="card shadow" style="width: 1150px;height: 750px;">
                            <div class="card-body"  style="width: 1100px;">
                                <div class="row container-fluid">
                                    <div class="col-md-6 text-left align-items-start align-content-start align-self-start mr-auto mt-3">
                                        <div class="text-left text-md-right dataTables_filter form-inline" id="dataTable_filter"><label></label></div>
                                        <div class="text-right text-md-right dataTables_filter form-inline mt-3" id="dataTable_filter" style="height: 20px;margin-bottom: 5px;">                               
                                            <form action="searchUser" method="POST">
                                                <input type="search" name="searchInput" class="form-control form-control-sm" aria-controls="dataTable" placeholder="Search Employee Account" style="width: 300px;">
                                                <button type="submit" class="btn btn-default text-truncate float-right float-sm-none add-another-btn"><span class="glyphicon glyphicon-search"></span> Search</button>
                                            </form>                                   
                                            <label></label>
                                        </div>
                                    </div>
                                </div>                      
                                <div class="table-responsive table-hover mt-5" id="dataTable" role="grid" aria-describedby="dataTable_info" style="height: 615px;">
                                    <table id="employeeTable" class="display table" style="text-align: center" width="100%">
                                        <thead>
                                            <tr>
                                                <th scope="col" style="text-align: center">#</th>
                                                <th scope="col" style="text-align: center">Delete</th>    
                                                <th scope="col" style="text-align: center">Edit</th> 
                                                <th scope="col" style="text-align: center">Employee Code <a href="sortUser?header=EMPLOYEE_CODE" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                <th scope="col" style="text-align: center">Last Name <a href="sortUser?header=LNAME" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                <th scope="col" style="text-align: center">First Name <a href="sortUser?header=FNAME" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                <th scope="col" style="text-align: center">Position <a href="sortUser?header=EMPLOYEE_POSITION" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                <th scope="col" style="text-align: center">Username <a href="sortUser?header=USERNAME" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                            </tr>
                                        </thead>
                                        <c:forEach items="${employeeList}" var="items" varStatus="status">
                                            <tr>
                                                <td>${status.count}</td>
                                                <td style="width: 5px;">
                                                    <button class="btn btn-danger pull-right glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteModal" id="${status.count}" name="employee_delete" type="button" value="${employeeList.get(status.index).getEmployee_code()}"></button>
                                                </td>
                                                <td style="width: 5px;">
                                                    <button class="btn btn-primary pull-right glyphicon glyphicon-edit" data-toggle="modal" data-target="#editModal" id="${status.count}" name="employee_search" type="button" value="${employeeList.get(status.index).getEmployee_code()}"></button> 
                                                </td> 
                                                <td>${employeeList.get(status.index).getEmployee_code()}</td>
                                                <td>${employeeList.get(status.index).getLName()}</td>
                                                <td>${employeeList.get(status.index).getFName()}</td>
                                                <td>${employeeList.get(status.index).getEmployee_position()}</td>
                                                <td>${employeeList.get(status.index).getUsername()}</td>
                                            </tr>  
                                        </c:forEach>     
                                    </table>
                                </div>
                            </div>
                        </div>
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