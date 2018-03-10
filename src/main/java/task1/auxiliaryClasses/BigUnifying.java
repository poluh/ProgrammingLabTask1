package task1.auxiliaryClasses;

public interface BigUnifying<E> {

    /**
     * The method of copying a number. Called on BigNumber, returning an identical BigNumber
     *
     * @return new BigNumber/Fraction even old BigNumber
     */
    E copy();

    /**
     * This method will tell you which character has a number.
     *
     * @return false is positive, true is negative
     */
    boolean isNegative();

    /**
     * Who will say what this method does?
     * You? Maybe this gentleman?
     * Who would have thought that this method
     * changes the sign of a number. (negative, positive)
     *
     * @param negative false is positive, true is negative
     */
    void setNegative(boolean negative);

    /**
     * And here we round off the numbers.
     * This method returns a rounded number to the required character.
     *
     * @param border index of the rounding sign
     * @return new rounded BigNumber/Fraction
     */
    E round(int border);

    /**
     * The method of adding two numbers, can take both both negative numbers,
     * and one negative with a positive ordinary.
     * Has an overloaded version for adding an ordinary int-number if necessary.
     *
     * @param other BigNumber/Fraction for sum
     * @return new BigNumber/Fraction
     */
    E plus(E other);

    /**
     * A method for subtracting two numbers,
     * which receives fraction = true if the "tails" (numbers after the comma)
     * are subtracted for the BigNumberFraction class.
     * Can subtract both two negative numbers, and negative with positive.
     * It has two overloaded methods, for subtracting the usual int-number and BigNumber-numbers.
     *
     * @param other BigNumber for subtraction
     * @return new BigNumber/Fraction
     */
    E minus(E other);

    /**
     * A method for multiplying two BigNumber
     * (also used in the times () method for BigNumberFraction).
     * Can multiply negative numbers. Uses the optional timesOneDigit method,
     * which takes number to be multiplied by it.
     * When multiplying by 10, to some extent uses appendZeros to get the result faster.
     *
     * @param other BigNumber for times
     * @return new BigNumber/Fraction
     */
    E times(E other);

    /**
     * These methods simplify addition/subtraction
     * when using a number as a counter.
     * Inc() increases the number by one, Dec(), respectively, reduces.
     */
    void inc();

    void dec();

}
