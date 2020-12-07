package me.newceptiondev.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * Reads a Textfile from the Inputs Folder with the given Name
     *
     * @param filename Name of File
     * @return List of all Lines
     */
    public static List<String> readFileAsListOfLines(String filename){
        try {
            return Files.readAllLines(Paths.get("inputs/" + filename + ".txt"));
        } catch (IOException e) {
            System.err.println("There was an Error reading the File.");
            return new ArrayList<>();
        }
    }

}
