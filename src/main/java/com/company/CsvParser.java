package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.company.Main.pathToTheFile;

public class CsvParser {

    List<String> columnNames;

    public CsvParser() throws IOException {
        columnNames = getCsvColumnNamesFromFile(pathToTheFile);
    }

    List<String> getCsvLinesFromFile(String path) throws IOException {
        return Files
                .lines(Paths.get(path))
                .skip(1)
                .collect(Collectors.toList());
    }

    List<String> getCsvColumnNamesFromFile(String path) throws IOException {    //todo add validation for a first line
        Optional<String> firstLine = Files.lines(Paths.get(path)).findFirst();
        if (firstLine.isPresent()) {
            return Arrays.asList(firstLine.get().split(","));
        } else throw new IllegalArgumentException();
    }

    boolean validateCsvLine(String line, int columnNumber) {
        return true;
    }

    List<String> splitCsvLine(String line) {                                    //todo implement split with "," case
        if (validateCsvLine(line, columnNames.size())) {
            return Arrays.asList(line.split(","));
        } else return null;
    }

    List<List<String>> splitAllLines(List<String> unsplitedLines) {
        List<List<String>> resultList = new ArrayList<>();
        for (String line : unsplitedLines) {
            resultList.add(splitCsvLine(line));
        }
        return resultList;
    }
    List<List<String>> splitAllLines(String path) throws IOException {
        List<String> unsplitedLines = getCsvLinesFromFile(path);
        List<List<String>> resultList = new ArrayList<>();
        for (String line : unsplitedLines) {
            resultList.add(splitCsvLine(line));
        }
        return resultList;
    }

    List<String> getLineByIndex(List<List<String>> splitedLines, int lineNumber) {
        if (splitedLines.size() > lineNumber && lineNumber > 0)
            return splitedLines.get(lineNumber);
        else throw new IllegalArgumentException();
    }


    String getElement(List<List<String>> splitedLines, int lineNumber, int elementNumber) {
        if (splitedLines.size() > lineNumber && lineNumber > 0
                && elementNumber < columnNames.size() && elementNumber > 0)
            return splitedLines.get(lineNumber)
                    .get(elementNumber);
        else throw new IllegalArgumentException();
    }
    public List<String> getColumnNames() {
        return columnNames;
    }
}
