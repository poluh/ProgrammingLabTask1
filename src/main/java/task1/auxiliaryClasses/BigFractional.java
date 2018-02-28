package task1.auxiliaryClasses;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static task1.auxiliaryClasses.BigNumber.maxOf;

/**
 * This class is based on the magnificent BigNumber.
 * The plus and minus methods (and to some extent also the times)
 * use the existing methods in BigNumber. For details go there.
 * This method stores by BigNumber on each side of the point,
 * can correspondingly store 2,147,483,647 characters on each side,
 * ensuring accuracy of calculations just up to 2,147,483,647 decimal places.
 */

public class BigFractional {
    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    /**
     * My code is self-documenting.
     */

    private BigNumber wholePart;
    private BigNumber fraction;

    private void create(BigNumber wholePart, BigNumber fraction) {
        try {
            this.wholePart = wholePart;
            this.fraction = fraction;
            log.log(Level.INFO, "Create new BigFractional = {0}", this);
        } catch (NumberFormatException ex) {
            String[] strings = {wholePart.toString(), fraction.toString()};
            log.log(Level.SEVERE, "Exception: Invalid format number({0}.{1}).", strings);
        }
    }

    public BigFractional(BigNumber wholePart, BigNumber fraction) {
        create(wholePart, fraction);
    }

    public BigFractional(String wholePart, String fraction) {
        Pattern pattern = Pattern.compile("-?\\d+");
        if (pattern.matcher(wholePart).matches() && pattern.matcher(fraction).matches()) {
            create(new BigNumber(wholePart), new BigNumber(fraction));
        } else {
            throw new NumberFormatException("Invalid format " + wholePart + " " + fraction);
        }
    }

    public BigFractional(String number) {
        if (number.matches("-?\\d+\\.\\d+")) {
            String[] parts = number.split("\\.");
            create(new BigNumber(parts[0]), new BigNumber(parts[1]));
        } else if (number.matches("\\d+")) {
            create(new BigNumber(number), new BigNumber("0"));
        } else {
            throw new NumberFormatException("Invalid format.");
        }
    }

    public BigFractional copy() {
        return new BigFractional(this.toString());
    }

    public boolean isNegative() {
        return this.wholePart.isNegative();
    }

    private void setNegative(boolean negative) {
        log.info("Change negative.");
        this.wholePart.setNegative(negative);
    }

    public BigFractional round(int border) {
        if (border == 0 || border >= this.fraction.length()) {
            return this;
        } else {
            this.fraction.round(border);
            return this;
        }
    }

    public BigFractional plus(BigFractional other) {
        return this.plus(other, 0);
    }

    public BigFractional plus(BigFractional other, int border) {
        if (this.wholePart.isNegative() || other.wholePart.isNegative()) {
            if (this.wholePart.isNegative() && !other.wholePart.isNegative()) {
                String[] strings = {other.toString(), this.toString()};
                log.log(Level.INFO, "Transformation of expression= {0} - {1}", strings);
                return other.minus(this, border);
            } else if (!this.wholePart.isNegative() && other.wholePart.isNegative()) {
                String[] strings = {this.toString(), other.toString()};
                log.log(Level.INFO, "Transformation of expression= {0} - {1}", strings);
                return this.minus(other, border);
            }
        }

        BigNumber fraction = this.fraction.plus(other.fraction, true);
        BigNumber wholePart;
        if (fraction.toString().length() > Math.max(this.fraction.length(), other.fraction.length())) {
            wholePart = this.wholePart.plus(other.wholePart).plus(1);
            fraction.setNumber(fraction.toString().substring(1));
        } else {
            wholePart = this.wholePart.plus(other.wholePart);
        }

        return new BigFractional(wholePart, fraction).round(border);
    }

    public BigFractional minus(BigFractional other) {
        return this.minus(other, 0);
    }

    public BigFractional minus(BigFractional other, int border) {
        BigNumber fraction = (this.isNegative() ^ other.isNegative()) ?
                this.fraction.plus(other.fraction) : this.fraction.minus(other.fraction, true);

        BigNumber wholePart = this.wholePart.minus(other.wholePart);
        fraction.delNegative();

        return new BigFractional(wholePart, fraction).round(border);
    }

    public BigFractional times(BigFractional other) {
        return this.times(other, 0);
    }

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
        log.log(Level.INFO, "Number without dot={0}", timesStr);

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
        log.log(Level.INFO, "Result={0}", answer.toString());
        return answer;
    }

    public BigNumber toBigNumber() {
        return new BigNumber(this.toString().replace(".", ""));
    }

    @Override
    public String toString() {
        return wholePart + "." + fraction;
    }

    @Override
    public boolean equals(Object obj) {
        return this.fraction.equals(((BigFractional) obj).fraction) &&
                this.wholePart.equals(((BigFractional) obj).wholePart);
    }

    @Override
    public int hashCode() {
        return this.wholePart.hashCode() + this.fraction.hashCode() * this.toString().hashCode();
    }
}
