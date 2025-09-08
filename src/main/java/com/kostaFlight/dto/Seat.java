package main.java.com.kostaFlight.dto;

public class Seat {
    private long seat_id;
    private long flight_id;
    private String seat_num;
    private int is_available;

    @Override
    public String toString() {
        return "Seat{" +
                "seat_id=" + seat_id +
                ", flight_id=" + flight_id +
                ", seat_num='" + seat_num + '\'' +
                ", is_available=" + is_available +
                '}';
    }

    public Seat(long seat_id, long flight_id, String seat_num, int is_available) {
        this.seat_id = seat_id;
        this.flight_id = flight_id;
        this.seat_num = seat_num;
        this.is_available = is_available;
    }

    public long getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(long seat_id) {
        this.seat_id = seat_id;
    }

    public long getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(long flight_id) {
        this.flight_id = flight_id;
    }

    public String getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(String seat_num) {
        this.seat_num = seat_num;
    }

    public int getIs_available() {
        return is_available;
    }

    public void setIs_available(int is_available) {
        this.is_available = is_available;
    }
}
