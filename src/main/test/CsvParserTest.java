import com.company.CsvParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class CsvParserTest {
    private static final String PATH = "test.csv";
    String csv = """
            firstname,lastname,email,age
            John,Smith,js@gmail.com,53
            Anna-Maria,"Bertran, Ecsuadrada",am@gmail.com,24
            Andrew,andrew@gmail.com,17
            """;
    static File file = new File(PATH);
    public CsvParser parser;
    Pattern splitPattern = Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");

    @Before
    public void init() throws IOException {
        if (file.createNewFile()) {
            FileWriter fileWriter = new FileWriter(PATH);
            fileWriter.write(csv);
            fileWriter.close();
        }
        parser = new CsvParser(PATH);
    }

    @After
    public void teardown() {
        Path path = FileSystems.getDefault().getPath("./test.csv");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shoulReturnCsvBody() throws IOException {
        List<String> list = parser.getCsvLinesFromFile(PATH);

        assertEquals(3, list.size());
        assertAll(
                () -> assertEquals("John,Smith,js@gmail.com,53", list.get(0)),
                () -> assertEquals("Anna-Maria,\"Bertran, Ecsuadrada\",am@gmail.com,24", list.get(1)),
                () -> assertEquals("Andrew,andrew@gmail.com,17", list.get(2))
        );
    }

    @Test
    public void shouldValidateCsv() {
        assertTrue(parser.validateCsvLine(
                splitPattern.split("John,Smith,js@gmail.com,53")
                , parser.getColumnNames().size()));
        assertTrue(parser.validateCsvLine(splitPattern.split("Anna-Maria,\"Bertran, Ecsuadrada\",am@gmail.com,24"),
                parser.getColumnNames().size()));
        assertFalse(parser.validateCsvLine(splitPattern.split("Andrew,andrew@gmail.com,17"),
                parser.getColumnNames().size()));
    }

    @Test
    public void shouldSplitAllLines() throws IOException {
        List<List<String>> list = parser.splitAllLines(PATH);

        assertEquals(2, list.size());
        assertAll(
                () -> assertEquals("John", list.get(0).get(0)),
                () -> assertEquals("Smith", list.get(0).get(1)),
                () -> assertEquals("js@gmail.com", list.get(0).get(2)),
                () -> assertEquals("53", list.get(0).get(3)),
                () -> assertEquals("Anna-Maria", list.get(1).get(0)),
                () -> assertEquals("\"Bertran, Ecsuadrada\"", list.get(1).get(1)),
                () -> assertEquals("am@gmail.com", list.get(1).get(2), "am@gmail.com"),
                () -> assertEquals("24", list.get(1).get(3))
        );
    }

    @Test
    public void shouldGetCsvHeader() throws IOException {
        List<String> list = parser.getCsvColumnNamesFromFile(PATH);
        assertAll(
                () -> assertEquals("firstname", list.get(0)),
                () -> assertEquals("lastname", list.get(1)),
                () -> assertEquals("email", list.get(2)),
                () -> assertEquals("age", list.get(3))
        );
    }
}
