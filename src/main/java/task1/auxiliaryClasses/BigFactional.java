package task1.auxiliaryClasses;

import java.util.logging.Level;
import java.util.logging.Logger;

import static task1.auxiliaryClasses.BigNumber.maxOf;

public class BigFactional {
    private static Logger log = Logger.getLogger(BigFactional.class.getName());

    private BigNumber wholePart;
    private BigNumber fraction;

    BigFactional(String wholePart, String fraction) {
        log.info("Create new BigFraction");
        if (wholePart.matches("-?\\d+") && fraction.matches("-?\\d+")) {
            this.wholePart = new BigNumber(wholePart);
            this.fraction = new BigNumber(fraction);
            log.info("Number=" + wholePart + "." + fraction);
        } else {
            log.log(Level.SEVERE, "Exception: Invalid format number.");
            throw new NumberFormatException("Invalid format.");
        }
    }

    BigFactional(BigNumber wholePart, BigNumber fraction) {
        this.wholePart = wholePart;
        this.fraction = fraction;
        log.info("Number=" + wholePart + "." + fraction);
    }

    public BigFactional(String number) {
        if (number.matches("-?\\d+\\.\\d+")) {
            String[] parts = number.split("\\.");
            this.wholePart = new BigNumber(parts[0]);
            this.fraction = new BigNumber(parts[1]);
            log.info("Number=" + this.wholePart + "." + this.fraction);
        } else {
            log.log(Level.SEVERE, "Exception: Invalid format number.");
            throw new NumberFormatException("Invalid format.");
        }
    }

    public boolean isNegative() {
        log.info("Check of negative num=" + this.toString());
        return this.wholePart.isNegative();
    }

    private void setNegative(boolean negative) {
        log.info("Change negative.");
        this.wholePart.setNegative(negative);
    }

    private BigFactional cut(int border) {
        if (border == 0) {
            return this;
        } else {
            int length = this.fraction.length();
            this.fraction.setNumber(this.fraction.toString().substring(0, border < length ? border : length - 1));
            return this;
        }
    }

    public BigFactional plus(BigFactional other) {
        return this.plus(other, 0);
    }

    public BigFactional plus(BigFactional other, int border) {
        if (this.wholePart.isNegative() && !other.wholePart.isNegative()) {
            log.info("Transformation of expression=" + other.toString() + "-" + this.toString());
            return other.minus(this, border);
        } else if (!this.wholePart.isNegative() && other.wholePart.isNegative()) {
            log.info("Transformation of expression=" + this.toString() + "-" + other.toString());
            return this.minus(other, border);
        }

        BigNumber fraction = this.fraction.plus(other.fraction, true);
        BigNumber wholePart;
        if (fraction.length() > maxOf(this.fraction, other.fraction).length()) {
            wholePart = this.wholePart.plus(other.wholePart).plus(1);
            fraction.setNumber(fraction.toString().substring(1));
        } else wholePart = this.wholePart.plus(other.wholePart);

        return new BigFactional(wholePart, fraction).cut(border);
    }

    public BigFactional minus(BigFactional other) {
        return this.minus(other, 0);
    }

    public BigFactional minus(BigFactional other, int border) {
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

        return new BigFactional(wholePart, fraction).cut(border);
    }

    public BigFactional times(BigFactional other) {
        return this.times(other, 0);
    }

    public BigFactional times(BigFactional other, int border) {
        log.info(this.toString() + " times " + other.toString());
        int tailLength = this.fraction.length() + other.fraction.length();
        BigNumber imaginaryThis = new BigNumber(this.wholePart.toString() + this.fraction.toString());
        BigNumber imaginaryOther = new BigNumber(other.wholePart.toString() + other.fraction.toString());
        BigNumber times = imaginaryThis.times(imaginaryOther);
        byte[] timesBytes = times.getBytes();
        log.info("Number without dot=" + times.toString());
        StringBuilder fraction = new StringBuilder();
        StringBuilder wholePart = new StringBuilder();
        for (int i = times.length() - 1; i >= 0; i--) {
            if (i > tailLength || (times.isNegative() && i >= tailLength)) {
                fraction.append(timesBytes[i]);
            } else {
                wholePart.append(timesBytes[i]);
            }
        }
        BigFactional answer =
                new BigFactional(wholePart.reverse().toString(), fraction.reverse().toString()).cut(border);
        answer.setNegative(times.isNegative());
        log.info("Result=" + answer.toString());
        return answer;
    }

    @Override
    public String toString() {
        return wholePart + "." + fraction;
    }

    @Override
    public boolean equals(Object obj) {
        return this.fraction.equals(((BigFactional) obj).fraction) && this.wholePart.equals(((BigFactional) obj).wholePart);
    }
}
