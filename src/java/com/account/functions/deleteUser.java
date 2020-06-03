package com.account.functions;

import com.helper.employeeHelper;
import com.model.employeeModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class deleteUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            employeeHelper helperEmp = new employeeHelper(con);

            ArrayList<employeeModel> employeeList = new ArrayList<>();

            //Start of Deleting
            int employee_code = Integer.parseInt(request.getParameter("employee_code"));
            int user_code = (Integer) session.getAttribute("user_code");

            if (user_code == employee_code) {
                request.setAttribute("userModal", true);
            } else {
                helperEmp.deleteAccount(employee_code);
                rs = helperEmp.viewAccounts();
                while (rs.next()) {
                    employeeModel employee = new employeeModel();
                    employee.setEmployee_code(rs.getInt("EMPLOYEE_CODE"));
                    employee.setLName(rs.getString("LNAME"));
                    employee.setFName(rs.getString("FNAME"));
                    employee.setEmployee_position(rs.getString("EMPLOYEE_POSITION"));
                    employee.setUsername(rs.getString("USERNAME"));
                    employee.setPassword(rs.getString("PASSWORD"));
                    employeeList.add(employee);
                }
                session.setAttribute("employeeList", employeeList);
            }
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
            Logger.getLogger(deleteUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(deleteUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
