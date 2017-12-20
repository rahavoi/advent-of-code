package rahavoi.advent.of.code;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Day18 {
    public static void main(String[] args) {
        Queue<BigInteger> receiveQueueA = new ArrayBlockingQueue<>(10000);
        Queue<BigInteger> receiveQueueB = new ArrayBlockingQueue<>(10000);
        Program programA = new Program(0L, receiveQueueA, receiveQueueB);
        Program programB = new Program(1L, receiveQueueB, receiveQueueA);
        new Thread(programA).start();
        new Thread(programB).start();
    }

    private static void task1() {
        Map<String, BigInteger> registers = new HashMap<>();
        String last = null;
        String[] commands = Util.loadFileAsString("day18.txt").split("\n");

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

            if (operator.equals("snd")) {
                last = valueOfX.toString();
            } else if (operator.equals("set")) {
                registers.put(register, valueOfY);
            } else if (operator.equals("add")) {
                registers.put(register, valueOfY.add(valueOfX));
            } else if (operator.equals("mul")) {
                registers.put(register, valueOfY.multiply(valueOfX));
            } else if (operator.equals("mod")) {
                registers.put(register, valueOfX.mod(valueOfY));
            } else if (operator.equals("rcv")) {
                if (!valueOfX.equals(BigInteger.ZERO)) {
                    System.out.println("Recovering the last sound: " + last);
                }
            } else if (operator.equals("jgz")) {
                if (valueOfX.compareTo(BigInteger.ZERO) > 0) {
                    i = i + valueOfY.intValue() - 1;
                }
            }
        }
    }

}
