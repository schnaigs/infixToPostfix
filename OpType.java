// Allison Schinagle
// CS265 Assignment 3

/**
 * Enum defining the the seven operations and their precedence.  Highest 
 * precedence is 2, which is parentheses, next highest is multiplication, 
 * division, and modulo with a precedence of 1, and then addition and 
 * subtraction with a precedence of 0.
 */
public enum OpType
{
    /**
     * Left parens. "("
     */
    L_PAR (2),

    /**
     * Right parens. ")"
     */
    R_PAR(2),

    /**
     * Multiplication. "*"
     */
    MUL (1), 

    /**
     * Division. "/"
     */
    DIV (1),

    /**
     * Modulo. "%"
     */
    MOD (1),

    /**
     * Addition. "+"
     */
    ADD (0), 

    /**
     * Subtraction. "-"
     */
    SUB (0);


    /**
     * The precedence of the operator. Lower numer = higher precedence.
     */
    private final int precedence;

    /**
     * Constructs the OpType with its precedence.  Automatically called when an
     * OpType is declared; cannot be invoked.
     * 
     * @param prec the precedence of the OpType
     */
    OpType(int prec) {
        this.precedence = prec;
    }

    /**
     * Return the precedence of the OpType this method is called on.
     *
     * @return the OpType's precedence as an int
     */
    int getPrecedence() {
        return this.precedence;
    }
}
