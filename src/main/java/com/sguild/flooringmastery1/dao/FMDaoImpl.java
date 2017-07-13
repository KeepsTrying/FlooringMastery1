/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.dao;

import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.dto.Product;
import com.sguild.flooringmastery1.dto.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author apprentice
 */
public class FMDaoImpl implements FMDao {

    private final String PRODUCT_INVENTORY = "Products.txt";

    private final String STATE_TAX_LIST = "Taxes.txt";

    private final String MODE_SELECTION = "Mode.txt";

    private static final String DELIMITER = ",";

    private Map<LocalDate, Map<Integer, Order>> atlas = new HashMap<>();

    private List<State> listOfStates = new ArrayList<>();

    private List<Product> productList = new ArrayList<>();

    @Override
    public void save() throws FMDataTransferException {
        PrintWriter save;
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MMddyyyy");

        Set<LocalDate> titan = atlas.keySet();

        String stringDate;
        String fileName;
        Set<Integer> orderNums;
        for (LocalDate eachDate : titan) {
            stringDate = eachDate.format(MMddyyyy);
            fileName = "Orders_" + stringDate + ".txt";

            try {
                save = new PrintWriter(new FileWriter("orders/" + fileName));
            } catch (IOException e) {
                throw new FMDataTransferException("Could not save Inventory", e);
            }
            Map<Integer, Order> ordersFromDate = atlas.get(eachDate);
            orderNums = ordersFromDate.keySet();
            for (int orderNum : orderNums) {
                Order thisOrder = new Order();
                thisOrder = ordersFromDate.get(orderNum);
                save.println(
                        thisOrder.getOrderNum() + DELIMITER + thisOrder.getCustomer() + DELIMITER
                        + thisOrder.getState().getStateCode() + DELIMITER + thisOrder.getTaxRate() + DELIMITER
                        + thisOrder.getMaterial().getName() + DELIMITER + thisOrder.getArea() + DELIMITER
                        + thisOrder.getCostPerSqFt() + DELIMITER + thisOrder.getLaborCostPerSqFt() + DELIMITER
                        + thisOrder.getMatCost() + DELIMITER + thisOrder.getLaborCost() + DELIMITER
                        + thisOrder.getTtlTax() + DELIMITER + thisOrder.getTtlCost());
                save.flush();
            }
            save.close();
        }
    }

    @Override
    public Order addOrder(Order newOrder) {
        int orderNum = newOrder.getOrderNum();
        LocalDate orderDate = newOrder.getDate();
        
        if (atlas.get(orderDate).put(orderNum, newOrder) == null){
            Map<Integer, Order> ordersFromNewOrderDate = new HashMap<>();
            ordersFromNewOrderDate.put(orderNum, newOrder);
            atlas.put(orderDate, ordersFromNewOrderDate);
        }
        
        
        
        
        
        
        /*
            Map<Integer, Order> ordersFromNewOrderDate = new HashMap<>();
            if (orderNum > 0) {
                newOrder.setOrderNum(orderNum);
            } else {
                newOrder.setOrderNum(1);
            }
            ordersFromNewOrderDate.put(orderNum, newOrder);
            atlas.put(newOrder.getDate(), ordersFromNewOrderDate);
        
        
        
            try {
            List<Order> ordersFromDate = getAllOrdersFromDate(newOrder.getDate());
            //if you can populate a keyset (valid order number) and orderNum hasn't been set yet, make it the next order
            

                //else user has input orderNum, check to see there isn't a matching number
            }
            
        }
        
        
        

        try {
            List<Order> ordersFromDate = getAllOrdersFromDate(newOrder.getDate());
            //if you can populate a keyset and orderNum hasn't been set yet, make it the next order
            if (orderNum == 0) {
                for (Order thisOrder : ordersFromDate) {
                    if (thisOrder.getOrderNum() >= orderNum) {
                        orderNum = thisOrder.getOrderNum() + 1;
                    }
                }
                newOrder.setOrderNum(orderNum);
                ordersFromDate.add(newOrder);
                for (Order thisOrder : ordersFromDate) {
                    updatedOrders.put(thisOrder.getOrderNum(), thisOrder);
                }
                atlas.put(orderDate, updatedOrders);

                //else user has input orderNum, check to see there isn't a matching number
            } else {
                for (Order thisOrder : ordersFromDate) {
                    if (orderNum == thisOrder.getOrderNum()) {
                        // if user tries to put in an order number that already exists, exception is thrown
                        throw new FMDataTransferException("Order Number " + orderNum + " already exists.");
                    } else {
                        //updatedOrders.put(thisOrder.getOrderNum(), thisOrder);
                        for (Order eachOrder : ordersFromDate) {
                            updatedOrders.put(eachOrder.getOrderNum(), eachOrder);
                        }
                    }
                }
                atlas.put(orderDate, updatedOrders);
            }
            //if doesn't exist, create new map and set orderNum
        } catch (FMDataTransferException | NullPointerException e) {
            Map<Integer, Order> ordersFromNewOrderDate = new HashMap<>();
            if (orderNum > 0) {
                newOrder.setOrderNum(orderNum);
            } else {
                newOrder.setOrderNum(1);
            }
            ordersFromNewOrderDate.put(orderNum, newOrder);
            atlas.put(newOrder.getDate(), ordersFromNewOrderDate);
            return newOrder;
        }
        return newOrder;

*/
    
        return newOrder;
    }
    
