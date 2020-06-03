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
        <script type="text/javascript">
            window.onload = function () {

                var test = <%=(Boolean) request.getAttribute("wrongAnswer")%>;
                if (test === false)
                {
                    document.getElementById('message').style.color = 'red';
                    document.getElementById('message').style.fontWeight = 'bold';
                    document.getElementById('message').style.fontSize = 'small';
                    document.getElementById('message').innerHTML = 'Wrong Answer';
                }
            };
        </script>
    </head>

    <body class="text-center d-xl-flex justify-content-xl-center align-items-xl-center" style="background-image: url(&quot;assets/img/mike-kenneally-TD4DBagg2wE-unsplash.jpg&quot;);">
        <div class="row">
            <div class="form-box" style="background-color: #ffffff;opacity: 0.91;">
                <form action="checkUser" method="POST">
                    <fieldset> 
                        <img id="avatar" class="avatar round" src="assets/img/58602729_355309098436098_2854881851326070784_n.jpg" style="width: 150px;height: 125px;">
                        <br>
                        ${question}<br><br>
                        <input class="form-control" type="text" id="username" name="forget_answer" placeholder="Answer" required="">
                        <button class="btn btn-primary btn-block" type="submit" style="background-color: rgb(136,57,0);">Submit</button><br>
                        <center><span id='message'></span></center><br>
                        <a href="index.jsp">Go Back </a>
                    </fieldset>
                </form>
            </div>
        </div>
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/bs-init.js"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.15/js/dataTables.bootstrap.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
        <script src="assets/js/Bootstrap-DataTables.js"></script>
        <script src="assets/js/Sidebar-Menu.js"></script>
    </body>

</html>