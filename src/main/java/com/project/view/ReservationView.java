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
    private static final MemberView memberView = new MemberView();
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

        int[] seats = new int[adults];
        seats = SeatView.selectSeats(flight, seats, adults);


        List<Ticket> tickets = new ArrayList<>();
        for(int i = 0; i < adults; i++) {
            tickets.add(new Ticket(String.valueOf(seats[i]), passengers.get(i).getName(), passengers.get(i).getPhone(), passengers.get(i).getPassportNumber()));
        }

        reservationController.makeReservation(member, flight, tickets);
        for (Ticket ticket : tickets) {
            System.out.println("예매 상세 내역");
            MemberView
        }

        memberView.run(member);
    }
}
