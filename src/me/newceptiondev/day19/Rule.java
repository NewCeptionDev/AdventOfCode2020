package me.newceptiondev.day19;

import java.util.*;

public class Rule {

    private final int id;
    private Set<List<Integer>> subRules = new HashSet<>();
    private char literal;

    public Rule(String description) {
        String[] idAndValues = description.split(":");

        id = Integer.parseInt(idAndValues[0]);

        if(idAndValues[1].contains("\"")) {
            literal = parseLiteral(idAndValues[1].trim());
        } else {
            subRules = parseSubRules(idAndValues[1].trim());
        }
    }

    private char parseLiteral(String value) {
        return value.charAt(1);
    }

    private Set<List<Integer>> parseSubRules(String values) {
        Set<List<Integer>> subRules = new HashSet<>();

        String[] rules = values.split("\\|");

        for(String rule : rules) {
            List<Integer> successiveRules = new ArrayList<>();

            String[] ruleValues = rule.trim().split(" ");

            for(String ruleValue : ruleValues) {
                successiveRules.add(Integer.parseInt(ruleValue));
            }

            subRules.add(successiveRules);
        }

        return subRules;
    }

    public String buildRegex(Map<Integer, Rule> rules) {

        if(subRules.isEmpty()) {
            return literal + "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("(");

        for(List<Integer> subRule : subRules) {
            if(subRule.contains(id)) {
                if(subRule.indexOf(id) == subRule.size() - 1) {
                    builder.append("(");
                    for(Integer rule : subRule) {
                        if(rule != id) {
                            builder.append(rules.get(rule).buildRegex(rules));
                        }
                    }
                    builder.append(")+");
                } else {
                    builder.append("(");
                    for(Integer rule : subRule) {
                        if(rule != id) {
                            builder.append("(").append(rules.get(rule).buildRegex(rules)).append(")+");
                        }
                    }
                    builder.append(")");
                }
            } else {
                builder.append("(");
                for(Integer rule : subRule) {
                    builder.append(rules.get(rule).buildRegex(rules));
                }
                builder.append(")");
            }
            builder.append("|");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");

        return builder.toString();
    }

    public int getId() {
        return id;
    }
}
