package main.java.com.kostaFlight.controller;

import main.java.com.kostaFlight.dto.Seat;
import main.java.com.kostaFlight.service.SeatService;
import main.java.com.kostaFlight.service.SeatServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatController {
    private static final SeatService seatService = new SeatServiceImpl();

    public static List<Seat> getSeats(long flight_id) {
        List<Seat> seats = new ArrayList<>();
        try {
            seats = seatService.findAllByFlightId(flight_id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return seats;
    }

    public static void updateAvailable(long seat_id) {
        try {
            seatService.changeIsAvailable(seat_id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
