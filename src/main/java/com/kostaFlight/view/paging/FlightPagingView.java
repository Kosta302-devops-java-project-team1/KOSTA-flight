package main.java.com.kostaFlight.view.paging;

import main.java.com.kostaFlight.dto.Member;

import java.util.List;
import java.util.Scanner;

public class FlightPagingView {
    private static final Scanner sc = new Scanner(System.in);

    public static <T> void paging(int size, List<T> list, DetailViewHandler<T> handler, Member member, int adults) {
        int pageSize = size;
        int currentPage = 0;
        int totalPages = (int) Math.ceil((double) list.size() / pageSize);

        while (true) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, list.size());

            System.out.println("\n=== 페이지 " + (currentPage + 1) + " / " + totalPages + " ===");
            for (int i = start; i < end; i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }

            System.out.println("\n메뉴: [번호 입력] 상세보기 ['['] 이전 [']'] 다음 ['0'] 뒤로가기");
            String input = sc.nextLine();

            if (input.equals("[")) {
                if (currentPage > 0) currentPage--;
                else System.out.println("첫 페이지입니다.");
            } else if (input.equals("]")) {
                if (currentPage < totalPages - 1) currentPage++;
                else System.out.println("마지막 페이지입니다.");
            } else if (input.equals("0")) {
                return;
            } else {
                try {
                    int selected = Integer.parseInt(input);
                    if (selected >= 1 && selected <= list.size()) {
                        handler.showDetail(list.get(selected - 1), member, adults);
                    } else {
                        System.out.println("잘못된 번호입니다.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("다시 입력해주세요.");
                }
            }
        }
    }
}
