package com.model;

public class itemModel {
    
    private int item_code;
    private String item_description;
    private float item_price;
    private String item_units;
    private int critical_quantity;

    public int getCritical_quantity() {
        return critical_quantity;
    }

    public void setCritical_quantity(int critical_quantity) {
        this.critical_quantity = critical_quantity;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
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

    public String getItem_units() {
        return item_units;
    }

    public void setItem_units(String item_units) {
        this.item_units = item_units;
    }
    
}
