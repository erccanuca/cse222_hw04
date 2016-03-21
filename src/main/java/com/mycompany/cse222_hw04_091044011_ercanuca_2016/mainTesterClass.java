package com.mycompany.cse222_hw04_091044011_ercanuca_2016;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ercan
 */
public class mainTesterClass {
    public static void main(String[] args) {
     
        try {
            FileInputStream fstream = new FileInputStream("C:/Users/Ercan/Documents/NetBeansProjects/cse222_hw04_091044011_ercanuca_2016/infix.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String tmpStr;
            InfixToPostfix infixToPostfix = new InfixToPostfix();
            PostfixToAssembly postToAssembly = new PostfixToAssembly();
            BufferedWriter writer = null;
 
            writer = new BufferedWriter( new FileWriter("assembly.asm"));


            
            
            while ((tmpStr = br.readLine()) != null) {
                String postfix = infixToPostfix.convert(tmpStr);
                System.out.println(postfix);
                postToAssembly.saveToLinkedList(postfix);
                String assembly = postToAssembly.convertToAssembly();
                System.out.println(assembly);
                writer.write(assembly);
                writer.write("\n");
                
            }
      
            in.close();
            writer.close();
        } catch (Exception e) {
        System.err.println(e);
        }
       
        
    }
   
}