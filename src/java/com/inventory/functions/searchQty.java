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

public class searchQty extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<inventoryModel> inventoryList = new ArrayList<>();
            ArrayList<inventoryModel> searchList = new ArrayList<>();

            //Start of searching
            String add = request.getParameter("searchAddInput");
            String sub = request.getParameter("searchSubInput");

            if ((add == null || add.isEmpty()) && (sub == null || sub.isEmpty())) {
                if (add == null) {
                    inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
                    request.setAttribute("inventoryList", inventoryList);
                    RequestDispatcher rd = request.getRequestDispatcher("Subtract_Qty.jsp");
                    rd.forward(request, response);
                } else if (sub == null) {
                    inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
                    request.setAttribute("inventoryList", inventoryList);
                    RequestDispatcher rd = request.getRequestDispatcher("Add_Qty.jsp");
                    rd.forward(request, response);
                }

            } else {
                if (add == null) {
                    rs = helperIte.searchInventory(sub);
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
                        searchList.add(inventory);
                    }
                    request.setAttribute("inventoryList", inventoryList);
                    session.setAttribute("searchList", searchList);
                    session.setAttribute("search", true);
                    RequestDispatcher rd = request.getRequestDispatcher("Subtract_Qty.jsp");
                    rd.forward(request, response);

                } else if (sub == null) {
                    rs = helperIte.searchInventory(add);
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
                        searchList.add(inventory);
                    }
                    request.setAttribute("inventoryList", inventoryList);
                    session.setAttribute("searchList", searchList);
                    session.setAttribute("search", true);
                    RequestDispatcher rd = request.getRequestDispatcher("Add_Qty.jsp");
                    rd.forward(request, response);

                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(searchQty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(searchQty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
