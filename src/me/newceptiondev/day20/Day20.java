package me.newceptiondev.day20;

import me.newceptiondev.util.FileUtil;
import me.newceptiondev.util.Tuple;

import java.util.*;

import static me.newceptiondev.day20.Tile.*;

public class Day20 {

    private static final String FILE_NAME = "day20Task1Input";

    public static void main(String[] args) {
        List<String> inputs = FileUtil.readFileAsListOfLines(FILE_NAME);

        new Day20(inputs);
    }

    List<Transformation> transformations = List.of(new Transformation(Transformation.ACTION.NONE, List.of(0, 1, 2, 3)),
                                                   new Transformation(Transformation.ACTION.ROTATE,
                                                                      List.of(1, 3, 0, 2)),
                                                   new Transformation(Transformation.ACTION.ROTATE,
                                                                      List.of(3, 2, 1, 0)),
                                                   new Transformation(Transformation.ACTION.ROTATE,
                                                                      List.of(2, 0, 3, 1)),
                                                   new Transformation(Transformation.ACTION.FLIP, List.of(2, 3, 0, 1)),
                                                   new Transformation(Transformation.ACTION.ROTATE,
                                                                      List.of(3, 1, 2, 0)),
                                                   new Transformation(Transformation.ACTION.ROTATE,
                                                                      List.of(1, 0, 3, 2)),
                                                   new Transformation(Transformation.ACTION.ROTATE,
                                                                      List.of(0, 2, 1, 3)));

    List<Integer> opposite = List.of(3, 2, 1, 0);

    List<List<Character>> monster = List.of(Arrays.stream("                  # ".split("")).map(s -> s.charAt(0)).toList(),
                                            Arrays.stream("#    ##    ##    ###".split("")).map(s -> s.charAt(0)).toList(),
                                            Arrays.stream(" #  #  #  #  #  #   ".split("")).map(s -> s.charAt(0)).toList());

    public Day20() {
    }

    private Day20(List<String> inputs) {
        System.out.println("Task 1: " + task1(inputs));
        System.out.println("Task 2: " + task2(inputs));
    }

    /**
     * Task 1
     *
     * @param inputs List of String
     *
     * @return
     */
    public long task1(List<String> inputs) {
        List<Tile> tiles = parseTiles(inputs);

        List<Tile> cornerTiles = findCornerTiles(tiles);

        return cornerTiles.stream().map(Tile::getId).reduce(1L, (i1, i2) -> i1 * i2);
    }

    /**
     * Task 2
     *
     * @param inputs List of String
     *
     * @return
     */
    public long task2(List<String> inputs) {
        List<Tile> tiles = parseTiles(inputs);

        int squareSize = (int) Math.sqrt(tiles.size());

        List<Tile> corners = findCornerTiles(tiles);
        Map<String, Tuple<Tile, Placement>> map = placeTiles(tiles, corners.get(1), squareSize);

        List<String> image = buildImage(map, squareSize, corners.get(0).getTileElements().size());

        image = searchAndReplaceMonster(image);

        return image.stream().map(line -> Arrays.stream(line.split("")).filter(s -> s.equals("#")).count()).reduce(0L, Long::sum);
    }

    public List<Tile> parseTiles(List<String> input) {
        List<Tile> tiles = new ArrayList<>();

        List<String> current = new ArrayList<>();

        for(String line : input) {
            if(line.isEmpty()) {
                tiles.add(new Tile(current));
                current = new ArrayList<>();
            } else {
                current.add(line);
            }
        }
        tiles.add(new Tile(current));

        return tiles;
    }

    private List<Tile> findCornerTiles(List<Tile> tiles) {
        List<Tile> corners = new ArrayList<>();

        for(Tile tile : tiles) {
            List<String> tileEdges = tile.getEdges();
            for(Tile tile2 : tiles) {
                List<String> tile2Edges = tile2.getEdges();
                if(tile.getId() != tile2.getId()) {
                    for(int side = 0; side < 4; side++) {
                        if(tile2Edges.contains(tileEdges.get(side))) {
                            tile.getConnections().put(side, tile2.getId());
                            break;
                        }
                    }
                }
            }

            if(tile.getConnections().keySet().size() == 2) {
                corners.add(tile);
            }
        }

        return corners;
    }

