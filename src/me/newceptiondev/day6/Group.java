package me.newceptiondev.day6;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Group {

    private final List<String> answers;

    public Group(final List<String> answers) {
        this.answers = answers;
    }

    /**
     * Returns the Unions of all Answers
     *
     * @return Set of Character
     */
    public Set<Character> getUnionOfAnswers() {
        Set<Character> union = new HashSet<>();

        for (String answer : answers) {
            union.addAll(mapStringToCharSet(answer));
        }

        return union;
    }

    /**
     * Returns the Intersection of all Answers
     *
     * @return Set of Character
     */
    public Set<Character> getIntersectionOfAnswers() {
        Set<Character> intersection = null;

        for (String answer : answers) {
            if (intersection == null) {
                intersection = mapStringToCharSet(answer);
            } else {
                intersection.retainAll(mapStringToCharSet(answer));
            }
        }

        return intersection;
    }

    /**
     * Maps a String to a Set of Character
     *
     * @param input String
     * @return Set of Character
     */
    private Set<Character> mapStringToCharSet(final String input) {
        Set<Character> characterSet = new HashSet<>();

        for (char character : input.toCharArray()) {
            characterSet.add(character);
        }

        return characterSet;
    }
}
