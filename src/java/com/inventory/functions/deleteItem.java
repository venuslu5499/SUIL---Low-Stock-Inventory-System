package com.inventory.functions;

import com.helper.itemHelper;
import com.model.inventoryModel;
import com.model.itemModel;
import com.model.supplierModel;
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

public class deleteItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<inventoryModel> inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
            ArrayList<inventoryModel> searchList = (ArrayList<inventoryModel>) session.getAttribute("searchList");
            ArrayList<inventoryModel> sortList = (ArrayList<inventoryModel>) session.getAttribute("sortList");
            ArrayList<itemModel> itemList = new ArrayList<>();
            ArrayList<supplierModel> supplierList = new ArrayList<>();
            boolean sort = (Boolean) session.getAttribute("sort");
            boolean search = (Boolean) session.getAttribute("search");

            //Start of deleting
            if (search == true) {
                int index = Integer.parseInt(request.getParameter("index"));
                String supplier_name = searchList.get(index).getSupplier_name();
                String item_description = searchList.get(index).getItem_description();
                int supplier_id = helperIte.getSuppID(item_description, supplier_name);
                int item_code = helperIte.getIteCode(item_description, supplier_name);
                int rowSupp = helperIte.countSupplier(supplier_id);
                int rowItem = helperIte.countItem(item_code);
                helperIte.deleteDelivery(item_code, supplier_id);

                if (rowSupp == 1) {
                    helperIte.deleteSupplier(supplier_id);
                }
                if (rowItem == 1) {
                    helperIte.deleteItem(item_code);
                }
            } else if (sort == true) {
                int index = Integer.parseInt(request.getParameter("index"));
                String supplier_name = sortList.get(index).getSupplier_name();
                String item_description = sortList.get(index).getItem_description();
                int supplier_id = helperIte.getSuppID(item_description, supplier_name);
                int item_code = helperIte.getIteCode(item_description, supplier_name);
                int rowSupp = helperIte.countSupplier(supplier_id);
                int rowItem = helperIte.countItem(item_code);
                helperIte.deleteDelivery(item_code, supplier_id);

                if (rowSupp == 1) {
                    helperIte.deleteSupplier(supplier_id);
                }
                if (rowItem == 1) {
                    helperIte.deleteItem(item_code);
                }
            } else {
                int index = Integer.parseInt(request.getParameter("index"));
                String supplier_name = inventoryList.get(index).getSupplier_name();
                String item_description = inventoryList.get(index).getItem_description();
                int supplier_id = helperIte.getSuppID(item_description, supplier_name);
                int item_code = helperIte.getIteCode(item_description, supplier_name);
                int rowSupp = helperIte.countSupplier(supplier_id);
                int rowItem = helperIte.countItem(item_code);
                helperIte.deleteDelivery(item_code, supplier_id);

                if (rowSupp == 1) {
                    helperIte.deleteSupplier(supplier_id);
                }
                if (rowItem == 1) {
                    helperIte.deleteItem(item_code);
                }
            }
            rs = helperIte.viewSuppliers();
            while (rs.next()) {
                supplierModel supplier = new supplierModel();
                supplier.setSupplier_ID(rs.getInt("SUPPLIER_ID"));
                supplier.setSupplier_name(rs.getString("SUPPLIER_NAME"));
                supplier.setSupplier_contact(rs.getString("SUPPLIER_CONTACT"));
                supplier.setSupplier_address(rs.getString("SUPPLIER_ADDRESS"));
                supplierList.add(supplier);
            }
            session.setAttribute("supplierList", supplierList);

            rs = helperIte.viewItems();
            while (rs.next()) {
                itemModel item = new itemModel();
                item.setItem_code(rs.getInt("ITEM_CODE"));
                item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
                item.setItem_price(rs.getFloat("ITEM_PRICE"));
                item.setItem_units(rs.getString("ITEM_UNITS"));
                itemList.add(item);
            }
            session.setAttribute("itemList", itemList);

            inventoryList = new ArrayList<>();
            int counterLow = 0;
            int counterNo = 0;
            rs = helperIte.retrieveInventory();
            while (rs.next()) {
                inventoryModel inventory = new inventoryModel();
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
            session.removeAttribute("statusList");
            session.setAttribute("search", false);
            session.setAttribute("sort", false);
            request.setAttribute("delModal", true);
            RequestDispatcher rd = request.getRequestDispatcher("Delete_Item.jsp");
            rd.forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(deleteItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(deleteItem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
