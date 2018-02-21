package task1.auxiliaryClasses;

import task1.auxiliaryClasses.Collection.ArrayBigNumber;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static task1.auxiliaryClasses.BigNumber.maxOf;

public class BigFractional {
    private static Logger log = Logger.getLogger(BigFractional.class.getName());

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
            throw new NumberFormatException("Invalid format.");
        }
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
        if (fraction.length() > maxOf(this.fraction, other.fraction).length()) {
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
        BigNumber fraction;
        if (this.isNegative() && other.isNegative()) {
            fraction = this.fraction.minus(other.fraction, true);
        } else if (this.isNegative() || other.isNegative()) {
            fraction = this.fraction.plus(other.fraction, true);
        } else {
            fraction = this.fraction.minus(other.fraction, true);
        }
        BigNumber wholePart = this.wholePart.minus(other.wholePart);
        fraction.delNegative();

        return new BigFractional(wholePart, fraction).round(border);
    }

    public BigFractional times(BigFractional other) {
        return this.times(other, 0);
    }

    public BigFractional times(BigFractional other, int border) {
        String[] strings = {this.toString(), other.toString()};
        log.log(Level.INFO, "{0} times {1}", strings);
        int tailLength = this.fraction.length() + other.fraction.length();
        BigNumber imaginaryThis = new BigNumber(this.wholePart.toString() + this.fraction.toString());
        BigNumber imaginaryOther = new BigNumber(other.wholePart.toString() + other.fraction.toString());
        BigNumber times = imaginaryThis.times(imaginaryOther);
        ArrayBigNumber timesArray = times.getArray();
        log.log(Level.FINE, "Number without dot={0}", times.toString());
        StringBuilder fraction = new StringBuilder();
        StringBuilder wholePart = new StringBuilder();

        for (int i = times.length() - 1; i >= 0; i--) {
            if (i > tailLength || (times.isNegative() && i >= tailLength)) {
                fraction.append(timesArray.get(i));
            } else {
                wholePart.append(timesArray.get(i));
            }
        }
        BigFractional answer =
                new BigFractional(wholePart.reverse().toString(), fraction.reverse().toString()).round(border);
        answer.setNegative(times.isNegative());
        log.log(Level.INFO,"Result={0}", answer.toString());
        return answer;
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
}
