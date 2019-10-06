package com.advent.of.code._2015;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2 {
    public static void main(String[] args) throws Exception{
        Long total = Files.readAllLines(Paths.get("src/main/resources/2015/Day2.txt"))
                .stream()
                .map(line -> {
                    String[] parts = line.trim().split("x");
                    long l = Long.parseLong(parts[0]);
                    long w = Long.parseLong(parts[1]);
                    long h = Long.parseLong(parts[2]);

                    long smallestSidePerimeter =
                            (l > w) ? (l > h ? getPerimeter(w,h) : getPerimeter(w,l)) : (w > h ? getPerimeter(l,h) : getPerimeter(l,w));


                    long volume = l*w*h;

                    //return getNeededWrappingPaper(l, w, h);
                    return smallestSidePerimeter + volume;
                }).reduce(0L, Long::sum);

        System.out.println(total);
    }

    private static Long getPerimeter(long a, long b){
        return 2*a + 2*b;
    }

    private static Long getNeededWrappingPaper(long l, long w, long h) {
        long sideA = l * w;
        long sideB = l * h;
        long sideC = h * w;

        long slack = sideA < sideB ? (sideA < sideC ? sideA : sideC) : (sideB < sideC ? sideB : sideC);

        return  2*l*w + 2*w*h + 2*h*l + slack;
    }
}
