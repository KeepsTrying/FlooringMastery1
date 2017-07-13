/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.servicelayer;

import com.sguild.flooringmastery1.dao.FMDao;
import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.dto.Product;
import com.sguild.flooringmastery1.dao.FMDataTransferException;
import com.sguild.flooringmastery1.dto.State;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public class FMServiceImpl implements FMService {

    private FMDao dao;

    public FMServiceImpl(FMDao dao) {
        this.dao = dao;
    }

    @Override
    public Order completeOrder(Order newOrder) {
        BigDecimal materialsCost, laborCost, ttlTax, ttlCost,
                costSqFt, laborSqFt, taxRate, area, subtotal;

        area = newOrder.getArea();

        costSqFt = newOrder.getMaterial().getMaterialCostSqFt();
        newOrder.setCostPerSqFt(costSqFt);

        laborSqFt = newOrder.getMaterial().getLaborCostSqFt();
        newOrder.setLaborCostPerSqFt(laborSqFt);

        taxRate = newOrder.getState().getStateTax();
        newOrder.setTaxRate(taxRate);

        materialsCost = costSqFt.multiply(area);
        newOrder.setMatCost(materialsCost);

        laborCost = laborSqFt.multiply(area);
        newOrder.setLaborCost(laborCost);

        subtotal = materialsCost.add(laborCost);

        if (taxRate.compareTo(ZERO) <= 0) {
            ttlCost = subtotal;
            ttlTax = new BigDecimal("0");
        } else {
            BigDecimal taxMultiplier = taxRate.scaleByPowerOfTen(-2);
            ttlTax = (subtotal.multiply(taxMultiplier)).setScale(2, HALF_UP);
            newOrder.setTtlTax(ttlTax);
            ttlCost = (subtotal.add(ttlTax)).setScale(2, HALF_UP);
        }
        newOrder.setTtlCost(ttlCost);

        return newOrder;
    }

    @Override
    public void save() throws FMDataTransferException {
        dao.save();
    }

    @Override
    public void load() throws FMDataTransferException {
        dao.load();
    }

    @Override
    public List<String> getAllOrderDates() {
        return dao.getAllOrderDates();
    }

    @Override
    public Order addOrder(Order newOrder) {
        return dao.addOrder(newOrder);
    }

    @Override
    public Order removeOrder(Order orderToRemove) throws FMDataTransferException {
        return dao.removeOrder(orderToRemove);
    }

    @Override
    public List<Order> getAllOrdersFromDate(LocalDate requestedDate) throws FMDataTransferException {
        List<Order> ordersFromDate = new ArrayList();
        //try {
        ordersFromDate = dao.getAllOrdersFromDate(requestedDate);
        //} catch (FMDataTransferException ex) {
        //System.out.println("Error loading Orders.  Please check that the date is applicable.");
        // }
        return ordersFromDate;
    }

    @Override
    public Order getOrderFromDate(LocalDate orderDate, int orderNum) throws FMDataTransferException {
        return dao.getOrderFromDate(orderDate, orderNum);
    }

    @Override
    public void loadProducts() throws FMDataTransferException {
        dao.loadProducts();
    }

    @Override
    public List<Product> getAllProducts() throws FMDataTransferException {
        return dao.getAllProducts();
    }

    @Override
    public Product getProduct(String productName) throws FMDataTransferException {
        return dao.getProduct(productName);
    }

    @Override
    public void loadStateTaxes() {
        try {
            dao.loadStateTaxes();
        } catch (FMDataTransferException ex) {
            System.out.println("Unable to load state taxes.  Please confirm the file Taxes.txt exists.");
        }
    }

    @Override
    public State getState(String stateCode) throws FMDataTransferException {
        return dao.getState(stateCode);
    }

    @Override
    public String loadMode() throws FMDataTransferException {
        try {
            return dao.loadMode();
        } catch (FMDataTransferException ex) {
            throw new FMDataTransferException(ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean validateOrderNumber(LocalDate orderDate, int orderNum) {
        return dao.validateOrderNumber(orderDate, orderNum);
    }

}
