package main.java.com.project.dto;

public class PassengerDto {
    private String name;
    private String phone;
    private String passportNumber;

    public PassengerDto() {
    }

    public PassengerDto(String name, String phone, String passportNumber) {
        this.name = name;
        this.phone = phone;
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
