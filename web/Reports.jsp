<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.helper.itemHelper"%>
<%@page import="com.model.inventoryModel"%>
<%@page import="java.sql.Date"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    Gson gsonObj = new Gson();
    Map<Object, Object> map = null;
    List<Map<Object, Object>> list = new ArrayList<>();
    List<Map<Object, Object>> list1 = new ArrayList<>();

    ArrayList<inventoryModel> inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
    itemHelper helperIte = new itemHelper((Connection) session.getAttribute("con"));
    int totalItems = helperIte.getTotal(inventoryList);
    for (int i = 0; inventoryList.size() > i; i++) {
        map = new HashMap<>();
        map.put("label", inventoryList.get(i).getItem_description());
        float quantity = inventoryList.get(i).getItem_quantity();
        map.put("y", Math.round((quantity / totalItems) * 100));
        if (helperIte.maxItem(inventoryList) == inventoryList.get(i).getItem_quantity()) {
            map.put("exploded", true);
        }
        list.add(map);
    }
    String dataPoints = gsonObj.toJson(list);
    ArrayList<inventoryModel> barChart = (ArrayList<inventoryModel>) session.getAttribute("barChart");
    Date barDate = (Date) session.getAttribute("barDate");
    if(barChart != null){
        ResultSet rs = helperIte.generateDateReport(barDate);
        int total = 0;
        map = new HashMap<>();
        Date sample;
        if (rs.next()) {
        for (int i = 0; barChart.size() > i; i++) 
        {
            if (rs.getDate("DATE_DELIVERED").equals(barChart.get(i).getDate_delivered())){
                total += barChart.get(i).getItem_quantity();
            }
        }
        map.put("y", total);
        map.put("label", rs.getDate("DATE_DELIVERED"));
        list1.add(map);
        }
    } else if(barChart == null) {
        map.put("y", 0);
        map.put("label", "No Dates");
        list1.add(map);       
    }
    String dataPoints1 = gsonObj.toJson(list1);
