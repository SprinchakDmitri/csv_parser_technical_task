package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SqlQueryBuilder {

    String createInsertQuery(String tableName, List<String> columnNames, List<List<String>> values) {
        StringBuilder query = new StringBuilder(
                "INSERT INTO TABLE " + tableName + " (");
        for (String name : columnNames) {
            query.append(name).append(",");
        }
        query.deleteCharAt(query.length() - 1).append(")").append(" VALUES ");

        for (List<String> sublist : values) {
            query.append("(");
            for (String field : sublist) {
                if (isNumeric(field)) query.append(field).append(",");
                else query.append("'").append(field).append("',");
            }
            query.deleteCharAt(query.length() - 1).append("),");
        }
        query.deleteCharAt(query.length() - 1).append(";");
        return query.toString();
    }

    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}
