package me.newceptiondev.day6;

import me.newceptiondev.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Day6 {

    public static final String fileName = "day6Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(fileName);

        new Day6(inputs);
    }

    public Day6(){
    }

    public Day6(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     * @return Sum of Answers per Group that were answered Yes by at least one
     */
    public int task1(final List<String> inputs) {
        List<Group> groups = mapInputToGroups(inputs);

        return groups.stream().map(Group::getUnionOfAnswers).map(Set::size).reduce(0, Integer::sum);
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     * @return Sum of Answers per Group that were answered Yes by every Group Member
     */
    public int task2(final List<String> inputs){
        List<Group> groups = mapInputToGroups(inputs);

        return groups.stream().map(Group::getIntersectionOfAnswers).map(Set::size).reduce(0, Integer::sum);
    }

    /**
     * Maps the Input to Groups
     *
     * @param inputs List of String
     * @return List of Group
     */
    private List<Group> mapInputToGroups(final List<String> inputs) {
        List<Group> groups = new ArrayList<>();

        List<String> currentGroup = new ArrayList<>();

        for (String line : inputs) {
            if(line.length() > 0){
                currentGroup.add(line);
            } else {
                //Adds the Group and resets the currentGroup
                groups.add(new Group(currentGroup));
                currentGroup = new ArrayList<>();
            }
        }
        //Add last Group
        groups.add(new Group(currentGroup));

        return groups;
    }
}
