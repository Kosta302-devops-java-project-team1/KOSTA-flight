package main.java.com.project.repository;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.Seat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDaoImpl implements SeatDao{

    @Override
    public int save(long flight_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into seats(flight_id, seat_num) values (?, ?);";
        int result=0;

        for (int i = 1; i <= 10; i++) {
            try {
                con = DBManager.getConnection();

                ps = con.prepareStatement(sql);
                ps.setLong(1, flight_id);
                ps.setLong(2, i);

                result = ps.executeUpdate();
            } finally {
                DBManager.releaseConnection(con, ps);
            }
        }

        return result;
    }

    @Override
    public List<Seat> findByFlightId(long flight_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql="select * FROM seats where flight_id = ? and is_available = ?;";

        List<Seat> seats = new ArrayList<>();

        try {
            con = DBManager.getConnection();

            ps = con.prepareStatement(sql);
            ps.setLong(1, flight_id);
            ps.setInt(2, 1);

            rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(
                        new Seat(
                                rs.getLong("seat_id"),
                                rs.getLong("flight_id"),
                                rs.getString("seat_num"),
                                rs.getInt("is_available")
                        ));
            }
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }

        return seats;
    }

    @Override
    public int update(long seat_id, int i) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "update seats set is_available = ? where seat_id = ?;";
        int result = 0;

        try {
            con = DBManager.getConnection();

            ps = con.prepareStatement(sql);
            ps.setInt(1, i);
            ps.setLong(2, seat_id);

            result = ps.executeUpdate();
        } finally {
            DBManager.releaseConnection(con, ps);
        }

        return result;
    }

    @Override
    public int update(Connection con, long flightId, String seat, int i) throws SQLException {
        PreparedStatement ps = null;
        String sql = "update seats set is_available = ? where flight_id = ? and seat_num = ?";
        int result = 0;

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, i);
            ps.setLong(2, flightId);
            ps.setString(3, seat);
            result = ps.executeUpdate();
            if (result == 0){
                con.rollback();
                throw new SQLException("seat 업데이트 하지 못함");
            }
        } finally {
            DBManager.releaseConnection(null, ps);
        }

        return result;
    }

    @Override
    public int findBySeatId(long seatId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select is_available from seats where seat_id = ?;";
        int result = -1;

        try {
            con = DBManager.getConnection();

            ps = con.prepareStatement(sql);
            ps.setLong(1, seatId);

            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt("is_available");
            }

        } finally {
            DBManager.releaseConnection(con, ps);
        }

        // todo -1 일경우...
        return result;
    }
}
