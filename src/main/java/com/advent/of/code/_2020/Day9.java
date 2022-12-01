package com.advent.of.code._2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 extends Day
{
    public static void main(String[] args) throws IOException
    {
        List<Long> numbers = lines("src/main/resources/2020/Day9.txt").stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
        long part1 = part1(numbers);
        long part2 = part2(numbers, part1);

        System.out.println(part1);
        System.out.println(part2);
    }

    private static long part2(List<Long> numbers, long part1)
    {
        int startFrom = 0;

        outer:
        while(true){
            int sum = 0;
            List<Long> contiguous = new ArrayList<>();
            for(int i = startFrom; i < numbers.size(); i++){
                sum+= numbers.get(i);
                contiguous.add(numbers.get(i));
                if(sum == part1){
                    long min = contiguous.stream().mapToLong(n -> n).min().getAsLong();
                    long max = contiguous.stream().mapToLong(n -> n).max().getAsLong();
                    return min + max;
                } else if(sum > part1){
                    startFrom++;
                    continue outer;
                }
            }
        }
    }

    private static long part1(List<Long> numbers)
    {
        Long part1 = null;

        outer:
        for(int i = 25; i < numbers.size(); i++){
            List<Long> preamble = new ArrayList<>();

            for(int j = i - 25; j < i; j++){
                preamble.add(numbers.get(j));
            }

            for(long n : preamble){
                if(preamble.contains(numbers.get(i) - n)){
                    continue outer;
                }
            }

            part1 = numbers.get(i);
            break outer;
        }
        return part1;
    }
}
