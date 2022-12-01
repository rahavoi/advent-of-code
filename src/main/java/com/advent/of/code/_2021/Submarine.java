package com.advent.of.code._2021;

import java.util.Map;
import java.util.function.Consumer;

public class Submarine {
    long aim;
    long depth;
    long position;

    private Map<String, Consumer<Long>> commands = Map.of(
        "forward", this::moveForward,
        "up", this::moveUp,
        "down", this::moveDown);

    public void moveForward(long value){
        position += value;
        depth += aim * value;
    }

    public void moveUp(long value){
        aim -= value;
    }

    public void moveDown(long value){
        aim += value;
    }

    public void move(String command, long value){
        commands.get(command).accept(value);
    }
}
