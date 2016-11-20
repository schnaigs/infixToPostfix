// Allison Schinagle
// CS265 Assingment 3
// Infix to Postfix converter
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;

/**
 * This class converts an infix expression to a postfix expression and evaluates it.
 * It contains a main function, a function to convert the infix expression to a 
 * postfix expression, a function to evaluate the postfix expression, and helper
 * functions for dealing with complex conditional statements.
 */
public class InfixToPostfix {
    /**
     * Reads in input, and for each line in input, converts it to a postfix
     * expression and evaluates it. 
     *
     * @throws Exception which will most likely be an IOException from
     *                   FileReader or BufferedReader
     */
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader("input.infix");
        BufferedReader br = new BufferedReader(fr);

        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(" ");

            Token[] infixTokens = new Token[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                try {
                    infixTokens[i] = new Operand(Integer.parseInt(tokens[i]));
                }
                catch (NumberFormatException e) {
                    infixTokens[i] = new Operator(tokens[i]);
                }
            }

            Stack<Token> postfixTokens = infixToPostfix(infixTokens);
            for (Token token : postfixTokens) {
                System.out.print(token.toString() + " ");
            }

            int result = evaluatePostfix(postfixTokens);
            System.out.println("= " + result);
        }
        br.close();
        fr.close();
    }

    /**
     * Converts an infix expression, represented as an array of Tokens, to a
     * postfix expression, represented as a Stack of Tokens.  An algorithm was
     * provided, but I found one that I found easier to follow:
     * - Initialize an opstack to store operators.
     * - If the token is an operand, append it to the end of the output list.
     * - If the token is a left parenthesis, push it on the opstack.
     * - If the token is a right parenthesis, pop the opstack until the 
     *   corresponding left parenthesis is removed.  Append each operator to 
     *   the end of the output list.
     * - If the token is an operator, *, /, +, or -, push it on the opstack. 
     *   However, first remove any operators already on the opstack that have 
     *   higher or equal precedence and append them to the output list.
     * - When the input expression has been completely processed, check the 
     *   opstack.  Any operators still on the stack can be removed and appended 
     *   to the end of the output list.
     * (from http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html)
     *
     * @param infixTokens an array of Tokens arranged such that they represent
     *                    an infix expression
     * @return a Stack of Tokens arranged such that they represent a postfix
     *         expression
     */
    public static Stack<Token> infixToPostfix(Token[] infixTokens) {
        Stack<Token> postfixTokens = new Stack<Token>();
        Stack<Operator> opStack = new Stack<Operator>();

        for (Token token : infixTokens) {
            if (token.isOperand()) {
                postfixTokens.push(token);
            }
            else if (isLeftParen(token)) {
                opStack.push((Operator)token);
            }
            else if (isRightParen(token)) {
                while (!isLeftParen(opStack.peek())) {
                    postfixTokens.push(opStack.pop());
                }
                // top of opStack should now be a left paren - discard it
                opStack.pop();
            }
            else if (token.isOperator()) {
                while(!opStack.isEmpty() && isEqualOrHigherPrecedence(
                                                (Operator)token, 
                                                opStack.peek())) {
                    postfixTokens.push(opStack.pop());
                }    
                opStack.push((Operator)token);
            }
        }

        while (!opStack.isEmpty()) {
            postfixTokens.push(opStack.pop());
        }

        return postfixTokens;
    }

    /**
     * Determines whether a Token represents a left paranthesis.
     *
     * @param token the token to be evaluated
     * @return true if the token represents a left parenthesis
     */
    public static boolean isLeftParen(Token token) {
        return ((Operator)token).getOp() == OpType.L_PAR;
    }

    /**
     * Determines whether a Token represents a right parenthesis.
     * 
     * @param token the token to be evaluated
     * @return true if the token represents a right parenthesis
     */
    public static boolean isRightParen(Token token) {
        return ((Operator)token).getOp() == OpType.R_PAR;
    }

    /**
     * Determines if the precedence of an Operator is higher than or equal to
     * that of another Operator.
     *
     * @param a one of the Operators to be compared
     * @param b the other Operator to be compared
     * @return true if a has an equal or higher precendence than b
     */
    public static boolean isEqualOrHigherPrecedence(Operator a, Operator b) {
        boolean isEqual = Operator.compare(a, b) == 0;
        boolean isHigher = Operator.compare(a, b) == 1;
        return isEqual || isHigher;
    }

    /**
     * Evaluates a postfix expression which is in the form of a stack using the
     * following algorithm: 
     * For each token:
     * - If it is an operand, push it on the stack.
     * - Else if it is an operator, then
     *   y = pop top value
     *   x = pop top value
     *   result = x (oper) y
     *   push result onto stack
     * - fi
     *
     * @param postfixTokens a Stack of Tokens ordered such that they make up
     *                      a postfix expression
     * @return the result of evaluating the postfix expression as an int
     */
    public static int evaluatePostfix(Stack<Token> postfixTokens) {
        Stack<Operand> evalStack = new Stack<Operand>();
        for (Token token : postfixTokens) {
            if (token.isOperand()) {
                evalStack.push((Operand)token);
            }
            else if (isAddition(token)) {
                int secondOperand = evalStack.pop().getVal();
                int firstOperand = evalStack.pop().getVal();
                evalStack.push(new Operand(firstOperand + secondOperand));
            }
            else if (isSubtraction(token)) {
                int secondOperand = evalStack.pop().getVal();
                int firstOperand = evalStack.pop().getVal();
                evalStack.push(new Operand(firstOperand - secondOperand));
            }
            else if (isMultiplication(token)) {
                int secondOperand = evalStack.pop().getVal();
                int firstOperand = evalStack.pop().getVal();
                evalStack.push(new Operand(firstOperand * secondOperand));
            }
            else if (isDivision(token)) {
                int secondOperand = evalStack.pop().getVal();
                int firstOperand = evalStack.pop().getVal();
                evalStack.push(new Operand(firstOperand / secondOperand));
            }
            else if (isModulo(token)) {
                int secondOperand = evalStack.pop().getVal();
                int firstOperand = evalStack.pop().getVal();
                evalStack.push(new Operand(firstOperand % secondOperand));
            }
        }

        return evalStack.pop().getVal();
    }

    /**
     * Determines whether a Token represents the addition operation.
     * 
     * @param token the token to be evaluated
     * @return true if the token represents addition
     */
    public static boolean isAddition(Token token) {
        return ((Operator)token).getOp() == OpType.ADD;
    }

    /**
     * Determines whether a Token represents the subtraction operation.
     * 
     * @param token the token to be evaluated
     * @return true if the token represents subtraction
     */
    public static boolean isSubtraction(Token token) {
        return ((Operator)token).getOp() == OpType.SUB;
    }

    /**
     * Determines whether a Token represents the multiplication operation.
     * 
     * @param token the token to be evaluated
     * @return true if the token represents multiplication
     */
    public static boolean isMultiplication(Token token) {
        return ((Operator)token).getOp() == OpType.MUL;
    }

    /**
     * Determines whether a Token represents the division operation.
     * 
     * @param token the token to be evaluated
     * @return true if the token represents division
     */
    public static boolean isDivision(Token token) {
        return ((Operator)token).getOp() == OpType.DIV;
    }

    /**
     * Determines whether a Token represents the modulo operation.
     * 
     * @param token the token to be evaluated
     * @return true if the token represents modulo
     */
    public static boolean isModulo(Token token) {
        return ((Operator)token).getOp() == OpType.MOD;
    }
} 
