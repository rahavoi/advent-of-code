package com.advent.of.code._2018;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {
    private static String start = "Step ";
    private static String middle = " must be finished before step ";
    private static String end = " can begin.";
    private List<Character> result = new ArrayList<>();
    private Map<Character, Set<Character>> actionsAndPrerequisites = new HashMap<>();

    private Set<Character> actionsWithPrerequisites = new HashSet();
    private Set<Character> prerequisites = new HashSet();
    private List<Character> allActions;
    private List<Character> actionsWithoutPrerequisites;

    private static int numberOfActions;
    private static int timeSpent = 0;

    private static int minTimePerStep = 60;
    private static int numberOfWorkers = 5;
    private boolean problemSolved = false;


    public static void main(String[] args) throws Exception {
        new Day7().solve();
    }

    private void solve() throws Exception {
        Files.readAllLines(Paths.get("src/main/resources/2018/Day7.txt"))
                .forEach(line -> {
                    char[] chars = line.replace(start, "").replace(middle, "").replace(end, "").toCharArray();
                    char prerequisite = chars[0];
                    char action = chars[1];

                    Set<Character> actionPrerequisites = actionsAndPrerequisites.get(action);
                    if (actionPrerequisites == null) {
                        actionPrerequisites = new HashSet<>();
                    }

                    prerequisites.add(prerequisite);
                    actionsWithPrerequisites.add(action);
                    actionPrerequisites.add(prerequisite);
                    actionsAndPrerequisites.put(action, actionPrerequisites);
                });

        Set<Character> allActionsSet = new HashSet<>(actionsWithPrerequisites);
        allActionsSet.addAll(prerequisites);
        allActions = new ArrayList<>(allActionsSet);
        Collections.sort(allActions);

        numberOfActions = allActions.size();

        actionsWithoutPrerequisites = getAllActionsWithoutPrerequisites();

        for(int i = 0; i < numberOfWorkers; i++){
            new Thread(new Elf(), "Elfo-" + i).start();
        }
    }

    private class Elf implements Runnable{
        public void run() {
            while (result.size() < numberOfActions){
                Character actionToExecute = getActionToExecute();
                int startTime = timeSpent;

                if(actionToExecute != null){
                    try {
                        Thread.sleep((getDuration(actionToExecute)));

                        markActionAsDone(actionToExecute, startTime);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            finishAndReportStatus();
        }
    }

    private synchronized void finishAndReportStatus(){
        if(!problemSolved){
            problemSolved = true;
            System.out.println(Thread.currentThread().getName() + " says: We're done, master! It took us: " + timeSpent + "s.");
        } else {
            System.out.println(Thread.currentThread().getName() + " says: Yieeeee");
        }
    }

    private synchronized Character getActionToExecute(){
        if(actionsWithoutPrerequisites.isEmpty()){
            return null;
        }

        Character action = actionsWithoutPrerequisites.get(0);
        allActions.remove(action);
        actionsWithoutPrerequisites.remove(action);
        return action;
    }

    private synchronized void markActionAsDone(Character action, int startTime){
        timeSpent = startTime + getDuration(action);
        result.add(action);
        System.out.println(Thread.currentThread().getName() + " says: Finished " + action + " at " + timeSpent + "s." + result );
        actionsAndPrerequisites.entrySet().forEach(entry -> entry.getValue().remove(action));
        actionsWithoutPrerequisites = getAllActionsWithoutPrerequisites();
    }

    public int getDuration(Character action){
        return minTimePerStep + (action - 64);
    }

    private List<Character> getAllActionsWithoutPrerequisites(){
        List<Character> result = allActions.stream().filter(a -> actionsAndPrerequisites.get(a) == null || actionsAndPrerequisites.get(a).size() == 0).collect(Collectors.toList());
        Collections.sort(result);
        return result;
    }
}

