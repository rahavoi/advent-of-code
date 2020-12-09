package com.advent.of.code._2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Day
{
    public static List<String> lines(String path) throws IOException
    {
        return Files.readAllLines(Paths.get(path));
    }
}
