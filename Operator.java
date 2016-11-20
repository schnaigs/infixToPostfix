// Operator.java - operator token (+ - * / ( ) %)
//
// Kurt Schmidt
// 3/07
//
// javac 1.6.0, on Linux version 2.6.18.6 (gcc version 3.4.6 (Gentoo
//		 3.4.6-r1, ssp-3.4.5-1.0, pie-8.7.9))
//
// EDITOR:  tabstop=2, cols=80
//
// NOTES:
// 	Tokens come in 2 flavors:
// 		operands (only integers here)
// 		operators (just +, -, *, /, %, parenthesis)
// 
//	This is so you can have a single container (Vector, ArrayList, whatever)
//	that holds an entire expression (Token).
//

/**
 * Represents an operator, i.e. an arithmetical operation that is performed on
 * operands.  Modified by me (Allison Schinagle) to conform with the Google Java
 * style guide (more or less; 4 spaces instead of 2), and added documentation 
 * and a toString method, and uses my OpType enum instead of the one given, and
 * constructs an OpType from a String.  Inherits from Token, since an Operator 
 * is a kind of token.
 */
public class Operator extends Token {
    /**
     * The type of arithmetical operation, as an OpType
     */
    protected OpType op;

    /**
     * Inherited from the Token class.  This is the Operator class, so it is an
     * operator, so this returns true.
     * 
     * @return true because this is an Operator
     */
    public boolean isOperator() { 
        return true; 
    }

    /**
     * Inherited from the Token class.  This is the Operator class so it is not
     * an Operand, so this returns false.
     *
     * @return false because this is not the Operand class
     */
    public boolean isOperand() { 
        return false; 
    }

    /**
     * Gets the operator's precedence using the enum OpType.getPrecedence().
     *
     * @return the precedence of the operator as an int
     */
    protected int getPrecedence() {
        return op.getPrecedence();
    }

    /**
     * Compares the precedences of two Operators.  If they're equal, returns 0,
     * if Operator a has a lower precedence than Operator a, return -1, and if 
     * Operator a has a higher precedence than Operator b, return 1.
     *
     * @return an int representing whether two Operators are equal in
     *         precedence, or if one is less than or higher than the other
     */
    public static int compare(Operator a, Operator b) {
        if (a.getPrecedence() == b.getPrecedence()) {
            return 0;
        }
        else if (a.getPrecedence() < b.getPrecedence()) {
            return -1;
        }
        else {
            return 1;
        }
    }

    /**
     * Retrieves the OpType.
     *
     * @return the OpType op
     */
    public OpType getOp() { 
        return this.op;
    }

    /**
     * Constructs an Operator, assigning an OpType to the instance variable op, 
     * from a String representing that operator.
     *
     * @param o The String representing the operator.
     */
    public Operator(String o) { 
        switch (o) {
            case "(":
                op = OpType.L_PAR;
                break;
            case ")":
                op = OpType.R_PAR;
                break;
            case "*": 
                op = OpType.MUL;
                break;
            case "/":
                op = OpType.DIV;
                break;
            case "+":
                op = OpType.ADD;
                break;
            case "-":
                op = OpType.SUB;
                break;
            case "%":
                op = OpType.MOD;
                break;
        } 
    }

    /**
     * Returns a representation of this Operator as a String.
     *
     * @return a String representing op
     */
    @Override
    public String toString() {
        switch (op) {
            case L_PAR:
                return "(";
            case R_PAR:
                return ")";
            case MUL:
                return "*";
            case DIV:
                return "/";
            case ADD:
                return "+";
            case SUB:
                return "-";
            case MOD:
                return "%";
            default:         // this should never happen
                return "";
        }
    }
}
