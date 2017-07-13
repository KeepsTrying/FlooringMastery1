/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1;

import com.sguild.flooringmastery1.controller.FMController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 *
 * @author apprentice
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        ApplicationContext thisContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        FMController controller = thisContext.getBean("controller", FMController.class);
        
        /*
        UserIO myIo = new UserIOConsoleImpl();
        FMView myView = new FMView(myIo);
        FMDao myDao = new FMDaoImpl();
        FMService myAlfred = new FMServiceImpl(myDao);
*/

        controller.run();
    }
    
}
