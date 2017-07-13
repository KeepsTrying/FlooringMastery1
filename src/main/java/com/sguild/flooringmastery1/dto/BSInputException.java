/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sguild.flooringmastery1.dto;

/**
 *
 * @author apprentice
 */
class BSInputException extends Exception {
    
    public BSInputException(String message) {
        super(message);
    }

    public BSInputException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
