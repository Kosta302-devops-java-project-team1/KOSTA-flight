package main.java.com.project.repository;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDaoImpl implements FlightDao{
    @Override
    public List<Long> saveOrUpdatePrice(List<Flight> flights) throws SQLException {
        Connection con=null;
        PreparedStatement ps=null;
        String sql="INSERT INTO flights(airline_name, departure_airport, departure_terminal, departure_time, arrival_airport, arrival_terminal, arrival_time, duration, price)" +
                "  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                " ON DUPLICATE KEY UPDATE price = VALUES(price), flight_id = LAST_INSERT_ID(flight_id);";

        List<Long> generatedIds = new ArrayList<>();

        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (Flight flight : flights) {
                ps.setString(1, flight.getAirline_name());
                ps.setString(2, flight.getDeparture_airport());
                ps.setInt(3, flight.getDeparture_terminal());
                ps.setString(4, flight.getDeparture_time());
                ps.setString(5, flight.getArrival_airport());
                ps.setInt(6, flight.getArrival_terminal());
                ps.setString(7, flight.getArrival_time());
                ps.setString(8, flight.getDuration());
                ps.setDouble(9, flight.getPrice());

                ps.addBatch();
            }
            ps.executeBatch();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    generatedIds.add(rs.getLong(1)); // flight_id 반환
                }
            }

        }finally {
            DBManager.releaseConnection(con, ps);
        }

        return generatedIds;
    }

    @Override
    public int updateSeatCount(long flight_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "update flights set remaining_seat = (select count(*) from seats where flight_id = ? and is_available = 1) where flight_id = ?;";
        int result=0;

        try {
            con = DBManager.getConnection();

            ps = con.prepareStatement(sql);
            ps.setLong(1, flight_id);
            ps.setLong(2, flight_id);

            result = ps.executeUpdate();

        }finally {
            DBManager.releaseConnection(con, ps);
        }

        return result;
    }

    @Override
    public int updateSeatCount(Connection con, long flight_id) throws SQLException {
        PreparedStatement ps = null;
        String sql = "update flights set remaining_seat = (select count(*) from seats where flight_id = ? and is_available = 1) where flight_id = ?;";
        int result=0;

        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, flight_id);
            ps.setLong(2, flight_id);

            result = ps.executeUpdate();

        }finally {
            DBManager.releaseConnection(null, ps);
        }

        return result;
    }

    @Override
    public List<Flight> findByOriginAndDestinationAndDepartDateAndCheckTime(String origin, String destination, String departDate) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql="SELECT * FROM flights where departure_airport = ? and arrival_airport = ? and DATE(departure_time) = ? and last_update > NOW() - INTERVAL 10 MINUTE";
        List<Flight> flights = new ArrayList<>();
        try {
            con = DBManager.getConnection();

            ps = con.prepareStatement(sql);
            ps.setString(1, origin);
            ps.setString(2, destination);
            ps.setString(3,departDate);

            rs = ps.executeQuery();
            while (rs.next()) {
                flights.add(
                        new Flight(
                                rs.getInt(1),       // flights_id
                                rs.getString(2),    // airline_name
                                rs.getString(3),    // d_ap
                                rs.getInt(4),       // d_ter
                                rs.getString(5),    // d_time
                                rs.getString(6),    // a_ap
                                rs.getInt(7),       // a_ter
                                rs.getString(8),    // a_time
                                rs.getString(9),    // duration
                                rs.getInt(10),      // price
                                rs.getInt(11),      // remain_seat
                                rs.getString(12)   // last_update
                                ));
            }
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }

        return flights;
    }

    public long findLastInsertId() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs= null ;
        String sql = "select last_insert_id();";
        long result = 0;
        try {
            con = DBManager.getConnection();

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getLong(1); // flight_id
            }
        }finally {
            DBManager.releaseConnection(con, ps);
        }

        return result;
    }

    @Override
    public Flight findByFlightId(long flightId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql="SELECT * FROM flights where flight_id = ?";
        Flight flight = null;
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, flightId);
            rs = ps.executeQuery();
            if (rs.next()) {
                flight = new Flight(
                                rs.getInt(1),       // flights_id
                                rs.getString(2),    // airline_name
                                rs.getString(3),    // d_ap
                                rs.getInt(4),       // d_ter
                                rs.getString(5),    // d_time
                                rs.getString(6),    // a_ap
                                rs.getInt(7),       // a_ter
                                rs.getString(8),    // a_time
                                rs.getString(9),    // duration
                                rs.getInt(10),      // price
                                rs.getInt(11),      // remain_seat
                                rs.getString(12)   // last_update
                        );
            }
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }

        return flight;
    }
}
