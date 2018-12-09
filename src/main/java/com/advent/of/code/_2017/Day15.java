package rahavoi.advent.of.code._2017;

public class Day15 {
    public static void main(String[] args) {
        int matchCount = 0;
        int multiplyA = 16807;
        int multiplyB = 48271;

        int divisor = 2147483647;

        long a = 289;
        long b = 629;

        for (int i = 0; i < 5000000; i++) {
            a = getNextCalculation(a, multiplyA, divisor, 4);
            b = getNextCalculation(b, multiplyB, divisor, 8);
            ;

            String lowerBitsA = get16LowerBits(a);
            String lowerBitsB = get16LowerBits(b);

            if (lowerBitsA.equals(lowerBitsB)) {
                matchCount++;
            }
        }

        System.out.println(matchCount);

    }

    private static long getNextCalculation(long number, int multFactor, int divisor, int mustBeMultiplyOf) {
        long result = number * multFactor % divisor;

        if (result % mustBeMultiplyOf != 0) {
            result = getNextCalculation(result, multFactor, divisor, mustBeMultiplyOf);
        }

        return result;

    }

    private static String get16LowerBits(long num) {
        String result = Long.toBinaryString(num);

        if (result.length() < 16) {
            int zeros = 16 - result.length();
            for (int i = 0; i < zeros; i++) {
                result = "0" + result;
            }
        }

        return result.substring(result.length() - 16, result.length());
    }
}
