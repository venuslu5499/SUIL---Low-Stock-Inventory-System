package com.account.functions;

import com.helper.employeeHelper;
import com.model.employeeModel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class addUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Random rand = new Random(); 
            ServletContext context = getServletContext();
            ResultSet rs = null;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            employeeHelper helperEmp = new employeeHelper(con);

            ArrayList<employeeModel> employeeList = (ArrayList<employeeModel>) session.getAttribute("employeeList");

            //Start of Adding
            int newEmployeeCode = rand.nextInt(1000);
            String newLName = request.getParameter("LName");
            String newFName = request.getParameter("FName");
            String newEmployeePosition = request.getParameter("employee_position");
            String newUsername = request.getParameter("username");
            String newPassword = request.getParameter("password");
            byte[] key = context.getInitParameter("key").getBytes();

            String encryptedPass = helperEmp.encrypt(newPassword, key);
            employeeModel employee = new employeeModel();
            employee.setEmployee_code(newEmployeeCode);
            employee.setLName(newLName);
            employee.setFName(newFName);
            employee.setEmployee_position(newEmployeePosition);
            employee.setUsername(newUsername);
            employee.setPassword(encryptedPass);
            helperEmp.createAccount(employee);
            employeeList.add(employee);
            session.setAttribute("employeeList", employeeList);

            RequestDispatcher rd = request.getRequestDispatcher("Manage_Accounts.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(addUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(addUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
