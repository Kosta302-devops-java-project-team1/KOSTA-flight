package main.java.com.kostaFlight.dto;

public class Member {
    private long id; //회원번호
    private String email; //이메일(id 대용)
    private String password;
    private String createAt; //가입날짜
    private long balance; //보유 포인트
    private boolean isAdmin; //관리자인지(0/false = 일반, 1/true = 관리자)

    public Member() {
    }

    public Member(long id, String email, String password, String createAt, long balance, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createAt = createAt;
        this.balance = balance;
        this.isAdmin = isAdmin;
    }

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


}
