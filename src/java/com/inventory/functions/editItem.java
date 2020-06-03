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

public class editItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<inventoryModel> inventoryList;
            ArrayList<itemModel> itemList = new ArrayList<>();
            ArrayList<supplierModel> supplierList = new ArrayList<>();
            boolean sort = (Boolean) session.getAttribute("sort");
            boolean search = (Boolean) session.getAttribute("search");
            boolean statusView = (Boolean) session.getAttribute("statusView");

            //Start of editing
            int index = Integer.parseInt(request.getParameter("index"));
            String itemDesc = request.getParameter("Item_Description");
            float itemPrice = Float.parseFloat(request.getParameter("Item_Price"));
            String itemUnit = request.getParameter("Item_Unit");
            String supplierName = request.getParameter("Supplier_Name");
            String supplierContact = request.getParameter("Supplier_Contact");
            String supplierAddress = request.getParameter("Supplier_Address");

            if (sort == true) {
                ArrayList<inventoryModel> sortList = (ArrayList<inventoryModel>) session.getAttribute("sortList");
                int itemCode = helperIte.getIteCode(sortList.get(index).getItem_description(), sortList.get(index).getSupplier_name());
                int supplierID = helperIte.getSuppID(sortList.get(index).getItem_description(), sortList.get(index).getSupplier_name());
                if (!itemDesc.isEmpty()) {
                    sortList.get(index).setItem_description(itemDesc);
                }
                if (itemPrice != 0) {
                    sortList.get(index).setItem_price(itemPrice);
                }
                if (!itemUnit.isEmpty()) {
                    sortList.get(index).setItem_unit(itemUnit);
                }
                if (!supplierName.isEmpty()) {
                    sortList.get(index).setSupplier_name(supplierName);
                }
                if (!supplierContact.isEmpty()) {
                    sortList.get(index).setSupplier_contact(supplierContact);
                }
                if (!supplierAddress.isEmpty()) {
                    sortList.get(index).setSupplier_address(supplierName);
                }                                   
                helperIte.updateItem(itemCode, sortList.get(index));
                helperIte.updateSupplier(supplierID, sortList.get(index));
                request.setAttribute("inventoryList", sortList);
            } else if (search == true) {
                ArrayList<inventoryModel> searchList = (ArrayList<inventoryModel>) session.getAttribute("searchList");
                int itemCode = helperIte.getIteCode(searchList.get(index).getItem_description(), searchList.get(index).getSupplier_name());
                int supplierID = helperIte.getSuppID(searchList.get(index).getItem_description(), searchList.get(index).getSupplier_name());
                if (!itemDesc.isEmpty()) {
                    searchList.get(index).setItem_description(itemDesc);
                }
                if (itemPrice != 0) {
                    searchList.get(index).setItem_price(itemPrice);
                }
                if (!itemUnit.isEmpty()) {
                    searchList.get(index).setItem_unit(itemUnit);
                }
                if (!supplierName.isEmpty()) {
                    searchList.get(index).setSupplier_name(supplierName);
                }
                if (!supplierContact.isEmpty()) {
                    searchList.get(index).setSupplier_contact(supplierContact);
                }
                if (!supplierAddress.isEmpty()) {
                    searchList.get(index).setSupplier_address(supplierName);
                }                 
                helperIte.updateItem(itemCode, searchList.get(index));
                helperIte.updateSupplier(supplierID, searchList.get(index));
                request.setAttribute("inventoryList", searchList);
            } else if (statusView == true) {
                ArrayList<inventoryModel> statusList = (ArrayList<inventoryModel>) session.getAttribute("statusList");
                int itemCode = helperIte.getIteCode(statusList.get(index).getItem_description(), statusList.get(index).getSupplier_name());
                int supplierID = helperIte.getSuppID(statusList.get(index).getItem_description(), statusList.get(index).getSupplier_name());
                if (!itemDesc.isEmpty()) {
                    statusList.get(index).setItem_description(itemDesc);
                }
                if (itemPrice != 0) {
                    statusList.get(index).setItem_price(itemPrice);
                }
                if (!itemUnit.isEmpty()) {
                    statusList.get(index).setItem_unit(itemUnit);
                }

                rs = helperIte.checkSupplier(supplierName);
                if (rs.next()) {
                    statusList.get(index).setSupplier_name(supplierName);
                    statusList.get(index).setSupplier_contact(supplierContact);
                } else {
                    if (!supplierName.isEmpty()) {
                        statusList.get(index).setSupplier_name(supplierName);
                    }
                    if (!supplierContact.isEmpty()) {
                        statusList.get(index).setSupplier_contact(supplierContact);
                    }
                    if (!supplierAddress.isEmpty()) {
                        statusList.get(index).setSupplier_address(supplierName);
                    } 
                }
                helperIte.updateItem(itemCode, statusList.get(index));
                helperIte.updateSupplier(supplierID, statusList.get(index));
                request.setAttribute("inventoryList", statusList);
            } else {
                inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
                int itemCode = helperIte.getIteCode(inventoryList.get(index).getItem_description(), inventoryList.get(index).getSupplier_name());
                int supplierID = helperIte.getSuppID(inventoryList.get(index).getItem_description(), inventoryList.get(index).getSupplier_name());
                if (!itemDesc.isEmpty()) {
                    inventoryList.get(index).setItem_description(itemDesc);
                }
                if (itemPrice != 0) {
                    inventoryList.get(index).setItem_price(itemPrice);
                }
                if (!itemUnit.isEmpty()) {
                    inventoryList.get(index).setItem_unit(itemUnit);
                }
                if (!supplierName.isEmpty()) {
                    inventoryList.get(index).setSupplier_name(supplierName);
                }
                if (!supplierContact.isEmpty()) {
                    inventoryList.get(index).setSupplier_contact(supplierContact);
                }
                if (!supplierAddress.isEmpty()) {
                    inventoryList.get(index).setSupplier_address(supplierAddress);
                }
                helperIte.updateItem(itemCode, inventoryList.get(index));
                helperIte.updateSupplier(supplierID, inventoryList.get(index));
                session.setAttribute("inventoryList", inventoryList);
            }
            inventoryList = new ArrayList<>();
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
                item.setCritical_quantity(rs.getInt("CRITICAL_QUANTITY"));
                itemList.add(item);
            }
            session.setAttribute("itemList", itemList);
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
            }
            session.setAttribute("inventoryList", inventoryList);
            session.removeAttribute("searchList");
            session.removeAttribute("sortList");
            session.removeAttribute("statusList");
            session.setAttribute("search", false);
            session.setAttribute("sort", false);
            session.setAttribute("statusView", false);
            request.setAttribute("validationEditModal", true);
            RequestDispatcher rd = request.getRequestDispatcher("Inventory.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(editItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(editItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
