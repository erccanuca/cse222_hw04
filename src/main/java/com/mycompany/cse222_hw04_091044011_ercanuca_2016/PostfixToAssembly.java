/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cse222_hw04_091044011_ercanuca_2016;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import java.util.LinkedList;
import java.util.StringTokenizer;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

/**
 * This Class Conver postfix to assembly code
 * @author ercan
 */
public class PostfixToAssembly {

    /**
     * Saving all tokens
     */
    private LinkedList<Character> postfixes;
    /**
     * Saving print a, print b postfix type
     */
    private LinkedList print;
    /**
     * Calculate and append string to this StringBuilder
     */
    private StringBuilder assembly;
    /**
     * The operators
     */
    private static final String OPERATORS = "+-*/=";
    /**
     * Save t0, t1 etc. indexes
     */
    private static int tempsIndex = 0;
    
    /**
     * Default constructor
     * Create objects for our data and,
     */
    public PostfixToAssembly(){
        postfixes = new LinkedList<>();
        assembly = new StringBuilder();
        print = new LinkedList();
    }
    /**
     * save all tokens to linkedlist
     * @param postfix calculated InfixToPostfix class, it is postfix string.
     * throws com.mycompany.cse222_hw04_091044011_ercanuca_2016.PostfixToAssembly.SyntaxErrorException 
     */
    public void saveToLinkedList(String postfix){
        /**
         * Take a tokenizer and save the postfix string
         */
        StringTokenizer postfixToken = new StringTokenizer(postfix);
        try {
            // process each token in the infix string
            while (postfixToken.hasMoreTokens()) {
                String nextToken = postfixToken.nextToken();
                // save like chars.
                postfixes.add(nextToken.charAt(0));
                /**
                 * if our digit like, 45,56 integers
                 */
                if (nextToken.length() > 1) {
                    char firstChar = nextToken.charAt(1);
                    postfixes.add(firstChar);
                }
                // save like string
                print.add(nextToken);
               
            } // end while 
        } catch (Exception exp) {
            throw new SyntaxException("Syntax error: no tokens.");
        }
    }
    /**
     * This method convert postfix data that is the saved on linkedlist
     * to assembly code.
     * @return assembly string lines.
     */
    public String convertToAssembly() {
        int saveToStoreIndex = 0; // save first index
        // search on the linkedlist and converting to assembly
        for (int i = 0; i < postfixes.size(); ++i) {
            // find new integer
            if (isDigit(postfixes.get(i))) {
                tempsIndex++;
            } // like a = 45 size = 4 
            else if (postfixes.size() == 4 && postfixes.get(i) == '=') {
                // appended computed datas
                assembly.append("li $t").append(tempsIndex - 2).append(",")
                        .append(postfixes.get(tempsIndex - 2)).append(postfixes.get(tempsIndex - 1))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex - 2)).append(postfixes.get(tempsIndex - 1))
                        .append(" in $t")
                        .append(tempsIndex - 2).append(".\n");

            } // like a = 4 size =  3
            else if (postfixes.size() == 3 && postfixes.get(i) == '=') {
                assembly.append("li $t").append(tempsIndex - 1).append(",")
                        .append(postfixes.get(tempsIndex))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex))
                        .append(" in $t")
                        .append(tempsIndex - 1).append(".\n");
                
             // to calcuate addition
            } else if (postfixes.get(i) == '+') {
                // if it is add with one digit
                if (isDigit(postfixes.get(i + 1))) {
                    assembly.append("li $t").append(tempsIndex).append(",")
                            .append(postfixes.get(tempsIndex + 1))
                            .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                            .append("=")
                            .append(postfixes.get(tempsIndex + 1))
                            .append(" in $t")
                            .append(tempsIndex).append(".\n");
                }
                // when end of string
                if (postfixes.get(i + 1) == '=') {
                    assembly.append("add $t").append(tempsIndex)
                            .append(",$t").append(tempsIndex)
                            .append(",$t").append(tempsIndex - 1)
                            .append("\t\t#$t").append(tempsIndex)
                            .append("=")
                            .append("$t").append(tempsIndex - 1)
                            .append("+$t")
                            .append(tempsIndex)
                            .append(" in $t")
                            .append(tempsIndex).append(".\n");
                    
                 // otherwise calculate normaly add
                } else {
                    assembly.append("add $t").append(tempsIndex + 1)
                            .append(",$t").append(tempsIndex - 3)
                            .append(",$t").append(tempsIndex - 2)
                            .append("\t\t#$t").append(tempsIndex + 1)
                            .append("=")
                            .append(postfixes.get(tempsIndex - 2))
                            .append("+")
                            .append(postfixes.get(tempsIndex - 1))
                            .append(" in $t")
                            .append(tempsIndex + 1).append(".\n");
                }
            // to calcuate subtraction
            } else if (postfixes.get(i) == '-') {
                // if it is add with one digit
                if (isDigit(postfixes.get(i + 1))) {
                    assembly.append("li $t").append(tempsIndex).append(",")
                            .append(postfixes.get(tempsIndex + 1))
                            .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                            .append("=")
                            .append(postfixes.get(tempsIndex + 1))
                            .append(" in $t")
                            .append(tempsIndex).append(".\n");

                }
                // when end of string
                if (postfixes.get(i + 1) == '=') {
                    assembly.append("sub $t").append(tempsIndex)
                            .append(",$t").append(tempsIndex)
                            .append(",$t").append(tempsIndex - 1)
                            .append("\t\t#$t").append(tempsIndex)
                            .append("=")
                            .append(postfixes.get(tempsIndex))
                            .append("+$t")
                            .append(tempsIndex)
                            .append(" in $t")
                            .append(tempsIndex).append(".\n");
                    assembly.append("move $t").append(0).append(",$t").append(tempsIndex)
                            .append(tempsIndex)
                            .append("\t\t#Move from $t").append(tempsIndex)
                            .append(" to $t").append(0).append(".\n");
                // otherwise calculate normaly sub
                } else {
                    assembly.append("sub $t").append(tempsIndex + 1)
                            .append(",$t").append(tempsIndex - 3)
                            .append(",$t").append(tempsIndex - 2)
                            .append("\t\t#$t").append(tempsIndex + 1)
                            .append("=")
                            .append(postfixes.get(tempsIndex - 2))
                            .append("+")
                            .append(postfixes.get(tempsIndex - 1))
                            .append(" in $t")
                            .append(tempsIndex + 1).append(".\n");
                }
            // to calculate multiply         
            } else if (postfixes.get(i) == '*') {
                // if it is multipling with digit
                if (isDigit(postfixes.get(i + 1))) {
                    assembly.append("li $t").append(tempsIndex).append(",")
                            .append(postfixes.get(tempsIndex + 1))
                            .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                            .append("=")
                            .append(postfixes.get(tempsIndex + 1))
                            .append(" in $t")
                            .append(tempsIndex).append(".\n");

                }
                // when end of string
                if (postfixes.get(i + 1) == '=' && postfixes.size() > 5) {
                    assembly.append("mult $t").append(tempsIndex - 1)
                            .append(",$t").append(tempsIndex).append("\n");
                    assembly.append("mflo $t").append(tempsIndex).append("\n");
                // normal multiply
                } else {
                    assembly.append("mult $t").append(tempsIndex - 3)
                            .append(",$t").append(tempsIndex - 2).append("\n");
                    assembly.append("mflo $t").append(tempsIndex - 1).append("\n");
                }
                // index decreasing for calculation of temprary indexes.
                tempsIndex--;
            // to calculate divide
            } else if (postfixes.get(i) == '/') {
                // if it is dividing with digit
                if (isDigit(postfixes.get(i + 1))) {
                    assembly.append("li $t").append(tempsIndex).append(",")
                            .append(postfixes.get(tempsIndex + 1))
                            .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                            .append("=")
                            .append(postfixes.get(tempsIndex + 1))
                            .append(" in $t")
                            .append(tempsIndex).append(".\n");

                }
                // when end of string
                if (postfixes.get(i + 1) == '=' && postfixes.size() > 5) {
                    assembly.append("div $t").append(tempsIndex)
                            .append(",$t").append(tempsIndex + 1).append("\n");
                    assembly.append("mflo $t").append(tempsIndex + 2).append("\n");
                // normal divide
                } else {
                    assembly.append("div $t").append(tempsIndex - 3)
                            .append(",$t").append(tempsIndex - 2).append("\n");
                    assembly.append("mflo $t").append(tempsIndex - 1).append("\n");
                }
                // index decreasing for calculation of temprary indexes.
                tempsIndex--;
            // the end string printing
            } else if (i == 0 && print.get(i).equals("print")) {
                assembly.append("move $a0").append(",$t")
                        .append(tempsIndex)
                        .append("\t\t#Move from $t")
                        .append(tempsIndex).append(" to $a0").append(".\n");
                assembly.append("li $v0,1")
                        .append("\t\t\t#Load $v0=1         ").append(".\n");
                assembly.append("syscall")
                        .append("\t\t\t\t#Print result").append(".\n");
            }
            else{
            }

        }
        return assembly.toString();
    }
    /**
     * Character is operator method
     *
     * @param ch character type
     * @return true if it operator otherwise false.
     */
    private boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1;
    }

}
