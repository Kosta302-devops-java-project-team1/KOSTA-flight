package main.java.com.project.view;

import main.java.com.project.controller.ReservationController;
import main.java.com.project.dto.Flight;
import main.java.com.project.dto.Member;
import main.java.com.project.dto.PassengerDto;
import main.java.com.project.dto.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationView {
    private static final ReservationController reservationController = ReservationController.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void reserve(Flight flight, Member member, int adults){
        List<PassengerDto> passengers = new ArrayList<>();
        for (int i = 0; i < adults; i++) {
            System.out.println((i+1) + "번째 탑승자 정보를 입력해 주세요.");
            System.out.print("이름> ");
            String name = sc.nextLine();
            System.out.print("전화번호> ");
            String phone = sc.nextLine();
            System.out.print("여권번호> ");
            String passportNumber = sc.nextLine();

            passengers.add(new PassengerDto(name, phone, passportNumber));
        }

        String[] seats = new String[adults];
        seats = SeatView.selectSeats(flight, seats, adults);


        List<Ticket> tickets = new ArrayList<>();
        for(int i = 0; i < adults; i++) {
            tickets.add(new Ticket(String.valueOf(seats[i]), passengers.get(i).getName(), passengers.get(i).getPhone(), passengers.get(i).getPassportNumber()));
        }

        int result = reservationController.makeReservation(member, flight, tickets);
        if (result == 1) {
            int num = 1;
            System.out.println("예매 상세 내역");
            System.out.println(" [이름] |    [전화번호]    |    [여권번호]   | [좌석번호]");
            for (Ticket ticket : tickets) {
                System.out.println((num++) + ". " + "[" + ticket.getPassenger() + "] | [" +
                        ticket.getPhoneNumber() + "] | [" +
                        ticket.getPassportNumber() + "] | [" +
                        ticket.getSeats() + "]");
            }

            while (true) {
                System.out.println("[0] 확인");
                int input = Integer.parseInt(sc.nextLine());
                if (input == 0) {
                    return;
                } else {
                    System.out.println("다시 입력해주세요.");
                }
            }
        }
    }
}
