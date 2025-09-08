package main.java.com.kostaFlight.repository;

import main.java.com.kostaFlight.dto.Seat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface SeatDao {
    /**
     * flight_id당 10개의 좌석을 저장한다
     */
    int[] save(long flight_id) throws SQLException;

    /**
     * 선택한 비행기의 좌석을 반환한다.
     */
    List<Seat> findByFlightId(long flight_id) throws SQLException;

    /**
     * 좌석 상태를 변경한다
     */
    int update(long seat_id, int i) throws SQLException;

    int update(Connection con, long flightId, String seat, int i) throws SQLException;

    int findBySeatId(long seatId) throws SQLException;
}
