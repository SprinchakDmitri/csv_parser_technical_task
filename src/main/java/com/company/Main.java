package com.company;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SqlQueryBuilder qb = new SqlQueryBuilder();
        CsvParser parser;
        Connection connection;
        Statement statement;
        String url;
        String pathToTheFile;

        while (true) {
            try {
                System.out.println("Please enter the absolute path to the database." +
                        "(Like '/home/username/../database.db)'\"");
                url = "jdbc:sqlite:";
                url += scanner.nextLine();

                if (!FilesUtils.checkIfFileExists(url.substring(12)))
                    throw new NoSuchFileException("url");
                FilesUtils.checkIfFileIsDb(url);
                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();
                break;
            } catch (NoSuchFileException | SQLException e) {
                System.out.println("Such database doesn't exist.Please try once again");
            } catch (IllegalArgumentException e) {
                System.out.println("This is not a database. Try once again");
            }
        }
        System.out.println("\uD83D\uDC4D");

        while (true) {
            try {
                System.out.println("Now, please enter the absolute path to the csv file." +
                        "(Like '/home/username/../file.csv) '");
                pathToTheFile = scanner.nextLine();

                if (!FilesUtils.checkIfFileExists(pathToTheFile))
                    throw new NoSuchFileException(pathToTheFile);
                FilesUtils.checkIfFileIsCsv(pathToTheFile);

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
                parser = new CsvParser(pathToTheFile);
                String sql = qb.createInsertQuery(
                        tableName,
                        parser.getColumnNames(),
                        parser.splitAllLines(pathToTheFile)
                );
                statement.executeUpdate(sql);
                break;
            } catch (IOException e) {
                System.out.println("Problems with reading data from file. Please try once again");
            } catch (SQLException e) {
                System.out.println("Such table doesn't exist.Please try once again");
            }
        }
        System.out.println("\uD83D\uDC4D  Table is updated");
        System.out.println(parser.printReport());
    }
}