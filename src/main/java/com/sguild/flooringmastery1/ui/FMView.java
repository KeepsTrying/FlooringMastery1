/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.ui;

import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.dto.Product;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class FMView {

    private UserIO io;

    public FMView(UserIO io) {
        this.io = io;
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public int printMainMenuAndGetSelection() {
        io.print("");
        displayBanner("Main Menu");

        io.print("1)  Display Applicable Dates");
        io.print("2)  Display Orders From Date");
        io.print("3)  Add an Order");
        io.print("4)  Edit an Order");
        io.print("5)  Remove an Order");
        io.print("6)  Save Current Work");
        io.print("7)  Exit");
        io.print("");

        return io.readInt("Please enter selection, 1-7", 1, 7);
    }

    public void displayBanner(String bannerTitle) {
        io.print("=== " + bannerTitle + " ===");
        io.print("----------------------------------------------------");
    }

    public void listOrderDates(List<String> orderDates) {
        io.print("");
        for (String orderDate : orderDates) {
            String date = orderDate.substring(0, 2) + "-" + orderDate.substring(2, 4) + "-" + orderDate.substring(4);
            io.print(date);
        }
        io.print("");
    }

    public String displayDate(LocalDate orderDate) {
        DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MMddyyyy");
        String date = orderDate.format(MMddyyyy);
        String displayDate = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4);
        return displayDate;
    }

    public LocalDate getDate(String prompt) {
        LocalDate requestedDate = null;
        boolean incorrect = false;

        do {
            io.print(prompt);
            int month = io.readInt("Please enter the month 1-12", 1, 12);
            int day = io.readInt("Please enter the day of the month 1-(28-31)", 1, 31);
            int year = io.readInt("Please enter the four digit year, 1970-2020", 1970, 2020);
            try {
                requestedDate = LocalDate.of(year, month, day);
                incorrect = false;
            } catch (DateTimeException e) {
                System.out.println("Please input a valid date.");
                incorrect = true;
            }
        } while (incorrect);
        return requestedDate;
    }

    public void displayOrdersFromDate(List<Order> listOfOrders) {
        if (listOfOrders.isEmpty()){
            displayBanner("There are no orders stored for this date.");
        } else {
        
        for (Order order : listOfOrders) {
            displayOrder(order);
        }
        }
        io.print("");
    }

    public Order addOrder() {
        displayBanner("Create New Order");
        LocalDate orderDate = getDate("Please Date the Order Invoice.");
        Order newOrder = new Order();
        newOrder.setDate(orderDate);
        newOrder.setCustomer(io.readString("What is the Customer's Name?"));
        newOrder.setArea(io.readBigD("How many square feet are they looking to cover?"));

        return newOrder;
    }

    public String displayAndRequestProduct(List<Product> productList) {
        io.print("");
        io.print("Material    |   Cost / sqFt    |   Labor / sqFt");
        for (Product singleProduct : productList) {
            io.print(singleProduct.getName() + "          " + singleProduct.getMaterialCostSqFt() + "         " + singleProduct.getLaborCostSqFt());
        }
        io.print("");

        return io.readString("What Material are they looking for?");
    }
    
    public void displayProducts(List<Product> productList) {
        io.print("");
        io.print("Material    |   Cost / sqFt    |   Labor / sqFt");
        for (Product singleProduct : productList) {
            io.print(singleProduct.getName() + "          " + singleProduct.getMaterialCostSqFt() + "         " + singleProduct.getLaborCostSqFt());
        }
        io.print("");
    }

    public String requestState() {
        return io.readString("What state is it being delivered to?  Input 2 letter state abbreviation.");
    }

    public int requestOrderNumber(String prompt) {
        int orderNum = io.readInt(prompt);
        return orderNum;
    }

    public void displayOrder(Order order) {
        io.print("--------------------------------------------------------------------------------");
        
        if (order.getOrderNum() > 0) {
            io.print("Order # " + order.getOrderNum() + "   Customer Name: " + order.getCustomer()
                    + "          State / Tax Rate: " + order.getState().getStateCode() + " / " + order.getState().getStateTax() + "%");
        } else {
            io.print("Order # " + "TBD" + "   Customer Name: " + order.getCustomer()
                    + "          State / Tax Rate: " + order.getState().getStateCode() + " / " + order.getState().getStateTax() + "%");
        }

        io.print(order.getArea() + " sqFt of " + order.getMaterial().getName() + " at $"
                + order.getLaborCostPerSqFt().add(order.getCostPerSqFt()) + " per square feet installed plus " + order.getTtlTax() + " in taxes.");
        io.print("Invoice Total:   $" + order.getTtlCost() + "                      Date: " + order.getDate());
        io.print("--------------------------------------------------------------------------------");
    }

    public String requestName() {
        return io.readString("What is the new customer name?");
    }

    public boolean requestConfirmation(String prompt) {
        io.print(prompt);
        boolean reply = io.readIntBoolean("1) Yes   2) No");
        return reply;
    }

    public void displayCurrentInfo(String prompt) {
        io.print("");
        io.print(prompt);
    }
    
    public String requestFieldEdit(String prompt) {
        return io.readString(prompt);
    }

    public BigDecimal validateBigD(String userData) {
        BigDecimal newData;
        boolean isValid = false;

        do {
            newData = io.readBigDecimal(userData);
            if (newData.compareTo(ZERO) < 0) {
                io.print("Please insert a positive number.");
                io.print(" ");
                isValid = false;
            } else {
                isValid = true;
            }
        } while (!isValid);

        return newData;
    }

    public boolean validateEmptyLine(String userInput) {
        boolean emptyLine = false;

        if (userInput.trim().isEmpty()) {
            emptyLine = true;
        } else {
            emptyLine = false;
        }

        return emptyLine;
    }

}
