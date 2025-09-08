package main.java.com.project.repository;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.Member;
import main.java.com.project.dto.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 테스트용 클래스(언제든 삭제 수정 가능)
 */
public class ReservationDaoImpl implements ReservationDao{

    @Override
    public long insertReservation(Connection con, long memberId, int count, int totalAmount) throws SQLException {
        PreparedStatement ps = null;
        String sql = "insert into reservations(member_id, count, total_amount) values(?, ?, ?)";
        int result = 0;
        long pk = 0;
        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, memberId);
            ps.setInt(2, count);
            ps.setInt(3, totalAmount);
            result = ps.executeUpdate();
            if(result == 0){
                con.rollback();
                throw new SQLException("예약테이블 레코드생성 실패");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                pk = rs.getLong(1);
            }
        } catch (SQLException e) {
            con.rollback();
            throw new SQLException("예약테이블 레코드생성 실패");
        } finally {
            DBManager.releaseConnection(null, ps);
        }
        return pk;
    }

    @Override
    public void deleteReservation(Connection con, long reservationId) throws SQLException {
        PreparedStatement ps = null;
        String sql = "delete from reservations where reservation_id = ?";
        int result = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            result = ps.executeUpdate();
            if(result == 0){
                con.rollback();
                throw new SQLException("예약테이블 레코드생성 실패");
            }
        } catch (SQLException e) {
            con.rollback();
            throw new SQLException("예약테이블 레코드생성 실패");
        } finally {
            DBManager.releaseConnection(null, ps);
        }
    }

    @Override
    public List<Reservation> selectAll() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from reservations order by reservation_id desc";
        List<Reservation> reservationList = new ArrayList<>();

        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                reservationList.add(new Reservation(rs.getLong("reservation_id"),
                        rs.getLong("member_id"),
                        rs.getInt("count"),
                        rs.getInt("total_amount"),
                        rs.getString("created_at")));
            }
            for(Reservation r : reservationList){
                System.out.println(r.getReservationId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }

        return reservationList;
    }

    @Override
    public List<Reservation> selectAllByMemberId(long memberId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from reservations where member_id = ? order by reservation_id desc";
        List<Reservation> reservationList = new ArrayList<>();
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, memberId);
            rs = ps.executeQuery();
            while (rs.next()){
                reservationList.add(new Reservation(rs.getLong("reservation_id"),
                        rs.getLong("member_id"),
                        rs.getInt("count"),
                        rs.getInt("total_amount"),
                        rs.getString("created_at")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }

        return reservationList;
    }

    @Override
    public Reservation selectOneByReservationId(long reservationId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from reservations where reservation_id = ? order by reservation_id desc";
        Reservation reservation = null;
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, reservationId);
            rs = ps.executeQuery();
            while (rs.next()){
                reservation = new Reservation(rs.getLong("reservation_id"),
                        rs.getLong("member_id"),
                        rs.getInt("count"),
                        rs.getInt("total_amount"),
                        rs.getString("created_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }

        return reservation;
    }
}
