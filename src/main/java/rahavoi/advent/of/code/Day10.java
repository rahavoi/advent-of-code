package rahavoi.advent.of.code;

public class Day10 {
    private static final int NUMBER_OF_ELEMENTS = 256;
    static int[] nums;
    static int currentPosition = 0;
    static int skipSize = 0;

    static int KNOT_BLOCK_SIZE = 16;

    public static void main(String[] args) {
        init();

        char[] characters = Util.loadFileAsString("day10.txt").toCharArray();
        char[] standardSuffixValues = { 17, 31, 73, 47, 23 };

        char[] combined = concatCharArrays(characters, standardSuffixValues);

        for (int x = 0; x < 64; x++) {
            for (char ch : combined) {
                int from = currentPosition;
                int to = (currentPosition + ch - 1) % NUMBER_OF_ELEMENTS;

                for (int i = 0; i < ch / 2; i++) {
                    if (to == -1) {
                        to = nums.length - 1;
                    }

                    int temp = nums[from];
                    nums[from] = nums[to];
                    nums[to] = temp;

                    from = (from + 1) % NUMBER_OF_ELEMENTS;
                    to = (to - 1) % NUMBER_OF_ELEMENTS;
                }

                currentPosition = (currentPosition + skipSize + ch) % NUMBER_OF_ELEMENTS;

                skipSize++;
            }
        }

        int totalBlocks = 0;
        int[] denseHash = new int[KNOT_BLOCK_SIZE];
        int[] block = new int[KNOT_BLOCK_SIZE];
        for (int i = 0; i < nums.length; i++) {
            int blockIndex = i % KNOT_BLOCK_SIZE;
            block[blockIndex] = nums[i];
            if (blockIndex == KNOT_BLOCK_SIZE - 1) {
                denseHash[totalBlocks] = dense(block);
                totalBlocks++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int num : denseHash) {
            String hex = Integer.toHexString(num);
            if (hex.length() != 2) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }

        System.out.print(sb.toString());
    }

    private static void init() {
        nums = new int[NUMBER_OF_ELEMENTS];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
    }

    private static int dense(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result = result ^ num;
        }

        return result;
    }

    private static char[] concatCharArrays(char[] a, char[] b) {
        char[] c = new char[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }

        int cPos = a.length;

        for (int j = 0; j < b.length; j++) {
            c[cPos] = b[j];
            cPos++;
        }

        return c;
    }
}
