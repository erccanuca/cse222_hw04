/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cse222_hw04_091044011_ercanuca_2016;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * This class covert to infix to postfix
 *
 * @author ercan
 */
public class InfixToPostfix {

    // Nested Class
    /**
     * Class to report a syntax error.
     */
    public static class SyntaxErrorException
            extends Exception {

        /**
         * Construct a SyntaxErrorException with the specified message.
         *
         * @param message The message
         */
        SyntaxErrorException(String message) {
            super(message);
        }
    }
    /**
     * Stack of operators.
     */
    private Stack<Character> operandStack;
    /**
     * The postfix string
     */
    private StringBuilder postfix;
    /**
     * The operators
     */
    private static final String OPERATORS = "+-*/=";
    /**
     * The precedence of operators, matches order in OPERATORS
     */
    private static final int[] PRECEDENCE = {1, 1, 2, 2, 0};

    /**
     * Exracts and processes each token in infix and returns the equivalent
     * postfix string.
     *
     * @param infix is token take from file
     * @return the equivalent postfix string throws SyntaxErrorException
     */
    public String convert(String infix) throws SyntaxErrorException {
        operandStack = new Stack<>();
        postfix = new StringBuilder();
        StringTokenizer infixToken = new StringTokenizer(infix);
        try {
            // process each token in the infix string
            while (infixToken.hasMoreTokens()) {
                String nextToken = infixToken.nextToken();
                char firsChar = nextToken.charAt(0);
                // is it operand?
                if (Character.isJavaIdentifierPart(firsChar)
                        || Character.isDigit(firsChar)) {
                    postfix.append(nextToken);
                    postfix.append(' ');
                }// is it an operator?
                else if (isOperator(firsChar)) {
                    processOperator(firsChar);
                } else {
                    throw new SyntaxErrorException("Unexpected character encountered" + firsChar);
                }
            } // end while
            //pop any remaining operators and
            //and append them to postfix
            while (!operandStack.empty()) {
                char op = operandStack.pop();
                postfix.append(op);
                postfix.append(' ');
            }
            // assert: Stack is empty, return result
            return postfix.toString();
        } catch (EmptyStackException exp) {
            throw new SyntaxErrorException("Syntax error: The stack is empty.");
        }
    }

    /**
     * Processes operator op by updating operandStack
     *
     * @param op is the operator throws EmptyStackException
     */
    private void processOperator(char op) {
        if (operandStack.isEmpty()) {
            operandStack.push(op);
        } else {
            // peek operator from stack and 
            // let topOp be top operator
            char topOp = operandStack.peek();
            if (precedence(op) > precedence(topOp)) {
                operandStack.push(op);
            } else {
                // pop all stacked operators with equal
                // or higher precedence than op
                while (!operandStack.isEmpty()
                        && precedence(op) <= precedence(topOp)) {
                    operandStack.pop();
                    postfix.append(topOp);
                    postfix.append(' ');
                    if (!operandStack.isEmpty()) {
                        // reset topOp
                        topOp = operandStack.peek();
                    }
                }
                // assert: Operator stack is empty or
                // current operator precedence > top of stack operator precedence
                operandStack.push(op);
            }
        }
    }

    /**
     * Precesence of operators comparison
     *
     * @param op operator
     * @return the precedence of current operator.
     */
    private int precedence(char op) {
        return PRECEDENCE[OPERATORS.indexOf(op)];
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
