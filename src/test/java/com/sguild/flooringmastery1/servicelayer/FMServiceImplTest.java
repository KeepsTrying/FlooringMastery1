/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.servicelayer;

import com.sguild.flooringmastery1.dao.FMDao;
import com.sguild.flooringmastery1.dao.FMDaoImplStub;
import com.sguild.flooringmastery1.dao.FMDataTransferException;
import com.sguild.flooringmastery1.dto.Order;
import com.sguild.flooringmastery1.dto.Product;
import com.sguild.flooringmastery1.dto.State;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static jdk.nashorn.internal.objects.NativeRegExp.test;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author apprentice
 */
public class FMServiceImplTest {
    
    private FMServiceImpl alfred;
    
    public FMServiceImplTest() {
        FMDao testDao = new FMDaoImplStub();
        alfred = new FMServiceImpl(testDao);  
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of completeOrder method, of class FMServiceImpl.
     * @throws com.sguild.flooringmastery1.dao.FMDataTransferException
     */
    @Test
    public void testCompleteOrder() throws FMDataTransferException {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        alfred.getOrderFromDate(testDate, 1);
    }

    /**
     * Test of save method, of class FMServiceImpl.
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
    }

    /**
     * Test of load method, of class FMServiceImpl.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
    }

    /**
     * Test of getAllOrderDates method, of class FMServiceImpl.
     */
    @Test
    public void testGetAllOrderDates() {
        List<String> orderDates = alfred.getAllOrderDates();
        Assert.assertEquals(1, orderDates.size());
    }

    /**
     * Test of addOrder method, of class FMServiceImpl.
     */
    @Test
    public void testAddOrder() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        List <Order> orderList = alfred.getAllOrdersFromDate(testDate);
        Order testOrder = orderList.get(0);
        Order testActual = alfred.getOrderFromDate(testOrder.getDate(), testOrder.getOrderNum());
        
        Assert.assertEquals(testOrder, testActual);
        
    }

    /**
     * Test of removeOrder method, of class FMServiceImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        Order testOrder = alfred.getOrderFromDate(testDate, 1);
        Assert.assertEquals(testOrder, alfred.removeOrder(testOrder));
    }

    /**
     * Test of getAllOrdersFromDate method, of class FMServiceImpl.
     */
    @Test
    public void testGetAllOrdersFromDate() throws Exception {
        int testValue = 1;
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        int actualValue = alfred.getAllOrdersFromDate(testDate).size();
        
        Assert.assertEquals(testValue, actualValue);
    }

    /**
     * Test of getOrderFromDate method, of class FMServiceImpl.
     */
    @Test
    public void testGetOrderFromDate() throws Exception {
        LocalDate testDate = LocalDate.of(2017, 5, 29);
        List <Order> orderList = alfred.getAllOrdersFromDate(testDate);
        Order testOrder = orderList.get(0);
        BigDecimal testValue = new BigDecimal("10.00").setScale(2);
        
        Assert.assertEquals(testValue, testOrder.getArea());
        
    }

    /**
     * Test of loadProducts method, of class FMServiceImpl.
     */
    @Test
    public void testLoadProducts() throws Exception {
        System.out.println("loadProducts");
    }

    /**
     * Test of getAllProducts method, of class FMServiceImpl.
     */
    @Test
    public void testGetAllProducts() throws Exception {
        int expected = 1;
        int actual = alfred.getAllProducts().size();
        
        Assert.assertEquals(expected, actual);
    }

    /**
     * Test of getProduct method, of class FMServiceImpl.
     */
    @Test
    public void testGetProduct() throws Exception {
        Product thisProduct = alfred.getProduct("tile");
        BigDecimal testValue = new BigDecimal("10.00").setScale(2);
        
        Assert.assertEquals(thisProduct.getLaborCostSqFt(), testValue);
    }

    /**
     * Test of loadStateTaxes method, of class FMServiceImpl.
     */
    @Test
    public void testLoadStateTaxes() {
      
  
    }

    /**
     * Test of getState method, of class FMServiceImpl.
     */
    @Test
    public void testGetState() throws Exception {
        State testState = alfred.getState("ky");
        BigDecimal testValue = new BigDecimal("10.00").setScale(2);
        
        Assert.assertEquals(testState.getStateTax(), testValue);
    }

    /**
     * Test of loadMode method, of class FMServiceImpl.
     */
    @Test
    public void testLoadMode() throws Exception {
        Assert.assertTrue(alfred.loadMode().equalsIgnoreCase("training"));
        
    }
    
}
