package main.java.com.project.view;

import main.java.com.project.controller.SeatController;
import main.java.com.project.dto.Flight;
import main.java.com.project.dto.Seat;

import java.util.List;
import java.util.Scanner;

public class SeatView {
    private static final Scanner sc = new Scanner(System.in);

    public static int[] selectSeats(Flight flight, int[] seats, int adults) {
        List<Seat> seatsList= SeatController.getSeats(flight.getFlight_id());

        int seatsPerRow = 6; // 왼쪽 3 + 오른쪽 3
        System.out.println("  1 2 3  4 5 6");

        for (int i = 0; i < seatsList.size(); i++) {
            Seat seat = seatsList.get(i);
            String symbol = seat.getIs_available() == 1 ? "■" : "□";

            System.out.print(symbol + " ");

            if ((i % seatsPerRow) == 2) {
                System.out.print("  ");
            }

            if ((i + 1) % seatsPerRow == 0) {
                System.out.println();
            }
        }

        System.out.println();

        for (int i = 0; i < adults; i++) {
            System.out.println((i + 1) + "번째 탑승자 좌석을 선택하세요.");
            seats[i] = Integer.parseInt(sc.nextLine());
        }
        return seats;
    }
}
