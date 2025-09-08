package main.java.com.project.view;

import main.java.com.project.controller.SeatController;
import main.java.com.project.dto.Flight;
import main.java.com.project.dto.Seat;

import java.util.List;
import java.util.Scanner;

public class SeatView {
    private static final Scanner sc = new Scanner(System.in);

    public static String[] selectSeats(Flight flight, String[] seats, int adults) {
        List<Seat> seatsList= SeatController.getSeats(flight.getFlight_id());

        int seatsPerRow = 6; // 왼쪽 3 + 오른쪽 3
        int totalRows = seatsList.size() / seatsPerRow;
        char[] seatCols = {'A', 'B', 'C', 'D', 'E', 'F'};

        System.out.print("   "); // 행번호 자리
        for (int j = 0; j < seatsPerRow; j++) {
            if (j == 3) System.out.print("  "); // 통로
            System.out.print(" " + seatCols[j]);
        }
        System.out.println();

        for (int row = 0; row < totalRows; row++) {
            // 왼쪽 행 번호 출력 (1부터 시작)
            System.out.printf("%2d ", row + 1);

            for (int col = 0; col < seatsPerRow; col++) {
                Seat seat = seatsList.get(row * seatsPerRow + col);
                String symbol = seat.getIs_available() == 1 ? "■" : "□";

                System.out.print(" " + symbol);

                if (col == 2) System.out.print("  "); // 통로
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < adults; i++) {
            System.out.println((i + 1) + "번째 탑승자 좌석을 선택하세요.");
            seats[i] = sc.nextLine();
        }
        return seats;
    }
}
