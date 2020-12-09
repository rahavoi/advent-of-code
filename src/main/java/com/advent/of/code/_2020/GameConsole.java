package com.advent.of.code._2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameConsole
{
    public static void execute(List<Command> commands)
    {
        Set<String> visited = new HashSet<>();
        int index = 0;
        int accumulator = 0;
        while(true){
            Command command = commands.get(index);

            String checkVisited = index + " " + command.text;
            if(visited.contains(checkVisited)){
                break;
            }

            visited.add(checkVisited);

            switch (command.operation){
                case "acc":
                    accumulator += command.value;
                    index++;
                    break;
                case "jmp":
                    index += command.value;
                    break;
                case "nop":
                    index++;
                    break;
            }

            if(index == commands.size()){
                System.out.println(accumulator);
                System.exit(1);
            }
        }
    }

    public static List<Command> parseCommands(List<String> lines)
    {
        List<Command> commands = new ArrayList<>();

        for(String command : lines){
            String[] parts = command.split(" ");
            String operation = parts[0];
            int value = Integer.parseInt(parts[1]);

            Command c = new Command(command, operation, value);
            commands.add(c);
        }
        return commands;
    }

}
