package main.java.com.kostaFlight.service;

import main.java.com.kostaFlight.dto.Seat;

import java.sql.SQLException;
import java.util.List;

public interface SeatService {
    /**
     * flight_id당 10개의 좌석을 저장한다
     */
    void initSeats(long flight_id) throws SQLException;

    /**
     * 선택가능한 좌석을 반환한다.
     */
    List<Seat> findAllByFlightId(long flight_id) throws SQLException;

    /**
     * 좌석 상태를 변경한다
     */
    void changeIsAvailable(long seat_id) throws SQLException;
}
