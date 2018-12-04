package com.advent.of.code._2018;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 {
    public static String FALLS_ASLEEP = "falls asleep";
    public static String WAKES_UP = "wakes up";
    public static String GUARD = "Guard #";

    //Got this as as the first run of task1 to find out the sleepiest guard. Then modified task 1 knowing who the sleepiest guy is and worked with him only to get minutes of sleep.
    public static String SLEEPIEST = "#3299";

    public static void main(String [] args) throws Exception{
        new Day4().task1();
        //new Day4().task2();
    }

    public void task1() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2018/Day4.txt"));
        List<Pair<Date, String>> events = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        lines.forEach(line -> {
            String timeStr = line.substring(0, 18).replace("[", "").replace("]", "");

            String event = line.substring(19);

            try {
                Date time = sdf.parse(timeStr);

                Pair<Date, String> entry = new Pair<>(time, event);
                events.add(entry);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        List<Pair<Date, String>> sortedEvents = events.stream()
                .sorted(Comparator.comparing(Pair::getKey))
                .collect(Collectors.toList());


        Map<String, Long> guardsSleepTime = new HashMap<>();
        String currentGuard = null;
        Date timeFellAsleep = null;

        Map<Integer, Long> minutes = new HashMap<>();
        for(int i = 0; i < 60; i++){
            minutes.put(i, 0L);
        }

        for(Pair<Date, String> event : sortedEvents) {
            String eventType = event.getValue();

            if(eventType.contains(GUARD)){
                currentGuard = eventType.split(" ")[1];
            } else if(eventType.contains(FALLS_ASLEEP)){
                timeFellAsleep = event.getKey();
            } else if(eventType.contains(WAKES_UP)){
                Date timeWokeUp = event.getKey();

                long sleptMinutes = (timeWokeUp.getTime() - timeFellAsleep.getTime()) / (60 * 1000) % 60;

                long sleptSoFar = guardsSleepTime.get(currentGuard) != null ? guardsSleepTime.get(currentGuard) : 0L;
                guardsSleepTime.put(currentGuard, sleptMinutes + sleptSoFar);

                if(currentGuard.contains(SLEEPIEST)){
                    Calendar cal = Calendar.getInstance();
                    while (timeFellAsleep.getTime() < timeWokeUp.getTime()){
                        cal.setTime(timeFellAsleep);
                        minutes.put(cal.get(Calendar.MINUTE), minutes.get(cal.get(Calendar.MINUTE)) + 1);
                        cal.add(Calendar.MINUTE, 1);

                        timeFellAsleep = cal.getTime();
                    }
                }

            }
        }

        Map.Entry<String, Long> sleepiestGuard = guardsSleepTime.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();
        System.out.println(Long.parseLong(sleepiestGuard.getKey().substring(1)) * sleepiestGuard.getValue());

    }

    public void task2() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/main/resources/2018/Day4.txt"));
        List<Pair<Date, String>> events = new ArrayList<>();

        lines.forEach(line -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String timeStr = line.substring(0, 18).replace("[", "").replace("]", "");

            String event = line.substring(19);

            try {
                Date time = sdf.parse(timeStr);

                Pair<Date, String> entry = new Pair<>(time, event);
                events.add(entry);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        List<Pair<Date, String>> sortedEvents = events.stream()
                .sorted(Comparator.comparing(Pair::getKey))
                .collect(Collectors.toList());


        Map<String, Map<Integer, Long>> guardsSleepTime = new HashMap<>();
        String currentGuard = null;
        Date timeFellAsleep = null;

        for(Pair<Date, String> event : sortedEvents) {
            String eventType = event.getValue();

            if(eventType.contains(GUARD)){
                currentGuard = eventType.split(" ")[1];

                if(guardsSleepTime.get(currentGuard) == null){
                    Map<Integer, Long> minutes = new HashMap<>();

                    for(int i = 0; i < 60; i++){
                        minutes.put(i, 0L);
                    }

                    guardsSleepTime.put(currentGuard, minutes);
                }

            } else if(eventType.contains(FALLS_ASLEEP)){
                timeFellAsleep = event.getKey();
            } else if(eventType.contains(WAKES_UP)){
                Date timeWokeUp = event.getKey();


                Calendar cal = Calendar.getInstance();
                Map<Integer, Long> minutes = guardsSleepTime.get(currentGuard);

                while (timeFellAsleep.getTime() < timeWokeUp.getTime()){
                    cal.setTime(timeFellAsleep);

                    minutes.put(cal.get(Calendar.MINUTE), minutes.get(cal.get(Calendar.MINUTE)) + 1);
                    cal.add(Calendar.MINUTE, 1);

                    timeFellAsleep = cal.getTime();
                }

            }
        }

        Map.Entry<String, Map<Integer, Long>> max = guardsSleepTime.entrySet().stream().max(Comparator.comparing(e -> e.getValue().entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue()))
                .get();

        System.out.println(Long.parseLong(max.getKey().substring(1)) *
                max.getValue().entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey());
    }
}
