package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//        Long flightId = 2L;
//        List<Long> result = getTicketsByFlightId(flightId);
//        System.out.println(result);
//        List<Long> flightsBetween = getFlightsBetween(LocalDate.of(2020, 1, 1).atStartOfDay(),
//                LocalDateTime.now());
//        System.out.println(flightsBetween);
        try {
            checkMetaData();
        } finally {
            ConnectionManager.closePool();
        }
    }

    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        List<Long> result = new ArrayList<>();
        String sql = """
                SELECT id
                FROM flight
                WHERE departure_date BETWEEN ? AND ?
                """;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFetchSize(50);
            preparedStatement.setQueryTimeout(10);
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getLong("id"));
            }
        }

        return result;
    }

    private static List<Long> getTicketsByFlightId(Long flight_id) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = ?
                """;
        List<Long> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, flight_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getObject("id", Long.class));
            }
        }
        return result;
    }

    private static void checkMetaData() throws SQLException {
        try (Connection connection = ConnectionManager.get()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                String catalog = catalogs.getString(1);
                ResultSet schemas = metaData.getSchemas();
                while (schemas.next()) {
                    String schema = schemas.getString("TABLE_SCHEM");
                    ResultSet tables = metaData.getTables(catalog, schema, "%", null);
                    if (schema.equals("public")) {
                        while (tables.next()) {
                            System.out.println(tables.getString("TABLE_NAME"));
                            System.out.println(metaData.getColumns(catalog, schema, "TABLE_NAME", null));
                        }
                    }
                }
            }
        }
    }

}
