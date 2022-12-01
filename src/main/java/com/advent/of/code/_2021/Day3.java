package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day3 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(Constants.PATH_2021 + "Day3.txt"));

        String firstLine = lines.get(0);

        StringBuilder gamma = new StringBuilder();
        StringBuilder epsilon = new StringBuilder();

        List<String> mostCommon = new ArrayList<>(lines); // most common
        List<String> leastCommon = new ArrayList<>(lines); // l



        for(int i = 0; i < firstLine.length(); i++){
            //gamma.append(new Day3().getOccurrences(mostCommon).zeroOccurrences.get(i) > new Day3().getOccurrences(mostCommon).onesOccurrences.get(i) ? '0' : '1');
            //epsilon.append(new Day3().getOccurrences(leastCommon).zeroOccurrences.get(i) > new Day3().getOccurrences(leastCommon).onesOccurrences.get(i) ? '1' : '0');

            int current = i;
            Day3 day3 = new Day3();

            Occurrences mostCommonOcc = day3.getOccurrences(mostCommon);
            Occurrences leastCommonOcc = day3.getOccurrences(leastCommon);


            if(mostCommon.size() > 1){

                mostCommon = mostCommon.stream().filter(l -> l.charAt(current) == (mostCommonOcc.zeroOccurrences.get(current) > mostCommonOcc.onesOccurrences.get(current) ? '0' : '1'))
                    .collect(Collectors.toList());

                System.out.println("After analyzing " + i + "th bit: " + mostCommon.size() + " candidates left for most common:");
                mostCommon.forEach(System.out::println);
            }

            if(leastCommon.size() > 1){
                leastCommon = leastCommon.stream().filter(l -> l.charAt(current) == (leastCommonOcc.zeroOccurrences.get(current) > leastCommonOcc.onesOccurrences.get(current) ? '1' : '0'))
                    .collect(Collectors.toList());

                //System.out.println("After analyzing " + i + "th bit: " + leastCommon.size() + " candidates left for least common:");
                //leastCommon.forEach(System.out::println);
            }
        }

        //System.out.println(Long.parseLong(gamma.toString(), 2) * Long.parseLong(epsilon.toString(), 2));

        System.out.println("Gamma: " + mostCommon.get(0));
        System.out.println("Epsilon: " + leastCommon.get(0));

        System.out.println(Long.parseLong(leastCommon.get(0).toString(), 2) * Long.parseLong(mostCommon.get(0).toString(), 2));

    }

    private Occurrences getOccurrences(List<String> lines){
        Map<Integer, Integer> zeroOccurrences = new HashMap<>();
        Map<Integer, Integer> onesOccurrences = new HashMap<>();


        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            for(int j = 0; j < line.length(); j++){
                int bit = Character.getNumericValue(line.charAt(j));

                zeroOccurrences.getOrDefault(j, 0);
                if(bit == 0){
                    int count = zeroOccurrences.getOrDefault(j, 0);
                    zeroOccurrences.put(j, count + 1);
                }

                if(bit == 1){
                    int count = onesOccurrences.getOrDefault(j, 0);
                    onesOccurrences.put(j, count + 1);
                }
            }
        }

        Occurrences occurrences = new Occurrences();
        occurrences.zeroOccurrences = zeroOccurrences;
        occurrences.onesOccurrences = onesOccurrences;

        return occurrences;
    }

    class Occurrences{
        Map<Integer, Integer> zeroOccurrences = new HashMap<>();
        Map<Integer, Integer> onesOccurrences = new HashMap<>();
    }
}
