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

public class sortUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            employeeHelper helperEmp = new employeeHelper(con);

            ArrayList<employeeModel> employeeList = new ArrayList<>();

            //Start sorting
            String header = request.getParameter("header");

            rs = helperEmp.sortUser(header);
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
            request.setAttribute("employeeList", employeeList);
            session.setAttribute("sortList", employeeList);
            session.setAttribute("sort", true);

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
            Logger.getLogger(sortUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(sortUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
