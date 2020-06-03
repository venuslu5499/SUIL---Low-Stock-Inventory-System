package com.helper;

import com.model.deliveryModel;
import com.model.inventoryModel;
import com.model.itemModel;
import com.model.supplierModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class itemHelper {

    private final Connection con;

    public itemHelper(Connection con) {
        this.con = con;
    }

    public ResultSet viewItems() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_ITEMS");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet viewSuppliers() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_SUPPLIER");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet viewDelivery() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_DELIVERY");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void createItem(itemModel item) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO SUIL_ITEMS (item_code, item_description, item_price, item_units, critical_quantity) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, item.getItem_code());
        ps.setString(2, item.getItem_description());
        ps.setFloat(3, item.getItem_price());
        ps.setString(4, item.getItem_units());
        ps.setInt(5, item.getCritical_quantity());
        ps.executeUpdate();
    }

    public void createSupplier(supplierModel supplier) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO SUIL_SUPPLIER (supplier_ID, supplier_name, supplier_contact, supplier_address) VALUES (?, ?, ?, ?)");
        ps.setInt(1, supplier.getSupplier_ID());
        ps.setString(2, supplier.getSupplier_name());
        ps.setString(3, supplier.getSupplier_contact());
        ps.setString(4, supplier.getSupplier_address());
        ps.executeUpdate();
    }

    public void createDelivery(deliveryModel delivery) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO SUIL_DELIVERY (item_code, supplier_ID, item_quantity, date_delivered, po_number) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, delivery.getItem_code());
        ps.setInt(2, delivery.getSupplier_ID());
        ps.setInt(3, delivery.getItem_quantity());
        ps.setDate(4, delivery.getDate_delivered());
        ps.setInt(5, delivery.getPo_number());
        ps.executeUpdate();
    }

    public ResultSet retrieveInventory() throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT PO_NUMBER, SUPPLIER_NAME, SUPPLIER_CONTACT, SUPPLIER_ADDRESS, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_UNITS, ITEM_QUANTITY, CRITICAL_QUANTITY, DATE_DELIVERED\n"
                + "FROM SUIL_DELIVERY\n"
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void updateQuantity(int item_quantity, Date date_delivered, int supplier_id, int item_code) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "UPDATE SUIL_DELIVERY "
                + "SET ITEM_QUANTITY = ?, DATE_DELIVERED = ? "
                + "WHERE SUPPLIER_ID = ? AND ITEM_CODE = ?");
        ps.setInt(1, item_quantity);
        ps.setDate(2, date_delivered);
        ps.setInt(3, supplier_id);
        ps.setInt(4, item_code);
        ps.executeUpdate();
    }

    public void updateItem(int itemCode, inventoryModel model) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "UPDATE SUIL_ITEMS\n"
                + "SET ITEM_DESCRIPTION = ?, ITEM_PRICE = ?, ITEM_UNITS = ?\n"
                + "WHERE ITEM_CODE = ?");
        ps.setString(1, model.getItem_description());
        ps.setFloat(2, model.getItem_price());
        ps.setString(3, model.getItem_unit());
        ps.setInt(4, itemCode);
        ps.executeUpdate();
    }

    public void updateSupplier(int supplierID, inventoryModel model) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "UPDATE SUIL_SUPPLIER\n"
                + "SET SUPPLIER_NAME = ?, SUPPLIER_CONTACT = ?, SUPPLIER_ADDRESS = ?\n"
                + "WHERE SUPPLIER_ID = ?");
        ps.setString(1, model.getSupplier_name());
        ps.setString(2, model.getSupplier_contact());
        ps.setString(3, model.getSupplier_address());
        ps.setInt(4, supplierID);
        ps.executeUpdate();
    }

    public void deleteSupplier(int supplier_ID) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM SUIL_SUPPLIER WHERE supplier_id = ?");
        ps.setInt(1, supplier_ID);
        ps.executeUpdate();
    }

    public void deleteItem(int item_code) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM SUIL_ITEMS WHERE item_code = ?");
        ps.setInt(1, item_code);
        ps.executeUpdate();
    }

    public void deleteDelivery(int item_code, int supplier_ID) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM SUIL_DELIVERY WHERE item_code = ? AND supplier_id = ?");
        ps.setInt(1, item_code);
        ps.setInt(2, supplier_ID);
        ps.executeUpdate();
    }

    public ResultSet searchInventory(String input) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT PO_NUMBER, SUPPLIER_NAME, SUPPLIER_CONTACT, SUPPLIER_ADDRESS, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_QUANTITY, ITEM_UNITS, CRITICAL_QUANTITY, DATE_DELIVERED\n"
                + "FROM SUIL_DELIVERY\n"
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE\n"
                + "WHERE ITEM_DESCRIPTION LIKE  '%" +input +"%'  OR\n" 
                + "SUPPLIER_NAME LIKE  '%" +input +"%' OR\n" 
                + "ITEM_UNITS LIKE  '%" +input +"%'");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet checkItem(String itemName) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_ITEMS WHERE item_description = ?");
        ps.setString(1, itemName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet checkSupplier(String supplierName) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_SUPPLIER WHERE supplier_name = ?");
        ps.setString(1, supplierName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet checkInventory(String supplierName, String itemName) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT * "
                + "FROM SUIL_DELIVERY "
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE\n"
                + "WHERE ITEM_DESCRIPTION = ? AND SUPPLIER_NAME = ?");
        ps.setString(1, itemName);
        ps.setString(2, supplierName);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public String checkStatus(int currentQty, int criticalQty) {
        String status = "error";
        if (currentQty == 0) {
            status = "No Stock";
        } else if (currentQty > criticalQty) {
            status = "In Stock";
        } else if (currentQty <= criticalQty) {
            status = "Low Stock";
        }

        return status;
    }

    public int statusCounterLow(String status, int counter) {
        if ("Low Stock".equals(status)) {
            counter += 1;
        }
        return counter;
    }
    
    public int statusCounterNo(String status, int counter) {
        if ("No Stock".equals(status)) {
            counter += 1;
        }
        return counter;
    }

    public float valueCounter(ArrayList<inventoryModel> inventoryList) {
        float val = 0;
        for (int i = 0; inventoryList.size() > i; i++) {
            val = val + inventoryList.get(i).getItem_quantity() * inventoryList.get(i).getItem_price();
        }

        return val;
    }

    public int countSupplier(int supplier_id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(SUPPLIER_ID) AS total FROM SUIL_DELIVERY WHERE SUPPLIER_ID = ?");
        ps.setInt(1, supplier_id);
        ResultSet rs = ps.executeQuery();
        int count = -999;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int countItem(int item_code) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(ITEM_CODE) AS total FROM SUIL_DELIVERY WHERE ITEM_CODE = ?");
        ps.setInt(1, item_code);
        ResultSet rs = ps.executeQuery();
        int count = -999;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int maxItem(ArrayList<inventoryModel> inventoryList) throws SQLException {
        int max = 0;
        for (int i = 0; inventoryList.size() > i; i++) {
            if (inventoryList.get(i).getItem_quantity() > max) {
                max = inventoryList.get(i).getItem_quantity();
            }
        }
        return max;
    }

    public ResultSet sortInventory(String header) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT PO_NUMBER, SUPPLIER_NAME, SUPPLIER_CONTACT, SUPPLIER_ADDRESS, ITEM_DESCRIPTION, ITEM_PRICE, ITEM_UNITS, ITEM_QUANTITY, CRITICAL_QUANTITY, DATE_DELIVERED\n"
                + "FROM SUIL_DELIVERY\n"
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE\n"
                + "ORDER BY " + header);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public int getSuppID(String itemName, String supplierName) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT SUIL_DELIVERY.SUPPLIER_ID\n"
                + "FROM SUIL_DELIVERY\n"
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE\n"
                + "WHERE ITEM_DESCRIPTION = ? AND SUPPLIER_NAME = ?");
        ps.setString(1, itemName);
        ps.setString(2, supplierName);
        ResultSet rs = ps.executeQuery();
        int count = -999;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public int getIteCode(String itemName, String supplierName) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT SUIL_DELIVERY.ITEM_CODE\n"
                + "FROM SUIL_DELIVERY\n"
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE\n"
                + "WHERE ITEM_DESCRIPTION = ? AND SUPPLIER_NAME = ?");
        ps.setString(1, itemName);
        ps.setString(2, supplierName);
        ResultSet rs = ps.executeQuery();
        int count = -999;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    public ResultSet getDates2(Date date_delivered) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT DISTINCT DATE_DELIVERED FROM SUIL_DELIVERY \n"
                + "where MONTH(DATE_DELIVERED) = MONTH('" + date_delivered + "') AND YEAR(DATE_DELIVERED) = YEAR('" + date_delivered + "')");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet getDates(ArrayList<inventoryModel> inventoryList) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT DISTINCT DATE_DELIVERED FROM SUIL_DELIVERY");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public int getTotal(ArrayList<inventoryModel> inventoryList) throws SQLException {
        int total = 0;
        for (int i = 0; inventoryList.size() > i; i++) {
            total += inventoryList.get(i).getItem_quantity();

        }
        return total;
    }

    public ResultSet generateDateReport(Date date_delivered) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "Select * from SUIL_DELIVERY "
                + "INNER JOIN SUIL_SUPPLIER ON SUIL_DELIVERY.SUPPLIER_ID = SUIL_SUPPLIER.SUPPLIER_ID\n"
                + "INNER JOIN SUIL_ITEMS ON SUIL_DELIVERY.ITEM_CODE = SUIL_ITEMS.ITEM_CODE\n"
                + "where MONTH(DATE_DELIVERED) = MONTH('" + date_delivered + "') AND YEAR(DATE_DELIVERED) = YEAR('" + date_delivered + "')");
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    

}
