package mycompiler;

import java.util.Scanner;

public class Lexer {
    //define the name of tokens
    public static final int EOI = 0; //end of file
    public static final int LP = 1; //left parenthesis
    public static final int RP = 2;
    public static final int NUM_OR_VAR = 3;
    public static final int PLUS = 4;
    public static final int TIMES = 5;
    public static final int MINUS = 6;
    public static final int DIVIDE = 7;
    public static final int SEMICOLON = 8;
    public static final int UNKNOWN_SYMBOL = 9;

    private String inputText = "";

    private String remainingInput = "";
    private String currentString = "";
    private int currentToken = -1;

    //read input from command, now only support one line
    public boolean readInput(){
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        while(line != "end"){
            inputText += line;
            line = scanner.nextLine();
        }
        remainingInput = inputText;
        scanner.close();
        return !inputText.equals("");
    }

    //when user input string from command, return all identified string and their token name
    public void runLexer(){
        if(currentToken == -1)
        {
            currentToken = lex();
            while(currentToken != EOI){
                System.out.println("String Identified: " + currentString + ", Token name: " + getTokenName(currentToken));
                currentToken = lex();
            }
        }
    }

    //determine the token of first string in remainingInput
    public int lex(){
        int res = -1;
        while(remainingInput.equals("")){
            if(!readInput()) return EOI;
            currentString = "";
        }

        //now denominate all meaningless char
        int ind = 0;
        char temp = remainingInput.charAt(ind);
        while(temp == '\n' || temp == '\t' || temp == ' '){
            ind++;
            temp = remainingInput.charAt(ind);
        }
        remainingInput = remainingInput.substring(ind);//since from rem[ind] is valid char, while res[ind-1] is not.

        //now we can make sure remainingInput has value, then we check the first string in remainingInput, and determine its token
        char currentChar = remainingInput.charAt(0);
        int processLen = -1;

        switch(currentChar)
        {
            case '+':
                res = PLUS;
                processLen++;
                break;
            case '*':
                res = TIMES;
                processLen++;
                break;
            case '-':
                res = MINUS;
                processLen++;
                break;
            case '/':
                res = DIVIDE;
                processLen++;
                break;
            case '(':
                res = LP;
                processLen++;
                break;
            case ')':
                res = RP;
                processLen++;
                break;
            case ';':
                res = SEMICOLON;
                processLen++;
                break;

            //now we can handle NUM_OR_VAR, and UNKNOWN_SYMBOL
            default:
                if(!isNumOrVar(currentChar)){
                    res = UNKNOWN_SYMBOL;
                    processLen++;
                    break;
                }
                res = NUM_OR_VAR;
                int i = 0;
                do{
                    processLen++;
                    i++;
                }
                while(i < remainingInput.length() && isNumOrVar(remainingInput.charAt(i)));
        }
        currentString = remainingInput.substring(0, processLen+1);//[0, processlen+1) == [0, processlen]
        if(processLen + 1  < remainingInput.length())
        {
            remainingInput = remainingInput.substring(processLen+1); //[processLen + 1, end)
        }
        else{
            res = EOI;

        }
        return  res;
    }

    private boolean isNumOrVar(Character currentChar){
        boolean ret = false;
        if(Character.isAlphabetic(currentChar) || Character.isDigit(currentChar))
            ret = true;
        return ret;
    }

    private String getTokenName(int currentToken){
        String ret = "";
        switch (currentToken){
            case '1':
                ret = "LP";
                break;
            case '2':
                ret = "RP";
                break;
            case '3':
                ret = "NUM_OR_VAR";
                break;
            case '4':
                ret = "PLUS";
                break;
            case '5':
                ret = "TIMES";
                break;
            case '6':
                ret = "MINUS";
                break;
            case '7':
                ret = "DIVIDE";
                break;
            case '8':
                ret = "SEMICOLON";
                break;
            case '9':
                ret = "UNKNOWN_SYMBOL";
                break;
        }
        return ret;
    }
}





