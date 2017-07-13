/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.ui;

import java.math.BigDecimal;

/**
 *
 * @author apprentice
 */
public interface UserIO {
    
    void print(String message);
    
    public void printInLine(String message);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);
    
    public boolean readIntBoolean(String prompt);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readString(String prompt);
    
    public BigDecimal readBigD(String prompt);
    
    public BigDecimal readBigDecimal(String newData);
    
    public BigDecimal readPositiveBigD (String prompt);
    
}
