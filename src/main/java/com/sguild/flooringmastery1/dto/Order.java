/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author apprentice
 */
public class Order {

    private int orderNum;
    private String customer;
    private State state;
    private BigDecimal taxRate;
    private boolean taxExempt;
    private Product material;
    private BigDecimal area;
    private BigDecimal costPerSqFt;
    private BigDecimal laborCostPerSqFt;
    private BigDecimal matCost;
    private BigDecimal laborCost;
    private BigDecimal discount;
    private BigDecimal ttlTax;
    private BigDecimal ttlCost;
    private LocalDate date;

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }
    
    
    /*
    properties from user input:
    date
    customer
    state
    taxExempt
    material
    area
    */
    
    /*
    properties computed by constructor
    taxRate?
    matCost
    laborCost
    ttlTax
    ttlCost
    */
    
    public Order() {
    }
    
    
    
    
    public BigDecimal getDiscount() {
        return discount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTtlTax() {
        return ttlTax;
    }

    public void setTtlTax(BigDecimal ttlTax) {
        this.ttlTax = ttlTax;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public boolean isTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(boolean taxExempt) {
        this.taxExempt = taxExempt;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Product getMaterial() {
        return material;
    }

    public void setMaterial(Product material) {
        this.material = material;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getMatCost() {
        return matCost;
    }

    public void setMatCost(BigDecimal matCost) {
        this.matCost = matCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTtlCost() {
        return ttlCost;
    }

    public void setTtlCost(BigDecimal ttlCost) {
        this.ttlCost = ttlCost;
    }

}
