package me.newceptiondev.day23;

import java.util.*;

public class Day23 {

    public static final String input = "284573961";

    public static void main(String[] args) {
        new Day23(input);
    }

    public Day23() {
    }

    public Day23(String input) {
        System.out.println("Task 1: " + task1(input));
        System.out.println("Task 2: " + task2(input));
    }

    /**
     * Task 1
     *
     * @param input String
     * @return
     */
    public String task1(final String input) {
        List<Integer> circle = parseCups(input);
        int currentCup = circle.get(0);

        for(int i = 0; i < 100; i++){
            doMove(circle, currentCup);
            currentCup = circle.indexOf(currentCup) < circle.size() - 1 ? circle.get(circle.indexOf(currentCup) + 1) : circle.get(0);
        }

        return printResult(circle);
    }

    /**
     * Task 2
     *
     * @param input List of String
     * @return
     */
    public long task2(final String input) {
        List<Integer> circle = parseCups(input);
        circle.addAll(fillCircle(circle));
        int currentCup = circle.get(0);

        for(int i = 0; i < 10000000; i++){
            doMove(circle, currentCup);
            currentCup = circle.indexOf(currentCup) < circle.size() - 1 ? circle.get(circle.indexOf(currentCup) + 1) : circle.get(0);
        }

        return multiplyTwoCupsBehind1(circle);
    }

    private List<Integer> parseCups(String input){
        List<Integer> circle = new ArrayList<>();

        for(char cup : input.toCharArray()){
            circle.add(Integer.parseInt(cup + ""));
        }

        return circle;
    }

    private void doMove(List<Integer> circle, int currentCup) {
        List<Integer> pickUpCups = new ArrayList<>();

        Iterator<Integer> it = circle.listIterator(circle.indexOf(currentCup));
        it.next();

        for(int i = 0; i < 3; i++) {
            if(!it.hasNext()) {
                it = circle.listIterator();
            }

            pickUpCups.add(it.next());
            it.remove();
        }

        int insertCup = findInsertCup(circle, currentCup);

        for(int i = 0; i < pickUpCups.size(); i++) {
            circle.add(circle.indexOf(insertCup) + 1 + i, pickUpCups.get(i));
        }
    }

    private int findInsertCup(List<Integer> circle, int currentCup) {
        int temporaryNextCup = currentCup - 1;

        while(!circle.contains(temporaryNextCup)) {
            if(temporaryNextCup > 1) {
                temporaryNextCup--;
            } else {
                temporaryNextCup = circle.stream().max(Integer::compareTo).get();
            }
        }


        return temporaryNextCup;
    }

    private String printResult(List<Integer> circle) {
        Iterator<Integer> iterator = circle.listIterator(circle.indexOf(1));

        StringBuilder result = new StringBuilder();
        Integer cup;

        iterator.next();


        while((cup = iterator.next()) != 1) {
            result.append(cup);

            if(!iterator.hasNext()) {
                iterator = circle.listIterator();
            }
        }

        return result.toString();
    }

    private Collection<Integer> fillCircle(List<Integer> currentCircle) {
        int highestNumber = currentCircle.stream().max(Integer::compareTo).get();

        List<Integer> toAdd = new ArrayList<>();

        for(int i = highestNumber + 1; i <= 1000000; i++) {
            toAdd.add(highestNumber);
        }

        return toAdd;
    }

    private long multiplyTwoCupsBehind1(List<Integer> circle) {
        Iterator<Integer> iterator = circle.listIterator(circle.indexOf(1));
        iterator.next();

        long result = 1;

        for(int i = 0; i < 2; i++){
            if(!iterator.hasNext()){
                iterator = circle.listIterator();
            }

            result *= iterator.next();
        }

        return result;
    }
}
