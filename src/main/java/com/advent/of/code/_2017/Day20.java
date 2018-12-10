package com.advent.of.code._2017;

import java.util.*;
import java.util.stream.Collectors;

public class Day20 {
    public static void main(String [] args){
        List<Particle> particles = new ArrayList<>();
        String[] lines = Util.loadFileAsString("day20.txt")
                .split("\n");

        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            String[] elements = line.split(", ");
            String[] positionELements = elements[0].substring(elements[0].indexOf('<') + 1, elements[0].indexOf('>'))
                    .split(",");
            String[] velocityElements = elements[1].substring(elements[1].indexOf('<') + 1, elements[1].indexOf('>'))
                    .split(",");

            String[] accelerationElements = elements[2].substring(elements[2].indexOf('<') + 1, elements[2].indexOf('>'))
                    .split(",");

            Coordinates position = new Coordinates(Integer.parseInt(positionELements[0]), Integer.parseInt(positionELements[1]), Integer.parseInt(positionELements[2]));
            Coordinates velocity = new Coordinates(Integer.parseInt(velocityElements[0]), Integer.parseInt(velocityElements[1]), Integer.parseInt(velocityElements[2]));
            Coordinates acceleration = new Coordinates(Integer.parseInt(accelerationElements[0]), Integer.parseInt(accelerationElements[1]), Integer.parseInt(accelerationElements[2]));

            Particle particle = new Particle(i, position, velocity, acceleration);
            particles.add(particle);
        }


        System.out.println("Total particles: " + particles.size());


        Particle min = getMin(particles);

        System.out.println("Min Particle: " + min.getId());

        int ticks = 1000;
        List<Particle> survivors = simulateMovementAndCollisions(particles, ticks);
        System.out.println("After " + ticks + " Movements " + survivors.size() + " particles survived" );
    }

    private static List<Particle> simulateMovementAndCollisions(List<Particle> particles, int movements){
        for(int i = 0; i < movements; i++){
            Map<Coordinates, Set<Particle>> positions = new HashMap<>();
            particles.forEach(p -> {
                update(p);
                Coordinates updatedPos = p.getPosition();
                Set<Particle> particlesWithSamePos = positions.get(updatedPos);

                if(particlesWithSamePos == null){
                    particlesWithSamePos = new HashSet<>();
                }

                particlesWithSamePos.add(p);
                positions.put(updatedPos, particlesWithSamePos);

            });

            List<Particle> collisions = positions.values().stream().filter(collidedParticles -> collidedParticles.size() > 1)
                .flatMap(Collection::stream).collect(Collectors.toList());

            particles.removeAll(collisions);
        }

        return particles;
    }

    private static Particle getMin(List<Particle> particles){
        return particles.stream().sorted(Comparator
                .comparing(Particle::getAbsAcceleration)
                .thenComparing(Particle::getAbsVelocity)
                .thenComparing(Particle::getAbsPosition))
                .collect(Collectors.toList()).get(0);
    }

    public static void update(Particle particle){
        particle.getVelocity().setX(particle.getVelocity().getX() + particle.getAcceleration().getX());
        particle.getVelocity().setY(particle.getVelocity().getY() + particle.getAcceleration().getY());
        particle.getVelocity().setZ(particle.getVelocity().getZ() + particle.getAcceleration().getZ());

        particle.getPosition().setX(particle.getPosition().getX() + particle.getVelocity().getX());
        particle.getPosition().setY(particle.getPosition().getY() + particle.getVelocity().getY());
        particle.getPosition().setZ(particle.getPosition().getZ() + particle.getVelocity().getZ());
    }

    public static class Particle{
        int id;
        Coordinates position;
        Coordinates velocity;
        Coordinates acceleration;

        public Particle(int id, Coordinates position, Coordinates velocity, Coordinates acceleration) {
            this.id = id;
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
        }

        public int getAbsAcceleration(){
            return Math.abs(this.getAcceleration().getX()) + Math.abs(this.getAcceleration().getY()) + Math.abs(this.getAcceleration().getZ());
        }

        public int getAbsPosition(){
            return Math.abs(this.getPosition().getX()) + Math.abs(this.getPosition().getY()) + Math.abs(this.getPosition().getZ());
        }

        public int getAbsVelocity(){
            return Math.abs(this.getVelocity().getX()) + Math.abs(this.getVelocity().getY()) + Math.abs(this.getVelocity().getZ());
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Coordinates getPosition() {
            return position;
        }

        public void setPosition(Coordinates position) {
            this.position = position;
        }

        public Coordinates getVelocity() {
            return velocity;
        }

        public void setVelocity(Coordinates velocity) {
            this.velocity = velocity;
        }

        public Coordinates getAcceleration() {
            return acceleration;
        }

        public void setAcceleration(Coordinates acceleration) {
            this.acceleration = acceleration;
        }
    }

    public static class Coordinates{
        int X;
        int Y;
        int Z;

        public Coordinates(int x, int y, int z) {
            X = x;
            Y = y;
            Z = z;
        }

        public int getX() {
            return X;
        }

        public void setX(int x) {
            X = x;
        }

        public int getY() {
            return Y;
        }

        public void setY(int y) {
            Y = y;
        }

        public int getZ() {
            return Z;
        }

        public void setZ(int z) {
            Z = z;
        }

        public boolean equals(Object obj){
            if (obj instanceof Coordinates){
                Coordinates c = (Coordinates) obj;
                return this.X == c.getX() && this.Y == c.getY() && this.Z == c.getZ();
            } else {
                return false;
            }
        }

        public int hashCode(){
            return X + Y + Z;
        }
    }
}
