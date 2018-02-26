package task1.auxiliaryClasses;

import task1.auxiliaryClasses.Collection.ArrayBigNumber;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * This class is designed to store REALLY LARGE NUMBERS,
 * as well as an auxiliary class for BigNumberFraction.
 * Can add, subtract and multiply numbers without loss in accuracy,
 * as well as fast enough in time.
 * (Multiplying two numbers in 100k characters takes
 * no more than two or three seconds on the MacBook Air 2017)
 * <p>
 * It can contain int-characters (2,147,483,647),
 * while the number storage design does not overdo
 * more than four bytes for every nine digits
 * (for details, in the ArrayBigNumber and IntegerObject classes).
 * <p>
 * PLEASE DO NOT KEEP THE NUMBER OF NUMBER, THE RESULT OF OPERATIONS ON WHICH CAN BE PLACED IN LONG OR INT
 */

public class BigNumber implements Comparable<BigNumber> {

    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    private ArrayBigNumber number;
    // Main storage for number
    private boolean negative;
    // Negative or positive number


    /**
     * The main method for specifying BigNumber.
     * Logs and initializes class fields.
     * <p>
     * Gets the input ArrayBigNumber -
     * a special storage class for large numbers based on IntegerObject.
     *
     * @param number   The number you want to write
     * @param negative Its sign (negative (false) or positive number (true))
     */

    private void create(ArrayBigNumber number, boolean negative) {
        try {
            this.number = number;
            this.negative = negative;
            log.log(Level.INFO, "Create new BigNumber={0}", this);
        } catch (NumberFormatException ex) {
            log.log(Level.SEVERE, "Exception: Invalid format number({0}).", number);
        }
    }

    /**
     * One of the constructors for creating BigNumber.
     * Accepts a string representation of a number, for example "9123929" or "-12321442".
     * Interprets the string in ArrayBigNumber,
     * as well as the sign of the number (negative or positive) then it passes to the create()
     *
     * @param number A string interpretation of a number with the corresponding character
     */

    public BigNumber(String number) {
        if (number.matches("-?\\d+")) {
            boolean negative = number.charAt(0) == '-';
            ArrayBigNumber numbers = new ArrayBigNumber();
            int offset = negative ? 1 : 0;
            for (int i = offset; i < number.length(); i++) {
                numbers.add((int) number.charAt(i));
            }
            create(numbers, negative);
        } else {
            throw new NumberFormatException("NUMBER = " + number);
        }
    }

    public BigNumber(BigNumber bigNumber, boolean negative) {
        create(bigNumber.number, negative);
    }

    /**
     * A classical constructor that receives an ArrayBigNumber and
     * a number sign that immediately passes to the private create() method.
     *
     * @param number   A specialized class of storing numbers based on IntegerObject
     * @param negative Its sign (negative (false) or positive number (true))
     */

    private BigNumber(ArrayBigNumber number, boolean negative) {
        create(number, negative);
    }

    /**
     * The method of copying a number. Called on BigNumber, returning an identical BigNumber
     *
     * @return new BigNumber even old BigNumber
     */

    public BigNumber copy() {
        BigNumber answer = new BigNumber("0");
        answer.negative = this.negative;
        answer.number = this.number;
        return answer;
    }

    public String getNumber() {
        return this.number.toString();
    }

    public ArrayBigNumber getArray() {
        return this.number;
    }

    public int get(int index) {
        return this.number.get(index);
    }

    public void setNumber(ArrayBigNumber number) {
        this.number = number;
    }

    public void setNumber(String number) {
        this.setNumber(new BigNumber(number).number);
    }

    public int length() {
        return this.number.size();
    }

    /**
     * The method of adding zeros before or after the number.
     * Used to initialize the shift of a number relatively larger in length,
     * as well as multiplication by 10 to some extent. Returns new ArrayBigNumber with zeros.
     *
     * @param quantity Number of zeros to add
     * @param atFirst  Add zeros to the left or to the right of the number
     * @return new ArrayBigNumber
     */

    public ArrayBigNumber appendZeros(int quantity, boolean atFirst) {
        String zeros = IntStream.range(0, quantity).mapToObj(i -> "0").collect(Collectors.joining());
        BigNumber buf = new BigNumber("0");
        buf.setNumber(!atFirst ? this.getNumber().concat(zeros) : zeros.concat(this.getNumber()));
        return buf.number;
    }

