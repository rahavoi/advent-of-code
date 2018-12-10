package com.advent.of.code._2017;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Program implements Runnable {
    private Long id;
    private Long sendCount = 0L;
    private Map<String, BigInteger> registers = new HashMap<>();

    @Override
    public void run() {
        process();

    }

    public Program(Long id, Queue<BigInteger> receiveQueue, Queue<BigInteger> sendQueue) {
        this.id = id;
        this.receiveQueue = receiveQueue;
        this.sendQueue = sendQueue;
    }

    Queue<BigInteger> sendQueue;
    Queue<BigInteger> receiveQueue;

    public Queue<BigInteger> getSendQueue() {
        return sendQueue;
    }

    public Queue<BigInteger> getReceiveQueue() {
        return receiveQueue;
    }

    private void process() {
        registers.put("p", new BigInteger(this.id.toString()));

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
                sendCount++;
                sendQueue.add(valueOfX);
            } else if (operator.equals("set")) {
                registers.put(register, valueOfY);
            } else if (operator.equals("add")) {
                registers.put(register, valueOfY.add(valueOfX));
            } else if (operator.equals("mul")) {
                registers.put(register, valueOfY.multiply(valueOfX));
            } else if (operator.equals("mod")) {
                registers.put(register, valueOfX.mod(valueOfY));
            } else if (operator.equals("rcv")) {
                // if (!valueOfX.equals(BigInteger.ZERO)) {
                receive(register);
                // }
            } else if (operator.equals("jgz")) {
                if (valueOfX.compareTo(BigInteger.ZERO) > 0) {
                    i = i + valueOfY.intValue() - 1;
                }
            }
        }
    }

    private void receive(String register) {
        BigInteger nextNum = receiveQueue.poll();
        if (nextNum == null) {
            try {
                System.out.println("Program " + this.id + ": SENT so far: " + sendCount);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            receive(register);
        } else {
            registers.put(register, nextNum);
        }
    }
}