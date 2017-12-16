package rahavoi.advent.of.code;

import java.util.ArrayList;
import java.util.List;

public class Day16 {
    public static void main(String[] args) {
        String input = Util.loadFileAsString("day16.txt");
        System.out.println(getPositionAfterBillionDances(input));
    }

    public static String getPositionAfterBillionDances(String input) {
        String programs = "abcdefghijklmnop";
        List<String> dances = new ArrayList<>();
        boolean stopDance = false;
        for (int i = 0; i < 1000000000; i++) {
            if (!stopDance) {
                programs = dance(programs, input);
                if (!dances.contains(programs)) {
                    dances.add(programs);
                } else {
                    stopDance = true;
                }
            }
        }

        return dances.get(1000000000 % dances.size() - 1);
    }

    public static String dance(String program, String input) {
        for (String command : input.split(",")) {
            if (command.startsWith("s")) {
                int num = Integer.parseInt(command.substring(1));
                String secondPart = program.substring(program.length() - num, program.length());
                program = secondPart + program.replace(secondPart, "");
            } else if (command.startsWith("x")) {
                String[] positions = command.substring(1).split("/");
                int pos1 = Integer.parseInt(positions[0]);
                int pos2 = Integer.parseInt(positions[1]);

                Character a = program.charAt(pos1);
                Character b = program.charAt(pos2);
                String temp = "?";
                program = program.replace(a.toString(), temp).replace(b.toString(), a.toString()).replace(temp, b.toString());
            } else if (command.startsWith("p")) {
                String[] partners = command.substring(1).split("/");
                String partnerA = partners[0];
                String partnerB = partners[1];
                String temp = "?";

                program = program.replace(partnerA, temp).replace(partnerB, partnerA).replace(temp, partnerB);

            }
        }

        return program;
    }
}
