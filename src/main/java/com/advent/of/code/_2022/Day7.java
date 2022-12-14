package com.advent.of.code._2022;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws Exception {
        List<String> input = Files.readAllLines(Paths.get("/Users/irahavoi/work/repos/advent-of-code/src/main/resources/2022/day7.txt"));

        Map<String, Directory> directoryMap = new HashMap<>();

        Directory root = new Directory();
        root.name = "/";
        directoryMap.put(root.name, root);

        Directory curentDir = null;
        for(String l : input){
            l = l.trim();
            if(l.startsWith("$ cd")){
                String dirName = l.split(" " )[2];
                if(dirName.equals("..")){
                    System.out.println("Going to parent dir" + dirName);
                    curentDir = curentDir.parent;
                } else {
                    if(dirName.equals("/")){
                        curentDir = root;
                    } else {
                        curentDir = curentDir.directories.stream().filter(directory -> directory.name.equals(dirName)).findFirst().get();
                    }
                }
            } else if(l.startsWith("$ ls")){
                System.out.println("Listing all directories");
            } else {
                System.out.println("Output of children of current dir: ");
                if(l.startsWith("dir ")){
                    String name = l.split(" ")[1];
                    String fullName = curentDir.getFullName() + "/" + name;
                    if(!directoryMap.containsKey(fullName)){
                        Directory childDir = new Directory();
                        childDir.name = name;
                        childDir.parent = curentDir;
                        curentDir.directories.add(childDir);
                        directoryMap.put(childDir.getFullName(), childDir);
                    }
                } else {
                    File file = new File();
                    file.size = Long.parseLong(l.split(" ")[0]);
                    System.out.println("File size: " + file.size);
                    file.name = l.split(" ")[1];
                    curentDir.files.add(file);
                }
            }
        }

        directoryMap.values().stream().forEach(d -> System.out.println("Dir " + d.name + " size: " + d.getSize()));


        long part1 = directoryMap.values().stream().mapToLong(d -> d.getSize()).filter(size -> size <= 100000).sum();


        System.out.println("Dir names: ");
        directoryMap.keySet().forEach(k -> System.out.println(k + " " + directoryMap.get(k).getSize()));

        long rootSize = directoryMap.get("/").getSize();
        long totalSize = 70000000;
        long neededSpace = 30000000;

        long availableSpace = totalSize - rootSize;

        long part2 = directoryMap.values().stream().mapToLong(d -> d.getSize()).filter(s -> (s + availableSpace) >= neededSpace).min().getAsLong();

        System.out.println("Root size: " + directoryMap.get("/").getSize());
        System.out.println(part1);
        System.out.println(part2);
    }

    static class Directory {
        Directory parent = null;
        Set<Directory> directories = new HashSet<>();
        Set<File> files = new HashSet<>();
        String name;

        public long getSize(){
            return files.stream().mapToLong(f -> f.size).sum() +
                    directories.stream().mapToLong(d -> d.getSize()).sum();
        }

        public String getFullName(){
            return (parent == null) ? "" : (parent.getFullName() + "/") + name;
        }
    }

    static class File {
        String name;
        long size;
    }
}
