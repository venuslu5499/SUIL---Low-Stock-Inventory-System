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
        <script type="text/javascript">
            window.onload = function () {

                var test = <%=(Boolean) request.getAttribute("delModal")%>;
                if (test === true) {
                    $("#delModal").modal('show');
                } else {
                    $("#delModal").modal('hide');
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
        <%--Pop Up Window for Delete Validation --%>
        <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center font-weight-bold">Item has been Deleted.</h4>
                    </div>
                    <div class="modal-footer">
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
                                <form action="deleteItem" method="POST" id="deleteForm">
                                    <fieldset>
                                        <center>
                                            <label for="Title"><h3>Delete the Selected Item?</h3></label><br>
                                            <button class="btn btn-danger" type="submit" id="index" name="index" value="">Submit</button>
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
            $('#deleteModal').on('show.bs.modal', function (e) {
                var id = e.relatedTarget.id;
                var index = document.getElementById(id).value;
                $(this).find('.modal-body').find('#deleteForm').find("#index").attr("value", id);
                $(this).find('.modal-header').html('<h4><b>Item Name: ' + index + "</h4>");
            });
        </script>  
        <div id="wrapper">
            <div id="sidebar-wrapper" style="width: 200px;">
                <ul class="sidebar-nav" style="width: 200px;color: rgb(51,24,0);">                   
                    <li> 
                        <a class="text-left" data-bs-hover-animate="pulse" href="checkPosition" style="width: 200px;margin-top: 15px; margin-left: -20px ;color: rgb(153,153,153);"><i class="fas fa-home"></i> Home Page </a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="Inventory.jsp" style="width: 220px;margin-top: 30px;margin-left: -20px ;"><i class="fas fa-dolly-flatbed"></i> Inventory  <span class="badge" style="color:yellow">${counterLow}</span><span class="badge" style="color:red">${counterNo}</span></a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="Subtract_Qty.jsp" style="width: 220px;margin-top: 30px;margin-left: -20px ;"><i class="fas fa-minus-circle"></i> Subtract</a>
                        <a class="text-left" data-bs-hover-animate="pulse" href="Add_Qty.jsp" style="width: 220px;margin-top: 30px;margin-left: -20px ;"><i class="fas fa-plus-circle"></i> Add Quantity</a></li>
                    <li>
                    </li>
                    <li style="width: 170px;"> 
                        <a class="text-left" data-bs-hover-animate="pulse" href="Insert_Item.jsp" style="width: 220px;margin-top: 30px;margin-left: -20px ;"><i class="fas fa-boxes"></i> Insert New Item</a>
                        <a data-bs-hover-animate="pulse" href="Delete_Item.jsp" style="width: 220px;margin-top: 30px;margin-left: -20px ;"><i class="fas fa-trash-alt"></i> Delete Item</a>
                        <a data-bs-hover-animate="pulse" href="Reports.jsp" style="width: 220px;margin-top: 30px;margin-left: -20px ;"><i class="fas fa-file-alt"></i> Inventory Reports</a>
                        <form action="logoutUser" method="POST">
                            <button class="text-left btn btn-outline-secondary text-white-50" data-bs-hover-animate="pulse" type="submit" style="width: 170px;margin-top: 30px;margin-left: -10px">Logout</button>
                        </form> 
                    </li>
                </ul>
            </div>    
            <div class="page-content-wrapper">
                <div class="container-fluid" style="opacity: 0.91;">
                    <div class="row" style="margin: 0;width: 1000px;height: 700px;">
                        <div class="col text-center align-self-center ml-auto">
                            <p class="lead text-left align-items-center align-content-center align-self-center" style="height: 20px;color: rgb(255,255,255);"></p>
                            <div class="card text-center shadow" style="width: 1100px;height: 750px;">
                                <div class="card-body" style="width: 1100px;">
                                    <div class="row container-fluid">
                                        <div class="col-md-6 text-left align-items-start align-content-start align-self-start mr-auto">
                                            <div class="text-right text-md-right dataTables_filter form-inline mt-3" id="dataTable_filter" style="height: 20px;margin-bottom: 5px;">
                                                <form action="searchInventory" method="POST">
                                                    <div class="input-group">
                                                        <input type="text" id="searchInput" name="searchDel" value="" class="form-control form-control-sm" aria-controls="dataTable" placeholder="Search Employee Account" style="width: 300px;">
                                                        <span class="input-group-btn">
                                                            <button href = "searchQty" name="searchDel" type="submit" class="btn btn-default text-truncate float-right add-another-btn"><span class="glyphicon glyphicon-search"></span> Search</button>
                                                        </span>                                  
                                                    </div>
                                                    <label></label>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="table-responsive table-hover mt-5" id="dataTable" role="grid" aria-describedby="dataTable_info" style="height: 615px;margin-bottom: 8px;">
                                        <% ArrayList<inventoryModel> inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");%>
                                        <table id="itemTable" class="display table" style="text-align: center" width="100%">
                                            <thead>
                                                <tr>
                                                    <th scope="col" style="text-align: center">#</th>
                                                    <th scope="col" style="text-align: center">Item Name <a href="sortInventory?header=ITEM_DESCRIPTION&page=del" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                    <th scope="col" style="text-align: center">Price <a href="sortInventory?header=ITEM_PRICE&page=del" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                    <th scope="col" style="text-align: center">Units <a href="sortInventory?header=ITEM_UNITS&page=del" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                    <th scope="col" style="text-align: center">Supplier Name <a href="sortInventory?header=SUPPLIER_NAME&page=del" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                    <th scope="col" style="text-align: center">Date Delivered <a href="sortInventory?header=SUPPLIER_CONTACT&page=del" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                    <th scope="col" style="text-align: center">Delete</th>
                                                </tr>
                                            </thead>
                                            <c:forEach items="${inventoryList}" var="items" varStatus="status">
                                                <tr>
                                                    <td>${status.count}</td>
                                                    <td>${inventoryList.get(status.index).getItem_description()}</td>
                                                    <td>&#8369; ${inventoryList.get(status.index).getItem_price()}</td>
                                                    <td>${inventoryList.get(status.index).getItem_unit()}</td>
                                                    <td>${inventoryList.get(status.index).getSupplier_name()}</td>
                                                    <td>${inventoryList.get(status.index).getDate_delivered()}</td>
                                                    <td style="width: 5px;">
                                                        <button class="btn btn-danger pull-right glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteModal" id="${status.index}" name="item_delete" type="button" value="${inventoryList.get(status.index).getItem_description()}"></button>
                                                    </td>
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