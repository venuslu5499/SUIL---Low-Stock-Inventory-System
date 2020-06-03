package com.inventory.functions;

import com.helper.itemHelper;
import com.model.deliveryModel;
import com.model.inventoryModel;
import com.model.itemModel;
import com.model.supplierModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class addItem extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Random rand = new Random();
            ResultSet rs;

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            itemHelper helperIte = new itemHelper(con);

            ArrayList<itemModel> itemList = (ArrayList<itemModel>) session.getAttribute("itemList");
            ArrayList<supplierModel> supplierList = (ArrayList<supplierModel>) session.getAttribute("supplierList");
            ArrayList<deliveryModel> deliveryList = (ArrayList<deliveryModel>) session.getAttribute("deliveryList");
            ArrayList<inventoryModel> inventoryList = new ArrayList<>();

            //Start of Adding
            int poNumber = rand.nextInt(2000);
            int supplierID = rand.nextInt(1000);
            String supplierName = request.getParameter("Supplier_Name");
            String supplierContact = request.getParameter("Supplier_Contact");
            String supplierAddress = request.getParameter("Supplier_Address");
            String itemDesc = request.getParameter("Item_Description");
            int itemCode = rand.nextInt(1000);
            float itemPrice = Float.parseFloat(request.getParameter("Item_Price"));
            String itemUnit = request.getParameter("Item_Unit");
            int CriValue = Integer.parseInt(request.getParameter("Critical_Qty"));
            int StaValue = Integer.parseInt(request.getParameter("Starting_Qty"));
            Date dateDel = Date.valueOf(request.getParameter("Date_Delivered"));

            //Check if item is in the Database if yes then it will not add
            rs = helperIte.checkInventory(supplierName, itemDesc);
            if(rs.next()){
                request.setAttribute("insertErrorModal", true);
                RequestDispatcher rd = request.getRequestDispatcher("Insert_Item.jsp");
                rd.forward(request, response);
            }
            //Check if item is in the Database if yes then it will not add
            rs = helperIte.checkItem(itemDesc);
            if (rs.next()) {
                itemCode = rs.getInt("ITEM_CODE");
            } else {
                itemModel item = new itemModel();
                item.setItem_code(itemCode);
                item.setItem_description(itemDesc);
                item.setItem_price(itemPrice);
                item.setItem_units(itemUnit);
                item.setCritical_quantity(CriValue);
                helperIte.createItem(item);
                itemList.add(item);
                session.setAttribute("itemList", itemList);
            }
            //Check if supplier is in the Database if yes then it will not add
            rs = helperIte.checkSupplier(supplierName);
            if (rs.next()) {
                supplierID = rs.getInt("SUPPLIER_ID");
            } else {
                supplierModel supplier = new supplierModel();
                supplier.setSupplier_ID(supplierID);
                supplier.setSupplier_name(supplierName);
                supplier.setSupplier_contact(supplierContact);
                supplier.setSupplier_address(supplierAddress);
                helperIte.createSupplier(supplier);
                supplierList.add(supplier);
                session.setAttribute("supplierList", supplierList);
            }
            // add item to delivery table
            deliveryModel delivery = new deliveryModel();
            delivery.setPo_number(poNumber);
            delivery.setItem_code(itemCode);
            delivery.setSupplier_ID(supplierID);
            delivery.setItem_quantity(StaValue);
            delivery.setDate_delivered(dateDel);
            helperIte.createDelivery(delivery);
            deliveryList.add(delivery);
            session.setAttribute("deliveryList", deliveryList);
            //add all items again to display
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

            request.setAttribute("validationModal", true);
            RequestDispatcher rd = request.getRequestDispatcher("Insert_Item.jsp");
            rd.forward(request, response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ParseException ex) {
            RequestDispatcher rd = request.getRequestDispatcher("Error_Page.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | ParseException ex) {
            RequestDispatcher rd = request.getRequestDispatcher("Error_Page.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
