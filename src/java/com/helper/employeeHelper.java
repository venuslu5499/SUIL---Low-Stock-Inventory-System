package com.helper;

import com.model.employeeModel;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

public class employeeHelper {

    private final Connection con;

    public employeeHelper(Connection con) {
        this.con = con;
    }

    public void createAccount(employeeModel model) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO SUIL_USERS (employee_code, LName, FName, employee_position, username, password) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1, model.getEmployee_code());
        ps.setString(2, model.getLName());
        ps.setString(3, model.getFName());
        ps.setString(4, model.getEmployee_position());
        ps.setString(5, model.getUsername());
        ps.setString(6, model.getPassword());
        ps.executeUpdate();
    }

    public ResultSet retrieveAccount(String username, String password) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_USERS WHERE username = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet retrieveForgetAccount(String username) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_USERS WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet retrieveQuestion(String username) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT QUESTION_ONE FROM SUIL_USERS WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
    
    public ResultSet retrieveAnswer(String username) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT ANSWER_ONE FROM SUIL_USERS WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void updateAccount(int employee_code, employeeModel model) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "UPDATE SUIL_USERS "
                + "SET LNAME = ?, FNAME = ?, EMPLOYEE_POSITION = ?, USERNAME = ?, PASSWORD = ?, QUESTION_ONE = ?, ANSWER_ONE = ?"
                + "WHERE EMPLOYEE_CODE = ?");
        ps.setString(1, model.getLName());
        ps.setString(2, model.getFName());
        ps.setString(3, model.getEmployee_position());
        ps.setString(4, model.getUsername());
        ps.setString(5, model.getPassword());
        ps.setString(6, model.getQuestion_one());
        ps.setString(7, model.getAnswer_one());
        ps.setInt(8, employee_code);
        ps.executeUpdate();
    }

    public void deleteAccount(int employee_code) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM SUIL_USERS WHERE employee_code = ?");
        ps.setInt(1, employee_code);
        ps.executeUpdate();
    }

    public ResultSet viewAccounts() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_USERS");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet searchAccounts(ResultSet rs, String input) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "SELECT * "
            + "FROM SUIL_USERS "
            + "WHERE LName LIKE  '%" +input +"%' OR\n"
            + "FName LIKE  '%" +input +"%' OR\n"
            + "employee_position LIKE  '%" +input +"%' OR\n"
            + "username LIKE  '%" +input +"%' OR\n"
            + "employee_code LIKE  '%" +input +"%'");
        rs = ps.executeQuery();
        return rs;
    }

    public ResultSet sortUser(String header) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
                "SELECT *\n"
                + "FROM SUIL_USERS\n"
                + "ORDER BY " + header);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public int getArrIndex(ArrayList<employeeModel> arr, int employee_code) throws SQLException {
        for (int i = 0; i <= arr.size(); i++) {
            if (arr.get(i).getEmployee_code() == employee_code) {
                return i;
            }
        }
        return -999;
    }

    public String getName(int employee_code) throws SQLException {
        String name = " ";
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SUIL_USERS WHERE EMPLOYEE_CODE = ?");
        ps.setInt(1, employee_code);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            name = rs.getString("LNAME") + ", " + rs.getString("FNAME");
        }
        return name;
    }

    public String encrypt(String strToEncrypt, byte[] key) {
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        }
        return encryptedString;
    }
}
