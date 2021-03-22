package com.company;

import java.io.File;
import java.util.regex.Pattern;

public class FilesUtils {
    boolean checkIfFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public void checkIfFileIsCsv(String path) {
        Pattern pattern = Pattern.compile(".*\\.(csv|CSV)$");
        if (!pattern.matcher(path).matches()) throw new IllegalArgumentException();
    }

    public void checkIfFileIsDb(String path) {
        Pattern pattern = Pattern.compile(".*\\.(db)$");
        if (!pattern.matcher(path).matches()) throw new IllegalArgumentException();
    }
}
