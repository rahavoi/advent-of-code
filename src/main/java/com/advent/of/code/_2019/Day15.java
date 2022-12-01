package com.advent.of.code._2019;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day15 {
    public static void main(String[] args) throws Exception{
        String[] input = Files.readString(Paths.get("src/main/resources/2019/day15.txt")).split(",");

        BigInteger[] nums = new BigInteger[input.length];

        for(int i = 0; i < nums.length; i++){
            nums[i] = new BigInteger(input[i]);
        }
    }
}
