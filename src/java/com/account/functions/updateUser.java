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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class updateUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ServletContext context = getServletContext();
            ResultSet rs;
            RequestDispatcher rd;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            employeeHelper helperEmp = new employeeHelper(con);

            ArrayList<employeeModel> employeeList = (ArrayList<employeeModel>) session.getAttribute("employeeList");
            String pos = (String) session.getAttribute("employee_position");

            //Start of Updating
            String changePass = request.getParameter("changePass");
            String LName = request.getParameter("LName");
            String FName = request.getParameter("FName");
            String employee_position = request.getParameter("employee_position");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            int employee_code;
            int indexArray;

            if (changePass == null) {
                employee_code = Integer.parseInt(request.getParameter("employee_code"));
                indexArray = helperEmp.getArrIndex(employeeList, employee_code);
                if (!LName.isEmpty()) {
                    employeeList.get(indexArray).setLName(LName);
                }
                if (!FName.isEmpty()) {
                    employeeList.get(indexArray).setFName(FName);
                }
                if (employee_position != null) {
                    employeeList.get(indexArray).setEmployee_position(employee_position);
                }
                if (!username.isEmpty()) {
                    employeeList.get(indexArray).setUsername(username);
                }
                helperEmp.updateAccount(employee_code, employeeList.get(indexArray));              
                rd = request.getRequestDispatcher("Manage_Accounts.jsp");    
            } else {
                employee_code = (Integer)session.getAttribute("user_code");
                indexArray = helperEmp.getArrIndex(employeeList, employee_code);
                byte[] key = context.getInitParameter("key").getBytes();
                String encryptedPass = helperEmp.encrypt(password, key);
                employeeList.get(indexArray).setPassword(encryptedPass);
                helperEmp.updateAccount(employee_code, employeeList.get(indexArray));
                request.setAttribute("passModal", true);
                if (pos.equals("Owner")) {
                    rd = request.getRequestDispatcher("OW_Change_Pass.jsp");

                } else {
                    rd = request.getRequestDispatcher("OM_Change_Pass.jsp");
                }
            }
            employeeList = new ArrayList<>();
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
            rd.forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(updateUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