    public boolean isNegative() {
        return this.negative;
    }

    public void delNegative() {
        this.negative = false;
    }

    /**
     * Method for deleting the minus of a number, returning a copy without denying.
     *
     * @param forDel BigNumber for del negative
     * @return copy original BigNumber without negative
     */

    public static BigNumber delNegative(BigNumber forDel) {
        boolean forDelNegative = forDel.negative;
        forDel.delNegative();
        BigNumber answer = forDel.copy();
        forDel.negative = forDelNegative;
        return answer;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    /**
     * A method that rounds a number to a specific character.
     * Rounds the original number by changing the original (!) number.
     *
     * @param border The position of the number to be rounded (including the number itself)
     */

    public void round(int border) {
        int roundNumber = this.get(border);
        BigNumber difference =
                new BigNumber(String.valueOf((int) Math.pow(10 - roundNumber, this.length() - border - 1)));
        if (roundNumber >= 5) {
            this.plus(difference);
        } else {
            this.minus(difference);
        }
    }

    public boolean isEmpty() {
        return this.number.size() == 0;
    }

    public static BigNumber maxOf(BigNumber first, BigNumber second) {
        return first.compareTo(second) > 0 ? first : second;
    }

    public static BigNumber minOf(BigNumber first, BigNumber second) {
        return first.compareTo(second) > 0 ? second : first;
    }

    public BigNumber plus(int other) {
        return this.plus(new BigNumber(String.valueOf(other)));
    }

    public BigNumber plus(BigNumber other) {
        return this.plus(other, false);
    }

    /**
     * The method of adding two numbers, can take both both negative numbers,
     * and one negative with a positive ordinary.
     * Has an overloaded version for adding an ordinary int-number if necessary.
     *
     * @param other BigNumber for sum
     * @return result sum
     */

    public BigNumber plus(BigNumber other, boolean fraction) {

        boolean bothNegative = this.isNegative() && other.isNegative();
        boolean oneNegative = this.isNegative() ^ other.isNegative();
        BigNumber firstBuf = delNegative(this);
        BigNumber secondBuf = delNegative(other);
        if (oneNegative) {
            if (firstBuf.equals(secondBuf)) return new BigNumber("0");
            if (firstBuf.compareTo(secondBuf) > 0) {
                return new BigNumber(firstBuf.minus(secondBuf), this.negative);
            } else {
                return new BigNumber(secondBuf.minus(firstBuf), other.negative);
            }
        } else {
            int length = Math.max(this.length(), other.length());
            firstBuf = new BigNumber(this.appendZeros(Math.abs(this.length() - length), !fraction),
                    false);
            secondBuf = new BigNumber(other.appendZeros(Math.abs(other.length() - length), !fraction),
                    false);
            log.log(Level.INFO, "First number for sum = {0}", firstBuf);
            log.log(Level.INFO, "Second number for sum = {0}", secondBuf);

            StringBuilder numberBuf = new StringBuilder();
            int remember = 0;
            for (int i = length; i > 0; i--) {
                int addedNum = firstBuf.get(i) + secondBuf.get(i) + remember;
                System.out.println(firstBuf.get(i));
                if (addedNum > 9) {
                    addedNum -= 10;
                    remember = 1;
                } else {
                    remember = 0;
                }
                numberBuf.append(addedNum);
            }
            numberBuf.append(remember == 1 ? "1" : "");
            ArrayBigNumber number = new ArrayBigNumber(numberBuf.reverse().toString());
            return bothNegative ? new BigNumber(number, true) : new BigNumber(number, false);
        }
    }

    private String removeZeros(String number) {
        return number.replaceFirst("^(0)+", "");
    }

    public BigNumber minus(int other) {
        return minus(new BigNumber(String.valueOf(other)), false);
    }

    public BigNumber minus(BigNumber other) {
        return minus(other, false);
    }

    /**
     * A method for subtracting two numbers,
     * which receives fraction = true if the "tails" (numbers after the comma)
     * are subtracted for the BigNumberFraction class.
     * Can subtract both two negative numbers, and negative with positive.
     * It has two overloaded methods, for subtracting the usual int-number and BigNumber-numbers.
     *
     * @param other     BigNumber for subtraction
     * @param factional Option. For BigNumberFraction
     * @return result subtraction
     */

    public BigNumber minus(BigNumber other, boolean factional) {
        if (this.equals(other)) return new BigNumber("0");

        boolean bothNegative = this.isNegative() && other.isNegative();
        boolean negative = this.compareTo(other) < 0;

        BigNumber firstBuf = delNegative(this);
        BigNumber secondBuf = delNegative(other);
        StringBuilder bufNumber = new StringBuilder();
        if (!bothNegative) {
            if (this.isNegative() ^ other.isNegative()) {
                return new BigNumber(firstBuf.plus(secondBuf), this.isNegative());
            } else {
                int length = Math.max(this.length(), other.length());
                BigNumber buf = firstBuf.copy();
                firstBuf = new BigNumber(maxOf(firstBuf, secondBuf)
                        .appendZeros(length - firstBuf.length(), true), false);
                secondBuf = new BigNumber(minOf(secondBuf, buf).
                        appendZeros(length - secondBuf.length(), true), false);
                int remember = 0;
                for (int i = length; i > 0; i--) {
                    int addedNum = firstBuf.get(i) - secondBuf.get(i) - remember;
                    if (addedNum < 0) {
                        addedNum += 10;
                        remember = 1;
                    } else {
                        remember = 0;
                    }
                    bufNumber.append(addedNum);
                }
            }
        } else {
            secondBuf.minus(firstBuf);
        }

        if (!factional) {
            return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + removeZeros(bufNumber.reverse().toString()));
        } else {
            return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + bufNumber.reverse().toString());
        }

    }

    private BigNumber timesOneDigit(int num) {
        if (num < 0 || num > 9) throw new IllegalArgumentException("The number must be one-digit");
        if (num == 1) return this;
        if (num == 0) return new BigNumber(appendZeros(1, false), false);

        BigNumber answer = new BigNumber("0");
        while (num > 0) {
            answer = answer.plus(this);
            --num;
        }
        return answer;
    }

    /**
     * A method for multiplying two BigNumber
     * (also used in the times () method for BigNumberFraction).
     * Can multiply negative numbers. Uses the optional timesOneDigit method,
     * which takes a single-digit (0-9) number to be multiplied by it.
     * When multiplying by 10, to some extent uses appendZeros to get the result faster.
     *
     * @param other BigNumber for times
     * @return result times
     */

    public BigNumber times(BigNumber other) {
        boolean negative = this.isNegative() ^ other.isNegative();

        Pattern pattern = Pattern.compile("1(0)+");
        if (pattern.matcher(this.toString()).matches() || pattern.matcher(other.toString()).matches()) {
            BigNumber whoMatches = pattern.matcher(this.toString()).matches() ? this : other;
            return new BigNumber(appendZeros(whoMatches.length(), false), negative);
        }

        BigNumber firstBuf = delNegative(this);
        BigNumber secondBuf = delNegative(other);
        BigNumber buf = maxOf(firstBuf, secondBuf);
        secondBuf = minOf(firstBuf, secondBuf);
        firstBuf = buf;

        BigNumber answer = new BigNumber("0");
        int length = secondBuf.length();
        for (int i = length; i > 0; i--) {
            answer = answer.plus(new BigNumber(firstBuf.timesOneDigit(secondBuf.get(i))
                    .appendZeros(length - i, false), false));
        }
        answer.setNegative(negative);
        return answer;
    }

    @Override
    public boolean equals(Object obj) {
        BigNumber object = (BigNumber) obj;
        return this.getClass() == obj.getClass() &&
                this.number.equals(object.number) &&
                this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return (this.isNegative() ? "-" : "") + this.getNumber();
    }

    @Override
    public int compareTo(BigNumber other) {
        boolean bothNegative = this.isNegative() && other.isNegative();
        boolean oneNegative = this.isNegative() ^ other.isNegative();
        if (this.length() != other.length()) {
            if (bothNegative && !oneNegative) {
                return this.length() < other.length() ? 1 : -1;
            } else if (!oneNegative) {
                return this.length() > other.length() ? 1 : -1;
            } else {
                return other.isNegative() ? 1 : -1;
            }
        } else {
            for (int i = 1; i <= this.length(); ++i) {
                if (bothNegative) {
                    return this.get(i) > other.get(i) ? 1 : -1;
                } else {
                    return this.get(i) > other.get(i) ? -1 : 1;
                }
            }
        }
        return 0;
    }
}