    @Override
    public boolean validateOrderNumber(LocalDate orderDate, int orderNum) {
        boolean validNumber = false;
        int eachOrderNumber = -1;
        List<Order> ordersFromDate = new ArrayList();
        try {
            ordersFromDate = getAllOrdersFromDate(orderDate);
        } catch (FMDataTransferException ex) {
            Order newOrder = new Order();
            newOrder.setOrderNum(orderNum);
            newOrder.setDate(orderDate);
            Map<Integer, Order> ordersFromNewOrderDate = new HashMap<>();
            ordersFromNewOrderDate.put(orderNum, newOrder);
            atlas.put(orderDate, ordersFromNewOrderDate);
            validNumber = true;
        }

        for (Order eachOrder : ordersFromDate) {
            eachOrderNumber = eachOrder.getOrderNum();
            if (eachOrderNumber == orderNum) {
                validNumber = false;
            } else {
                validNumber = true;
            }
        }
        return validNumber;
    }
    
    

    @Override
    public List<String> getAllOrderDates() {
        List<String> listOfOrderDates = new ArrayList<>();
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MMddyyyy");
        String stringDate = "";
        Set<LocalDate> dates = atlas.keySet();

        for (LocalDate date : dates) {
            stringDate = date.format(MMddyyyy);
            listOfOrderDates.add(stringDate);
        }
        return listOfOrderDates;
    }

    @Override
    public Order removeOrder(Order orderToRemove) throws FMDataTransferException {
        LocalDate orderDate = orderToRemove.getDate();
        int orderNum = orderToRemove.getOrderNum();
        atlas.get(orderDate).remove(orderNum);
        return orderToRemove;
    }

    @Override
    public void load() throws FMDataTransferException {
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MMddyyyy");

        String fullFileName = "";
        String stringOrderDate = "";
        LocalDate parsedDate = null;
        File folder = new File("orders");
        File[] orderFiles = folder.listFiles();

        for (File eachFile : orderFiles) {
            fullFileName = eachFile.getName();
            String txtExtension = ".txt";
            if (fullFileName.endsWith(txtExtension)) {
                stringOrderDate = (fullFileName.substring(7, fullFileName.length() - txtExtension.length()));
                parsedDate = LocalDate.parse(stringOrderDate, MMddyyyy);
            }
            Map<Integer, Order> thisDatesOrders = new HashMap<>();
            Scanner ordersReader = null;

            try {
                ordersReader = new Scanner(new BufferedReader(new FileReader("orders/" + fullFileName)));
                String currentLine;
                String[] currentTokens;
                boolean haveErrors = false;
                int orderNum = 0;

                while (ordersReader.hasNextLine()) {
                    currentLine = ordersReader.nextLine();
                    currentTokens = currentLine.split(DELIMITER);
                    if (currentTokens.length == 12) {

                        Order currentOrder = new Order();

                        try {
                            currentOrder.setOrderNum(Integer.parseInt(currentTokens[0]));
                            orderNum = currentOrder.getOrderNum();
                        } catch (NumberFormatException e) {
                            haveErrors = true;
                        }

                        currentOrder.setCustomer(currentTokens[1]);

                        currentOrder.setState(getState(currentTokens[2]));
                        currentOrder.setTaxRate(validateBigD(currentTokens[3], stringOrderDate, orderNum));
                        currentOrder.setDate(parsedDate);
                        currentOrder.setMaterial(getProduct(currentTokens[4]));
                        currentOrder.setArea(validateBigD(currentTokens[5], stringOrderDate, orderNum));
                        currentOrder.setCostPerSqFt(validateBigD(currentTokens[6], stringOrderDate, orderNum));
                        currentOrder.setLaborCostPerSqFt(validateBigD(currentTokens[7], stringOrderDate, orderNum));
                        currentOrder.setMatCost(validateBigD(currentTokens[8], stringOrderDate, orderNum));
                        currentOrder.setLaborCost(validateBigD(currentTokens[9], stringOrderDate, orderNum));
                        currentOrder.setTtlTax(validateBigD(currentTokens[10], stringOrderDate, orderNum));
                        currentOrder.setTtlCost(validateBigD(currentTokens[11], stringOrderDate, orderNum));

                        thisDatesOrders.put(orderNum, currentOrder);

                    }
                }
                ordersReader.close();
                atlas.put(parsedDate, thisDatesOrders);
            } catch (FileNotFoundException e) {
                throw new FMDataTransferException("Data could not be loaded.");
            }
        }

    }

