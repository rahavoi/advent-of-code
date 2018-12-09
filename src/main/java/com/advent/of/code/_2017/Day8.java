package rahavoi.advent.of.code._2017;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Day8 {
    private static Map<String, Integer> registers = new HashMap<>();
    private static int highestValueDuringProcessing = 0;

    public static void main(String[] args) {
        Arrays.asList(Util.loadFileAsString("day8.txt")
                .split("\n")).forEach(record -> {
                    String[] items = record.split(" ");
                    String name = items[0];
                    ArithmeticOperator increaseOrDecrease = ArithmeticOperator.fromString(items[1]);
                    int value = Integer.parseInt(items[2]);
                    String registerToCheck = items[4];
                    LogicalOperator logicalOperator = LogicalOperator.fromString(items[5]);
                    int valueToCheck = Integer.parseInt(items[6]);

                    Integer registerToCheckValue = registers.get(registerToCheck);
                    if (registerToCheckValue == null) {
                        registerToCheckValue = 0;
                        registers.put(registerToCheck, registerToCheckValue);
                    }

                    if (logicalOperator.apply(registerToCheckValue, valueToCheck)) {
                        Integer currentValue = registers.get(name);
                        if (currentValue == null) {
                            currentValue = 0;
                        }
                        int newValue = increaseOrDecrease.apply(currentValue, value);
                        if (highestValueDuringProcessing < newValue) {
                            highestValueDuringProcessing = newValue;
                        }
                        registers.put(name, newValue);
                    }

                });

        Entry<String, Integer> max = registers.entrySet().stream().max((e1, e2) -> e1.getValue() - e2.getValue()).get();
        System.out.println("Highest valued DURING processing: " + highestValueDuringProcessing);
        System.out.println("Max Value AFTER processing: " + max.getKey() + " = " + max.getValue());
    }

    public enum ArithmeticOperator {
        INCREASE("inc") {
            @Override
            public int apply(int x1, int x2) {
                return x1 + x2;
            }
        },
        DECREASE("dec") {
            @Override
            public int apply(int x1, int x2) {
                return x1 - x2;
            }
        };

        private final String text;

        public abstract int apply(int x1, int x2);

        private ArithmeticOperator(String text) {
            this.text = text;
        }

        public static ArithmeticOperator fromString(String text) {
            for (ArithmeticOperator ao : ArithmeticOperator.values()) {
                if (ao.text.equalsIgnoreCase(text)) {
                    return ao;
                }
            }
            return null;
        }
    }

    public enum LogicalOperator {
        GREATER_THAN(">") {
            public boolean apply(int x1, int x2) {
                return x1 > x2;
            }
        },
        LESS_THAN("<") {
            public boolean apply(int x1, int x2) {
                return x1 < x2;
            }
        },
        EQUALS("==") {
            public boolean apply(int x1, int x2) {
                return x1 == x2;
            }
        },
        EQUALS_OR_GREATER_THAN(">=") {
            public boolean apply(int x1, int x2) {
                return x1 >= x2;
            }
        },
        EQUALS_OR_LESS_THAN("<=") {
            public boolean apply(int x1, int x2) {
                return x1 <= x2;
            }
        },
        NOT_EQUALS("!=") {
            public boolean apply(int x1, int x2) {
                return x1 != x2;
            }
        };

        private final String text;

        public abstract boolean apply(int x1, int x2);

        private LogicalOperator(String text) {
            this.text = text;
        }

        public static LogicalOperator fromString(String text) {
            for (LogicalOperator lo : LogicalOperator.values()) {
                if (lo.text.equalsIgnoreCase(text)) {
                    return lo;
                }
            }
            return null;
        }
    }
}
