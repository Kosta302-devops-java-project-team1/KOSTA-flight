package main.java.com.kostaFlight.repository;

import main.java.com.kostaFlight.dto.Ticket;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TicketDao {
    void insertTicket(Connection con, List<Ticket> list) throws SQLException;
    void deleteTicket(Connection con, long reservationId) throws SQLException;
    List<Ticket> selectByReservationId(Connection con, long reservationId) throws SQLException;
    List<Ticket> selectByReservationId(long reservationId) throws SQLException;
}
