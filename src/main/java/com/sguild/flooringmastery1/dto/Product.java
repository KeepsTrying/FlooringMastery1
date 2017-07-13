/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.dto;

import java.math.BigDecimal;

/**
 *
 * @author apprentice
 */
public class Product {
    
    private String name;
    private BigDecimal materialCostSqFt;
    private BigDecimal laborCostSqFt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMaterialCostSqFt() {
        return materialCostSqFt;
    }

    public void setMaterialCostSqFt(BigDecimal materialCostSqFt) {
        this.materialCostSqFt = materialCostSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal laborCostSqFt) {
        this.laborCostSqFt = laborCostSqFt;
    }

}
