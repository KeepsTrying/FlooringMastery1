/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.controller;

import com.sguild.flooringmastery1.dao.FMDataTransferException;
import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.servicelayer.FMService;
import com.sguild.flooringmastery1.ui.FMView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author apprentice
 */
public class FMController {

    private FMView view;
    private FMService alfred;

    public FMController(FMService service, FMView view) {
        this.alfred = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int mainMenuSelection = 0;
        String mode = null;
        try {
            alfred.loadStateTaxes();
            alfred.loadProducts();
            alfred.load();
            mode = alfred.loadMode();
        } catch (FMDataTransferException e) {
            view.displayErrorMessage(e.getLocalizedMessage());
        }

        while (keepGoing) {

            if (mode.equalsIgnoreCase("production")) {
                view.displayBanner("Production Mode");
            } else if (mode.equalsIgnoreCase("training")) {
                view.displayBanner("Training Mode");
            }

            mainMenuSelection = printMainMenuAndGetSelection();

            switch (mainMenuSelection) {

                case 1:
                    listDates();
                    keepGoing = true;
                    break;
                case 2:
                    listOrdersFromDate();
                    keepGoing = true;
                    break;
                case 3:
                    addOrder();
                    keepGoing = true;
                    break;
                case 4:
                    editOrder();
                    keepGoing = true;
                    break;
                case 5:
                    removeOrder();
                    keepGoing = true;
                    break;
                case 6:
                    if (mode.equalsIgnoreCase("production")) {
                        save();
                    } else if (mode.equalsIgnoreCase("training")) {
                        view.displayBanner("Thank you for remembering to save!");
                    }
                    keepGoing = true;
                    break;
                case 7:
                    keepGoing = false;
                    view.displayBanner("Please remember to thank the customer for their business!");
                    break;
                default:
                    unknownCommand();
            }
        }
    }

    private int printMainMenuAndGetSelection() {
        return view.printMainMenuAndGetSelection();
    }

    private void listDates() {
        view.listOrderDates(alfred.getAllOrderDates());
    }

    private void listOrdersFromDate() {
        List<Order> orders = new ArrayList();
        try {
            orders = alfred.getAllOrdersFromDate(view.getDate("What Date's orders would you like to view?"));
        } catch (FMDataTransferException ex) {
            view.displayErrorMessage(ex.getLocalizedMessage());
        }
        view.displayOrdersFromDate(orders);
    }

    private void unknownCommand() {
        view.displayBanner("Unknown Command");
    }

    private void addOrder() {
        Order newOrder = new Order();
        
        do {
        newOrder = view.addOrder();
        } while (!alfred.validateOrderNumber(newOrder.getDate(), newOrder.getOrderNum()));
        String materialRequest;
        boolean valid = false;

        while (!valid) {
            try {
                newOrder.setState(alfred.getState(view.requestState()));
                valid = true;
            } catch (FMDataTransferException | NullPointerException e) {
                valid = false;
                view.displayErrorMessage("Please enter a 2 letter state code.");
            }
        }

        valid = false;

        while (!valid) {
            try {
                materialRequest = view.displayAndRequestProduct(alfred.getAllProducts());
                newOrder.setMaterial(alfred.getProduct(materialRequest));
                valid = true;
            } catch (FMDataTransferException ex) {
                valid = false;
                view.displayErrorMessage(ex.getLocalizedMessage());
            }

        }

        newOrder = alfred.completeOrder(newOrder);

        view.displayOrder(newOrder);
        boolean willAdd = view.requestConfirmation("Would you like to add this Order?");

        if (willAdd) {
            alfred.addOrder(newOrder);
            view.displayBanner("Order for " + newOrder.getCustomer() + " Added");
        } else {
            view.displayBanner("Order discarded.");
        }
    }

    private void removeOrder() {
        view.displayBanner("Removing an Order");
        LocalDate removeFromDate = view.getDate("What date was the order placed?");

        try {
            view.displayOrdersFromDate(alfred.getAllOrdersFromDate(removeFromDate));
        } catch (FMDataTransferException e) {
            view.displayErrorMessage("Invoice date does not exist.");
        }

        int orderNum = 0;

        orderNum = view.requestOrderNumber("Which order would you like to remove?");

        Order removeThisOrder = new Order();
        try {
            removeThisOrder = alfred.getOrderFromDate(removeFromDate, orderNum);
        } catch (FMDataTransferException e) {
            view.displayErrorMessage("Order " + orderNum + " does not exist.");
        }

        view.displayOrder(removeThisOrder);
        boolean willRemove = view.requestConfirmation("Would you like to delete this order?");
        if (willRemove) {
            try {
                alfred.removeOrder(removeThisOrder);
                view.displayBanner("Order Removed");
            } catch (FMDataTransferException e) {
                view.displayErrorMessage(e.getLocalizedMessage());
            }
        } else {
            view.displayBanner("Order Retained");
        }
    }

