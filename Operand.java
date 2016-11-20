// Operand.java - Token, holds integer operands
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
// 		operators (just +, -, *, /, exponentiation, parenthesis)
// 
//	This is so you can have a single container (Vector, ArrayList, whatever)
//	that holds an entire expression (Token).
//

/**
 * Represents an operand, i.e. a positive number upon which arithmetical
 * operations are performed.  Modified by me (Allison Schinagle) to conform
 * with the Google Java style guide (more or less; 4 spaces instead of 2), and
 * added documentation, and a toString method.  Inherits from Token, since an
 * Operand is a kind of token.
 */
public class Operand extends Token {
    /**
     * The value of the operand.
     */
    protected int val;

    /**
     * Inherited from the Token class.  This is the Operand class, so it is not
     * an operator, so this returns false.
     *
     * @return false because this is not the Operator class
     */
    public boolean isOperator() { 
        return false; 
    }

    /**
     * Inherited from the Token class.  This is the Operand class, so it is an
     * operand, so this returns true.
     * 
     * @return true because this is the Operand class
     */
    public boolean isOperand() { 
        return true; 
    }

    /**
     * Retrieves the value that the instance of this class is representing.
     * 
     * @return val
     */
    public int getVal() {
        return this.val; 
    }

    /**
     * Constructs an Operand object.  val is set to the given value, v. 
     *
     * @param v the value that this Operand will represent
     */
    public Operand(int v) { 
        this.val = v; 
    }

    /**
     * Returns a representation of this Operand as a String.
     *
     * @return a String representing the value of this Operand
     */
    public String toString() {
        return (new Integer(val)).toString();
    }
}
