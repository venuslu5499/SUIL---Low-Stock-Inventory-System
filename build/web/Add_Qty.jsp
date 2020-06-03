<%@page import="com.model.inventoryModel"%>
<%@page import="java.util.ArrayList"%>
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

                var test = <%=(Boolean) request.getAttribute("addModal")%>;
                if (test === true) {
                    $("#addModal").modal('show');
                } else {
                    $("#addModal").modal('hide');
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
        <%--Pop Up Window for ADD --%>
        <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center font-weight-bold">Item has been Updated.</h4>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
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
            <form action="addQty" method="POST">
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
                                                    <script>
                                                        function getInput(e) {
                                                            e.href = e.href + "?searchAddInput=" + document.getElementById('searchInput').value;
                                                        }
                                                    </script>
                                                    <div class="input-group">
                                                        <input type="text" id="searchInput" name="searchAddInput" value="" class="form-control form-control-sm" aria-controls="dataTable" placeholder="Search Inventory" style="width: 300px;">
                                                        <span class="input-group-btn">
                                                            <a href = "searchQty" name="searchAddInput" type="submit" class="btn btn-default text-truncate float-right float-sm-none add-another-btn" onclick="getInput(this)"><span class="glyphicon glyphicon-search"></span> Search</a>                                  
                                                        </span>
                                                    </div>
                                                    <label></label>
                                                </div>
                                            </div>
                                            <button type="submit" class="btn btn-primary mt-3"><span class="glyphicon glyphicon-floppy-disk"></span> Save Changes</button>
                                        </div>
                                        <div class="table-responsive table-hover table mt-5" id="dataTable" role="grid" aria-describedby="dataTable_info" style="height: 616px;margin-bottom: 8px;">
                                            <% ArrayList<inventoryModel> inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");%>
                                            <table id="itemTable" class="display table" style="text-align: center" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th scope="col" style="text-align: center">#</th>
                                                        <th scope="col" style="text-align: center">Item Name <a href="sortInventory?header=ITEM_DESCRIPTION&page=add" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                        <th scope="col" style="text-align: center">Item Unit <a href="sortInventory?header=ITEM_UNITS&page=add" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                        <th scope="col" style="text-align: center">Supplier Name <a href="sortInventory?header=SUPPLIER_NAME&page=add" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                        <th scope="col" style="text-align: center">Current Quantity <a href="sortInventory?header=ITEM_QUANTITY&page=add" style="text-decoration: none; color: black"><i class="fas fa-sort"></i></a></th>
                                                        <th scope="col" style="text-align: center">Status</th>
                                                        <th scope="col" style="text-align: center">Additional Quantity</th>
                                                    </tr>
                                                </thead>
                                                <c:forEach items="${inventoryList}" var="items" varStatus="status">
                                                    <tr>
                                                        <td>${status.count}</td>
                                                        <td>${inventoryList.get(status.index).getItem_description()}</td>
                                                        <td>${inventoryList.get(status.index).getItem_unit()}</td>
                                                        <td>${inventoryList.get(status.index).getSupplier_name()}</td>       
                                                        <td>${inventoryList.get(status.index).getItem_quantity()}</td>
                                                        <td>${inventoryList.get(status.index).getStatus()}</td>
                                                        <td><center><input class="form-control w-50" style="text-align: center" name="addQty" value="0" placeholder="0" type="number"  min="0"></center></td>
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
            </form>                     
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