package com.advent.of.code._2022;
;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 {
    public static void main(String[] args) throws Exception{
        Monkey monkey0 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("83"), new BigInteger("88"), new BigInteger("96"), new BigInteger("79"), new BigInteger("86"), new BigInteger("88"), new BigInteger("70"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.multiply(new BigInteger("5"));
                    }
                },
                11,
                2,
                3

        );

        Monkey monkey1 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("59"), new BigInteger("63"), new BigInteger("98"), new BigInteger("85"), new BigInteger("68"), new BigInteger("72"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.multiply(new BigInteger("11"));
                    }
                },
                5,
                4,
                0

        );

        Monkey monkey2 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("90"), new BigInteger("79"), new BigInteger("97"), new BigInteger("52"), new BigInteger("90"), new BigInteger("94"), new BigInteger("71"), new BigInteger("70"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.add(new BigInteger("2"));
                    }
                },
                19,
                5,
                6

        );

        Monkey monkey3 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("97"), new BigInteger("55"), new BigInteger("62"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.add(new BigInteger("5"));
                    }
                },
                13,
                2,
                6

        );

        Monkey monkey4 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("74"), new BigInteger("54"), new BigInteger("94"), new BigInteger("76"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.multiply(old);
                    }
                },
                7,
                0,
                3

        );

        Monkey monkey5 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("58"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.add(new BigInteger("4"));
                    }
                },
                17,
                7,
                1

        );

        Monkey monkey6 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("66"), new BigInteger("63"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.add(new BigInteger("6"));
                    }
                },
                2,
                7,
                5

        );

        Monkey monkey7 = new Monkey(
                new ArrayList<>(Arrays.asList(new BigInteger("56"), new BigInteger("56"), new BigInteger("90"), new BigInteger("96"), new BigInteger("68"))),
                new Operation() {
                    @Override
                    BigInteger operate(BigInteger old) {
                        return old.add(new BigInteger("7"));
                    }
                },
                3,
                4,
                1

        );

        List<Monkey> monkeys = new ArrayList<>();
        monkeys.add(monkey0);
        monkeys.add(monkey1);
        monkeys.add(monkey2);
        monkeys.add(monkey3);
        monkeys.add(monkey4);
        monkeys.add(monkey5);
        monkeys.add(monkey6);
        monkeys.add(monkey7);


        /*
        Monkey monkey0 = new Monkey(
                new ArrayList<>(Arrays.asList(79l, 98l)),
                new Operation() {
                    @Override
                    long operate(long old) {
                        return old * 19;
                    }
                },
                23,
                2,
                3

        );
        Monkey monkey1 = new Monkey(
                new ArrayList<>(Arrays.asList(54l, 65l, 75l, 74l)),
                new Operation() {
                    @Override
                    long operate(long old) {
                        return old + 6;
                    }
                },
                19,
                2,
                0

        );

        Monkey monkey2 = new Monkey(
                new ArrayList<>(Arrays.asList(79l, 60l, 97l)),
                new Operation() {
                    @Override
                    long operate(long old) {
                        return old * old;
                    }
                },
                13,
                1,
                3

        );

        Monkey monkey3 = new Monkey(
                new ArrayList<>(Arrays.asList(74l)),
                new Operation() {
                    @Override
                    long operate(long old) {
                        return old + 3;
                    }
                },
                17,
                0,
                1

        );

        List<Monkey> monkeys = new ArrayList<>();
        monkeys.add(monkey0);
        monkeys.add(monkey1);
        monkeys.add(monkey2);
        monkeys.add(monkey3);
        */

        List<BigInteger> tests =
                monkeys.stream().map(m -> new BigInteger(Integer.toString(m.testDivisibleBy))).collect(Collectors.toList());


        BigInteger lcm = lcm(tests);



        for(int i = 0; i < 10000; i++){
            System.out.println(i);
            for(Monkey m : monkeys){
                if(m.items.size() > 0){
                    int itemsSize = m.items.size();
                    for(int x = 0; x < itemsSize; x++){
                        m.timesInspected++;
                        BigInteger item = m.items.get(0);
                        m.items.remove(0);

                        BigInteger newItem = m.operation.operate(item).mod(lcm);

                        if(newItem.mod(new BigInteger(Integer.toString(m.testDivisibleBy))).equals(BigInteger.ZERO)){
                            monkeys.get(m.testPassThrowTo).items.add(newItem);
                        } else {
                            monkeys.get(m.testFailThrowTo).items.add(newItem);
                        }
                    }
                }
            }
        }

        Collections.sort(monkeys, Comparator.comparing(Monkey::getTimesInspected));
        long result = monkeys.get(monkeys.size() - 1).timesInspected * monkeys.get(monkeys.size() - 2).timesInspected;
        System.out.println(result);
    }

    private static BigInteger gcd(BigInteger a, BigInteger b)
    {
        while (b.compareTo(BigInteger.ZERO) > 0)
        {
            BigInteger temp = b;
            b = a.mod(b); // % is remainder
            a = temp;
        }
        return a;
    }

    private static BigInteger lcm(BigInteger a, BigInteger b)
    {
        return a.multiply(b.divide(gcd(a, b)));
    }

    private static BigInteger lcm(List<BigInteger> input)
    {
        BigInteger result = input.get(0);
        for(int i = 1; i < input.size(); i++) result = lcm(result, input.get(i));
        return result;
    }

    private static BigInteger gcd(List<BigInteger> input)
    {
        BigInteger result = input.get(0);
        for(int i = 1; i < input.size(); i++) result = gcd(result, input.get(i));
        return result;
    }

    static class Monkey {
        List<BigInteger> items;
        Operation operation;
        int testDivisibleBy;
        int testPassThrowTo;
        int testFailThrowTo;

        long timesInspected;

        public Monkey(List<BigInteger> startingItems, Operation operation, int testDivisibleBy, int testPassThrowTo, int testFailThrowTo) {
            this.items = startingItems;
            this.operation = operation;
            this.testDivisibleBy = testDivisibleBy;
            this.testPassThrowTo = testPassThrowTo;
            this.testFailThrowTo = testFailThrowTo;
        }

        public long getTimesInspected() {
            return timesInspected;
        }
    }

    abstract static class Operation {
        abstract BigInteger operate(BigInteger old);
    }
}
