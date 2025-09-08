package main.java.com.kostaFlight.dto;

public class Board {
    private long id; // 이벤트 글 번호
    private long memberId; // 이벤트 글 작성자번호
    private String content; // 이벤트 글 내용
    private String createdAt; // 작성시각
    private String updatedAt; // 수정시각
    private String eventEndAt; // 이벤트 종료기한
    private boolean isClose; // 이벤트 종료여부 (false/0 = 진행중, true/1 = 종료)

    public Board() {
    }

    public Board(long id, long memberId, String content, String createdAt, String updatedAt, String eventEndAt, boolean isClose) {
        this.id = id;
        this.memberId = memberId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.eventEndAt = eventEndAt;
        this.isClose = isClose;
    }

    public Board(long memberId, String content, String eventEndAt) {
        this.memberId = memberId;
        this.content = content;
        this.eventEndAt = eventEndAt;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEventEndAt() {
        return eventEndAt;
    }

    public void setEventEndAt(String eventEndAt) {
        this.eventEndAt = eventEndAt;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", eventEndAt='" + eventEndAt + '\'' +
                ", isClose=" + isClose +
                '}';
    }
}
