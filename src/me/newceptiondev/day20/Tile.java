package me.newceptiondev.day20;

import java.util.*;

public class Tile {

    private long id;
    private List<String> tileElements;
    private List<String> edges;
    private Map<Integer, Long> connections;

    public Tile(List<String> input) {

        String[] titleSplit = input.get(0).split(" ");

        id = Long.parseLong(titleSplit[1].substring(0, titleSplit[1].length() - 1));
        tileElements = input.subList(1, input.size());
        edges = getEdges(tileElements);
        edges.addAll(getEdges().stream().map(s -> new StringBuilder(s).reverse().toString()).toList());
        connections = new HashMap<>();
    }

    public List<String> getEdges() {
        return edges;
    }

    public static List<String> getEdges(List<String> tileElements) {
        List<String> edges = new ArrayList<>();

        edges.add(tileElements.get(0));
        edges.add(tileElements.stream().map(y -> y.charAt(0)).map(c -> c + "").reduce("", String::concat));
        edges.add(tileElements.stream().map(y -> y.charAt(y.length() - 1)).map(c -> c + "").reduce("", String::concat));
        edges.add(tileElements.get(tileElements.size() - 1));

        return edges;
    }

    public long getId() {
        return id;
    }

    public Map<Integer, Long> getConnections() {
        return connections;
    }

    public List<String> rotate90() {
        return rotate90(tileElements);
    }

    public static List<String> rotate90(List<String> in) {
        List<String> result = new ArrayList<>();

        for(int i = 0; i < in.get(0).length(); i++) {
            result.add("");
        }

        for(int y = 0; y < in.size(); y++) {
            for(int x = 0; x < in.get(y).length(); x++) {
                result.set(x, in.get(y).charAt(x) + result.get(x));
            }
        }

        return result;
    }

    public static List<String> flip(List<String> in) {
        List<String> result = new ArrayList<>();

        for(String s : in) {
            result.add(new StringBuilder(s).reverse().toString());
        }

        return result;
    }

    public List<String> flip() {
        return flip(tileElements);
    }

    public List<String> getTileElements() {
        return tileElements;
    }

    public void setTileElements(final List<String> tileElements) {
        this.tileElements = tileElements;
    }

    public void setEdges(final List<String> edges) {
        this.edges = edges;
    }
}
