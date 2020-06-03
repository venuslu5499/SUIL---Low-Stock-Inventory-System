package com.inventory.functions;

import com.helper.itemHelper;
import com.model.inventoryModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class viewStatus extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<inventoryModel> inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
            ArrayList<inventoryModel> statusList = new ArrayList<>();

            //Start of viewing
            String status = request.getParameter("status");

            switch (status) {
                case "No":
                    for (int i = 0; i < inventoryList.size(); i++) {
                        if (inventoryList.get(i).getStatus().equals("No Stock")) {
                            statusList.add(inventoryList.get(i));
                        }
                    }
                    break;
                case "Low":
                    for (int i = 0; i < inventoryList.size(); i++) {
                        if (inventoryList.get(i).getStatus().equals("Low Stock")) {
                            statusList.add(inventoryList.get(i));
                        }
                    }
                    break;
                case "In":
                    for (int i = 0; i < inventoryList.size(); i++) {
                        if (inventoryList.get(i).getStatus().equals("In Stock")) {
                            statusList.add(inventoryList.get(i));
                        }
                    }
                    break;
                default:
                    break;
            }
            request.setAttribute("inventoryList", statusList);
            session.setAttribute("statusList", statusList);
            session.setAttribute("statusView", true);
            RequestDispatcher rd = request.getRequestDispatcher("Inventory.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