%>

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

                var chart = new CanvasJS.Chart("chartContainer", {
                    theme: "light2",
                    animationEnabled: true,
                    exportFileName: "PieChartInventory",
                    exportEnabled: true,
                    data: [{
                            type: "pie",
                            showInLegend: true,
                            legendText: "{label}",
                            toolTipContent: "{label}: <strong>{y}%</strong>",
                            indexLabel: "{label} {y}%",
                            dataPoints: <%out.print(dataPoints);%>
                        }]
                });
                chart.render();
                var chart1 = new CanvasJS.Chart("chartContainer1", {
                    animationEnabled: true,
                    theme: "light2", // "light1", "light2", "dark1", "dark2"
                    data: [{
                            type: "column",
                            showInLegend: true,
                            legendMarkerColor: "grey",
                            dataPoints: <%out.print(dataPoints1);%>
                        }]
                });
                chart1.render();

                var test = <%=(Boolean) request.getAttribute("reportModal")%>;
                if (test === true) {
                    $("#reportModal").modal('show');
                } else {
                    $("#reportModal").modal('hide');
                }
            };
        </script>
        <style>
        .login label, 
        .login input {
            float:left;
        }
        </style>
    </head>

    <body style="background-image: url(&quot;assets/img/mike-kenneally-TD4DBagg2wE-unsplash.jpg&quot;);opacity: 1;">

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
        <div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center font-weight-bold">Report Generated!</h4>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
        <!-- item Modal -->
        <div class="modal fade" id="itemModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="exampleModalLabel"><b>List of Items</b></h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive table-hover" id="dataTable" role="grid" aria-describedby="dataTable_info" style="margin-bottom: 8px;">
                            <table id="itemTable" class="display table" style="text-align: center" width="100%">
                                <thead>
                                    <tr>
                                        <th scope="col" style="text-align: center">#</th>
                                        <th scope="col" style="text-align: center">Item Name</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${itemList}" var="item" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${itemList.get(status.index).getItem_description()}</td> 
                                    </tr>  
                                </c:forEach>     
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
        <!-- supplier Modal -->
        <div class="modal fade" id="supplierModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="exampleModalLabel"><b>List of Suppliers</b></h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive table-hover" id="dataTable" role="grid" aria-describedby="dataTable_info" style="margin-bottom: 8px;">
                            <table id="itemTable" class="display table" style="text-align: center" width="100%">
                                <thead>
                                    <tr>
                                        <th scope="col" style="text-align: center">#</th>
                                        <th scope="col" style="text-align: center">Supplier Name</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${supplierList}" var="item" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${supplierList.get(status.index).getSupplier_name()}</td> 
                                    </tr>  
                                </c:forEach>     
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
        <!-- value Modal -->
        <div class="modal fade" id="valueModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="exampleModalLabel"><b>List of Item Values</b></h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive table-hover" id="dataTable" role="grid" aria-describedby="dataTable_info" style="margin-bottom: 8px;">
                            <table id="itemTable" class="display table" style="text-align: center" width="100%">
                                <thead>
                                    <tr>
                                        <th scope="col" style="text-align: center">#</th>
                                        <th scope="col" style="text-align: center">Item Name</th>
                                        <th scope="col" style="text-align: center">Item Quantity</th>
                                        <th scope="col" style="text-align: center">Item Price</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${itemList}" var="item" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${itemList.get(status.index).getItem_description()}</td> 
                                        <td>${inventoryList.get(status.index).getItem_quantity()}</td> 
                                        <td>&#8369; ${itemList.get(status.index).getItem_price()}</td> 
                                    </tr>  
                                </c:forEach>     
                            </table>
                        </div>
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
            <div class="page-content-wrapper"></div>
        </div>
        <div class="container" style="opacity: 0.91;">
            <div class="row text-left mt-5">
                <div class="col">
                    <div class="card text-white bg-danger mb-3" style="height: 100%; width: 105%">
                        <div class="card-body" style="background-color: rgb(222, 110, 82)">
                            <h5 class="card-title font-weight-bold display-3">&#8369; ${inVal}</h5>
                            <h4>Total Inventory Value</h5>
                        </div>
                    <!--    <div class="card-footer"><center><button class="btn text-white" data-toggle="modal" data-target="#valueModal" style="color: white;width:98%;">More Info <i class="fas fa-arrow-circle-right"></i></button></center></div> -->
                    </div>
                </div>
                <div class="col">
                    <div class="card text-white bg-success mb-3" style="opacity: 0.91;height: 100%; width: 105%">
                        <div class="card-body" style="background-color: rgb(98, 201, 66)">
                            <h5 class="card-title font-weight-bold display-3">${fn:length(inventoryList)}</h5>
                            <h4>Total Items</h5>
                        </div>
                    <!--    <div class="card-footer"><center><button class="btn text-white" data-toggle="modal" data-target="#itemModal" style="color: white;width:98%;">More Info <i class="fas fa-arrow-circle-right"></i></button></center></div> -->
                    </div>
                </div>
                <div class="col">
                    <div class="card text-white bg-info mb-3" style="height: 100%; width: 101%;">
                        <div class="card-body" style="background-color: rgb(49, 173, 214)">
                            <h5 class="card-title font-weight-bold display-3">${fn:length(supplierList)}</h5>
                            <h4>Total Suppliers</h5>
                        </div>
                    <!--    <div class="card-footer"><center><button class="btn text-white" data-toggle="modal" data-target="#supplierModal" style="color: white;width:98%;">More Info <i class="fas fa-arrow-circle-right"></i></button></center></div> -->
                    </div>
                </div>
            </div>
            <div class="row text-left mt-3">
                <div class="container-fluid">
                    <div class="card text-center" style="height: 140%;background-color: whitesmoke">
                        <div class="card-body">
                            <div class="text-right text-md-right form-inline" style="height: 20px;margin-bottom: 5px;">        
                                <form action="generateReport" method="POST">
                                    <div class="input-group col-lg-12">
                                        <input name="dateReport" type="date" class="form-control input-lg" aria-controls="dataTable" placeholder="Search Report" style="width: 895px;">                           
                                        <span class="input-group-btn">
                                            <button href = "generateReport" type="submit" class="btn btn-default btn-lg text-truncate float-right float-sm-none">
                                                <span class="glyphicon glyphicon-file"></span> Generate Report
                                            </button>
                                        </span>   
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row text-left mt-5">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="panel panel-default" style="width:1108px">
                                <div class="panel-heading">
                                    <label><h4><b>Item Details</b></h4></label>
                                </div>
                                <div class="panel-body">
                                    <div id="chartContainer" style="height: 370px; width: 100%;"></div>
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
        <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
        <script src="assets/js/Bootstrap-DataTables.js"></script>
        <script src="assets/js/Sidebar-Menu.js"></script>
    </body>

</html>