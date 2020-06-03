<%@page import="com.model.supplierModel"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
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
        <style>
        * {
          box-sizing: border-box;
        }

        .autocomplete {
          position: relative;
        }

        input {
          border: 1px solid transparent;
          background-color: #f1f1f1;
          padding: 10px;
          font-size: 16px;
        }

        input[type=text] {
          background-color: #f1f1f1;
          width: 100%;
        }

        .autocomplete-items {
          position: absolute;
          border: 1px solid #d4d4d4;
          border-bottom: none;
          border-bottom-color: black;
          border-top: none;
          z-index: 99;
          top: 100%;
          left: 0;
          right: 0;
        }

        .autocomplete-items div {
          padding: 10px;
          padding-left: -10px;
          cursor: pointer;
          background-color: #fff; 
          border-bottom: 1px solid #d4d4d4; 
        }

        /*when hovering an item:*/
        .autocomplete-items div:hover {
          background-color: #e9e9e9; 
        }

        /*when navigating through the items using the arrow keys:*/
        .autocomplete-active {
          background-color: whitesmoke !important; 
          color: black; 
        }
        </style>
        <script type="text/javascript">
            window.onload = function () {
                var test = <%=(Boolean) request.getAttribute("insertErrorModal")%>;
                if (test === true) {
                    $("#insertErrorModal").modal('show');
                } else {
                    $("#insertErrorModal").modal('hide');
                }
                
                var test = <%=(Boolean) request.getAttribute("validationModal")%>;
                if (test === true) {
                    $("#validationModal").modal('show');
                } else {
                    $("#validationModal").modal('hide');
                }
            };
        </script>
    </head>

    <body style="background-image: url(&quot;assets/img/mike-kenneally-TD4DBagg2wE-unsplash.jpg&quot;);opacity: 25;">

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
         <!-- Validation Modal -->
        <div class="modal fade" id="validationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center font-weight-bold">Item has been inserted</h4>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
        <!-- Insert Error Modal -->
        <div class="modal fade" id="insertErrorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="position: absolute; top: 40%; left: 50%; transform: translate(-50%, -50%);" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h4 class="text-center font-weight-bold">The inserted Item with Supplier is already in the database.</h4>
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
        <div class="container full-height">
            <div class="row flex center v-center full-height">
                <div class="col-8 col-sm-4" style="opacity: 0.95;">
                    <div class="form-box" style="background-color: #ffffff;opacity: 0.95;">
                        <form autocomplete="off" action="addItem" method="POST">
                            <fieldset><img id="avatar" class="avatar round" src="assets/img/58602729_355309098436098_2854881851326070784_n.jpg" style="width: 150px;height: 125px;">
                                <div class="autocomplete">
                                    <input class="form-control" type="text" id="suppname" name="Supplier_Name" placeholder="Supplier Name" required="">
                                </div>                             
                                <input class="form-control" type="tel" id="username" name="Supplier_Contact" placeholder="123-45-678" required=""  pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}">
                                <input class="form-control" type="text" id="username" name="Supplier_Address" placeholder="Address" required="">
                                <input class="form-control" type="text" id="username" name="Item_Description" placeholder="Item Name" required="">
                                <input class="form-control" type="text" id="username" name="Item_Price" placeholder="Price" required="">
                                <input class="form-control" type="text" id="username" name="Item_Unit" placeholder="Unit" required="">
                                <input class="form-control" type="number" id="username" name="Critical_Qty" placeholder="Critical Quantity" required="">
                                <input class="form-control" type="number" id="username" name="Starting_Qty" placeholder="Starting Quantity" required="">
                                <input class="form-control" type="date" id="username" name="Date_Delivered" placeholder="Date Delivered" required="">
                                <button class="btn btn-primary btn-block" type="submit" style="background-color: rgb(136,57,0);">Insert New Item</button>
                            </fieldset>
                        </form>
                        <script>
                         function autocomplete(inp, arr) {
                           var currentFocus;
                           inp.addEventListener("input", function(e) {
                               var a, b, i, val = this.value;
                               closeAllLists();
                               if (!val) { return false;}
                               currentFocus = -1; 
                               a = document.createElement("DIV");
                               a.setAttribute("id", this.id + "autocomplete-list");
                               a.setAttribute("class", "autocomplete-items");
                               this.parentNode.appendChild(a);
                               for (i = 0; i < arr.length; i++) {
                                 if (arr[i].substr(0, val.length).toUpperCase() === val.toUpperCase()) {
                                   b = document.createElement("DIV");
                                   b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                                   b.innerHTML += arr[i].substr(val.length);
                                   b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                                   b.addEventListener("click", function(e) {
                                       inp.value = this.getElementsByTagName("input")[0].value;
                                       closeAllLists();
                                   });
                                   a.appendChild(b);
                                 }
                               }
                           });
                           inp.addEventListener("keydown", function(e) {
                               var x = document.getElementById(this.id + "autocomplete-list");
                               if (x) x = x.getElementsByTagName("div");
                               if (e.keyCode === 40) {
                                 currentFocus++;
                                 addActive(x);
                               } else if (e.keyCode === 38) { 
                                 currentFocus--;
                                 addActive(x);
                               } else if (e.keyCode === 13) {
                                 e.preventDefault();
                                 if (currentFocus > -1) {
                                   if (x) x[currentFocus].click();
                                 }
                               }
                           });
                           function addActive(x) {
                             if (!x) return false;
                             removeActive(x);
                             if (currentFocus >= x.length) currentFocus = 0;
                             if (currentFocus < 0) currentFocus = (x.length - 1);
                             x[currentFocus].classList.add("autocomplete-active");
                           }
                           function removeActive(x) {
                             for (var i = 0; i < x.length; i++) {
                               x[i].classList.remove("autocomplete-active");
                             }
                           }
                           function closeAllLists(elmnt) {
                             var x = document.getElementsByClassName("autocomplete-items");
                             for (var i = 0; i < x.length; i++) {
                               if (elmnt !== x[i] && elmnt !== inp) {
                                 x[i].parentNode.removeChild(x[i]);
                               }
                             }
                           }
                           document.addEventListener("click", function (e) {
                               closeAllLists(e.target);
                           });
                         }
                         var suppNames = ["Miguel's", "Patty's", "Mark's"];
                         autocomplete(document.getElementById("suppname"), suppNames);
                         </script>
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