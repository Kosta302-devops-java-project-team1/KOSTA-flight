package main.java.com.kostaFlight.dto;

public class Reservation {
    private long Id; //예약번호
    private long memberId; //회원번호 - 예약한 회원의 번호
    private int count; //예약인원
    private int total_amount; //최종가격 - 가격 * 인원
    private String createdAt; //예약시간(결제시간)

    public Reservation() {
    }

    public Reservation(long Id, long memberId, int count, int total_amount, String createdAt) {
        this.Id = Id;
        this.memberId = memberId;
        this.count = count;
        this.total_amount = total_amount;
        this.createdAt = createdAt;
    }

    public Reservation(long memberId, int count, int total_amount) {
        this.memberId = memberId;
        this.count = count;
        this.total_amount = total_amount;
    }

    public long getReservationId() {
        return Id;
    }

    public void setReservationId(long Id) {
        this.Id = Id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "Id=" + Id +
                ", memberId=" + memberId +
                ", count=" + count +
                ", total_amount=" + total_amount +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
