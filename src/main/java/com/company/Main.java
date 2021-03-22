package com.company;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    static String pathToTheFile = "/home/sprinchak/Downloads/con.csv";


    public static void main(String[] args) throws IOException {

        FilesUtils filesUtils = new FilesUtils();
        Scanner scanner = new Scanner(System.in);
        CsvParser parser = new CsvParser();
        SqlQueryBuilder qb = new SqlQueryBuilder();
        Connection connection;
        Statement statement;
        String url;

        while (true) {
            try {
                System.out.println("Please enter the path to the database." +
                        "(Like '/home/username/../database.db)'\"");
                url = "jdbc:sqlite:";
                url += scanner.nextLine();

                if (!filesUtils.checkIfFileExists(url.substring(12)))
                    throw new NoSuchFileException("url");
                filesUtils.checkIfFileIsDb(url);
                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();
                break;
            } catch (NoSuchFileException | SQLException e) {
                System.out.println("Such database doesn't exist.Please try once again");
            }catch (IllegalArgumentException e){
                System.out.println("This is not a database. Try once again");
            }
        }
        System.out.println("\uD83D\uDC4D");

        while (true) {
            try {
                System.out.println("Now, please enter the absolute path to the csv file." +
                        "(Like '/home/username/../file.csv)'");
                pathToTheFile = scanner.nextLine();

                if (!filesUtils.checkIfFileExists(pathToTheFile))
                    throw new NoSuchFileException(pathToTheFile);
                filesUtils.checkIfFileIsCsv(pathToTheFile);
                break;
            } catch (NoSuchFileException e) {
                System.out.println("Such file doesn't exist.Please try once again");
            } catch (IllegalArgumentException e) {
                System.out.println("File is not a .csv. Please chose another file with .csv extension");
            }
        }
        System.out.println("\uD83D\uDC4D");

        while (true) {
            try {
                System.out.println("Please enter the table name to update.");
                String tableName = scanner.nextLine();

                String sql = qb.createInsertQuery(
                        tableName,
                        parser.getColumnNames(),
                        parser.splitAllLines(pathToTheFile)
                );
                statement.executeUpdate(sql);
                break;
            } catch (Exception e) {
                System.out.println("Such table doesn't exist.Please try once again");
            }
        }
        System.out.println("\uD83D\uDC4D  Table is updated");
    }
}
