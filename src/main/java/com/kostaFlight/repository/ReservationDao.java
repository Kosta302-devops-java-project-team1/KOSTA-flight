package main.java.com.kostaFlight.repository;

import main.java.com.kostaFlight.dto.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 테스트용 클래스(언제든 삭제 수정 가능)
 */
public interface ReservationDao {
    long insertReservation(Connection con, long memberId, int count, int totalAmount) throws SQLException;
    void deleteReservation(Connection con, long reservationId) throws SQLException;
    List<Reservation> selectAll() throws SQLException;
    List<Reservation> selectAllByMemberId(long memberId) throws SQLException;
    Reservation selectOneByReservationId(long reservationId) throws SQLException;
}
