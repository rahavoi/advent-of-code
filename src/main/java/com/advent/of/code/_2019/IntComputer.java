package com.advent.of.code._2019;

public class IntComputer
{
    private int[] program;
    public IntComputer(int[] program){
        this.program = program;

    }

    public int process(int noun, int verb){
        program[1] = noun;
        program[2] = verb;
        int pos = 0;

        while (true){
            if(program[pos] == 99){
                //System.out.println("Halt!");
                return program[0];
            }
            int operand1 = program[program[pos + 1]];
            int operand2 = program[program[pos + 2]];
            int storeAt = program[pos + 3];



            switch (program[pos]){
                case 1:
                    //System.out.println("Addition: ");
                    program[storeAt] = operand1 + operand2;
                    break;
                case 2:
                    //System.out.println("Multiplication: ");
                    program[storeAt] = operand1 * operand2;

                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + program[pos]);
            }

            pos+=4;

        }
    }
}
