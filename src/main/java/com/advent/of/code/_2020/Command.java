package com.advent.of.code._2020;

class Command{
    String text;
    String operation;
    int value;

    public Command(String text, String operation, int value)
    {
        this.text = text;
        this.operation = operation;
        this.value = value;
    }
}