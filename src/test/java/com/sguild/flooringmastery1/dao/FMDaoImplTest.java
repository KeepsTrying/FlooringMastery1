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
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class FMDaoImplTest {

    FMDaoImpl testDao;

    public FMDaoImplTest() {
        testDao = new FMDaoImpl();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws FMDataTransferException {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class FMDaoImpl.
     */
    @Test
    public void testSave() throws Exception {
        //was instructed not to test Save
    }

    /**
     * Test of addOrder method, of class FMDaoImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddOrder() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        BigDecimal testData = new BigDecimal("1.00").setScale(2);

        testDao.loadProducts();
        testDao.loadStateTaxes();
        testDao.load();

        Product carpet = new Product();
        List<Product> productList = new ArrayList<>();
        productList = testDao.getAllProducts();
        for (Product testProduct : productList) {
            if (testProduct.getName().equals("Carpet")) {
                carpet = testProduct;
            }
        }

        Order testOrder = new Order();
        testOrder.setDate(testDate);
        testOrder.setCustomer("test");
        testOrder.setState(testDao.getState("KY"));
        testOrder.setTaxRate(testOrder.getState().getStateTax());
        testOrder.setMaterial(carpet);
        testOrder.setArea(testData);
        testOrder.setCostPerSqFt(carpet.getMaterialCostSqFt());
        testOrder.setLaborCostPerSqFt(carpet.getLaborCostSqFt());
        testOrder.setMatCost(testData);
        testOrder.setLaborCost(testData);
        testOrder.setTtlTax(testData);
        testOrder.setTtlCost(testData);

        testDao.addOrder(testOrder);

        Assert.assertEquals(8, testOrder.getOrderNum());
        Assert.assertEquals(8, testDao.getAllOrdersFromDate(testDate).size());
    }

    /**
     * Test of getAllOrderDates method, of class FMDaoImpl.
     *
     * @throws com.sguild.flooringmastery1.dao.FMDataTransferException
     */
    @Test
    public void testGetAllOrderDates() throws FMDataTransferException {
        testDao.loadStateTaxes();
        testDao.loadProducts();
        testDao.load();
        List<String> orderDates = new ArrayList<>();
        orderDates = testDao.getAllOrderDates();
        Assert.assertEquals(11, orderDates.size());
    }

    /**
     * Test of removeOrder method, of class FMDaoImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        testDao.loadProducts();
        testDao.loadStateTaxes();
        testDao.load();

        LocalDate testDate = LocalDate.of(2017, 5, 29);
        Order testOrder = testDao.getOrderFromDate(testDate, 7);

        //size = 8, expected will be 7
        //order num 8
        testDao.removeOrder(testOrder);
        Assert.assertEquals(6, testDao.getAllOrdersFromDate(testDate).size());

    }

    /**
     * Test of load method, of class FMDaoImpl.
     */
    @Test
    public void testLoad() throws Exception {
        testDao.loadProducts();
        testDao.loadStateTaxes();
        testDao.load();

        System.out.println(testDao.getAllOrderDates().size());
    }

    /**
     * Test of getAllOrdersFromDate method, of class FMDaoImpl.
     */
    @Test
    public void testGetAllOrdersFromDate() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 5, 23);
        testDao.loadProducts();
        //testDao.getAllProducts();
        testDao.loadStateTaxes();
        testDao.load();

        Assert.assertEquals(4, testDao.getAllOrdersFromDate(testDate).size());
    }

    /**
     * Test of getOrderFromDate method, of class FMDaoImpl.
     */
    @Test
    public void testGetOrderFromDate() throws Exception {
        testDao.loadProducts();
        testDao.loadStateTaxes();
        testDao.load();
        LocalDate testDate = LocalDate.of(2017, 5, 26);
        
        Order testOrder = testDao.getOrderFromDate(testDate, 1);
        Assert.assertTrue(testOrder.getCustomer().equalsIgnoreCase("Bob"));
    }

    /**
     * Test of loadProducts method, of class FMDaoImpl.
     */
    @Test
    public void testLoadProducts() throws Exception {
        testDao.loadProducts();
        Assert.assertEquals(8, testDao.getAllProducts().size());
    }

    /**
     * Test of getAllProducts method, of class FMDaoImpl.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        testDao.loadProducts();
        Assert.assertEquals(8, testDao.getAllProducts().size());
    }

    /**
     * Test of getProduct method, of class FMDaoImpl.
     */
    @Test
    public void testGetProduct() throws Exception {
        testDao.loadProducts();
        Product testProduct = testDao.getProduct("Wood");
        BigDecimal testValue = new BigDecimal("5.15").setScale(2);
        Assert.assertEquals("Wood", testProduct.getName());
        Assert.assertEquals(testValue, testProduct.getMaterialCostSqFt());
    }

    /**
     * Test of loadStateTaxes method, of class FMDaoImpl.
     */
    @Test
    public void testLoadStateTaxes() throws Exception {
        Assert.assertEquals(58, testDao.loadStateTaxes().size());

    }

    /**
     * Test of getState method, of class FMDaoImpl.
     */
    @Test
    public void testGetState() throws Exception {
        testDao.loadStateTaxes();
        State kentucky = testDao.getState("ky");
        BigDecimal expected = new BigDecimal("6.00").setScale(2);

        Assert.assertEquals(expected, kentucky.getStateTax());
    }

    /**
     * Test of loadMode method, of class FMDaoImpl.
     */
    @Test
    public void testLoadMode() throws Exception {
        Assert.assertTrue(testDao.loadMode().equalsIgnoreCase("training"));
    }

}
