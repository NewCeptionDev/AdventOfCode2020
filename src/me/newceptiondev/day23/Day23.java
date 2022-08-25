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
        int max = 1000000;

        Cup[] cupMap = new Cup[max + 1];

        Cup head = new Cup(circle.get(0));
        cupMap[head.value] = head;
        Cup tail = head;

        for(int i = 1; i < circle.size(); i++) {
            Cup c = new Cup(circle.get(i));
            cupMap[c.value] = c;
            c.next = head;
            tail.next = c;
            tail = c;
        }

        for(int i = Collections.max(circle) + 1; i <= max; i++) {
            Cup c = new Cup(i);
            cupMap[c.value] = c;
            c.next = head;
            tail.next = c;
            tail = c;
        }

        for(int i = 0; i < 10000000; i++) {
            Cup current = head;
            Cup c1 = current.next;
            Cup c3 = c1.next.next;

            head.next = c3.next;

            int targetIndex = current.value == 1 ? max : current.value - 1;
            while(targetIndex == c1.value || targetIndex == c1.next.value || targetIndex == c3.value) {
                targetIndex--;
                targetIndex = targetIndex < 1 ? max : targetIndex;
            }

            Cup target = cupMap[targetIndex];

            c3.next = target.next;
            target.next = c1;
            tail = c3;
            head = current.next;
        }

        Cup one = cupMap[1];
        return (long)one.next.value * one.next.next.value;
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
}
