/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.servicelayer;

import com.sguild.flooringmastery1.dao.FMDataTransferException;
import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.dto.Product;
import com.sguild.flooringmastery1.dto.State;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author apprentice
 */
public interface FMService {
    
    public void save() throws FMDataTransferException;
    
    public void load() throws FMDataTransferException;
    
    public List<String> getAllOrderDates();
    
    public List<Order> getAllOrdersFromDate(LocalDate requestedDate) throws FMDataTransferException;
    
    public Order addOrder(Order newOrder);
    
    public boolean validateOrderNumber(LocalDate orderDate, int orderNum);
    
    public Order removeOrder(Order orderToRemove) throws FMDataTransferException;
    
    public Order getOrderFromDate(LocalDate orderDate, int orderNum) throws FMDataTransferException;
    
    public void loadProducts() throws FMDataTransferException;
            
    public List<Product> getAllProducts() throws FMDataTransferException;
    
    public Product getProduct(String productName) throws FMDataTransferException;
    
    public void loadStateTaxes() throws FMDataTransferException;
    
    public State getState(String stateCode) throws FMDataTransferException;
    
    public Order completeOrder(Order newOrder);
    
    public String loadMode() throws FMDataTransferException;
    
}
