package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class FilesUtils {
    public static boolean checkIfFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void checkIfFileIsCsv(String path) {
        Pattern pattern = Pattern.compile(".*\\.(csv|CSV)$");
        if (!pattern.matcher(path).matches()) throw new IllegalArgumentException();
    }

    public static void checkIfFileIsDb(String path) {
        Pattern pattern = Pattern.compile(".*\\.(db)$");
        if (!pattern.matcher(path).matches()) throw new IllegalArgumentException();
    }

    public static void createBadDataFile(List<String> content) {
        String fileName = new SimpleDateFormat("'<bad-data>'yyyy-MM-dd-HH-mm-ss'.csv'").format(new Date());
        StringBuilder sb = new StringBuilder();
        for (String line: content) {
            sb.append(line).append("\n");
        }
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(sb.toString());
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Bad data file creation error");
        }
    }
}
