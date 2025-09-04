package main.java.com.project.service;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import main.java.com.project.common.AmadeusConnect;
import main.java.com.project.dto.Flight;
import main.java.com.project.dto.FlightDto;
import main.java.com.project.repository.FlightDao;
import main.java.com.project.repository.FlightDaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightService {
    private static final FlightDao flightDao = new FlightDaoImpl();

    public List<Flight> findFlights(FlightDto flightDto) throws ResponseException, SQLException {
        List<Flight> flights = new ArrayList<>();

        // api retrieve
        FlightOfferSearch[] offers =
                AmadeusConnect.INSTANCE.flights(
                        flightDto.getOrigin(),
                        flightDto.getDestination(),
                        flightDto.getDepartDate(),
                        flightDto.getAdults()
                );

        // flight 객체 저장
        for (FlightOfferSearch offer : offers) {
            double price = offer.getPrice().getTotal();
            int remainingSeat = offer.getNumberOfBookableSeats();
            for (FlightOfferSearch.Itinerary itinerary : offer.getItineraries()) {
                for (FlightOfferSearch.SearchSegment segment : itinerary.getSegments()) {
                    // flight entity
                    String departure_airport = segment.getDeparture().getIataCode();
                    int departure_terminal = Integer.parseInt(segment.getDeparture().getTerminal());
                    String departure_time = segment.getDeparture().getAt();
                    String arrival_airport = segment.getArrival().getIataCode();
                    int arrival_terminal = Integer.parseInt(segment.getArrival().getTerminal());
                    String arrival_time = segment.getArrival().getAt();
                    String duration = segment.getDuration();
                    String airline_name = segment.getCarrierCode();

                    // flight 객체
                    Flight flight = new Flight(
                            airline_name,
                            departure_airport,
                            departure_terminal,
                            departure_time,
                            arrival_airport,
                            arrival_terminal,
                            arrival_time,
                            duration,
                            price,
                            remainingSeat
                    );

                    flights.add(flight);
                    int result = flightDao.saveOrUpdatePrice(flight);


                    // todo 에러 예외처리
                    if (result == 0) throw new SQLException("저장 실패");
                }
            }
        }
        return flights;
    }

    private List<Flight> findFlightsFromDB(FlightDto flightDto) throws SQLException {
        return flightDao.findByOriginAndDestinationAndDepartDateAndCheckTime(flightDto.getOrigin(), flightDto.getDestination(), flightDto.getDepartDate());
    }

    public void updateSeatInfo(int flight_id) {

    }
}
