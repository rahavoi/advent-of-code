package com.advent.of.code._2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day16 extends Day {
    public static void main(String[] args) throws IOException {
        Integer[] myTicketInput = {89,139,79,151,97,67,71,53,59,149,127,131,103,109,137,73,101,83,61,107};
        List<Integer> myTicket = Arrays.asList(myTicketInput);
        List<Rule> rules = parseRules();
        List<List<Integer>> tickets = parseTickets();

        List<List<Integer>> validTickets =tickets.stream()
                .filter(ticket -> ticket.size() == ticket.stream().filter(num -> rules.stream().anyMatch(rule -> rule.isValid(num)))
                .collect(Collectors.toList()).size()).collect(Collectors.toList());

        Map<Integer, Set<String>> positionsToNames = new HashMap<>();
        Set<String> allNames = rules.stream().map(r -> r.name).collect(Collectors.toSet());

        for(int i = 0; i < myTicket.size(); i++){
            positionsToNames.put(i, allNames);
        }

        //In the end only one name should remain in each set:
        validTickets.forEach(ticket -> {
            for(int i = 0; i < ticket.size(); i ++){
                int position = i;

                Set<String> names = positionsToNames.get(position);
                Integer num = ticket.get(position);

                Set<String> matchingRules = rules.stream()
                    .filter(rule -> rule.isValid(num)).map(r -> r.name)
                    .collect(Collectors.toSet());

                Set<String> filtered = names.stream().filter(matchingRules::contains).collect(Collectors.toSet());

                positionsToNames.put(position, filtered);
            }
        });

        while (positionsToNames.values().stream().anyMatch(n -> n.size() > 1)){
            Set<String> categoriesToExclude = positionsToNames.values().stream().filter(n -> n.size() == 1)
                .map(n -> n.iterator().next()).collect(Collectors.toSet());


            positionsToNames.values().stream().filter(n -> n.size() > 1)
                .forEach(names -> names.removeAll(categoriesToExclude));
        }

        long sum = 1;


        for(int i = 0; i < myTicket.size(); i++){
            if(positionsToNames.get(i) != null && positionsToNames.get(i).size() == 1){
                String name = positionsToNames.get(i).iterator().next();

                if(name.startsWith("departure")){
                    sum *= myTicket.get(i);
                }
            }
        }

        System.out.println(sum);

    }

    private static List<Rule> parseRules() throws IOException
    {
        List<Rule> rules = new ArrayList<>();

        lines("src/main/resources/2020/Day16_rules.txt").forEach(line ->{
            Rule rule = new Rule();
            String[] parts = line.split(": ");
            rule.name = parts[0];

            String[] rangeParts = parts[1].split(" or ");
            rule.firstRangeMin = Integer.parseInt(rangeParts[0].split("-")[0]);
            rule.firstRangeMax = Integer.parseInt(rangeParts[0].split("-")[1]);

            rule.secondRangeMin = Integer.parseInt(rangeParts[1].split("-")[0]);
            rule.secondRangeMax = Integer.parseInt(rangeParts[1].split("-")[1]);

            rules.add(rule);
        });
        return rules;
    }

    private static List<List<Integer>> parseTickets() throws IOException
    {
        List<List<Integer>> tickets = new ArrayList<>();
        lines("src/main/resources/2020/Day16_tickets.txt").forEach(line -> {
                List<Integer> ticket = new ArrayList<>();

                String[] parts = line.split(",");

                for(String part : parts){
                    ticket.add(Integer.parseInt(part));
                }

                tickets.add(ticket);
            });
        return tickets;
    }

    static class Rule {
        String name;
        Integer firstRangeMin;
        Integer firstRangeMax;
        Integer secondRangeMin;
        Integer secondRangeMax;

        boolean isValid(int num){
            return (num >= this.firstRangeMin && num <= this.firstRangeMax) ||
                (num >= this.secondRangeMin && num <= this.secondRangeMax);
        }
    }
}
