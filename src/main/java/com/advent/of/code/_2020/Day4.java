package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4
{
    private static final String HEIGHT_CM = "cm";
    private static final String HEIGHT_IN = "in";
    private static List<String> validEyeColors = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    public static void main(String[] args) throws Exception{
        List<Map<String, String>> documents = getDocuments();
        System.out.println(countValid(documents));
    }

    private static List<Map<String, String>> getDocuments() throws IOException
    {
        List<Map<String, String>> documents = new ArrayList<>();

        Map<String, String> doc = new HashMap<>();

        for(String line : Files.readAllLines(Paths.get("src/main/resources/2020/Day4.txt"))){
            if(line.trim().isEmpty()){
                documents.add(doc);
                doc = new HashMap<>();
            } else{
                String[] attrs = line.split(" ");

                for(String attr : attrs){
                    String[] parts = attr.split(":");
                    doc.put(parts[0], parts[1]);
                }
            }
        }

        documents.add(doc);
        return documents;
    }

    private static int countValid(List<Map<String, String>> documents)
    {
        int validCount = 0;
        for(Map<String, String> d : documents){
            if(isValid(d)){
                validCount++;
            }
        }
        return validCount;
    }

    private static boolean isValid(Map<String, String> doc){
        try{
            Integer byr = Integer.parseInt(doc.get("byr"));
            boolean validBirthYear= byr >= 1920 && byr <= 2002;

            Integer iyr = Integer.parseInt(doc.get("iyr"));
            boolean validIssueYear = iyr >= 2010 && iyr <= 2020;

            Integer eyr = Integer.parseInt(doc.get("eyr"));
            boolean validExpiryYear = eyr >= 2020 && eyr <= 2030;

            String height = doc.get("hgt");
            boolean validHeight = false;
            if(height.contains(HEIGHT_CM) ^ height.contains(HEIGHT_IN)){
                Integer hgt = Integer.parseInt(height.replace(HEIGHT_CM, "").replace(HEIGHT_IN, ""));

                if(height.contains(HEIGHT_CM)){
                    validHeight = hgt >= 150 && hgt <= 193;
                } else if(height.contains(HEIGHT_IN)){
                    validHeight = hgt >= 59 && hgt <= 76;
                }
            }

            String hcl = doc.get("hcl");
            boolean validHair = false;
            if(hcl.length() == 7){
                validHair = hcl.matches("#[0-9a-f]+");
            }

            boolean validEyes = validEyeColors.contains(doc.get("ecl"));

            String pid = doc.get("pid");
            boolean validPid = pid.length() == 9 &&
                pid.matches("[0-9]+");

            return validBirthYear &&
                validIssueYear &&
                validExpiryYear &&
                validHeight &&
                validHair &&
                validEyes &&
                validPid;
        } catch (Exception e){
            return false;
        }
    }
}
