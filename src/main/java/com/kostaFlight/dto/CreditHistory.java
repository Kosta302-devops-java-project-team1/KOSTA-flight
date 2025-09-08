package main.java.com.kostaFlight.dto;

public class CreditHistory {
    private long id;
    private long memberId;
    private long amount;
    private String createdAt;

    public CreditHistory() {
    }

    public CreditHistory(long id, long memberId, long amount, String createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CreditHistory{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", amount=" + amount +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