    private Map<String, Tuple<Tile, Placement>> placeTiles(List<Tile> tiles, Tile tile, int size) {
        Placement firstPlacement = Placement.first(tile, size);
        Queue<String> queue = new LinkedList<>();
        queue.add(firstPlacement.asString());
        Set<Long> visited = new HashSet<>();
        visited.add(tile.getId());
        Map<String, Tuple<Tile, Placement>> map = new HashMap<>();
        map.put(firstPlacement.asString(), new Tuple<>(tile, firstPlacement));
        while(!queue.isEmpty()) {
            String placementAsString = queue.poll();
            Tuple<Tile, Placement> mapElement = map.get(placementAsString);
            List<String> edges = mapElement.getX().getEdges();
            Map<Integer, Long> connections = mapElement.getX().getConnections();
            for(int side : connections.keySet()) {
                if(!visited.contains(connections.get(side))) {
                    visited.add(connections.get(side));
                    Tile connectedTile =
                        tiles.stream().filter(t -> t.getId() == connections.get(side)).findFirst().get();

                    int currentSide = mapElement.getY().getRotationMap().indexOf(side);
                    String edge = edges.get(currentSide);
                    int oppositeSide = opposite.get(currentSide);

                    for(Transformation transformation : transformations) {
                        List<String> updatedData = transformation.action == Transformation.ACTION.ROTATE ? connectedTile.rotate90() : transformation.action == Transformation.ACTION.FLIP ? connectedTile.flip() : connectedTile.getTileElements();
                        connectedTile.setTileElements(updatedData);
                        List<String> updatedEdges = getEdges(updatedData);

                        if(updatedEdges.get(oppositeSide).equals(edge)) {
                            Placement placement = mapElement.getY().connect(currentSide, transformation.rotationMap);
                            queue.add(placement.asString());
                            connectedTile.setEdges(updatedEdges);
                            map.put(placement.asString(), new Tuple<>(connectedTile, placement));
                            break;
                        }
                    }
                }
            }
        }

        return map;
    }

    private List<String> buildImage(Map<String, Tuple<Tile, Placement>> map, int squareSize, int tileImageSize) {
        int imageWidth = squareSize * (tileImageSize - 2);
        List<String> image = new ArrayList<>();

        for(int i = 0; i < imageWidth; i++) {
            image.add("");
        }

        for(int y = 0; y < squareSize; y++) {
            for(int x = 0; x < squareSize; x++) {
                Tuple<Tile, Placement> mapElement = map.get(x + ":" + y);
                List<String> tileData = mapElement.getX().getTileElements();

                for(int innerY = 1; innerY < tileData.size() - 1; innerY++) {
                    image.set((y * (tileImageSize - 2)) + innerY - 1, image.get((y * (tileImageSize - 2)) + innerY - 1) + tileData.get(innerY).substring(1, tileData.get(innerY).length() - 1));
                }
            }
        }

        return image;
    }

    private List<String> searchAndReplaceMonster(List<String> image) {
        List<String> data = image;
        for(Transformation transformation : transformations) {
            data = transformation.action == Transformation.ACTION.ROTATE ? rotate90(data) : transformation.action == Transformation.ACTION.FLIP ? flip(data) : data;

            List<String> newData = new ArrayList<>();
            boolean changed;
            do {
                changed = false;
                for(int y = 0; y < data.size() && !changed; y++) {
                    for(int x = 0; x < data.get(y).length() && !changed; x++) {
                        if(matchesMonster(data, x, y)) {
                            newData = updateImageWithMonster(data, x, y);
                            changed = true;
                        }
                    }
                }
                if(changed) {
                    data = newData;
                }
            } while(changed);
        }

        return data;
    }

    private boolean matchesMonster(List<String> image, int x, int y) {
        boolean matches = true;

        for(int innerY = 0; innerY < monster.size() && matches; innerY++) {
            for(int innerX = 0; innerX < monster.get(innerY).size() && matches; innerX++) {
                matches = monster.get(innerY).get(innerX) != '#' || (image.size() > (y + innerY) && image.get(y + innerY).length() > (x + innerX) && image.get(y + innerY).charAt(x + innerX) == monster.get(innerY).get(innerX));
            }
        }

        return matches;
    }

    private List<String> updateImageWithMonster(List<String> image, int x, int y) {
        List<String> newImage = new ArrayList<>(image);

        for(int innerY = 0; innerY < monster.size(); innerY++) {
            for(int innerX = 0; innerX < monster.get(innerY).size(); innerX++) {
                if(monster.get(innerY).get(innerX) == '#') {
                    newImage.set(y + innerY, newImage.get(y + innerY).substring(0, x + innerX) + "O" + newImage.get(y + innerY).substring(x + innerX + 1));
                }
            }
        }

        return newImage;
    }
}

