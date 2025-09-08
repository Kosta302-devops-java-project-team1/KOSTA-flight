package main.java.com.kostaFlight.repository;

import main.java.com.kostaFlight.dto.Flight;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FlightDao {
    /**
     * insert flights
     */
    List<Long> saveOrUpdatePrice(List<Flight> flights) throws SQLException;

    /**
     * update flights seats count
     */
    int updateSeatCount(long flight_id) throws SQLException;

    int updateSeatCount(Connection con, long flight_id) throws SQLException;
    /**
     * select flights
     * @param origin
     * @param destination
     * @param departDate
     * @return
     */
    List<Flight> findByOriginAndDestinationAndDepartDateAndCheckTime(String origin, String destination, String departDate) throws SQLException;

    /**
     *  방금 추가된 flight_id를 가져오는 쿼리
     * @return flight_id
     */
    public long findLastInsertId() throws SQLException;

    Flight findByFlightId(long flightId) throws SQLException;
}
