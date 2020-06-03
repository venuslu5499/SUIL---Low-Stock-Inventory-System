package com.inventory.functions;

import com.helper.itemHelper;
import com.model.inventoryModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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

public class subQty extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;
            Date dateDel;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<inventoryModel> inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
            ArrayList<inventoryModel> searchList = (ArrayList<inventoryModel>) session.getAttribute("searchList");
            ArrayList<inventoryModel> sortList = (ArrayList<inventoryModel>) session.getAttribute("sortList");
            boolean sort = (Boolean) session.getAttribute("sort");
            boolean search = (Boolean) session.getAttribute("search");

            //Start of updating
            String[] qtyArray = request.getParameterValues("subQty");

            if (search == true) {
                int newQty = 0;
                for (int counter = 0; counter < searchList.size(); counter++) {
                    int supplier_id = helperIte.getSuppID(searchList.get(counter).getItem_description(), searchList.get(counter).getSupplier_name());
                    int item_code = helperIte.getIteCode(searchList.get(counter).getItem_description(), searchList.get(counter).getSupplier_name());
                    int currentQty = searchList.get(counter).getItem_quantity();
                    int subQty = Integer.parseInt(qtyArray[counter]);
                    if (currentQty > subQty) {
                        newQty = currentQty - subQty;
                    } else if (currentQty <= subQty) {
                        newQty = 0;
                    }
                    dateDel = inventoryList.get(counter).getDate_delivered();
                    helperIte.updateQuantity(newQty, dateDel, supplier_id, item_code);
                }
            } else if (sort == true) {
                int newQty = 0;
                for (int counter = 0; counter < sortList.size(); counter++) {
                    int supplier_id = helperIte.getSuppID(sortList.get(counter).getItem_description(), sortList.get(counter).getSupplier_name());
                    int item_code = helperIte.getIteCode(sortList.get(counter).getItem_description(), sortList.get(counter).getSupplier_name());
                    int currentQty = sortList.get(counter).getItem_quantity();
                    int subQty = Integer.parseInt(qtyArray[counter]);
                    if (currentQty > subQty) {
                        newQty = currentQty - subQty;
                    } else if (currentQty <= subQty) {
                        newQty = 0;
                    }
                    dateDel = sortList.get(counter).getDate_delivered();
                    helperIte.updateQuantity(newQty, dateDel, supplier_id, item_code);
                }
            } else {
                for (int counter = 0; counter < inventoryList.size(); counter++) {
                    int newQty = 0;
                    int supplier_id = helperIte.getSuppID(inventoryList.get(counter).getItem_description(), inventoryList.get(counter).getSupplier_name());
                    int item_code = helperIte.getIteCode(inventoryList.get(counter).getItem_description(), inventoryList.get(counter).getSupplier_name());
                    int currentQty = inventoryList.get(counter).getItem_quantity();
                    int subQty = Integer.parseInt(qtyArray[counter]);
                    if (currentQty > subQty) {
                        newQty = currentQty - subQty;
                    } else if (currentQty <= subQty) {
                        newQty = 0;
                    }
                    dateDel = inventoryList.get(counter).getDate_delivered();
                    helperIte.updateQuantity(newQty, dateDel, supplier_id, item_code);
                }
            }
            inventoryList = new ArrayList<>();
            int counterLow = 0;
            int counterNo = 0;
            rs = helperIte.retrieveInventory();
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
                counterLow = helperIte.statusCounterLow(status, counterLow);
                counterNo = helperIte.statusCounterNo(status, counterNo);
            }
            float value = helperIte.valueCounter(inventoryList);
            session.setAttribute("inVal", value);
            session.setAttribute("value", value);
            session.setAttribute("counterLow", counterLow);
            session.setAttribute("counterNo", counterNo);
            session.setAttribute("inventoryList", inventoryList);
            session.removeAttribute("searchList");
            session.removeAttribute("sortList");
            session.setAttribute("search", false);
            session.setAttribute("sort", false);
            request.setAttribute("subModal", true);
            RequestDispatcher rd = request.getRequestDispatcher("Subtract_Qty.jsp");
            rd.forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(subQty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(subQty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
