package task1.auxiliaryClasses;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * This class is based on the magnificent BigNumber.
 * The plus and minus methods (and to some extent also the times)
 * use the existing methods in BigNumber. For details go there.
 * This method stores by BigNumber on each side of the point,
 * can correspondingly store 2,147,483,647 characters on each side,
 * ensuring accuracy of calculations just up to 2,147,483,647 decimal places.
 */

public class BigFractional implements Comparable<BigFractional>, BigUnifying<BigFractional> {
    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    /**
     * My code is self-documenting.
     * <p>
     * (Joke, lol. Check BigUnifying)
     * <p>
     * All non-unique methods are commented on there.
     * Here are designers and unique methods.
     */

    private BigNumber whole;
    private BigNumber fraction;

    public BigFractional(BigNumber whole, BigNumber fraction) {
        create(whole, fraction);
    }

    public BigFractional(String whole, String fraction) {
        Pattern pattern = Pattern.compile("-?\\d+");
        if (pattern.matcher(whole).matches() && pattern.matcher(fraction).matches()) {
            create(new BigNumber(whole), new BigNumber(fraction));
        } else {
            throw new NumberFormatException("Invalid format " + whole + " " + fraction);
        }
    }

    public BigFractional(String number) {
        if (number.matches("-?\\d+\\.\\d+")) {
            String[] parts = number.split("\\.");
            create(new BigNumber(parts[0]), new BigNumber(parts[1]));
        } else if (number.matches("\\d+")) {
            create(new BigNumber(number), new BigNumber("0"));
        } else {
            throw new NumberFormatException("Invalid format");
        }
    }

    /**
     * A special method is the creator used by the constructors.
     * Gets on the input two BigNumber, integer and fractional parts respectively.
     *
     * @param whole    First part
     * @param fraction Second part
     */
    private void create(BigNumber whole, BigNumber fraction) {
        try {
            this.whole = whole;
            this.fraction = fraction;
            log.log(Level.INFO, "Create new BigFractional = {0}", this);
        } catch (NumberFormatException ex) {
            String[] strings = {whole.toString(), fraction.toString()};
            log.log(Level.SEVERE, "Exception: Invalid format number({0}.{1})", strings);
        }
    }

    @Override
    public BigFractional copy() {
        return new BigFractional(this.toString());
    }

    @Override
    public boolean isNegative() {
        return this.whole.isNegative();
    }

    @Override
    public void setNegative(boolean negative) {
        log.fine("Change negative");
        this.whole.setNegative(negative);
    }

    @Override
    public BigFractional round(int border) {
        if (border == 0 || border >= this.fraction.length()) {
            return this;
        } else {
            return new BigFractional(this.whole, this.fraction.round(border));
        }
    }

    @Override
    public BigFractional plus(BigFractional other) {
        return this.plus(other, 0);
    }

    /**
     * This method has an additional parameter - border.
     * The value to which the result should be rounded.
     *
     * @param other  BigFraction for sum
     * @param border Accuracy rounding
     * @return new BigFraction
     */
    public BigFractional plus(BigFractional other, int border) {
        if (this.whole.isNegative() || other.whole.isNegative()) {
            if (this.whole.isNegative() && !other.whole.isNegative()) {
                String[] strings = {other.toString(), this.toString()};
                log.log(Level.INFO, "Transformation of expression = {0} - {1}", strings);
                BigFractional thisBuf = this.copy();
                thisBuf.whole.delNegative();
                return other.minus(thisBuf, border);
            } else if (!this.whole.isNegative() && other.whole.isNegative()) {
                String[] strings = {this.toString(), other.toString()};
                log.log(Level.INFO, "Transformation of expression = {0} - {1}", strings);
                return this.minus(other, border);
            }
        }

        BigNumber fraction = this.fraction.plus(other.fraction, true);
        BigNumber wholePart;
        if (fraction.toString().length() > Math.max(this.fraction.length(), other.fraction.length())) {
            wholePart = this.whole.plus(other.whole).plus(1);
            fraction.setNumber(fraction.toString().substring(1));
        } else {
            wholePart = this.whole.plus(other.whole);
        }

        return new BigFractional(wholePart, fraction).round(border);
    }

