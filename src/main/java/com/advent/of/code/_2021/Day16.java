package com.advent.of.code._2021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day16 {
    static int versionSum = 0;
    public static void main(String[] args) throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("/Users/illiarahavoi/work/tmp/advent_2021/advent-of-code/src/main/resources/2021/Day16.txt"));

        String input = lines.get(0);
        String binary = convertToBinary(input);


        System.out.println(binary);
        List<Long> result = parsePacket(binary);

        System.out.println("Version sum: " + versionSum);
        System.out.println("Result: " + result.get(0));
    }

    public static List<Long> parsePacket(String input){
        if(input.replace("0", "").length() == 0){
            System.out.println("Ignoring remaining bits..");
            return new ArrayList<>();
        }
        //First three bits are version:
        int version = Integer.parseInt(input.substring(0, 3), 2);

        //Next three bits is type id:
        int typeId = Integer.parseInt(input.substring(3, 6), 2);

        System.out.println("######Version: " + version);
        versionSum += version;
        System.out.println("Type Id : " + typeId);

        if(typeId == 4){
            return processLiteralPacket(input);
        } else {
            CompoundPacketResult result = parseCompoundPacket(input);
            List<Long> sub = result.subPackets;
            List<Long> other = result.otherPackets;

            Long subPacketsReduce = null;

            switch (typeId){
                case 0:
                    System.out.println("Sum");
                    //sum of sub-packets
                    subPacketsReduce = sub.stream().mapToLong( e -> e).sum();
                    break;
                case 1:
                    System.out.println("Product");
                    //product of sub-packets
                    subPacketsReduce = sub.stream().reduce(1L, (a, b) -> a * b);
                    break;
                case 2:
                    System.out.println("Min");
                    //minimum of subpackets
                    subPacketsReduce = sub.stream().mapToLong( e -> e).min().getAsLong();
                    break;
                case 3:
                    System.out.println("Max");
                    //maximum of subpackets
                    subPacketsReduce = sub.stream().mapToLong( e -> e).max().getAsLong();
                    break;
                case 4:
                    //Impossible
                    throw new IllegalArgumentException("Can't be!");
                case 5:
                    System.out.println("GT");
                    //greater than. 1 if value of 1st subpacket > 2nd subpacket. otherwise 0.
                    subPacketsReduce = sub.get(0) > sub.get(1) ? 1L : 0L;
                    break;
                case 6:
                    System.out.println("LT");
                    //less than. 1 if value of 1st subpacket < 2nd subpacket. otherwise 0.
                    subPacketsReduce = sub.get(0) < sub.get(1) ? 1L : 0L;
                    break;
                case 7:
                    System.out.println("EQUALS?");
                    //equal to. 1 if value of 1st subpacket == 2nd subpacket. otherwise 0.
                    subPacketsReduce = sub.get(0).equals(sub.get(1)) ? 1L : 0L;
                    break;

                default:
                    throw new IllegalArgumentException("Invalid type Id " + typeId);
            }

            List<Long> finalResult = new ArrayList<>();
            finalResult.add(subPacketsReduce);
            finalResult.addAll(other);

            return finalResult;

        }
    }

    private static CompoundPacketResult parseCompoundPacket(String input) {
        System.out.println("Operator packet (contains one or more packets)");
        int lengthTypeId = Character.getNumericValue(input.charAt(6));
        System.out.println("Length type Id : " + lengthTypeId);

        if(lengthTypeId == 0){
            //the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet
            String b = removeZeroPadding(input.substring(7, 7 + 15));
            Integer totalLengthInBits = Integer.parseInt(b, 2);
            System.out.println("Total length in bits of subpackets: " + totalLengthInBits);
            List<Long> subPackets = parsePacket(input.substring(7 + 15, 7 + 15 + totalLengthInBits));
            List<Long> otherPackets = parsePacket(input.substring(7 + 15 + totalLengthInBits));

            return new CompoundPacketResult(subPackets, otherPackets);


        } else {
            //the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet.
            String b = removeZeroPadding(input.substring(7, 7 + 11));
            Integer numberOfSubPackets = Integer.parseInt(b, 2);
            System.out.println("Number of subpackets: " + numberOfSubPackets);

            //TODO: this does not belong here. only numberOfSubPackets belongs to the list. others don't
            List<Long> allPackets = parsePacket(input.substring(7 + 11));
            List<Long> subPackets = new ArrayList<>();
            List<Long> otherPackets = new ArrayList<>();

            for(int i = 0; i < numberOfSubPackets; i++){
                subPackets.add(allPackets.get(i));
            }

            for(int i = numberOfSubPackets; i < allPackets.size(); i++){
                otherPackets.add(allPackets.get(i));
            }

            return new CompoundPacketResult(subPackets, otherPackets);
        }
    }

    static class CompoundPacketResult{
        List<Long> subPackets;
        List<Long> otherPackets;

        public CompoundPacketResult(List<Long> subPackets, List<Long> otherPackets) {
            this.subPackets = subPackets;
            this.otherPackets = otherPackets;
        }
    }

    private static List<Long> processLiteralPacket(String input) {
        System.out.println("Literal packet:");
        //TODO: analyze groups of 5 bits
        //for each group, if it starts with 1, it is NOT the last group
        //if it starts with 0, it is the last group. extra bits are ignored
        //remaining 4 bits in a group represent a number.
        boolean lastGroup = false;
        int startPos = 6;
        StringBuilder sb = new StringBuilder();
        while (!lastGroup){
            lastGroup = input.charAt(startPos) == '0';

            String group = input.substring(startPos + 1, startPos + 5);
            sb.append(group);
            startPos += 5;
        }
        Long literalValue  = Long.parseLong(removeZeroPadding(sb.toString()), 2);
        System.out.println("Literal value: " + literalValue);
        String remaining = input.substring(startPos);

        List<Long> result = new ArrayList<>();
        result.add(literalValue);
        result.addAll(parsePacket(remaining));

        return result;
    }

    public static String removeZeroPadding(String input){
        while(input.startsWith("0")){
            input = input.substring(1);
        }

        return input;
    }


    public static String convertToBinary(String input){
        StringBuilder result = new StringBuilder();


        for(char c : input.toCharArray()){
            /**
             0 = 0000
             1 = 0001
             2 = 0010
             3 = 0011
             4 = 0100
             5 = 0101
             6 = 0110
             7 = 0111
             8 = 1000
             9 = 1001
             A = 1010
             B = 1011
             C = 1100
             D = 1101
             E = 1110
             F = 1111
             */
            switch (c){
                case '0':
                    result.append("0000");
                    break;
                case '1':
                    result.append("0001");
                    break;
                case '2':
                    result.append("0010");
                    break;
                case '3':
                    result.append("0011");
                    break;
                case '4':
                    result.append("0100");
                    break;
                case '5':
                    result.append("0101");
                    break;
                case '6':
                    result.append("0110");
                    break;
                case '7':
                    result.append("0111");
                    break;
                case '8':
                    result.append("1000");
                    break;
                case '9':
                    result.append("1001");
                    break;
                case 'A':
                    result.append("1010");
                    break;
                case 'B':
                    result.append("1011");
                    break;
                case 'C':
                    result.append("1100");
                    break;
                case 'D':
                    result.append("1101");
                    break;
                case 'E':
                    result.append("1110");
                    break;
                case 'F':
                    result.append("1111");
                    break;
            }
        }

        return result.toString();
    }
}
