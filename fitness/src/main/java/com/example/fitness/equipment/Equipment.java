package com.example.fitness.equipment;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 器材类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @ExcelProperty("ID")
    private int id;

    @ExcelProperty("器材名字")
    private String name;

    @ExcelProperty("样式")
    private String type;

    @ExcelProperty("数量")
    private int quantity;

    @ExcelProperty("价格")
    private double price;

    @ExcelProperty("产商")
    private String manufacturer;

    @ExcelProperty(value = "购买日期", format = "yyyy-MM-dd")
    @DateTimeFormat("yyyy-MM-dd")
    private Date purchaseDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}