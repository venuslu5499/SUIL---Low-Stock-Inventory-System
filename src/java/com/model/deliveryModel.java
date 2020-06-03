
package com.model;

import java.sql.Date;

public class deliveryModel {
    
    private int po_number;   
    private int item_code;
    private int supplier_ID;
    private int item_quantity;
    private Date date_delivered;

    public int getPo_number() {
        return po_number;
    }

    public void setPo_number(int po_number) {
        this.po_number = po_number;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public int getSupplier_ID() {
        return supplier_ID;
    }

    public void setSupplier_ID(int supplier_ID) {
        this.supplier_ID = supplier_ID;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }
    
    public Date getDate_delivered() {
        return date_delivered;
    }

    public void setDate_delivered(Date date_delivered) {
        this.date_delivered = date_delivered;
    }
       
}
