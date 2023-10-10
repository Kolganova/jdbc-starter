package com.dmdev.jdbc.starter.dao;

import com.dmdev.jdbc.starter.exception.DaoException;
import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDao {

    private static final TicketDao INSTANCE = new TicketDao();
    private static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?;
            """;
     private static final String SAVE_SQL = """
            INSERT INTO ticket(passenger_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?, ?, ?, ?, ?);
            """;


    private TicketDao() {
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    
//  in progress
//
//    public Ticket save (Ticket ticket) {
//        try (Connection connection = ConnectionManager.get()) {
//
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//    }
}
