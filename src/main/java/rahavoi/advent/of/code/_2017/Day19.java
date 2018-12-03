package rahavoi.advent.of.code._2017;

import java.util.ArrayList;
import java.util.List;

public class Day19 {
    private static long steps;
    private static boolean done;
    private static String[] lines;
    private static int posInLine;
    private static int lineNumber;
    private static DIRECTION previousDirection;
    private static ORIENTATION previousOrientation;
    private static List<Character> symbols = new ArrayList<>();


    public static void main(String[] args) {
        init();

        while (!done) {
            move();
        }
    }

    private static void init() {
        lines = Util.loadFileAsString("day19.txt").split("\n");
        posInLine = lines[0].indexOf('|');
        previousDirection = DIRECTION.DOWN;
        previousOrientation = ORIENTATION.VERTICAL;
        lineNumber = 1;
        steps++;
    }

    private static void move() {
        char symbol = lines[lineNumber].charAt(posInLine);
        if(symbol == ' '){
            done = true;
            printSymbols();
            return;
        }

        ORIENTATION orientation =ORIENTATION.fromChar(symbol);
        if(orientation == null){
            System.out.print("!!!!!!!!" + symbol + "!!!!!!!!!!!!!");
            symbols.add(symbol);
        }

        if(orientation == null || (orientation != ORIENTATION.PIVOT && !orientation.equals(previousOrientation))){
            System.out.println("Ignoring");
            previousOrientation.move();
        } else{
            try {
                orientation.move();
            } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e){
                System.out.println("Out of grid: line " + lineNumber + ", symbol " + posInLine);
                printSymbols();
                done = true;
            }
        }


    }

    private static void printSymbols(){
        for(char c : symbols){
            System.out.print(c);
        }
        System.out.println("Steps total: " + steps);
    }


    public enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    public enum ORIENTATION{
        VERTICAL('|'),
        HORIZONTAL('-'),
        PIVOT('+');

        char symbol;

        ORIENTATION(char symbol){
            this.symbol = symbol;
        }

        public static ORIENTATION fromChar(char symbol){
            for(ORIENTATION o : ORIENTATION.values()){
                if(o.symbol == symbol){
                    return o;
                }
            }

            return null;
        }

        public void move(){
            switch (symbol){
                case '|':
                    steps++;
                 if(previousDirection == DIRECTION.DOWN){
                     System.out.println("Moving Down..");
                     lineNumber++;
                 } else {
                     previousDirection = DIRECTION.UP;
                     System.out.println("Moving Up..");
                     lineNumber--;
                 }
                 break;
                case '-':
                    steps++;
                    if(previousDirection == DIRECTION.RIGHT){
                        System.out.println("Moving Right..");
                        posInLine++;
                    } else {
                        previousDirection = DIRECTION.LEFT;
                        System.out.println("Moving Left..");
                        posInLine--;
                    }
                    break;
                case '+':
                     System.out.println("Pivoting..");
                     if(previousDirection == DIRECTION.DOWN || previousDirection == DIRECTION.UP){
                         System.out.println("Have to move horizontally now");
                         previousOrientation = ORIENTATION.HORIZONTAL;
                         steps++;
                         if(posInLine > 0 && lines[lineNumber].charAt(posInLine - 1) != ' '){
                             System.out.println("Moving left..");
                             previousDirection = DIRECTION.LEFT;
                             posInLine--;
                         } else{
                             System.out.println("Moving right..");
                             previousDirection = DIRECTION.RIGHT;
                             posInLine++;
                         }
                     } else{
                         System.out.println("Have to move vertically now");
                         steps++;
                         previousOrientation = ORIENTATION.VERTICAL;
                         //Looking up:
                         if(lineNumber > 0 && lines[lineNumber - 1].length() > posInLine  && lines[lineNumber - 1].charAt(posInLine) != ' '){
                             System.out.println("Moving up..");
                             previousDirection = DIRECTION.UP;
                             lineNumber--;
                         } else{
                             System.out.println("Moving down..");
                             previousDirection = DIRECTION.DOWN;
                             lineNumber++;
                         }
                     }
                     break;
                default:
                    throw new IllegalArgumentException("Unknown Orientation!");
            }
        }
    }
}