    private void editOrder() {
        view.displayBanner("Order Editing");
        
        boolean validInput = false;
        String userInput;
        int orderNum = -1;
        LocalDate editDate = null;

        while (!validInput) {
            editDate = view.getDate("What date was the order you want to edit invoiced?");
            try {
                view.displayOrdersFromDate(alfred.getAllOrdersFromDate(editDate));
                validInput = true;
            } catch (FMDataTransferException ex) {
                validInput = false;
                view.displayErrorMessage("There are no orders from that date.");
            }
        }
        validInput = false;

         do {
            orderNum = view.requestOrderNumber("Which order would you like to edit?");
            validInput = alfred.validateOrderNumber(editDate, orderNum);
        } while (validInput);

        Order selectedOrder = new Order();
        try {
            selectedOrder = alfred.getOrderFromDate(editDate, orderNum);
            alfred.removeOrder(selectedOrder);
        } catch (FMDataTransferException e) {
            view.displayErrorMessage("Order " + orderNum + " does not exist.");
        }

        Order editedOrder = new Order();

        view.displayCurrentInfo("Current Date: " + view.displayDate(selectedOrder.getDate()));
        editedOrder.setDate(view.getDate("When would you like to date this invoice?"));

        while (!validInput) {
            view.displayCurrentInfo("Current #: " + selectedOrder.getOrderNum());
            orderNum = view.requestOrderNumber("What would you like the new order number to be?");
            validInput = alfred.validateOrderNumber(editDate, orderNum);
        }
        editedOrder.setOrderNum(orderNum);
        alfred.addOrder(editedOrder);
        validInput = false;
        
        view.displayCurrentInfo("Current customer name: " + selectedOrder.getCustomer());
        userInput = view.requestName();
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setCustomer(selectedOrder.getCustomer());
        } else {
            editedOrder.setCustomer(userInput);
        }

        while (!validInput) {
            userInput = view.requestState();
            if (view.validateEmptyLine(userInput)) {
                editedOrder.setState(selectedOrder.getState());
                validInput = true;
            } else {
                try {
                    editedOrder.setState(alfred.getState(userInput));
                    validInput = true;
                } catch (FMDataTransferException ex) {
                    view.displayErrorMessage(ex.getLocalizedMessage());
                    validInput = false;
                }
            }
        }

        validInput = false;
        String material = null;

        //view.displayCurrentInfo("Current tax rate: " + selectedOrder.getTaxRate() + "%");
        //editedOrder.setTaxRate(view.validateBigD("What is the new tax rate?"));
        while (!validInput) {
            view.displayCurrentInfo("Current Material: " + selectedOrder.getMaterial().getName());
            try {
                view.displayProducts(alfred.getAllProducts());
            } catch (FMDataTransferException ex) {
                view.displayErrorMessage(ex.getLocalizedMessage());
            }

            userInput = view.requestFieldEdit("What Material are they looking for?");

            if (view.validateEmptyLine(userInput)) {
                validInput = true;
                editedOrder.setMaterial(selectedOrder.getMaterial());
            } else {

                try {
                    editedOrder.setMaterial(alfred.getProduct(userInput));
                    validInput = true;
                } catch (FMDataTransferException ex) {
                    validInput = false;
                    view.displayErrorMessage(ex.getLocalizedMessage());
                }
            }
        }

        //since states have set tax values, only prompt for state
        //view.displayCurrentInfo("Current tax rate: " + selectedOrder.getTaxRate() + "%");
        //editedOrder.setTaxRate(view.validateBigD("What is the new tax rate?"));
        view.displayCurrentInfo("Current Area: " + selectedOrder.getArea() + "sqFt");
        userInput = view.requestFieldEdit("What is the new area?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setArea(selectedOrder.getArea());
        } else {
            editedOrder.setArea(view.validateBigD(userInput));
        }

        view.displayCurrentInfo("Current material cost / sqFt:  $" + selectedOrder.getCostPerSqFt());
        userInput = view.requestFieldEdit("What is the new material cost / sqFt?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setCostPerSqFt(selectedOrder.getCostPerSqFt());
        } else {
            editedOrder.setCostPerSqFt(view.validateBigD(userInput));
        }

        view.displayCurrentInfo("Current labor cost / sqFt: $" + selectedOrder.getLaborCostPerSqFt());
        userInput = view.requestFieldEdit("What is the new labor cost / sqFt?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setLaborCostPerSqFt(selectedOrder.getLaborCostPerSqFt());
        } else {
            editedOrder.setLaborCostPerSqFt(view.validateBigD(userInput));
        }

        view.displayCurrentInfo("Total material cost: $" + selectedOrder.getMatCost());
        userInput = view.requestFieldEdit("What is the new total material cost?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setMatCost(selectedOrder.getMatCost());
        } else {
            editedOrder.setMatCost(view.validateBigD(userInput));
        }

        view.displayCurrentInfo("Total labor cost: $" + selectedOrder.getLaborCost());
        userInput = view.requestFieldEdit("What is the new total labor cost?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setLaborCost(selectedOrder.getLaborCost());
        } else {
            editedOrder.setLaborCost(view.validateBigD(userInput));
        }

        view.displayCurrentInfo("Total Taxes: $" + selectedOrder.getTtlTax());
        userInput = view.requestFieldEdit("What is the new tax total?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setTtlTax(selectedOrder.getTtlTax());
        } else {
            editedOrder.setTtlTax(view.validateBigD(userInput));
        }

        view.displayCurrentInfo("Invoice Total: $" + selectedOrder.getTtlCost());
        userInput = view.requestFieldEdit("What is the new invoice total?");
        if (view.validateEmptyLine(userInput)) {
            editedOrder.setTtlCost(selectedOrder.getTtlCost());
        } else {
            editedOrder.setTtlCost(view.validateBigD(userInput));
        }

        view.displayBanner("Updated Order");
        view.displayOrder(editedOrder);
        if (!view.requestConfirmation("Would you like to keep this updated order?")) {
            try {
                alfred.removeOrder(editedOrder);
                alfred.addOrder(selectedOrder);
            } catch (FMDataTransferException ex) {
                view.displayErrorMessage(ex.getLocalizedMessage());
            }
        }
    }

    private void save() {
        try {
            alfred.save();
            view.displayBanner("Current Work Saved");
        } catch (FMDataTransferException e) {
            view.displayErrorMessage("Could not persist data to storage. Please check storage is available.");
        }
    }
}
