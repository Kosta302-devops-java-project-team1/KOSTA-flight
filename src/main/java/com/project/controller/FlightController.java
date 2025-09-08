package main.java.com.project.controller;

import com.amadeus.exceptions.ResponseException;
import main.java.com.project.dto.Flight;
import main.java.com.project.dto.FlightDto;
import main.java.com.project.dto.Member;
import main.java.com.project.service.FlightService;
import main.java.com.project.view.FlightSearchFailView;
import main.java.com.project.view.FlightSearchSuccessView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightController {
    private static final FlightService flightService = new FlightService();

    // 편도행
    public static void flightSearch(String origin, String destination, String departDate, int adults, Member member) {
        try {
            FlightDto flightDto = new FlightDto(origin, destination, departDate, adults);

            List<Long> flightIds = flightService.findFlights(flightDto);

            FlightSearchSuccessView.printFlightList(flightIds, member, adults);
        } catch (ResponseException | SQLException e) {
            FlightSearchFailView.errorMessage("서비스 장애가 발생하였습니다." + e.getMessage());
        }
    }

    // 왕복행
    public static void flightSearch(String origin, String destination, String departDate, int adults, String returnDate, Member member) {
        try {
            FlightDto flightDto = new FlightDto(origin, destination, departDate, adults);
            FlightDto returnFlightDto = new FlightDto(destination, origin, returnDate, adults);

            List<Long> flightIds = flightService.findFlights(flightDto);
            FlightSearchSuccessView.printFlightList(flightIds, member, adults);

            List<Long> returnFlightsIds = flightService.findFlights(returnFlightDto);
            FlightSearchSuccessView.printFlightList(returnFlightsIds, member, adults);

        } catch (ResponseException | SQLException e) {
            FlightSearchFailView.errorMessage("서비스 장애가 발생하였습니다." + e.getMessage());
        }
    }

    public static Flight searchOneFlight(long flightId){
        Flight flight = null;
        try {
            flight = flightService.findByOneFlightId(flightId);
        } catch (SQLException e) {
            FlightSearchFailView.errorMessage("서비스 장애가 발생하였습니다." + e.getMessage());
        }
        return flight;
    }

    public static List<Flight> searchFlights(List<Long> flightIds) {
       List<Flight> flights = new ArrayList<>();
       try {
           for (long id : flightIds) {
           flights.add(flightService.findByOneFlightId(id));
           }
       } catch (SQLException e) {
           FlightSearchFailView.errorMessage("입력이 틀렸습니다. 다시 입력해주세요.");
       }

       return flights;
    }
}
