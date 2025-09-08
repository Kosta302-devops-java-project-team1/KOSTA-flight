package main.java.com.kostaFlight.dto;

public class Ticket {
    private long id; // 티켓번호
    private long flightId; // 항공번호(항공Dto의 pk)
    private long reservationId; // 예매번호(예약Dto의 pk)
    private String seats; // 좌석번호
    private String passenger; // 탑승자이름
    private String phoneNumber; // 탑승자 전화번호
    private String passportNumber; // 탑승자 여권번호

    public Ticket() {
    }

    public Ticket(long id, long flightId, long reservationId, String seats, String passenger, String phoneNumber, String passportNumber) {
        this.id = id;
        this.flightId = flightId;
        this.reservationId = reservationId;
        this.seats = seats;
        this.passenger = passenger;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
    }

    public Ticket(String seats, String passenger, String phoneNumber, String passportNumber) {
        this.seats = seats;
        this.passenger = passenger;
        this.phoneNumber = phoneNumber;
        this.passportNumber = passportNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", reservationId=" + reservationId +
                ", seats='" + seats + '\'' +
                ", passenger='" + passenger + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }
}
