package main.java.com.kostaFlight.dto;

/**
 * Flight entity
 */
public class Flight {
    private long flight_id;
    private String airline_name;
    private String departure_airport;
    private int departure_terminal;
    private String departure_time;
    private String arrival_airport;
    private int arrival_terminal;
    private String arrival_time;
    private String duration;
    private double price;
    private int remaining_seat;
    private String last_update;

    @Override
    public String toString() {
        String formattedPrice = String.format("%,.0f원", price);
        String departureTime = this.departure_time.substring(11, 16);
        String arrivalTime = this.arrival_time.substring(11, 16);
        String durationString = formatDuration(duration); // 또는 duration 필드를 적절히 파싱

        return String.format("%s항공 | %s[%s공항(%s터미널)] >>> %s[%s공항(%s터미널)] - 소요시간:[%s] | 최저가격: %s",
                this.airline_name,
                departureTime,
                this.departure_airport,
                this.departure_terminal,
                arrivalTime,
                this.arrival_airport,
                this.arrival_terminal,
                durationString,
                formattedPrice);
    }

    public Flight() {
    }

    private String formatDuration(String duration) {
        // duration이 null이거나 비어있으면 빈 문자열 반환
        if (duration == null || duration.isEmpty()) {
            return "";
        }

        // 'P'와 'T'는 제거하고, 'H'와 'M'으로 분리
        String parsed = duration.substring(2); // "PT" 제거

        int hours = 0;
        int minutes = 0;

        // 'H'와 'M' 인덱스를 찾아 시간과 분을 추출
        int hourIndex = parsed.indexOf('H');
        int minuteIndex = parsed.indexOf('M');

        if (hourIndex != -1) {
            hours = Integer.parseInt(parsed.substring(0, hourIndex));
        }

        if (minuteIndex != -1) {
            int startIndex = (hourIndex != -1) ? hourIndex + 1 : 0;
            minutes = Integer.parseInt(parsed.substring(startIndex, minuteIndex));
        }

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append("시간 ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("분");
        }

        return sb.toString().trim();
    }

    public Flight(String airline_name, String departure_airport, int departure_terminal, String departure_time, String arrival_airport, int arrival_terminal, String arrival_time, String duration, double price) {
        this.airline_name = airline_name;
        this.departure_airport = departure_airport;
        this.departure_terminal = departure_terminal;
        this.departure_time = departure_time;
        this.arrival_airport = arrival_airport;
        this.arrival_terminal = arrival_terminal;
        this.arrival_time = arrival_time;
        this.duration = duration;
        this.price = price;
    }

    // @AllArgsCons
    public Flight(long flight_id, String airline_name, String departure_airport, int departure_terminal, String departure_time, String arrival_airport, int arrival_terminal, String arrival_time, String duration, double price, int remaining_seat, String last_update) {
        this.flight_id = flight_id;
        this.airline_name = airline_name;
        this.departure_airport = departure_airport;
        this.departure_terminal = departure_terminal;
        this.departure_time = departure_time;
        this.arrival_airport = arrival_airport;
        this.arrival_terminal = arrival_terminal;
        this.arrival_time = arrival_time;
        this.duration = duration;
        this.price = price;
        this.remaining_seat = remaining_seat;
        this.last_update = last_update;
    }

    public long getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getAirline_name() {
        return airline_name;
    }

    public void setAirline_name(String airline_name) {
        this.airline_name = airline_name;
    }

    public String getDeparture_airport() {
        return departure_airport;
    }

    public void setDeparture_airport(String departure_airport) {
        this.departure_airport = departure_airport;
    }

    public int getDeparture_terminal() {
        return departure_terminal;
    }

    public void setDeparture_terminal(int departure_terminal) {
        this.departure_terminal = departure_terminal;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_airport() {
        return arrival_airport;
    }

    public void setArrival_airport(String arrival_airport) {
        this.arrival_airport = arrival_airport;
    }

    public int getArrival_terminal() {
        return arrival_terminal;
    }

    public void setArrival_terminal(int arrival_terminal) {
        this.arrival_terminal = arrival_terminal;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRemaining_seat() {
        return remaining_seat;
    }

    public void setRemaining_seat(int remaining_seat) {
        this.remaining_seat = remaining_seat;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
