package rahavoi.advent.of.code;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Day23 {
    static int mulCount;

    public static void main(String[] args) {
        task1();
        System.out.println(mulCount);
    }

    private static void task1() {
        Map<String, BigInteger> registers = new HashMap<>();
        String[] commands = Util.loadFileAsString("day23.txt").split("\n");

        for (int i = 0; i < commands.length; i++) {
            String command = commands[i];
            String[] elements = command.split(" ");
            String operator = elements[0];
            String register = elements[1];

            BigInteger valueOfX = null;
            try {
                valueOfX = new BigInteger(elements[1]);
            } catch (NumberFormatException e) {
                valueOfX = registers.get(register);
            }

            if (valueOfX == null) {
                valueOfX = new BigInteger("0");
            }

            BigInteger valueOfY = null;

            if (elements.length == 3) {
                try {
                    valueOfY = new BigInteger(elements[2]);
                } catch (NumberFormatException e) {
                    valueOfY = registers.get(elements[2]);
                }

            }

            if (valueOfY == null) {
                valueOfY = new BigInteger("0");
            }

            if (operator.equals("set")) {
                registers.put(register, valueOfY);
            } else if (operator.equals("sub")) {
                registers.put(register, valueOfX.subtract(valueOfY));
            } else if (operator.equals("mul")) {
                mulCount++;
                registers.put(register, valueOfY.multiply(valueOfX));
            } else if (operator.equals("jnz")) {
                if (valueOfX.compareTo(BigInteger.ZERO) > 0 || valueOfX.compareTo(BigInteger.ZERO) < 0) {
                    i = i + valueOfY.intValue() - 1;
                }
            }
        }
    }
}
