package main.java.com.kostaFlight.common;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public enum AmadeusConnect {
    INSTANCE;
    private Amadeus amadeus;
    private AmadeusConnect() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("resources/dbInfo.properties"));
            this.amadeus = Amadeus
                    .builder(prop.getProperty("clientId"), prop.getProperty("clientSecret"))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 왕복 검색 api요청
     * @param origin
     * @param destination
     * @param departDate
     * @param adults
     * @param returnDate
     * @return
     * @throws ResponseException
     */
    public FlightOfferSearch[] flights(String origin, String destination, String departDate, String adults, String returnDate) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", origin)
                        .and("destinationLocationCode", destination)
                        .and("departureDate", departDate)
                        .and("returnDate", returnDate)
                        .and("adults", adults)
                        .and("nonStop", true)
                        .and("currencyCode", "KRW")
                        .and("max", 20));
    }

    /**
     * 편도 검색 api 요청
     * @param origin
     * @param destination
     * @param departDate
     * @param adults
     * @return
     * @throws ResponseException
     */
    public FlightOfferSearch[] flights(String origin, String destination, String departDate, int adults) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", origin)
                        .and("destinationLocationCode", destination)
                        .and("departureDate", departDate)
                        .and("adults", adults)
                        .and("nonStop", true)
                        .and("currencyCode", "KRW")
                        .and("max", 20));
    }
}
