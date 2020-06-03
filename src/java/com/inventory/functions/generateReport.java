package com.inventory.functions;

import com.helper.employeeHelper;
import com.helper.itemHelper;
import com.itextpdf.text.BaseColor;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.model.inventoryModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

public class generateReport extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, DocumentException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Document docs = new Document();
            long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            PdfWriter.getInstance(docs, new FileOutputStream("C:\\Users\\Banini\\Desktop\\Suil_Report - " + date + ".pdf"));
            docs.setPageSize(PageSize.A4.rotate());
            docs.open();

            HttpSession session = request.getSession();
            Connection con = (Connection) session.getAttribute("con");
            ArrayList<inventoryModel> inventoryList;

            itemHelper helperIte = new itemHelper(con);
            employeeHelper helperEmp = new employeeHelper(con);

            if (request.getParameter("dateReport") != null) {
                inventoryList = new ArrayList<>();
                Date dateReport = Date.valueOf(request.getParameter("dateReport"));
                session.setAttribute("barDate", dateReport);
                ResultSet rs = helperIte.generateDateReport(dateReport);
                while (rs.next()) {
                    inventoryModel inventory = new inventoryModel();
                    inventory.setSupplier_name(rs.getString("SUPPLIER_NAME"));
                    inventory.setSupplier_contact(rs.getString("SUPPLIER_CONTACT"));
                    inventory.setItem_description(rs.getString("ITEM_DESCRIPTION"));
                    inventory.setItem_price(rs.getFloat("ITEM_PRICE"));
                    inventory.setItem_unit(rs.getString("ITEM_UNITS"));
                    inventory.setItem_quantity(rs.getInt("ITEM_QUANTITY"));
                    inventory.setCritical_quantity(rs.getInt("CRITICAL_QUANTITY"));
                    inventory.setDate_delivered(rs.getDate("DATE_DELIVERED"));
                    String status = helperIte.checkStatus(inventory.getItem_quantity(), inventory.getCritical_quantity());
                    inventory.setStatus(status);
                    inventoryList.add(inventory);
                }
            } else {
                inventoryList = (ArrayList<inventoryModel>) session.getAttribute("inventoryList");
            }
            session.setAttribute("barChart", inventoryList);
            
            float inVal = helperIte.valueCounter(inventoryList);
            String name = helperEmp.getName((Integer) session.getAttribute("user_code"));

            PdfPTable table = new PdfPTable(9);

            Font inventory = new Font(Font.FontFamily.HELVETICA, 33,
                    Font.BOLD);
            Font inventory2 = new Font(Font.FontFamily.HELVETICA, 16,
                    Font.BOLD);
            Font subtitle = new Font(Font.FontFamily.HELVETICA, 11,
                    Font.BOLD, BaseColor.BLACK);
            Font head = new Font(Font.FontFamily.HELVETICA, 12,
                    Font.BOLD, BaseColor.BLACK);
            Font dataFont = new Font(Font.FontFamily.HELVETICA, 12);

            PdfPCell cell00 = new PdfPCell(new Paragraph("SUIL CAFÉ HAN", inventory));
            cell00.setBorder(Rectangle.NO_BORDER);
            cell00.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell00.setColspan(9);
            PdfPCell cell01 = new PdfPCell(new Paragraph("INVENTORY REPORT", inventory2));
            cell01.setBorder(Rectangle.NO_BORDER);
            cell01.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell01.setColspan(9);
            PdfPCell cell02 = new PdfPCell(new Paragraph(" ", subtitle));
            cell02.setBorder(Rectangle.NO_BORDER);
            cell02.setColspan(9);
            PdfPCell cell03 = new PdfPCell(new Paragraph("No. of Items: ", subtitle));
            cell03.setBorder(Rectangle.NO_BORDER);
            cell03.setColspan(1);
            PdfPCell cell04 = new PdfPCell(new Paragraph("" + inventoryList.size(), subtitle));
            cell04.setBorder(Rectangle.NO_BORDER);
            cell04.setColspan(8);
            PdfPCell cell05 = new PdfPCell(new Paragraph(" ", subtitle));
            cell05.setBorder(Rectangle.NO_BORDER);
            cell05.setColspan(9);
            PdfPCell cell06 = new PdfPCell(new Paragraph("Total Value: ", subtitle));
            cell06.setBorder(Rectangle.NO_BORDER);
            cell06.setColspan(1);
            PdfPCell cell07 = new PdfPCell(new Paragraph("" + inVal, subtitle));
            cell07.setBorder(Rectangle.NO_BORDER);
            cell07.setColspan(8);
            PdfPCell cell08 = new PdfPCell(new Paragraph(" ", subtitle));
            cell08.setBorder(Rectangle.NO_BORDER);
            cell08.setColspan(9);

            table.addCell(cell00);
            table.addCell(cell01);
            table.addCell(cell02);
            table.addCell(cell03);
            table.addCell(cell04);
            table.addCell(cell05);
            table.addCell(cell06);
            table.addCell(cell07);
            table.addCell(cell08);
            BaseColor myColor, data;
            myColor = WebColors.getRGBColor("ffdead");
            data = WebColors.getRGBColor("ffffff");

            PdfPCell cell1 = new PdfPCell(new Paragraph("#", head));
            cell1.setBackgroundColor(myColor);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.setMinimumHeight(40);
            PdfPCell cell001 = new PdfPCell(new Paragraph("Inventory ID", head));
            cell001.setBackgroundColor(myColor);
            cell001.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell001.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell001.setMinimumHeight(40);
            PdfPCell cell2 = new PdfPCell(new Paragraph("Item Name", head));
            cell2.setBackgroundColor(myColor);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setMinimumHeight(40);
            PdfPCell cell3 = new PdfPCell(new Paragraph("Item Price", head));
            cell3.setBackgroundColor(myColor);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell3.setMinimumHeight(40);
            PdfPCell cell4 = new PdfPCell(new Paragraph("Item Unit", head));
            cell4.setBackgroundColor(myColor);
            cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell4.setMinimumHeight(40);
            PdfPCell cell5 = new PdfPCell(new Paragraph("Item Quantity", head));
            cell5.setBackgroundColor(myColor);
            cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell5.setMinimumHeight(40);
            PdfPCell cell6 = new PdfPCell(new Paragraph("Supplier Name", head));
            cell6.setBackgroundColor(myColor);
            cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell6.setMinimumHeight(40);
            PdfPCell cell7 = new PdfPCell(new Paragraph("Supplier Contact", head));
            cell7.setBackgroundColor(myColor);
            cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell7.setMinimumHeight(40);
            PdfPCell cell8 = new PdfPCell(new Paragraph("Date Last Delivered", head));
            cell8.setBackgroundColor(myColor);
            cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell8.setMinimumHeight(40);

            table.addCell(cell1);
            table.addCell(cell001);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);

            int records = 1;

            for (int i = 0; i < inventoryList.size(); i++) {
                PdfPCell data1 = new PdfPCell(new Paragraph(Integer.toString(records), dataFont));
                data1.setBackgroundColor(data);
                data1.setHorizontalAlignment(Element.ALIGN_CENTER);
                data1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data1.setMinimumHeight(40);
                PdfPCell data001 = new PdfPCell(new Paragraph(Integer.toString((int) inventoryList.get(i).getPo_number()), dataFont));
                data001.setBackgroundColor(data);
                data001.setHorizontalAlignment(Element.ALIGN_CENTER);
                data001.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data001.setMinimumHeight(40);
                PdfPCell data2 = new PdfPCell(new Paragraph(inventoryList.get(i).getItem_description(), dataFont));
                data2.setBackgroundColor(data);
                data2.setHorizontalAlignment(Element.ALIGN_CENTER);
                data2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data2.setMinimumHeight(40);
                PdfPCell data3 = new PdfPCell(new Paragraph(Integer.toString((int) inventoryList.get(i).getItem_price()), dataFont));
                data3.setBackgroundColor(data);
                data3.setHorizontalAlignment(Element.ALIGN_CENTER);
                data3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data3.setMinimumHeight(40);
                PdfPCell data4 = new PdfPCell(new Paragraph(inventoryList.get(i).getItem_unit(), dataFont));
                data4.setBackgroundColor(data);
                data4.setHorizontalAlignment(Element.ALIGN_CENTER);
                data4.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data4.setMinimumHeight(40);
                PdfPCell data5 = new PdfPCell(new Paragraph(Integer.toString(inventoryList.get(i).getItem_quantity()), dataFont));
                data5.setBackgroundColor(data);
                data5.setHorizontalAlignment(Element.ALIGN_CENTER);
                data5.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data5.setMinimumHeight(40);
                PdfPCell data6 = new PdfPCell(new Paragraph(inventoryList.get(i).getSupplier_name(), dataFont));
                data6.setBackgroundColor(data);
                data6.setHorizontalAlignment(Element.ALIGN_CENTER);
                data6.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data6.setMinimumHeight(40);
                PdfPCell data7 = new PdfPCell(new Paragraph(inventoryList.get(i).getSupplier_contact(), dataFont));
                data7.setBackgroundColor(data);
                data7.setHorizontalAlignment(Element.ALIGN_CENTER);
                data7.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data7.setMinimumHeight(40);
                PdfPCell data8 = new PdfPCell(new Paragraph(inventoryList.get(i).getDate_delivered().toString(), dataFont));
                data8.setBackgroundColor(data);
                data8.setHorizontalAlignment(Element.ALIGN_CENTER);
                data8.setVerticalAlignment(Element.ALIGN_MIDDLE);
                data8.setMinimumHeight(40);

                records++;

                table.addCell(data1);
                table.addCell(data001);
                table.addCell(data2);
                table.addCell(data3);
                table.addCell(data4);
                table.addCell(data5);
                table.addCell(data6);
                table.addCell(data7);
                table.addCell(data8);
            }

            PdfPCell cell0 = new PdfPCell(new Paragraph("Accessed on " + date));
            cell0.setBorder(Rectangle.NO_BORDER);
            cell0.setColspan(9);
            PdfPCell cell11 = new PdfPCell(new Paragraph("Generated by " + name));
            cell11.setBorder(Rectangle.NO_BORDER);
            cell11.setColspan(9);

            table.addCell(cell0);
            table.addCell(cell11);

            docs.add(table);
            docs.close();

            request.setAttribute("reportModal", true);

            if (request.getParameter("dateReport") != null) {
                RequestDispatcher rd = request.getRequestDispatcher("Reports.jsp");
                rd.forward(request, response);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("Inventory.jsp");
                rd.forward(request, response);
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | DocumentException ex) {
            Logger.getLogger(generateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | DocumentException ex) {
            Logger.getLogger(generateReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
