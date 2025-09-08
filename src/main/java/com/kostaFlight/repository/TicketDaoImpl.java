package main.java.com.kostaFlight.repository;

import main.java.com.kostaFlight.common.DBManager;
import main.java.com.kostaFlight.dto.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao{
    @Override
    public void insertTicket(Connection con, List<Ticket> list) throws SQLException{
        PreparedStatement ps = null;
        String sql = "insert into tickets(flight_id, reservation_id, seat_num, passenger, phone_number, passport_number) " +
                "values(?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            for(Ticket t : list){
                ps.setLong(1, t.getFlightId());
                ps.setLong(2, t.getReservationId());
                ps.setString(3, t.getSeats());
                ps.setString(4, t.getPassenger());
                ps.setString(5, t.getPhoneNumber());
                ps.setString(6, t.getPassportNumber());
                ps.addBatch();
            }

            int[] results = ps.executeBatch();

            for(int result : results){
                if(result == 0){
                    con.rollback();
                    throw new SQLException("티켓 생성하지 못함");
                }
            }
        } catch (SQLException e) {
            con.rollback();
            throw new RuntimeException(e);
        } finally {
            DBManager.releaseConnection(null, ps);
        }
    }

    @Override
    public void deleteTicket(Connection con, long reservationId) throws SQLException {
        PreparedStatement ps = null;
        String sql = "delete from tickets where reservation_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            int result = ps.executeUpdate();
            if(result == 0){
                con.rollback();
                throw new SQLException("티켓 삭제못함");
            }
        } catch (SQLException e) {
            con.rollback();
            throw new SQLException("티켓 삭제못함");
        } finally {
            DBManager.releaseConnection(null, ps);
        }
    }

    @Override
    public List<Ticket> selectByReservationId(Connection con, long reservationId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Ticket> ticketList = new ArrayList<>();
        String sql = "select flight_id, seat_num from tickets where reservation_id = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            rs = ps.executeQuery();
            while(rs.next()){
                Ticket ticket = new Ticket();
                ticket.setFlightId(rs.getLong("flight_id"));
                ticket.setSeats(rs.getString("seat_num"));
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            con.rollback();
            throw new SQLException("좌석정보 가져오지 못함");
        } finally {
            DBManager.releaseConnection(null, ps, rs);
        }
        return ticketList;
    }

    @Override
    public List<Ticket> selectByReservationId(long reservationId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Ticket> ticketList = new ArrayList<>();
        String sql = "select * from tickets where reservation_id = ?";

        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            rs = ps.executeQuery();
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getLong("ticket_id"),
                        rs.getLong("flight_id"),
                        rs.getLong("reservation_id"),
                        rs.getString("seat_num"),
                        rs.getString("passenger"),
                        rs.getString("phone_number"),
                        rs.getString("passport_number"));
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            throw new SQLException("좌석정보 가져오지 못함");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return ticketList;
    }
}
