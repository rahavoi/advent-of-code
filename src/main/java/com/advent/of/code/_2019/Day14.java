package com.advent.of.code._2019;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {
    private final static String ORE = "ORE";
    private final static String FUEL = "FUEL";
    static long oreCargo = 1000000000000l;
    static int totalMined;

    static Map<String, Long> leftOvers =new HashMap<>();

    private static Map<String, Formula> chemicalsAndFormulas = new HashMap<>();

    public static void main(String[] args) throws Exception {


        List<String> input =

                Files.readAllLines(Paths.get("src/main/resources/2019/Day14.txt"));

        List<Ingredient> oreBasedChemicals = new ArrayList<>();

        for(String s : input){
            String[] parts = s.split(" => ");
            String chemicalsNeeded = parts[0];

            String result = parts[1];
            String [] amountAndResult = result.split(" ");

            Integer quantity = Integer.parseInt(amountAndResult[0]);
            String name = amountAndResult[1];


            List<Ingredient> formula = new ArrayList<>();
            for(String ch : chemicalsNeeded.split(",")){
                String[] p = ch.trim().split(" ");
                Integer chQuantity = Integer.parseInt(p[0]);
                String chName = p[1];

                formula.add(new Ingredient(chName, chQuantity));
            }

            Ingredient chemical = new Ingredient(name, quantity);
            chemicalsAndFormulas.put(name, new Formula(chemical, formula));

            if(formula.size() == 1 && formula.get(0).name.equals("ORE")){
                oreBasedChemicals.add(chemical);
            }
        }

        //oreBasedChemicals.forEach(ch -> System.out.println(ch.name));

        Formula fuelFormula = chemicalsAndFormulas.get("FUEL");
        fuelFormula.ingredients.forEach(ch ->
                System.out.println(ch.name + ": " + ch.quantity));

        long fuelCount = 0;

        //TODO: for each fuel ingredient recursively find how much oreis needed to produce.
        while (oreCargo > totalMined){
            totalMined = 0;
            getIngredient(fuelFormula);
            oreCargo -= totalMined;
            fuelCount++;
        }

        System.out.println("Total fuel produced : " + fuelCount);
    }

    static  int getIngredient(Formula formula){
        List<Ingredient> ingredients = formula.ingredients;

        for(Ingredient ingredient : ingredients){
            if(ingredient.name.equals(ORE)){
                totalMined += ingredient.quantity;
                //System.out.println("Mined ORE: " + ingredient.quantity);

                //System.out.println("Produced " + formula.result.name+ ": " + formula.result.quantity );
                return formula.result.quantity;
            } else {
                long needed = ingredient.quantity -
                        leftOvers.getOrDefault(ingredient.name, 0l);

                //System.out.println("Need to produce " + needed + "of " + ingredient.name);

                while(needed > 0){
                    int produced =
                            getIngredient(chemicalsAndFormulas.get(ingredient.name));
                    needed -= produced;
                }

                leftOvers.put(ingredient.name, needed * -1);
                //System.out.println("Total ORE mined: " + totalMined);
            }
        }

        return formula.result.quantity;
    }

    static class Ingredient {
        String name;
        Integer quantity;

        public Ingredient(String name, Integer quantity) {
            this.name = name;
            this.quantity = quantity;
        }
    }

    static  class Formula{
        Ingredient result;
        List<Ingredient> ingredients;

        public Formula(Ingredient result, List<Ingredient> ingredients) {
            this.result = result;
            this.ingredients = ingredients;
        }
    }
}
