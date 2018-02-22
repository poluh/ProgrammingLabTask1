package task1.auxiliaryClasses;

import task1.auxiliaryClasses.Collection.ArrayBigNumber;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BigNumber implements Comparable<BigNumber> {

    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    private ArrayBigNumber number;
    private boolean negative;

    private void create(ArrayBigNumber number, boolean negative) {
        try {
            this.number = number;
            this.negative = negative;
            log.log(Level.INFO, "Create new BigNumber={0}", this);
        } catch (NumberFormatException ex) {
            log.log(Level.SEVERE, "Exception: Invalid format number({0}).", number);
        }
    }

    public BigNumber(String number) {
        if (number.matches("-?\\d+")) {
            boolean negative = number.charAt(0) == '-';
            ArrayBigNumber numbers = new ArrayBigNumber();
            int offset = negative ? 1 : 0;
            for (int i = offset; i < number.length(); i++) {
                numbers.add(number.charAt(i));
            }
            create(numbers, negative);
        } else {
            throw new NumberFormatException("NUMBER = " + number);
        }
    }

    private BigNumber(ArrayBigNumber number, boolean negative) {
        create(number, negative);
    }

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

    public void round(int border) {
        int roundNumber = this.get(border);
        BigNumber difference = new BigNumber(String.valueOf((int) Math.pow(10 - roundNumber, this.length() - border - 1)));
        if (roundNumber >= 5) {
            this.plus(difference);
        } else this.minus(difference);
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

        boolean bothNegative = this.isNegative() && other.isNegative();
        boolean oneNegative = this.isNegative() ^ other.isNegative();
        BigNumber firstBuf;
        BigNumber secondBuf;
        if (oneNegative) {
            firstBuf = delNegative(this);
            secondBuf = delNegative(other);
            if (firstBuf.equals(secondBuf)) return new BigNumber("0");
            return maxOf(firstBuf, secondBuf).minus(minOf(firstBuf, secondBuf));
        } else {
            int length = Math.max(this.length(), other.length());
            firstBuf = new BigNumber(this.appendZeros(Math.abs(this.length() - length), true),
                    false);
            secondBuf = new BigNumber(other.appendZeros(Math.abs(other.length() - length), true),
                    false);
            log.log(Level.INFO, "First number for sum = {0}", firstBuf);
            log.log(Level.INFO, "Second number for sum = {0}", secondBuf);

            StringBuilder numberBuf = new StringBuilder();
            int remember = 0;
            for (int i = length; i > 0; i--) {
                int addedNum = firstBuf.get(i) + secondBuf.get(i) + remember;

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

    public BigNumber minus(BigNumber other, boolean factional) {
        boolean bothNegative = this.isNegative() && other.isNegative();
        boolean oneNegative = this.isNegative() ^ other.isNegative();
        BigNumber firstBuf = this.copy();
        BigNumber secondBuf = other.copy();

        if (oneNegative) {
            firstBuf.setNegative(true);
            secondBuf.setNegative(true);
            return firstBuf.plus(secondBuf);
        } else if (!bothNegative) {

            if (this.equals(other)) return new BigNumber("0");

            int length = Math.max(this.length(), other.length());
            firstBuf = new BigNumber(this.appendZeros(Math.abs(this.length() - length), true),
                    false);
            secondBuf = new BigNumber(other.appendZeros(Math.abs(other.length() - length), true),
                    false);
            StringBuilder bufNumber = new StringBuilder();
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

            if (!factional) {
                return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + removeZeros(bufNumber.reverse().toString()));
            } else {
                return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + bufNumber.reverse().toString());
            }
        } else {
            firstBuf = delNegative(this);
            secondBuf = delNegative(other);
            return secondBuf.minus(firstBuf);
        }
    }

    public BigNumber timesOneNum(int num) {
        if (num == 1) return this;
        if (num == 0) {
            return new BigNumber(appendZeros(1, false), false);
        }

        BigNumber answer = new BigNumber("0");
        while (num > 0) {
            answer = answer.plus(this);
            --num;
        }
        return answer;
    }

    public BigNumber times(BigNumber other) {

        return other;
    }

    @Override
    public boolean equals(Object obj) {
        BigNumber object = (BigNumber) obj;
        return this.getClass() == obj.getClass() && this.number.equals(object.number);
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
