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
            int maxRetries = 10;
            int attempt = 0;

            while (attempt < maxRetries) {
                try {
                    attempt++;
                    System.out.println("항공사로부터 정보 가져오는 중... (시도 " + attempt + ")");

                    FlightDto flightDto = new FlightDto(origin, destination, departDate, adults);
                    List<Long> flightIds = flightService.findFlights(flightDto);

                    FlightSearchSuccessView.printFlightList(flightIds, member, adults);
                    return; // 성공했으면 메서드 종료

                } catch (ResponseException | SQLException e) {
                    System.err.println("시도 " + attempt + " 실패: " + e.getMessage());

                    if (attempt >= maxRetries) {
                        FlightSearchFailView.errorMessage("서비스 장애가 발생하였습니다. (" + maxRetries + "회 시도 후 실패)");
                        return; // 더 이상 시도하지 않고 종료
                    }

                    try {
                        Thread.sleep(2000); // 2초 대기 후 다시 시도
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
    }

    // 왕복행
    public static void flightSearch(String origin, String destination, String departDate, int adults, String returnDate, Member member) {
        int maxRetries = 3;  // 최대 3번 시도
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                attempt++;
                System.out.println("항공사로부터 정보 가져오는 중... (시도 " + attempt + ")");

                FlightDto flightDto = new FlightDto(origin, destination, departDate, adults);
                List<Long> flightIds = flightService.findFlights(flightDto);

                FlightSearchSuccessView.printFlightList(flightIds, member, adults);
                return; // 성공했으면 메서드 종료

            } catch (ResponseException | SQLException e) {
                System.err.println("시도 " + attempt + " 실패: " + e.getMessage());

                if (attempt >= maxRetries) {
                    FlightSearchFailView.errorMessage("서비스 장애가 발생하였습니다. (" + maxRetries + "회 시도 후 실패)");
                    return; // 더 이상 시도하지 않고 종료
                }

                try {
                    Thread.sleep(2000); // 2초 대기 후 다시 시도
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
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
