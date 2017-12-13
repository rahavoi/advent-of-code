package rahavoi.advent.of.code;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Day13 {
    static Map<Integer, Integer> levels = new HashMap<>();

    public static void main(String[] args) {
        Arrays.asList(Util.loadFileAsString("day13.txt")
                .split("\n")).forEach(record -> {
                    String[] elements = record.split(":");
                    int layer = Integer.parseInt(elements[0].trim());
                    int size = Integer.parseInt(elements[1].trim());

                    levels.put(layer, size);
                });

        for (int delay = 0;; delay++) {
            boolean caught = false;
            int maxLayer = levels.keySet().stream().max(Comparator.naturalOrder()).get() + 1;
            for (int i = 0; i < maxLayer; i++) {
                Integer layerSize = levels.get(i);

                if (layerSize != null) {
                    int scannerPos = (delay + i) % ((layerSize - 1) * 2);
                    if (scannerPos == 0) {
                        caught = true;
                        break;
                    }
                }
            }

            if (!caught) {
                System.out.println("Passed after delay: " + delay);
                break;
            }
        }
    }
}
