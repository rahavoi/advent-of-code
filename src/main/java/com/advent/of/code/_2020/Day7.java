package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7
{
    static String myBag = "shiny gold";
    public static void main(String[] args) throws Exception {
        Map<String, List<Rule>> allRules = parseRules();
        int part1 = allRules.keySet().stream().mapToInt(type -> canHave(type, allRules) ? 1 : 0).sum();
        int part2 = allRules.get(myBag).stream().mapToInt(r -> r.quantity +  r.quantity * subBags(r.color, allRules)).sum();

        System.out.println(part1);
        System.out.println(part2);

    }

    static int subBags(String type, Map<String, List<Rule>> allRules){
        return allRules.get(type).stream()
            .mapToInt(r -> r.quantity +  r.quantity * subBags(r.color, allRules))
            .sum();
    }

    static boolean canHave(String type, Map<String, List<Rule>> allRules){
        return allRules.get(type).stream()
            .filter(r -> myBag.equals(r.color) || canHave(r.color, allRules))
            .findFirst().isPresent();
    }

    private static Map<String, List<Rule>> parseRules() throws IOException
    {
        Map<String, List<Rule>> allRules = new HashMap<>();
        for(String line : Files.readAllLines(Paths.get("src/main/resources/2020/Day7.txt"))){
            String[] parts = line.split(" contain ");
            String type = parts[0].replace(" bags", "");
            String[] descriptions = parts[1].replace(".","").split(",");

            List<Rule> rules = new ArrayList<>();
            for(String desc: descriptions){
                String r = desc.replace("bags", "").replace("bag", "").trim();
                String q = r.split(" ")[0];
                String t = r.replace(q, "").trim();

                if(!q.equals("no")){
                    Rule rule = new Rule();
                    rule.color = t;
                    rule.quantity = Integer.parseInt(q);
                    rules.add(rule);
                }
            }

            allRules.put(type, rules);
        }

        return allRules;
    }

    static class Rule{
        String color;
        int quantity;
    }
}
