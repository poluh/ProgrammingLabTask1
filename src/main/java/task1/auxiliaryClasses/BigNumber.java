package task1.auxiliaryClasses;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class BigNumber implements Comparable<BigNumber> {

    private static Logger log = Logger.getLogger(BigFractional.class.getName());

    private byte[] number;
    private boolean negative;

    private void create(byte[] number, boolean negative) {
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
            byte[] bytes = new byte[number.length() - (negative ? 1 : 0)];
            int offset = negative ? 1 : 0;
            for (int i = offset; i < number.length(); i++) {
                bytes[i - offset] = Byte.parseByte(String.valueOf(number.charAt(i)));
            }
            create(bytes, negative);
        } else {
            throw new NumberFormatException();
        }
    }

    private BigNumber(byte[] number, boolean negative) {
        create(number, negative);
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
        this.setNumber(new BigNumber(number).getBytes());
    }

    public int length() {
        return this.number.length;
    }

    private byte[] appendZeros(int quantity, boolean atFirst) {
        String zeros = IntStream.range(0, quantity).mapToObj(i -> "0").collect(Collectors.joining());
        BigNumber buf = new BigNumber("0");
        buf.setNumber(!atFirst ? this.getNumber().concat(zeros) : zeros.concat(this.getNumber()));
        return buf.getBytes();
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
        if (border > this.length()) this.setNumber(this.appendZeros(border - this.length(), false));
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
            BigNumber bufOther = new BigNumber(other.getBytes(), false);
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
        byte[] bufThis;
        byte[] bufOther;
        bufThis = this.appendZeros(Math.abs(this.length() - length), !factional);
        bufOther = other.appendZeros(Math.abs(other.length() - length), !factional);

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
            BigNumber bufThis = this;
            bufThis.delNegative();
            return new BigNumber(other.plus(bufThis).number, true);
        }
        if (other.isNegative() && !this.isNegative()) {
            BigNumber otherBuf = new BigNumber(other.getBytes(), false);
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

        if (Arrays.equals(this.number, other.number)) return new BigNumber("0");

        StringBuilder bufNumber = new StringBuilder();
        byte[] minuend;
        byte[] subtrahend;
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
        for (int i = minuend.length - 1; i >= 0; i--) {
            int addedNum = minuend[i] - subtrahend[i] - addedBuf;
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
        if (this.number == new byte[]{'0'} || other.number == new byte[]{'0'}) return new BigNumber("0");
        if (this.number == new byte[]{'1'} || other.number == new byte[]{'1'}) {
            return other.number == new byte[]{'1'} ? this : other;
        }
        if (this.number == new byte[]{'2'}) return other.plus(other);
        if (other.number == new byte[]{'2'}) return this.plus(this);

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
        byte[] otherBuf = minOf(bufThis, bufOther).number;

        for (int i = otherBuf.length - 1; i >= 0; i--) {
            BigNumber resultTimes = bigNumber.timesOneNum(parseInt(String.valueOf(otherBuf[i])));
            resultTimes.setNumber(resultTimes.appendZeros(otherBuf.length - i - 1, false));
            bufAnswer = bufAnswer.plus(resultTimes);
        }
        if (negative) bufAnswer.negative = true;
        return bufAnswer;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass() && Arrays.equals(this.number, ((BigNumber) obj).number);
    }

    @Override
    public String toString() {
        return (this.isNegative() ? "-" : "") + this.getNumber();
    }

    @Override
    public int compareTo(BigNumber other) {
        if (this.isNegative() == other.isNegative()) {
            boolean bothNegative = this.isNegative() && other.isNegative();
            if (this.length() == other.length()) {
                byte[] bufThis = this.number;
                byte[] bufOther = other.number;

                for (int i = 0; i < this.length(); i++) {
                    if (bufThis[i] != bufOther[i]) {
                        if (bothNegative) {
                            return bufThis[i] < bufOther[i] ? 1 : -1;
                        } else {
                            return bufThis[i] > bufOther[i] ? 1 : -1;
                        }
                    }
                }
            } else{
                if (bothNegative) {
                    return this.length() < other.length() ? 1 : -1;
                } else {
                    return this.length() > other.length() ? 1 : -1;
                }
            }

        }
        return other.isNegative() ? 1 : -1;
    }
}
