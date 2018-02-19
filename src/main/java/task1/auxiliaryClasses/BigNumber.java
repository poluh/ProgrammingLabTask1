package task1.auxiliaryClasses;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class BigNumber {

    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    private byte[] number;
    private boolean negative;

    public BigNumber(String number) {
        if (number.matches("-?\\d+")) {
            log.info("Create new BigNumber");
            this.negative = number.toCharArray()[0] == '-';
            log.info(negative ? "Number is negative" : "Number is not negative");
            char[] buf = number.toCharArray();
            this.number = new byte[number.length() - (this.negative ? 1 : 0)];
            int byteIndex = 0;
            for (int i = this.negative ? 1  : 0; i < number.length(); i++) {
                this.number[byteIndex] = Byte.parseByte(String.valueOf(buf[i]));
                byteIndex++;
            }
        } else {
            log.log(Level.SEVERE, "Unknown format number {0}", number);
            throw new NumberFormatException();
        }
    }

    private BigNumber(byte[] number, boolean negative) {
        log.info("Create new BigNumber");
        this.number = number;
        this.negative = negative;
    }

    private BigNumber() {
    }

    public String getNumber() {
        StringBuilder answer = new StringBuilder();
        for (byte element : this.number) {
            answer.append(element);
        }
        return answer.toString();
    }

    public byte[] getBytes() {
        return number;
    }

    public void setNumber(byte[] number) {
        this.number = number;
    }

    public void setNumber(String number) {
        char[] charsNum = number.toCharArray();
        if (charsNum[0] == '-') this.negative = true;
        byte[] bytesNum = new byte[charsNum.length - (this.negative ? 1 : 0)];
        for (int i = 0; i < charsNum.length; i++) {
            bytesNum[i] = Byte.parseByte(String.valueOf(charsNum[i]));
        }
        this.setNumber(bytesNum);
    }

    public int length() {
        return this.number.length;
    }

    private byte[] appendZero(int column, boolean beginning) {
        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < column; i++) {
            zeros.append("0");
        }
        char[] buf;
        if (beginning) {
            buf = (zeros.toString() + this.getNumber()).toCharArray();
        } else buf =  (this.getNumber() + zeros.toString()).toCharArray();
        byte[] answer = new byte[buf.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = Byte.parseByte(String.valueOf(buf[i]));
        }

        return answer;
    }

    public boolean isGreater(BigNumber other) {
        if ((this.isNegative() && !other.isNegative()) || (!this.isNegative() && other.isNegative())) {
            return ((this.isNegative() && !other.isNegative()) || (!this.isNegative() && other.isNegative()));
        }

        if (this.length() == other.length()) {
            byte[] bufThis = this.number;
            byte[] bufOther = other.number;

            for (int i = 0; i < this.length(); i++) {
                if (bufThis[i] != bufOther[i]) {
                    return bufThis[i] > bufOther[i];
                }
            }
        }
        return this.isNegative() && other.isNegative() ? this.length() < other.length() : this.length() > other.length();
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
        if (border > this.length()) this.setNumber(this.appendZero(border - this.length(), false));
        byte[] bytes = this.getBytes();

        this.setNumber(this.getNumber().substring(0, border + 1));
        if (bytes[border] >= 5) {
            this.setNumber(this.plus(10 - bytes[border]).getNumber());
        } else {
            bytes[border] = 0;
            this.setNumber(bytes);
        }
        this.setNumber(this.getNumber().substring(0, border));
    }

    public boolean isEmpty() {
        return this.number.length == 0;
    }

    public static BigNumber maxOf(BigNumber first, BigNumber second) {
        return first.isGreater(second) ? first : second;
    }

    public static BigNumber minOf(BigNumber first, BigNumber second) {
        return first.isGreater(second) ? second : first;
    }

    public BigNumber plus(int other) {
        return this.plus(new BigNumber(String.valueOf(other)), false);
    }

    public BigNumber plus(BigNumber other) {
        return this.plus(other, false);
    }


    public BigNumber plus(BigNumber other, boolean factional) {
        if (this.isNegative() && !other.isNegative()) {
            this.delNegative();
            if (maxOf(this, other) == this) {
                return new BigNumber(maxOf(this, other).minus(minOf(this, other)).number, true);
            } else {
                BigNumber answer = this.minus(other);
                answer.delNegative();
                return answer;
            }
        }
        if (other.isNegative() && !this.isNegative()) {
            other.delNegative();
            return this.minus(other);
        }
        if (this.isNegative() && other.isNegative()) {
            this.delNegative();
            other.delNegative();
            return new BigNumber(this.plus(other).number, true);
        }

        int length = this.length() > other.length() ? this.length() : other.length();
        byte[] bufThis;
        byte[] bufOther;
        if (!factional) {
            bufThis = this.appendZero(Math.abs(this.length() - length), true);
            bufOther = other.appendZero(Math.abs(other.length() - length), true);
        } else {
            bufThis = this.appendZero(Math.abs(this.length() - length), false);
            bufOther = other.appendZero(Math.abs(other.length() - length), false);
        }

        StringBuilder bufAnswer = new StringBuilder();
        int addedBuf = 0;
        for (int i = length - 1; i >= 0; i--) {
            int addedNum = bufThis[i] +
                    bufOther[i] + addedBuf;
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
            this.delNegative();
            return new BigNumber(other.plus(this).number, true);
        }
        if (other.isNegative() && !this.isNegative()) {
            other.delNegative();
            return this.minus(other);
        }
        if (this.isNegative() && other.isNegative()) {
            this.delNegative();
            other.delNegative();
            return other.minus(this);
        }

        if (Arrays.equals(this.number, other.number)) return new BigNumber("0");

        StringBuilder bufAnswer = new StringBuilder();
        byte[] minuend;
        byte[] subtrahend;
        int length = Integer.max(this.length(), other.length());
        if (!factional) {
            minuend = maxOf(this, other).number;
            subtrahend = minOf(this, other)
                    .appendZero(Math.abs(minOf(this, other).length() - length), true);
        } else {
            BigNumber isMinuend = new BigNumber(this.appendZero(length - this.length(), false),
                    this.negative);
            BigNumber isSubtrahend = new BigNumber(other.appendZero(length - other.length(), false),
                    other.negative);
            minuend = maxOf(isMinuend, isSubtrahend).number;
            subtrahend = minOf(isMinuend, isSubtrahend).number;
        }
        int addedBuf = 0;
        for (int i = minuend.length - 1; i >= 0; i--) {
            int addedNum = minuend[i] - subtrahend[i] - addedBuf;
            if (addedNum < 0) {
                addedNum = addedNum + 10;
                addedBuf = 1;
            } else addedBuf = 0;
            bufAnswer.append(addedNum);
        }

        if (!factional) {
            return new BigNumber((this.isGreater(other) ? "" : "-") + removeZeros(bufAnswer.reverse().toString()));
        } else {
            return new BigNumber((this.isGreater(other) ? "" : "-") + bufAnswer.reverse().toString());
        }
    }

    private BigNumber timesOneNum(int num) {
        if (num == 1) return this;
        if (num == 0) {
            return new BigNumber(appendZero(1, false), false);
        }

        BigNumber answer = new BigNumber("0");
        while (num > 0) {
            answer = answer.plus(this);
            --num;
        }
        return answer;
    }

    public BigNumber times(BigNumber other) {
        if (this.number == new byte[]{'0'} || other.number == new byte[]{'0'}) return new BigNumber("0");
        if (this.number == new byte[]{'1'} || other.number == new byte[]{'1'}) {
            return other.number == new byte[]{'1'} ? this : other;
        }
        if (this.number == new byte[]{'2'}) return other.plus(other);
        if (other.number == new byte[]{'2'}) return this.plus(this);

        BigNumber bufAnswer = new BigNumber("0");
        boolean negative = (this.isNegative() && !other.isNegative()) || (!this.isNegative() && other.isNegative());
        this.delNegative();
        other.delNegative();

        Pattern pattern = Pattern.compile("(\\[1, (0, )*0])|(10+)");
        BigNumber big;
        BigNumber zeros;
        if (pattern.matcher(other.toString()).matches() || pattern.matcher(this.toString()).matches()) {
            big = pattern.matcher(other.toString()).matches() ? this : other;
            zeros = big == this ? other : this;
            bufAnswer = new BigNumber(big.appendZero(zeros.length() - 1, false), false);
            if (negative) bufAnswer.negative = true;
            return bufAnswer;
        }

        BigNumber bigNumber = maxOf(this, other);
        byte[] otherBuf = minOf(this, other).number;

        for (int i = otherBuf.length - 1; i >= 0; i--) {
            BigNumber resultTimes = bigNumber.timesOneNum(parseInt(String.valueOf(otherBuf[i])));
            resultTimes.setNumber(resultTimes.appendZero(otherBuf.length - i - 1, false));
            bufAnswer = bufAnswer.plus(resultTimes);
        }
        if (negative) bufAnswer.negative = true;
        return bufAnswer;
    }

    @Override
    public boolean equals(Object obj) {
        return Arrays.equals(this.number, ((BigNumber) obj).number);
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (byte element : this.number) {
            answer.append(element);
        }
        return this.negative ? "-" + answer.toString() : answer.toString();
    }
}
