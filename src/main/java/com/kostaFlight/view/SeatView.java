package main.java.com.kostaFlight.view;

import main.java.com.kostaFlight.controller.SeatController;
import main.java.com.kostaFlight.dto.Flight;
import main.java.com.kostaFlight.dto.Seat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class SeatView {
    private static final Scanner sc = new Scanner(System.in);

    public static String[] selectSeats(Flight flight, String[] seats, int adults) {
        List<Seat> seatsList= SeatController.getSeats(flight.getFlight_id());

        seatsList.sort((s1, s2) -> {
            String seat1 = s1.getSeat_num();
            String seat2 = s2.getSeat_num();

            // 1. 알파벳 부분 추출 (A~F)
            char col1 = seat1.charAt(0);
            char col2 = seat2.charAt(0);

            // 2. 숫자 부분 추출 (행 번호)
            int row1 = Integer.parseInt(seat1.substring(1));
            int row2 = Integer.parseInt(seat2.substring(1));

            // 행(숫자) 기준 먼저 비교 → 같으면 열(알파벳) 비교
            if (row1 != row2) {
                return Integer.compare(row1, row2);
            }
            return Character.compare(col1, col2);
        });

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
            while (true) {
                System.out.println((i + 1) + "번째 탑승자 좌석을 선택하세요.");
                String seat = sc.nextLine();
                Optional<Seat> selected = seatsList.stream()
                        .filter(s -> s.getSeat_num().equalsIgnoreCase(seat) && s.getIs_available() == 1)
                        .findFirst();
                if (selected.isPresent()){
                    seats[i] = seat;
                    break;
                } else {
                    System.out.println("다른 좌석을 선택해 주세요.");
                }
            }
        }
        return seats;
    }
}
