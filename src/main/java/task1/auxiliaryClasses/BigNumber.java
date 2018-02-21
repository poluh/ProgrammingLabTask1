package task1.auxiliaryClasses;

import task1.auxiliaryClasses.Collection.ArrayBigNumber;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.max;

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

    private ArrayBigNumber appendZeros(int quantity, boolean atFirst) {
        String zeros = IntStream.range(0, quantity).mapToObj(i -> "0").collect(Collectors.joining());
        BigNumber buf = new BigNumber("0");
        buf.setNumber(!atFirst ? this.getNumber().concat(zeros) : zeros.concat(this.getNumber()));
        return buf.number;
    }

    public boolean isNegative() {
        return this.negative;
    }

    public void delNegative() {
        if (this.negative) this.negative = false;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public void round(int border) {
        if (border > this.length() - 1) this.setNumber(this.appendZeros(border - this.length() + 1, false));
        ArrayBigNumber numberArr = this.number;

        this.setNumber(this.getNumber().substring(0, border + 1));
        if (numberArr.get(border) >= 5) {
            this.setNumber(this.plus(10 - numberArr.get(border)).getNumber());
        } else {
            numberArr.set(border, 0);
            this.setNumber(numberArr);
        }
        this.setNumber(this.getNumber().substring(0, border - 1));
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
        return this.plus(new BigNumber(String.valueOf(other)), false);
    }

    public BigNumber plus(BigNumber other) {
        return this.plus(other, false);
    }


    public BigNumber plus(BigNumber other, boolean factional) {
        if (this.isNegative() && !other.isNegative()) {
            BigNumber bufThis = this;
            bufThis.delNegative();
            if (bufThis.compareTo(other) > 0) {
                return new BigNumber(maxOf(bufThis, other).minus(minOf(bufThis, other)).number, true);
            } else {
                BigNumber answer = bufThis.minus(other);
                answer.delNegative();
                return answer;
            }
        }
        if (other.isNegative() && !this.isNegative()) {
            BigNumber bufOther = new BigNumber(other.number, false);
            return this.minus(bufOther);
        }
        if (this.isNegative() && other.isNegative()) {
            BigNumber bufThis = this;
            BigNumber bufOther;
            bufOther = other;
            bufThis.delNegative();
            bufOther.delNegative();
            return new BigNumber(bufThis.plus(bufOther).number, true);
        }

        int length = max(this.length(), other.length());
        ArrayBigNumber bufThis;
        ArrayBigNumber bufOther;
        bufThis = this.appendZeros(Math.abs(this.length() - length), !factional);
        bufOther = other.appendZeros(Math.abs(other.length() - length), !factional);

        StringBuilder bufAnswer = new StringBuilder();
        int addedBuf = 0;
        for (int i = length - 1; i >= 0; i--) {
            int addedNum = bufThis.get(i) +
                    bufOther.get(i) + addedBuf;
            if (addedNum > 9) {
                addedNum = addedNum - 10;
                addedBuf = 1;
            } else addedBuf = 0;

            bufAnswer.append(addedNum);
        }
        if (addedBuf == 1) bufAnswer.append(1);
        return new BigNumber(bufAnswer.reverse().toString());
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

        if (this.isNegative() && !other.isNegative()) {
            BigNumber bufThis = this;
            bufThis.delNegative();
            return new BigNumber(other.plus(bufThis).number, true);
        }
        if (other.isNegative() && !this.isNegative()) {
            BigNumber otherBuf = new BigNumber(other.number, false);
            return this.minus(otherBuf);
        }
        if (this.isNegative() && other.isNegative()) {
            BigNumber bufThis = this;
            BigNumber bufOther;
            bufOther = other;
            bufThis.delNegative();
            bufOther.delNegative();
            return bufOther.minus(bufThis);
        }

        if (this.number.equals(other.number)) return new BigNumber("0");

        StringBuilder bufNumber = new StringBuilder();
        ArrayBigNumber minuend;
        ArrayBigNumber subtrahend;
        int length = Integer.max(this.length(), other.length());
        if (!factional) {
            minuend = maxOf(this, other).number;
            subtrahend = minOf(this, other)
                    .appendZeros(Math.abs(minOf(this, other).length() - length), true);
        } else {
            BigNumber isMinuend = new BigNumber(this.appendZeros(length - this.length(), false),
                    this.negative);
            BigNumber isSubtrahend = new BigNumber(other.appendZeros(length - other.length(), false),
                    other.negative);
            minuend = maxOf(isMinuend, isSubtrahend).number;
            subtrahend = minOf(isMinuend, isSubtrahend).number;
        }
        int addedBuf = 0;
        for (int i = minuend.size() - 1; i >= 0; i--) {
            int addedNum = minuend.get(i) - subtrahend.get(i) - addedBuf;
            if (addedNum < 0) {
                addedNum = addedNum + 10;
                addedBuf = 1;
            } else addedBuf = 0;
            bufNumber.append(addedNum);
        }

        if (!factional) {
            return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + removeZeros(bufNumber.reverse().toString()));
        } else {
            return new BigNumber((this.compareTo(other) > 0 ? "" : "-") + bufNumber.reverse().toString());
        }
    }

    private BigNumber timesOneNum(int num) {
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
        if (this.number.equals(new ArrayBigNumber(0)) ||
                other.number.equals(new ArrayBigNumber(0))) return new BigNumber("0");
        if (this.number.equals(new ArrayBigNumber(0)) ||
                other.number.equals(new ArrayBigNumber(0))) {
            return other.number.equals(new ArrayBigNumber(0)) ? this : other;
        }
        if (this.number.equals(new ArrayBigNumber(2))) return other.plus(other);
        if (other.number.equals(new ArrayBigNumber(2))) return this.plus(this);

        BigNumber bufAnswer = new BigNumber("0");
        boolean negative = this.isNegative() ^ other.isNegative();
        BigNumber bufThis = this;
        BigNumber bufOther;
        bufOther = other;
        bufThis.delNegative();
        bufOther.delNegative();

        Pattern pattern = Pattern.compile("(\\[1, (0, )*0])|(10+)");
        BigNumber big;
        BigNumber zeros;
        if (pattern.matcher(bufOther.toString()).matches() || pattern.matcher(bufThis.toString()).matches()) {
            big = pattern.matcher(bufOther.toString()).matches() ? bufThis : bufOther;
            zeros = big == bufThis ? bufOther : bufThis;
            bufAnswer = new BigNumber(big.appendZeros(zeros.length() - 1, false), false);
            if (negative) bufAnswer.negative = true;
            return bufAnswer;
        }

        BigNumber bigNumber = maxOf(bufThis, bufOther);
        ArrayBigNumber otherBuf = minOf(bufThis, bufOther).number;

        for (int i = otherBuf.size() - 1; i >= 0; i--) {
            BigNumber resultTimes = bigNumber.timesOneNum(otherBuf.get(i));
            resultTimes.setNumber(resultTimes.appendZeros(otherBuf.size() - i - 1, false));
            bufAnswer = bufAnswer.plus(resultTimes);
        }
        if (negative) bufAnswer.negative = true;
        return bufAnswer;
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
            for (int i = 0; i < this.length(); ++i) {
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
