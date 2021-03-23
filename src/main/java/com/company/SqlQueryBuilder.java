package com.company;

import java.util.List;
import java.util.regex.Pattern;

public class SqlQueryBuilder {

    private final Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    String createInsertQuery(String tableName, List<String> columnNames, List<List<String>> values) {
        StringBuilder query = new StringBuilder(
                "INSERT INTO " + tableName + " (");
        for (String name : columnNames) {
            query.append(name.trim()).append(",");
        }
        query.deleteCharAt(query.length() - 1).append(")").append(" VALUES ");

        for (List<String> sublist : values) {
            query.append("(");
            for (String field : sublist) {
                if (isNumeric(field)) query.append(field).append(",");
                else {
                    if (field.contains("'"))
                        field = String.join("''", field.split("'"));

                    query.append("'").append(field).append("',");
                }
            }
            query.deleteCharAt(query.length() - 1).append("),");
        }
        query.deleteCharAt(query.length() - 1).append(";");
        return query.toString();
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return numericPattern.matcher(strNum).matches();
    }
}
