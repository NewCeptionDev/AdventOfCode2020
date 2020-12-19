package me.newceptiondev.day19;

import java.util.*;

public class Rule {

    private int id;
    private Set<List<Integer>> subRules = new HashSet<>();
    private char literal;

    public Rule(String description) {
        String[] idAndValues = description.split(":");

        this.id = Integer.parseInt(idAndValues[0]);

        if (idAndValues[1].contains("\"")) {
            this.literal = parseLiteral(idAndValues[1].trim());
        } else {
            this.subRules = parseSubRules(idAndValues[1].trim());
        }
    }

    private char parseLiteral(String value) {
        return value.charAt(1);
    }

    private Set<List<Integer>> parseSubRules(String values) {
        Set<List<Integer>> subRules = new HashSet<>();

        String[] rules = values.split("\\|");

        for (String rule : rules) {
            List<Integer> successiveRules = new ArrayList<>();

            String[] ruleValues = rule.trim().split(" ");

            for (String ruleValue : ruleValues) {
                successiveRules.add(Integer.parseInt(ruleValue));
            }

            subRules.add(successiveRules);
        }

        return subRules;
    }

    public int getId() {
        return id;
    }

    public Set<String> buildPossibleLiterals(Map<Integer, Rule> rules) {
        if (subRules.size() == 0) {
            return Set.of(literal + "");
        } else {
            Set<String> ruleCombinations = new HashSet<>();
            for (List<Integer> subRule : subRules) {
                List<Set<String>> ruleResults = new ArrayList<>();

                for(Integer ruleId : subRule){
                    ruleResults.add(rules.get(ruleId).buildPossibleLiterals(rules));
                }

                if(ruleResults.size() > 1) {
                    ruleCombinations.addAll(getPossibilities(ruleResults));
                } else {
                    ruleCombinations.addAll(ruleResults.get(0));
                }
            }

            return ruleCombinations;
        }
    }

    private Set<String> getPossibilities(List<Set<String>> sets){
        Set<String> possibilities = new HashSet<>();

        if(sets.size() == 2){
            for (String setOnePossibility : sets.get(0)) {
                for (String setTwoPossibility : sets.get(1)) {
                    possibilities.add(setOnePossibility + setTwoPossibility);
                }
            }

            return possibilities;
        } else {
            return getPossibilities(List.of(sets.get(0), getPossibilities(sets.subList(1, sets.size()))));
        }
    }
}
