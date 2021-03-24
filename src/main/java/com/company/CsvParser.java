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


public class CsvParser {

    List<String> columnNames;
    List<String> badData = new ArrayList<>();
    int failedRecords = 0;
    int successRecords = 0;
    int allRecords = 0;
    Pattern splitPattern = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");

    public CsvParser(String pathToTheFile) throws IOException {
        columnNames = getCsvColumnNamesFromFile(pathToTheFile);
    }

    public List<String> getCsvLinesFromFile(String path) throws IOException {
        return Files
                .lines(Paths.get(path))
                .skip(1)
                .collect(Collectors.toList());
    }

    public List<String> getCsvColumnNamesFromFile(String path) throws IOException {
        Optional<String> firstLine = Files.lines(Paths.get(path)).findFirst();
        if (firstLine.isPresent()) {
            return Arrays.asList(firstLine.get().split(","));
        } else throw new IllegalArgumentException();
    }

    public boolean validateCsvLine(String[] line, int columnNumber) {
        return line.length == columnNumber;
    }

    private List<String> splitCsvLine(String line) {
        allRecords++;
        String[] splited = splitPattern.split(line);
        if (validateCsvLine(splited, columnNames.size())) {
            successRecords++;
            return Arrays.asList(splited);
        } else {
            failedRecords++;
            badData.add(line);
            return null;
        }
    }

    public List<List<String>> splitAllLines(String path) throws IOException {
        List<String> unsplitedLines = getCsvLinesFromFile(path);
        List<List<String>> resultList = new ArrayList<>();
        List<String> temp;

        for (String line : unsplitedLines) {
            temp = splitCsvLine(line);
            if (temp != null)
                resultList.add(temp);
        }
        if (badData.size() != 0) {
            FilesUtils.createBadDataFile(badData);
        }
        return resultList;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public String printReport() {
        return "All records: " + allRecords + "\n" +
                "Success records: " + successRecords + "\n" +
                "Failed records: " + failedRecords;
    }
}

