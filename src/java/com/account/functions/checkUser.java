package com.account.functions;

import com.helper.employeeHelper;
import com.model.employeeModel;
import com.helper.itemHelper;
import com.model.deliveryModel;
import com.model.inventoryModel;
import com.model.itemModel;
import com.model.supplierModel;
import java.sql.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import javax.servlet.ServletContext;

public class checkUser extends HttpServlet {

    Connection con = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            String userDB = config.getInitParameter("userDB");
            String passDB = config.getInitParameter("passDB");
            String url = config.getInitParameter("url");
            Class.forName(config.getInitParameter("driver"));
            con = DriverManager.getConnection(url, userDB, passDB);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(checkUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext context = getServletContext();
        ResultSet rs;
        employeeHelper helperEmp = new employeeHelper(con);
        itemHelper helperIte = new itemHelper(con);

        HttpSession session = request.getSession();
        session.setAttribute("con", con);
        session.setAttribute("sort", false);
        session.setAttribute("search", false);
        session.setAttribute("statusView", false);

        ArrayList<employeeModel> employeeList = new ArrayList<>();
        ArrayList<itemModel> itemList = new ArrayList<>();
        ArrayList<supplierModel> supplierList = new ArrayList<>();
        ArrayList<deliveryModel> deliveryList = new ArrayList<>();
        ArrayList<inventoryModel> inventoryList = new ArrayList<>();
        
        //Check Forget Password
            String inputEmployeePosition = "";
            String encryptedPass = "";
            String inputUsername = request.getParameter("forget_username");
            String forgetAnswer = request.getParameter("forget_answer");
            if(inputUsername != null) {
                rs = helperEmp.retrieveQuestion(inputUsername);
                if(rs.next()){
                     session.setAttribute("username", inputUsername);
                     session.setAttribute("question", rs.getString("QUESTION_ONE"));
                     RequestDispatcher rd = request.getRequestDispatcher("Forget_Password.jsp");
                     rd.forward(request, response);
                }
            } else if (forgetAnswer != null){
                inputUsername = (String)session.getAttribute("username");
                rs = helperEmp.retrieveAnswer((String)session.getAttribute("username"));
                if(rs.next()){
                    String answer = rs.getString("ANSWER_ONE");
                    if (!forgetAnswer.equals(answer)){
                        String question = (String) session.getAttribute("question");
                        session.setAttribute("question", question);
                        RequestDispatcher rd = request.getRequestDispatcher("Forget_Password.jsp");
                        rd.forward(request, response);   
                    }
                    session.removeAttribute("question");
                    rs = helperEmp.retrieveForgetAccount(inputUsername);
                    if(rs.next())
                    {
                       encryptedPass = rs.getString("PASSWORD");
                    }   
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("Forget_Password.jsp");
                    rd.forward(request, response); 
                }                 
            } else {
            //Start of Checking
                inputUsername = request.getParameter("username");
                String inputPassword = request.getParameter("password");
                byte[] key = context.getInitParameter("key").getBytes(); 
                encryptedPass = helperEmp.encrypt(inputPassword, key);
            }
            rs = helperEmp.retrieveAccount(inputUsername, encryptedPass);
            if (rs.next()) {
                session.setAttribute("inputUsername", inputUsername);
                session.setAttribute("user_code", rs.getInt("EMPLOYEE_CODE"));

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

                rs = helperIte.viewItems();
                while (rs.next()) {
                    itemModel item = new itemModel();
                    item.setItem_code(rs.getInt("ITEM_CODE"));
                    item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
                    item.setItem_price(rs.getFloat("ITEM_PRICE"));
                    item.setCritical_quantity(rs.getInt("CRITICAL_QUANTITY"));
                    item.setItem_units(rs.getString("ITEM_UNITS"));
                    itemList.add(item);
                }
                session.setAttribute("itemList", itemList);

                rs = helperIte.viewSuppliers();
                while (rs.next()) {
                    supplierModel supplier = new supplierModel();
                    supplier.setSupplier_ID(rs.getInt("SUPPLIER_ID"));
                    supplier.setSupplier_name(rs.getString("SUPPLIER_NAME"));
                    supplier.setSupplier_contact(rs.getString("SUPPLIER_CONTACT"));
                    supplierList.add(supplier);
                }
                session.setAttribute("supplierList", supplierList);

                rs = helperIte.viewDelivery();
                while (rs.next()) {
                    deliveryModel delivery = new deliveryModel();
                    delivery.setPo_number(rs.getInt("PO_NUMBER"));
                    delivery.setItem_code(rs.getInt("ITEM_CODE"));
                    delivery.setSupplier_ID(rs.getInt("SUPPLIER_ID"));
                    delivery.setItem_quantity(rs.getInt("ITEM_QUANTITY"));
                    delivery.setDate_delivered(rs.getDate("DATE_DELIVERED"));
                    deliveryList.add(delivery);
                }
                session.setAttribute("deliveryList", deliveryList);

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
                    inventory.setItem_price(rs.getFloat("ITEM_PRICE"));
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
                
                rs = helperEmp.retrieveAccount(inputUsername, encryptedPass);
                if (rs.next()) {
                    inputEmployeePosition = rs.getString("employee_position");
                }
                session.setAttribute("employee_position", inputEmployeePosition);

                if (inputEmployeePosition.equals("Owner")) {
                    RequestDispatcher rd = request.getRequestDispatcher("OW_Home_Page.jsp");
                    rd.forward(request, response);
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("OM_Home_Page.jsp");
                    rd.forward(request, response);
                }

            } else {
                boolean loginResult = false;
                request.setAttribute("errorMessage", loginResult);
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(checkUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(checkUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
