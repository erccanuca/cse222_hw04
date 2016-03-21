/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cse222_hw04_091044011_ercanuca_2016;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 *
 * @author ercan
 */
public class PostfixToAssembly {
    
  // Nested Class
  /** Class to report a syntax error. */
  public static class SyntaxErrorException
      extends Exception {
    /**
     * Construct a SyntaxErrorException with the specified
     * message.
     * @param message The message
     */
    SyntaxErrorException(String message) {
      super(message);
    }
  } 
    private LinkedList<Character> postfixes;
    private LinkedList print;
    
    private StringBuilder assembly;
    /**
     * The operators
     */
    private static final String OPERATORS = "+-*/=";
    
    private static int tempsIndex = 0;
    private static int next = 0;
    private static int next2 = 1;

    public void saveToLinkedList(String postfix) throws SyntaxErrorException{
        postfixes = new LinkedList<>();
        assembly  = new StringBuilder();
        print  = new LinkedList();
        StringTokenizer postfixToken = new StringTokenizer(postfix);
        try {
                // process each token in the infix string
                while(postfixToken.hasMoreTokens())
                {   
                    String nextToken = postfixToken.nextToken();
                    postfixes.add(nextToken.charAt(0));
                    if(nextToken.length()>1){
                        char firstChar = nextToken.charAt(1);
                        postfixes.add(firstChar);

                    }
                    print.add(nextToken);
                    /*

                                case '/':
                                    assembly.append("div $t").append(tempsIndex-1).append(",$t").append(tempsIndex-2).append("\n");
                                    assembly.append("mflo $t").append(tempsIndex).append("\n");
                                    if(postfixToken.hasMoreTokens() && '='!= postfixes.get(index-2)) {
                                        tempsIndex++;
                                   */
                    } // end while 
           }catch (Exception exp) {
                throw new SyntaxErrorException("Syntax error: The stack is empty."); 
           }
                   
}
    public String convertToAssembly(){
        int saveToStoreIndex = 0;
        
        for(int i=0; i < postfixes.size(); ++i){
            
            if(isDigit(postfixes.get(i))){ 
                tempsIndex++;
            }
            // like a = 44
            else if(postfixes.size()==4 && postfixes.get(i)=='='){
                
                assembly.append("li $t").append(tempsIndex-2).append(",")
                        .append(postfixes.get(tempsIndex-2)).append(postfixes.get(tempsIndex-1))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex-2)).append(postfixes.get(tempsIndex-1))
                        .append(" in $t")
                        .append(tempsIndex-2).append(".\n");
                       
            }
            // like a = 4
            else if(postfixes.size()==3 && postfixes.get(i)=='='){
                assembly.append("li $t").append(tempsIndex-1).append(",")
                        .append(postfixes.get(tempsIndex))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex))
                        .append(" in $t")
                        .append(tempsIndex-1).append(".\n");
                       
            }
            else if(postfixes.get(i)=='+'){
                if(isDigit(postfixes.get(i+1))){
                    assembly.append("li $t").append(tempsIndex).append(",")
                        .append(postfixes.get(tempsIndex+1))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex+1))
                        .append(" in $t")
                        .append(tempsIndex).append(".\n");
               
                }
                if(postfixes.get(i+1)=='='){
                    assembly.append("add $t").append(tempsIndex)
                        .append(",$t").append(tempsIndex)
                        .append(",$t").append(tempsIndex-1)
                        .append("\t\t#$t").append(tempsIndex)
                        .append("=")
                        .append("$t").append(tempsIndex-1)
                        .append("+$t")
                        .append(tempsIndex)
                        .append(" in $t")
                        .append(tempsIndex).append(".\n");
                
                }
                else{
                     assembly.append("add $t").append(tempsIndex+1)
                        .append(",$t").append(tempsIndex-3)
                        .append(",$t").append(tempsIndex-2)
                        .append("\t\t#$t").append(tempsIndex+1)
                        .append("=")
                        .append(postfixes.get(tempsIndex-2))
                        .append("+")
                        .append(postfixes.get(tempsIndex-1))
                        .append(" in $t")
                        .append(tempsIndex+1).append(".\n");
                }
               // tempsIndex++;

                
            }
            else if(postfixes.get(i)=='-'){
                if(isDigit(postfixes.get(i+1))){
                    assembly.append("li $t").append(tempsIndex).append(",")
                        .append(postfixes.get(tempsIndex+1))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex+1))
                        .append(" in $t")
                        .append(tempsIndex).append(".\n");
               
                }
                if(postfixes.get(i+1)=='='){
                    assembly.append("sub $t").append(tempsIndex)
                        .append(",$t").append(tempsIndex)
                        .append(",$t").append(tempsIndex-1)
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
                
                }
                else{
                     assembly.append("sub $t").append(tempsIndex+1)
                        .append(",$t").append(tempsIndex-3)
                        .append(",$t").append(tempsIndex-2)
                        .append("\t\t#$t").append(tempsIndex+1)
                        .append("=")
                        .append(postfixes.get(tempsIndex-2))
                        .append("+")
                        .append(postfixes.get(tempsIndex-1))
                        .append(" in $t")
                        .append(tempsIndex+1).append(".\n");
                }
               // tempsIndex++;              
            }
            else if(postfixes.get(i)=='*'){
                if(isDigit(postfixes.get(i+1))){
                    assembly.append("li $t").append(tempsIndex).append(",")
                        .append(postfixes.get(tempsIndex+1))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex+1))
                        .append(" in $t")
                        .append(tempsIndex).append(".\n");
                   
               
                }
                if(postfixes.get(i+1)=='='&& postfixes.size()>5){
                    assembly.append("mult $t").append(tempsIndex-1)
                            .append(",$t").append(tempsIndex).append("\n");
                    assembly.append("mflo $t").append(tempsIndex).append("\n");
                
                }
                else{
                    assembly.append("mult $t").append(tempsIndex-3)
                            .append(",$t").append(tempsIndex-2).append("\n");
                    assembly.append("mflo $t").append(tempsIndex-1).append("\n");
                }
                tempsIndex--;
                
            }
            else if(postfixes.get(i)=='/'){
                if(isDigit(postfixes.get(i+1))){
                    assembly.append("li $t").append(tempsIndex).append(",")
                        .append(postfixes.get(tempsIndex+1))
                        .append("\t\t\t#").append(postfixes.get(saveToStoreIndex))
                        .append("=")
                        .append(postfixes.get(tempsIndex+1))
                        .append(" in $t")
                        .append(tempsIndex).append(".\n");
                   
               
                }
                if(postfixes.get(i+1)=='=' && postfixes.size()>5){
                    assembly.append("div $t").append(tempsIndex)
                            .append(",$t").append(tempsIndex+1).append("\n");
                    assembly.append("mflo $t").append(tempsIndex+2).append("\n");
                
                }
                else{
                    assembly.append("div $t").append(tempsIndex-3)
                            .append(",$t").append(tempsIndex-2).append("\n");
                    assembly.append("mflo $t").append(tempsIndex-1).append("\n");
                }
                tempsIndex--;
                
            }
            else if(i==0 && print.get(i).equals("print")){
                assembly.append("move $a0").append(",$t")
                        .append(tempsIndex)
                        .append("\t\t#Move from $t")
                        .append(tempsIndex).append(" to $a0").append(".\n");
                assembly.append("li $v0,1")
                        .append("\t\t\t#Load $v0=1         ").append(".\n");
                assembly.append("syscall")
                        .append("\t\t\t\t#Print result").append(".\n");
            }
            else if(isLetter(postfixes.get(i))){
               // System.out.println("letter: " + postfixes.get(i));
            }
                
                //System.out.println(varlist.get(i));
            }
        
        return assembly.toString();
    }
    /**
     * Character is operator method
     * @param ch character type
     * @return true if it operator otherwise false.
     */
    private boolean isOperator(char ch){
        return OPERATORS.indexOf(ch)!=-1;
    }

}
    
