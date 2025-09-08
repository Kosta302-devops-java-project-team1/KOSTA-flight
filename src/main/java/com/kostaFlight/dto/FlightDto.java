package main.java.com.kostaFlight.dto;

/**
 * flightSearch retrieve e object
 */
public class FlightDto {
    private String origin;
    private String destination;
    private String departDate;
    private int adults;

    public FlightDto(String origin, String destination, String departDate, int adults) {
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.adults = adults;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

}