    @Override
    public List<Order> getAllOrdersFromDate(LocalDate requestedDate) throws FMDataTransferException {
        List<Order> ordersFromDate = new ArrayList<>();
        try {
            ordersFromDate = new ArrayList(atlas.get(requestedDate).values());
        } catch (NullPointerException ex) {
            throw new FMDataTransferException("No orders from requested date.");
        }
        return ordersFromDate;
    }

    private BigDecimal validateBigD(String currentToken, String date, int orderNum) throws FMDataTransferException {
        double bigDAsDouble;
        BigDecimal fullBigD, scaledBigD;

        try {
            bigDAsDouble = Double.parseDouble(currentToken);
            fullBigD = new BigDecimal(currentToken);
            scaledBigD = fullBigD.setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new FMDataTransferException("Improper field found of Date " + date + " in Order# " + orderNum + ", " + currentToken);
        }
        return scaledBigD;
    }

    private BigDecimal validateBigD(String currentToken, String productOrStateName, boolean isProduct) throws FMDataTransferException {
        double bigDAsDouble;
        BigDecimal fullBigD, scaledBigD;

        try {
            bigDAsDouble = Double.parseDouble(currentToken);
            fullBigD = new BigDecimal(currentToken);
            scaledBigD = fullBigD.setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            if (isProduct) {
                throw new FMDataTransferException("Improper field found in Material " + productOrStateName);
            } else {
                throw new FMDataTransferException("Improper field found in State " + productOrStateName);
            }
        }
        return scaledBigD;
    }

    @Override
    public Order getOrderFromDate(LocalDate orderDate, int orderNum) throws FMDataTransferException {
        //Map<Integer, Order> ordersFromDate = new HashMap<>();
        //ordersFromDate = getAllOrdersFromDate(orderDate);
        //atlas.put(orderDate, ordersFromDate);

        Order requestedOrder = new Order();
        try {
            requestedOrder = atlas.get(orderDate).get(orderNum);
        } catch (NullPointerException e) {
            throw new FMDataTransferException("date not found");
        }

        return requestedOrder;
    }

    @Override
    public void loadProducts() throws FMDataTransferException {

        Scanner productReader;

        try {
            productReader = new Scanner(new BufferedReader(new FileReader(PRODUCT_INVENTORY)));
        } catch (FileNotFoundException e) {
            throw new FMDataTransferException("Could not load Product Inventory");
        }

        String currentLine;
        String[] currentTokens;

        while (productReader.hasNextLine()) {
            currentLine = productReader.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            Product currentProduct = new Product();
            boolean isProduct = true;

            currentProduct.setName(currentTokens[0]);
            currentProduct.setMaterialCostSqFt(validateBigD(currentTokens[1], currentTokens[0], isProduct));
            currentProduct.setLaborCostSqFt(validateBigD(currentTokens[2], currentTokens[0], isProduct));

            productList.add(currentProduct);

        }
        productReader.close();
    }

    @Override
    public List<Product> getAllProducts() throws FMDataTransferException {
        return productList;
    }

    @Override
    public Product getProduct(String productName) throws FMDataTransferException {

        Product requestedProduct = new Product();
        boolean hasProduct = false;

        for (Product singleProduct : productList) {
            if (singleProduct.getName().equalsIgnoreCase(productName)) {
                hasProduct = true;
                requestedProduct = singleProduct;
            }
        }
        if (!hasProduct) {
            throw new FMDataTransferException("No such material");
        }
        return requestedProduct;
    }

    @Override
    public List<State> loadStateTaxes() throws FMDataTransferException {

        Scanner stateReader;

        try {
            stateReader = new Scanner(new BufferedReader(new FileReader(STATE_TAX_LIST)));
        } catch (FileNotFoundException e) {
            throw new FMDataTransferException("Could not load State Tax Info.  Please confirm Taxes.txt exists.");
        }

        String currentLine;
        String[] currentTokens;

        while (stateReader.hasNextLine()) {
            currentLine = stateReader.nextLine();
            currentTokens = currentLine.split(DELIMITER);

            State currentState = new State();
            boolean isProduct = false;

            currentState.setStateCode(currentTokens[0]);
            currentState.setStateTax(validateBigD(currentTokens[1], currentTokens[0], isProduct));

            listOfStates.add(currentState);

        }
        stateReader.close();
        return listOfStates;

    }

    @Override
    public State getState(String stateCode) throws FMDataTransferException {
        State requestedState = new State();
        for (State state : listOfStates) {
            if (state.getStateCode().equalsIgnoreCase(stateCode)) {
                requestedState = state;
            }
        }

        if (requestedState.getStateCode() == null) {
            throw new FMDataTransferException("Incorrect State Abbreviation.");
        }
        return requestedState;
    }

    @Override
    public String loadMode() throws FMDataTransferException {
        Scanner productReader;

        try {
            productReader = new Scanner(new BufferedReader(new FileReader(MODE_SELECTION)));
        } catch (FileNotFoundException e) {
            throw new FMDataTransferException("Could not load Mode Selection");
        }

        String currentLine;
        String mode = null;

        while (productReader.hasNextLine()) {
            currentLine = productReader.nextLine();
            mode = currentLine;
        }
        return mode;
    }
}
