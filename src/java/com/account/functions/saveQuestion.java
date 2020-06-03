
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

public class saveQuestion extends HttpServlet {

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
            String question = request.getParameter("question");
            String answer = request.getParameter("answer");
            int employee_code = (Integer)session.getAttribute("user_code");
            int indexArray  = helperEmp.getArrIndex(employeeList, employee_code);
            employeeList.get(indexArray).setQuestion_one(question);
            employeeList.get(indexArray).setAnswer_one(answer);

            helperEmp.updateAccount(employee_code, employeeList.get(indexArray));
            if (pos.equals("Owner")) {
                rd = request.getRequestDispatcher("OW_Security_Questions.jsp");

            } else {
                rd = request.getRequestDispatcher("OM_Security_Questions.jsp");
            }
            
            request.setAttribute("securityModal", true);
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
            Logger.getLogger(saveQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(saveQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
