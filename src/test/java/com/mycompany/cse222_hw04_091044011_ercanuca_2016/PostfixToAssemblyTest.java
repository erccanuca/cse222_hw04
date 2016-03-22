/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cse222_hw04_091044011_ercanuca_2016;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
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
public class PostfixToAssemblyTest {
    
    public PostfixToAssemblyTest() {
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
     * Test of saveToLinkedList method, of class PostfixToAssembly.
     */
    @Test
    public void testSaveToLinkedList() {
        System.out.println("saveToLinkedList");
        String postfix = "a 4 = "; // infix "a = 4"
        PostfixToAssembly instance = new PostfixToAssembly();
        instance.saveToLinkedList(postfix); 
    }

    /**
     * Test of convertToAssembly method, of class PostfixToAssembly.
     * throws java.lang.Exception
     */
    
    public void testConvertToAssembly() throws Exception{
        System.out.println("convertToAssembly");
        PostfixToAssembly instance = new PostfixToAssembly();
        String expResult = "a 4 = ";
        instance.saveToLinkedList(expResult);
        String result1 = "li $t0,4			#a=4 in $t0.\n";
        String result2= instance.convertToAssembly();
        assertEquals(result1, result2); 
    }
    
    /**
     * Test of convertToAssembly method, of class PostfixToAssembly.
     * throws java.lang.IllegalArgumentException
     * throws java.lang.Exception
     * @throws java.lang.Exception
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertToAssembly_IllegalExp() throws IllegalArgumentException, Exception {
        System.out.println("convertToAssembly");
        PostfixToAssembly instance = new PostfixToAssembly();
        String expResult = "a 3 0 / = ";
        instance.saveToLinkedList(expResult);
        String result1 = "div $t0,$t1\n" +
                         "mflo $t2\n";
        String result2= instance.convertToAssembly();
        assertEquals(result1, result2); 
    }
    
    
}
