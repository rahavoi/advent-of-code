package com.advent.of.code._2020;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1
{
    public static void main(String[] args)throws Exception {

        List<Integer> nums = Files.readAllLines(Paths.get("src/main/resources/2020/Day1.txt"))
            .stream().map(l -> Integer.parseInt(l)).collect(Collectors.toList());

        part1(nums);
        part2(nums);
    }

    private static void part1(List<Integer> nums){
        for(int n : nums){

            if(nums.contains(2020 - n)){
                System.out.println(n * (2020 - n));
                break;
            }
        }
    }

    private static void part2(List<Integer> nums){
        for(int num : nums){
            if(checkSum(num, nums)){
                break;
            }
        }
    }

    private static boolean checkSum(int num, List<Integer> nums)
    {
        int remainder = 2020 - num;

        for(int i =0; i < nums.size(); i++){
            if(nums.get(i) == num){
                continue;
            }

            int match = remainder - nums.get(i);
            if(nums.contains(match)){
                System.out.println(nums.get(i) * match * num);
                return true;
            }
        }

        return false;
    }
}
