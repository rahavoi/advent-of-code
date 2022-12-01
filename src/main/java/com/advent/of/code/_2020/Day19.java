package com.advent.of.code._2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends Day
{
    private static Map<Integer, String> ruleMap = new HashMap<>();

    public static void main(String[] args) {
        List<String> lines = lines("src/main/resources/2020/Day19_rules.txt");

        lines.forEach(l -> {
            String[] parts = l.split(": ");
            String ruleName = parts[0];
            String ruleBody = parts[1];

            ruleMap.put(Integer.parseInt(ruleName), ruleBody);
        });

        String regEx = "^" + getRegex(ruleMap.get(0)) + "$";

        int sum = lines("src/main/resources/2020/Day19_msg.txt")
            .stream()
            .mapToInt(l -> isValid(regEx, l) ? 1 : 0)
            .sum();

        System.out.println(sum);
    }

    private static boolean isValid(String regex, String message){
        return message.matches(regex);
    }

    static int  maxLoops = 10;
    static int count11 = 0;
    static int count8 = 0;
    public static String getRegex(String ruleDescription){
        if (ruleDescription.contains("\"")) { return ruleDescription.replaceAll("\"", ""); }

        StringBuilder sb = new StringBuilder("(");

        String[] parts = ruleDescription.split(" ");

        for(String part : parts){
            if(Character.isDigit(part.charAt(0))){
                int ruleId = Integer.parseInt(part);

                if(ruleId == 11){
                    count11++;

                    if(count11 == maxLoops){
                        return "";
                    }
                }

                if(ruleId == 8){
                    count8++;

                    if(count8 == maxLoops){
                        return "";
                    }
                }
                sb.append(getRegex(ruleMap.get(ruleId)));
            } else {
                sb.append("|");
            }
        }

        sb.append(")");

        return sb.toString();
    }

}
