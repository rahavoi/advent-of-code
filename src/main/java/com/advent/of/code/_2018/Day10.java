package com.advent.of.code._2018;


import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

public class Day10 {
    int counter = 1;

    private class Canvas extends JPanel {
        private List<MovingPoint> movingPoints;

        public void setMovingPoints(List<MovingPoint> movingPoints) {
            this.movingPoints = movingPoints;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.YELLOW);

            setBackground(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.scale(3, 3);

            System.out.println(counter++);
            for (int i = 0; i < movingPoints.size(); i++) {
                MovingPoint mp = movingPoints.get(i);
                g2d.drawLine(mp.getX(), mp.getY(), mp.getX(), mp.getY());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Day10().task1();
    }

    private  void task1() throws Exception {
        List<MovingPoint> movingPoints = parseInput();
        MovingPoint minXPoint = movingPoints.stream().min(Comparator.comparing(MovingPoint::getX)).get();
        MovingPoint maxXPoint = movingPoints.stream().max(Comparator.comparing(MovingPoint::getX)).get();
        MovingPoint minYPoint = movingPoints.stream().min(Comparator.comparing(MovingPoint::getY)).get();
        MovingPoint maxYPoint = movingPoints.stream().max(Comparator.comparing(MovingPoint::getY)).get();

        while (Math.abs(maxXPoint.getX()) - Math.abs(minXPoint.getX()) > 100 &&
                Math.abs(maxYPoint.getY()) - Math.abs(minYPoint.getY()) > 100){
            movingPoints.forEach(mp -> {
                mp.setX(mp.getX() + mp.getVelocityX());
                mp.setY(mp.getY() + mp.getVelocityY());
            });
            counter++;
        }

        Integer minX = movingPoints.stream().min(Comparator.comparing(MovingPoint::getX)).get().getX();
        Integer minY = movingPoints.stream().min(Comparator.comparing(MovingPoint::getY)).get().getY();

        movingPoints.forEach(p -> {
            p.setX(p.getX() - minX);
            p.setY(p.getY() - minY);
        });

        Canvas canvas = new Canvas();
        canvas.setMovingPoints(movingPoints);

        JFrame frame = new JFrame("Day10");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while (true){
            movingPoints.forEach(mp -> {
                mp.setX(mp.getX() + mp.getVelocityX());
                mp.setY(mp.getY() + mp.getVelocityY());
            });

            Thread.sleep(250);
            canvas.repaint();
        }
    }

    private List<MovingPoint> parseInput() throws IOException {
        List<MovingPoint> movingPoints = new ArrayList<>();
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/2018/Day10.txt"));
        input.forEach(line -> {
            String[] positionAndVelocity = line.replace("position=", "").split(" velocity=");
            String[] positionString = positionAndVelocity[0].replace("<", "").replace(">", "").split(",");
            String[] velocityString = positionAndVelocity[1].replace("<", "").replace(">", "").split(",");

            Integer x = Integer.parseInt(positionString[0].trim());
            Integer y = Integer.parseInt(positionString[1].trim());

            Integer velocityX = Integer.parseInt(velocityString[0].trim());
            Integer velocityY = Integer.parseInt(velocityString[1].trim());

            movingPoints.add(new MovingPoint(x, y, velocityX, velocityY));
        });

        return movingPoints;
    }

    private class MovingPoint{
        private Integer x;
        private Integer y;
        private Integer velocityX;
        private Integer velocityY;

        public MovingPoint(Integer x, Integer y, Integer velocityX, Integer velocityY) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
        }

        public Integer getX() {
            return x;
        }

        public void setX(Integer x) {
            this.x = x;
        }

        public Integer getY() {
            return y;
        }

        public void setY(Integer y) {
            this.y = y;
        }

        public Integer getVelocityX() {
            return velocityX;
        }

        public Integer getVelocityY() {
            return velocityY;
        }
    }
}

