/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.dao;

import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.dto.Product;
import com.sguild.flooringmastery1.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public class FMDaoImplStub implements FMDao {

    //private Map<LocalDate, Map<Integer, Order>> atlas = new HashMap<>();
    private List<State> testStates = new ArrayList<>();

    private List<Product> testProducts = new ArrayList<>();

    public Order testOrder;

    public FMDaoImplStub() {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        BigDecimal testValue = new BigDecimal("10.00").setScale(2);
        Product tile = new Product();

        tile.setLaborCostSqFt(testValue);
        tile.setMaterialCostSqFt(testValue);
        testProducts.add(tile);

        State kentucky = new State();
        kentucky.setStateCode("ky");
        kentucky.setStateTax(testValue);
        testStates.add(kentucky);

        testOrder = new Order();
        testOrder.setDate(testDate);
        testOrder.setOrderNum(1);
        testOrder.setCustomer("Test Customer");
        testOrder.setArea(testValue);
        testOrder.setMaterial(tile);
        testOrder.setState(kentucky);
        Map<Integer, Order> testMap = new HashMap<>();
        testMap.put(testOrder.getOrderNum(), testOrder);

        //atlas.put(testDate, testMap);
    }

    @Override
    public void save() throws FMDataTransferException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load() throws FMDataTransferException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order addOrder(Order newOrder) {
        return newOrder;
    }

    @Override
    public boolean validateOrderNumber(LocalDate orderDate, int orderNum) {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        if (orderDate == testDate && orderNum == 1) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Order removeOrder(Order orderToRemove) throws FMDataTransferException {
        if (orderToRemove.equals(testOrder)) {
            return orderToRemove;
        } else {
            return null;
        }

    }

    @Override
    public List<String> getAllOrderDates() {
        List<String> testDates = new ArrayList<>();
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MMddyyyy");
        String theDate = testOrder.getDate().format(MMddyyyy);
        testDates.add(theDate);

        return testDates;
    }

    @Override
    public List<Order> getAllOrdersFromDate(LocalDate requestedDate) throws FMDataTransferException {
        List<Order> ordersFromDate = new ArrayList<>();
        ordersFromDate.add(testOrder);
        return ordersFromDate;
    }

    @Override
    public Order getOrderFromDate(LocalDate orderDate, int orderNum) throws FMDataTransferException {
        LocalDate testDate = testOrder.getDate();
        if (orderDate.equals(testDate) && orderNum == 1) {
            return testOrder;
        } else {
            return null;
        }
    }

    @Override
    public void loadProducts() throws FMDataTransferException {
        //System.out.println("load products");
    }

    @Override
    public List<Product> getAllProducts() throws FMDataTransferException {
        return testProducts;
    }

    @Override
    public Product getProduct(String productName) throws FMDataTransferException {
        if (productName.equalsIgnoreCase("tile")) {
            return testProducts.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<State> loadStateTaxes() throws FMDataTransferException {
        return testStates;
    }

    @Override
    public State getState(String stateCode) throws FMDataTransferException {
        if (stateCode.equalsIgnoreCase("ky")) {
            return testStates.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String loadMode() throws FMDataTransferException {
        return "training";
    }

}