    @Override
    public void inc() {
        this.whole.inc();
    }

    @Override
    public void dec() {
        this.whole.dec();
    }

    @Override
    public BigFractional minus(BigFractional other) {
        return this.minus(other, 0);
    }

    /**
     * This method has an additional parameter - border.
     * The value to which the result should be rounded.
     *
     * @param other  BigFraction for subtraction
     * @param border accuracy rounding
     * @return new BigFraction
     */
    public BigFractional minus(BigFractional other, int border) {
        int length = Math.max(this.fraction.length(), other.fraction.length());
        BigNumber firstBuf = new BigNumber(this.copy().fraction
                .appendZeros(length - this.fraction.length(), false), false);
        BigNumber secondBuf = new BigNumber(other.copy().fraction
                .appendZeros(length - other.fraction.length(), false), false);
        BigNumber fraction = (this.isNegative() ^ other.isNegative()) ?
                this.fraction.plus(other.fraction) : firstBuf.minus(secondBuf, true);
        // Alignment relative to the largest fractional part

        BigNumber wholePart = this.whole.minus(other.whole);
        fraction.delNegative();

        return new BigFractional(wholePart, fraction).round(border);
    }

    @Override
    public BigFractional times(BigFractional other) {
        return this.times(other, 0);
    }

    /**
     * This method has an additional parameter - border.
     * The value to which the result should be rounded.
     *
     * @param other  BigFraction for times
     * @param border accuracy rounding
     * @return new BigFraction
     */
    public BigFractional times(BigFractional other, int border) {

        Pattern pattern = Pattern.compile("-?1\\.0+");
        if (pattern.matcher(this.toString()).matches() ^ pattern.matcher(other.toString()).matches()) {
            BigFractional buf = pattern.matcher(this.toString()).matches() ? other : this;
            BigFractional matches = this.equals(buf) ? other : this;
            boolean negative = matches.isNegative();
            return new BigFractional((negative ? "-" : "") + buf.toString());
        }

        String[] strings = {this.toString(), other.toString()};
        log.log(Level.INFO, "{0} times {1}", strings);

        BigNumber imaginaryThis = this.toBigNumber();
        BigNumber imaginaryOther = other.toBigNumber();
        BigNumber times = imaginaryThis.times(imaginaryOther);

        String timesStr = times.toString();
        log.log(Level.INFO, "Number without dot = {0}", timesStr);

        int tailLength = times.length() - (this.fraction.length() + other.fraction.length() + 1);

        StringBuilder fraction = new StringBuilder();
        StringBuilder wholePart = new StringBuilder();

        for (int i = 0; i < timesStr.length(); i++) {
            if (i > tailLength || (times.isNegative() && i >= tailLength)) {
                fraction.append(timesStr.charAt(i));
            } else {
                wholePart.append(timesStr.charAt(i));
            }
        }
        BigFractional answer =
                new BigFractional(wholePart.toString(), fraction.toString()).round(border);
        answer.setNegative(times.isNegative());
        log.log(Level.INFO, "Result = {0}", answer.toString());
        return answer;
    }

    /**
     * Сonverts a fractional number into an ordinary number,
     * discarding a point.
     * Example: 123.456 => 123456
     *
     * @return new BigNumber
     */
    public BigNumber toBigNumber() {
        return new BigNumber(this.toString().replace(".", ""));
    }

    @Override
    public String toString() {
        return whole + "." + fraction;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() &&
                this.fraction.equals(((BigFractional) obj).fraction) &&
                this.whole.equals(((BigFractional) obj).whole);
    }

    @Override
    public int hashCode() {
        return this.whole.hashCode() + this.fraction.hashCode();
    }


    @Override
    public int compareTo(BigFractional other) {
        int firstCompare = this.whole.compareTo(other.whole);
        return firstCompare == 0 ? this.fraction.compareTo(other.fraction) : firstCompare;

    }
}
