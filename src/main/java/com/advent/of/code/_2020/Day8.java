package com.advent.of.code._2020;

import java.util.List;

public class Day8 extends Day
{
    public static void main(String[] args) throws Exception {
        List<Command> commands = GameConsole.parseCommands(lines("src/main/resources/2020/Day8.txt"));

        for(int i = 0; i < commands.size(); i++){
            Command curr = commands.get(i);
            String originalOperation = curr.operation;

            if(!originalOperation.equals("acc")){
                curr.operation = originalOperation.equals("jmp") ? "nop" : "jmp";
            }

            GameConsole.execute(commands);
            curr.operation = originalOperation;
        }
    }

}
