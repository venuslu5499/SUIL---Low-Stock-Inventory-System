package com.inventory.functions;

import com.helper.itemHelper;
import com.model.inventoryModel;
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

public class searchInventory extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;
            RequestDispatcher rd = request.getRequestDispatcher("Inventory.jsp");

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<inventoryModel> inventoryList = new ArrayList<>();

            //Start of searching
            String searchInv = request.getParameter("searchInv");
            String searchDel = request.getParameter("searchDel");

            if ((searchInv == null || searchInv.isEmpty()) && (searchDel == null || searchDel.isEmpty())) {
                if (searchInv == null) {
                    session.setAttribute("search", false);
                    inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
                    request.setAttribute("inventoryList", inventoryList);
                    rd = request.getRequestDispatcher("Delete_Item.jsp");
                    rd.forward(request, response);
                } else if (searchDel == null) {
                    session.setAttribute("search", false);
                    inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
                    request.setAttribute("inventoryList", inventoryList);
                    rd = request.getRequestDispatcher("Inventory.jsp");
                    rd.forward(request, response);
                }
            } else {
                if (searchInv == null) {
                    rs = helperIte.searchInventory(searchDel);
                    while (rs.next()) {
                        inventoryModel inventory = new inventoryModel();
                        inventory.setPo_number(rs.getInt("PO_NUMBER"));
                        inventory.setSupplier_name(rs.getString("SUPPLIER_NAME"));
                        inventory.setSupplier_contact(rs.getString("SUPPLIER_CONTACT"));
                        inventory.setSupplier_address(rs.getString("SUPPLIER_ADDRESS"));
                        inventory.setItem_description(rs.getString("ITEM_DESCRIPTION"));
                        inventory.setItem_price(rs.getInt("ITEM_PRICE"));
                        inventory.setItem_unit(rs.getString("ITEM_UNITS"));
                        inventory.setItem_quantity(rs.getInt("ITEM_QUANTITY"));
                        inventory.setCritical_quantity(rs.getInt("CRITICAL_QUANTITY"));
                        inventory.setDate_delivered(rs.getDate("DATE_DELIVERED"));
                        String status = helperIte.checkStatus(inventory.getItem_quantity(), inventory.getCritical_quantity());
                        inventory.setStatus(status);
                        inventoryList.add(inventory);
                        request.setAttribute("inventoryList", inventoryList);
                        session.setAttribute("searchList", inventoryList);
                        session.setAttribute("search", true);

                        rd = request.getRequestDispatcher("Delete_Item.jsp");

                    }
                } else if (searchDel == null) {
                    rs = helperIte.searchInventory(searchInv);
                    while (rs.next()) {
                        inventoryModel inventory = new inventoryModel();
                        inventory.setPo_number(rs.getInt("PO_NUMBER"));
                        inventory.setSupplier_name(rs.getString("SUPPLIER_NAME"));
                        inventory.setSupplier_contact(rs.getString("SUPPLIER_CONTACT"));
                        inventory.setSupplier_address(rs.getString("SUPPLIER_ADDRESS"));
                        inventory.setItem_description(rs.getString("ITEM_DESCRIPTION"));
                        inventory.setItem_price(rs.getInt("ITEM_PRICE"));
                        inventory.setItem_unit(rs.getString("ITEM_UNITS"));
                        inventory.setItem_quantity(rs.getInt("ITEM_QUANTITY"));
                        inventory.setCritical_quantity(rs.getInt("CRITICAL_QUANTITY"));
                        inventory.setDate_delivered(rs.getDate("DATE_DELIVERED"));
                        String status = helperIte.checkStatus(inventory.getItem_quantity(), inventory.getCritical_quantity());
                        inventory.setStatus(status);
                        inventoryList.add(inventory);
                        request.setAttribute("inventoryList", inventoryList);
                        session.setAttribute("searchList", inventoryList);
                        session.setAttribute("search", true);

                        rd = request.getRequestDispatcher("Inventory.jsp");
                    }
                }
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(searchInventory.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(searchInventory.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
