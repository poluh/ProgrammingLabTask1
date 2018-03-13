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

public class BigNumber implements Comparable<BigNumber>, BigUnifying<BigNumber> {

    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    // Main storage for number
    private ArrayBigNumber number;
    // Negative or positive number
    private boolean negative;

    private int maxBlockDigits;

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
            create(new ArrayBigNumber(negative ? number.substring(1) : number), negative);
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
    public BigNumber(ArrayBigNumber number, boolean negative) {
        create(number, negative);
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

    public static BigNumber maxOf(BigNumber first, BigNumber second) {
        return first.compareTo(second) > 0 ? first : second;
    }

    public static BigNumber minOf(BigNumber first, BigNumber second) {
        return first.compareTo(second) > 0 ? second : first;
    }

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
            this.maxBlockDigits = this.getArray().getMaxBlockDigits();
            log.log(Level.FINE, "Create new BigNumber={0}", this);
        } catch (NumberFormatException ex) {
            log.log(Level.SEVERE, "Exception: Invalid format number({0}).", number);
        }
    }

    @Override
    public BigNumber copy() {
        BigNumber answer = new BigNumber("0");
        answer.negative = this.negative;
        answer.number = this.number;
        return answer;
    }

    public String getNumber() {
        return this.number.toString();
    }

    public void setNumber(String number) {
        this.setNumber(new ArrayBigNumber(number));
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

    public int columnBlocks() {
        return this.number.columnBlocks();
    }

    public int length() {
        return this.number.length();
    }

    /**
     * The method of adding zeros before or after the number.
     * Used to initialize the shift of a number relatively larger in length,
     * as well as multiplication by 10 to some extent. Returns new ArrayBigNumber with zeros.
     *
     * @param quantity Number of zeros to add
     * @param toLeft   Add zeros to the left or to the right of the number
     * @return new ArrayBigNumber
     */

    public ArrayBigNumber appendZeros(int quantity, boolean toLeft) {
        String zeros = IntStream.range(0, quantity).mapToObj(i -> "0").collect(Collectors.joining());
        BigNumber buf = new BigNumber("0");
        buf.setNumber(!toLeft ? this.getNumber().concat(zeros) : zeros.concat(this.getNumber()));
        return buf.number;
    }

    @Override
    public boolean isNegative() {
        return this.negative;
    }

    @Override
    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public void delNegative() {
        this.negative = false;
    }

    @Override
    public BigNumber round(int border) {
        int roundNumber = (int) (this.get(border / this.getArray().getLenOneNum()) /
                Math.pow(10, this.length() - border - 1) % 10);
        return roundNumber >= 5 ? this.plus(10 - roundNumber) : this.minus(roundNumber);
    }

    public BigNumber plus(int other) {
        return this.plus(new BigNumber(String.valueOf(other)));
    }

    @Override
    public BigNumber plus(BigNumber other) {
        return this.plus(other, false);
    }

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
            int columnBlocks = Math.max(this.columnBlocks(), other.columnBlocks());
            firstBuf = new BigNumber(this.appendZeros(Math.abs(this.length() - length), !fraction),
                    false);
            secondBuf = new BigNumber(other.appendZeros(Math.abs(other.length() - length), !fraction),
                    false);
            log.log(Level.INFO, "First number for sum = {0}", firstBuf);
            log.log(Level.INFO, "Second number for sum = {0}", secondBuf);
            ArrayBigNumber numberBuf = new ArrayBigNumber();
            int remember = 0;
            for (int i = columnBlocks - 1; i >= 0; i--) {
                int addedNum = firstBuf.get(i) + secondBuf.get(i) + remember;
                if (addedNum > maxBlockDigits) {
                    addedNum -= maxBlockDigits;
                    remember = 1;
                } else {
                    remember = 0;
                }
                numberBuf.add(addedNum);
            }
            numberBuf.add(remember == 1 ? "1" : "");
            numberBuf.setLengthLastBlock(Math.min(this.getArray().getLengthLastBlock(), other.getArray().getLengthLastBlock()));
            numberBuf.reverse();
            return new BigNumber(fraction ? numberBuf : new ArrayBigNumber(removeZeros(numberBuf.toString())),
                    bothNegative);
        }
    }

    @Override
    public void inc() {
        this.lastBlockOperation(true);
    }

    @Override
    public void dec() {
        this.lastBlockOperation(false);
    }

    private void lastBlockOperation(boolean operator) {
        ArrayBigNumber thisArray = this.getArray();
        int lastBlock = thisArray.get(thisArray.columnBlocks() - 1);
        if (lastBlock < this.maxBlockDigits - (operator ? 1 : 0)) {
            thisArray.set(thisArray.columnBlocks() - 1, lastBlock + (operator ? 1 : -1));
        } else {
            if (operator) {
                this.plus(1);
            } else {
                this.minus(1);
            }
        }
    }

    private String removeZeros(String number) {
        return number.replaceFirst("^(0)+", "");
    }

    public BigNumber minus(int other) {
        return minus(new BigNumber(String.valueOf(other)), false);
    }

    @Override
    public BigNumber minus(BigNumber other) {
        return minus(other, false);
    }

    public BigNumber minus(BigNumber other, boolean factional) {
        if (this.equals(other)) return new BigNumber("0");

        boolean bothNegative = this.isNegative() && other.isNegative();

        BigNumber firstBuf = delNegative(this);
        BigNumber secondBuf = delNegative(other);
        ArrayBigNumber bufNumber = new ArrayBigNumber();
        if (!bothNegative) {
            if (this.isNegative() ^ other.isNegative()) {
                return new BigNumber(firstBuf.plus(secondBuf), this.isNegative());
            } else {
                int length = Math.max(this.length(), other.length());
                int columnBlocks = Math.max(this.columnBlocks(), other.columnBlocks());
                BigNumber buf = firstBuf.copy();
                firstBuf = new BigNumber(maxOf(firstBuf, secondBuf)
                        .appendZeros(length - firstBuf.length(), !factional), false);
                secondBuf = new BigNumber(minOf(secondBuf, buf)
                        .appendZeros(length - secondBuf.length(), !factional), false);
                int remember = 0;
                for (int i = columnBlocks - 1; i >= 0; i--) {
                    int addedNum = firstBuf.get(i) - secondBuf.get(i) - remember;
                    if (addedNum < 0) {
                        addedNum += this.maxBlockDigits;
                        remember = 1;
                    } else {
                        remember = 0;
                    }
                    bufNumber.add(addedNum);
                }
            }
        } else {
            return secondBuf.minus(firstBuf);
        }
        bufNumber.reverse();
        log.log(Level.FINE, "Answer number = {0}", bufNumber.toString());
        if (!factional) {
            return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + removeZeros(bufNumber.toString()));
        } else {
            return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + bufNumber.toString());
        }

    }

    @Override
    public BigNumber times(BigNumber other) {
        boolean negative = this.isNegative() ^ other.isNegative();

        Pattern pattern = Pattern.compile("-?1(0)*");
        if (pattern.matcher(this.toString()).matches() || pattern.matcher(other.toString()).matches()) {
            BigNumber whoMatches = pattern.matcher(this.toString()).matches() ? this : other;
            return new BigNumber(appendZeros(whoMatches.length() - 1, false), negative);
        }

        BigNumber thisBuf = this.copy();
        BigNumber otherBuf = other.copy();

        int shift = 0;
        int answerLength = thisBuf.columnBlocks() + otherBuf.columnBlocks() - 1;
        long[][] intermediatesCollection =
                new long[otherBuf.columnBlocks()][thisBuf.columnBlocks() + otherBuf.columnBlocks()];
        for (int i = otherBuf.columnBlocks() - 1; i >= 0; i--) {
            for (int j = thisBuf.columnBlocks() - 1; j >= 0; j--) {
                long intermediate = (long) thisBuf.get(j) * (long) otherBuf.get(i);
                intermediatesCollection[otherBuf.columnBlocks() - i - 1][answerLength - j - shift] = intermediate;
            }
            shift++;
        }

        int maxBlockDigits = this.maxBlockDigits;
        int addedBuf = 0;
        ArrayBigNumber answer = new ArrayBigNumber();
        for (int internalIndex = answerLength; internalIndex >= 0; internalIndex--) {
            long buffer = 0L;
            for (int externalIndex = otherBuf.columnBlocks() - 1; externalIndex >= 0; externalIndex--) {
                buffer = Math.addExact(buffer, intermediatesCollection[externalIndex][internalIndex]);
            }
            buffer += addedBuf;
            if (buffer >= maxBlockDigits) {
                addedBuf = (int) (buffer / (maxBlockDigits));
                buffer %= maxBlockDigits;
            } else {
                addedBuf = 0;
            }
            answer.add((int) buffer);
        }
        answer.reverse();
        answer = new ArrayBigNumber(removeZeros(answer.toString()));
        return new BigNumber(answer, this.negative ^ other.negative);
    }

    public BigNumber division(BigNumber other) {
        if (other.equals(new BigNumber("0"))) throw new ArithmeticException("Division by zero. Just do not it. Okay?");
        if (other.compareTo(this) > 0) return new BigNumber("0");
        BigNumber answer = new BigNumber("0");
        BigNumber otherBuf = other.copy();
        int columnBlocks = this.columnBlocks();
        if (other.columnBlocks() == 1) {
            ArrayBigNumber arrayBuf = new ArrayBigNumber();
            for (int i = 0; i < columnBlocks; i++) {
                arrayBuf.add(this.get(i) >> other.toInt() - 1);
            }
            return new BigNumber(arrayBuf, false);
        }
        // TODO for large number
        while (otherBuf.compareTo(other) > 0) {
            otherBuf.minus(other);
            answer.inc();
        }
        return answer.times(other);

    }

    @Override
    public boolean equals(Object obj) {
        BigNumber object = (BigNumber) obj;
        return this.getClass() == obj.getClass() &&
                this.number.equals(object.number) &&
                this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.getNumber().hashCode() * this.getArray().getStorage().hashCode();
    }

    @Override
    public String toString() {
        return (this.isNegative() ? "-" : "") + this.getNumber();
    }

    public int toInt() {
        if (this.columnBlocks() == 1) return this.get(0);
        throw new StackOverflowError();
    }

    @Override
    public int compareTo(BigNumber other) {
        boolean bothNegative = this.isNegative() && other.isNegative();
        if (this.isNegative() ^ other.isNegative()) return this.isNegative() ? -1 : 1;
        if (this.length() != other.length()) {
            if (bothNegative) {
                return this.length() < other.length() ? 1 : -1;
            } else {
                return this.length() > other.length() ? 1 : -1;
            }
        } else {
            for (int i = 0; i < this.columnBlocks(); ++i) {
                if (this.get(i) != other.get(i)) {
                    if (bothNegative) {
                        return this.get(i) > other.get(i) ? -1 : 1;
                    } else {
                        return this.get(i) > other.get(i) ? 1 : -1;
                    }
                }
            }
        }
        return 0;
    }
}
