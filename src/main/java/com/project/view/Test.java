package main.java.com.project.view;

import main.java.com.project.dto.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket("1", "A", "010-1111-1111", "M123123123"));
        tickets.add(new Ticket("2", "B", "010-1111-1111", "M123123123"));
        int num = 1;
        System.out.println("예매 상세 내역");
        System.out.println(" [이름] |    [전화번호]    |    [여권번호]   | [좌석번호]");
        for (Ticket ticket : tickets) {
            System.out.println((num++)+". " + "["+ticket.getPassenger()+"] | [" +
                    ticket.getPhoneNumber() + "] | [" +
                    ticket.getPassportNumber() + "] | [" +
                    ticket.getSeats() + "]");
        }
    }
}
