
package com.model;

import java.sql.Date;

public class inventoryModel {
    
    private int po_number;
    private String supplier_name;
    private String supplier_contact;
    private String supplier_address;
    private String item_description;
    private float item_price;
    private String item_unit;
    private int item_quantity;
    private int critical_quantity;
    private Date date_delivered;
    String status;

    
    public int getPo_number() {
        return po_number;
    }

    public void setPo_number(int po_number) {
        this.po_number = po_number;
    }
    public String getSupplier_address() {
        return supplier_address;
    }

    public void setSupplier_address(String supplier_address) {
        this.supplier_address = supplier_address;
    }
    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_contact() {
        return supplier_contact;
    }

    public void setSupplier_contact(String supplier_contact) {
        this.supplier_contact = supplier_contact;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public float getItem_price() {
        return item_price;
    }

    public void setItem_price(float item_price) {
        this.item_price = item_price;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public int getCritical_quantity() {
        return critical_quantity;
    }

    public void setCritical_quantity(int critical_quantity) {
        this.critical_quantity = critical_quantity;
    }
    
    public Date getDate_delivered() {
        return date_delivered;
    }

    public void setDate_delivered(Date date_delivered) {
        this.date_delivered = date_delivered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
       
}
