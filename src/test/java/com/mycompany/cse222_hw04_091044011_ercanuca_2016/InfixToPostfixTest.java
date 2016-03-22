/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cse222_hw04_091044011_ercanuca_2016;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ercan
 */
public class InfixToPostfixTest {
    
    public InfixToPostfixTest() {
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
     * Test of convert method, of class InfixToPostfix.
     */
    @Test
    public void testConvert() {
        System.out.println("convert");
        String infix = "a + b";
        InfixToPostfix instance = new InfixToPostfix();
        String expResult = "a b + ";
        String result = instance.convert(infix); 
        assertEquals(expResult, result);

    }
    
}
