package main.java.com.project.view.paging;

import main.java.com.project.dto.Flight;
import main.java.com.project.dto.Member;
import main.java.com.project.view.ReservationView;

import java.util.Scanner;

public class FlightDetailHandler implements DetailViewHandler<Flight> {
    private static final Scanner sc = new Scanner(System.in);

    @Override
    public void showDetail(Flight flight, Member member, int adults) {
        // 비회원 뷰
        if (member == null) {
            while (true) {
                System.out.println("\n=== 항공권 상세보기 ===");
                System.out.println(flight);

                System.out.println("[0] 이전으로");
                String input = sc.nextLine();

                if (input.equals("0")) {
                    return; // 뒤로가기
                } else {
                    System.out.println("잘못된 입력입니다.");
                }
            }
        }

        while (true) {
            System.out.println("\n=== 항공권 상세보기 ===");
            System.out.println(flight);

            System.out.println("[1] 예매하기 [0] 이전으로");
            String input = sc.nextLine();

            if (input.equals("1")) {
                ReservationView.reserve(flight, member, adults);
                return; // 상세보기 종료
            } else if (input.equals("0")) {
                return; // 뒤로가기
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
