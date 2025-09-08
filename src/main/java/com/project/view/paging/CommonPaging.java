package main.java.com.project.view.paging;

import main.java.com.project.dto.Board;
import main.java.com.project.dto.CreditHistory;
import main.java.com.project.dto.Reservation;

import java.util.List;
import java.util.Scanner;

public class CommonPaging {

    public static void paging(int size, int page, List<?> list, String kind) {
        int pageSize = size;
        int currentPage = page;
        int totalPages = (int) Math.ceil((double) list.size() / pageSize);
        int number = 1;

        int start = currentPage * pageSize;
        int end = Math.min(start + pageSize, list.size());
        System.out.println("\n=== 페이지 " + (currentPage + 1) + " / " + totalPages + "===");
        switch (kind){
            case "event" :
                for (int i = start; i < end; i++) {
                    Board board = (Board) list.get(i);
                    System.out.println(number + ". ["+ board.getId()+"] ["+board.getContent()+"] [" + board.getEventEndAt() + "]" );
                    number++;
                }
                break;
            case "reservation" :
                for (int i = start; i < end; i++) {
                    Reservation reservation = (Reservation) list.get(i);
                    System.out.println(number+". 예매번호 : "+reservation.getReservationId()+" | 예매시간 : "+
                            reservation.getCreatedAt());
                    number++;
                }
                break;
            case "chargeDetail" :
                for (int i = start; i < end; i++){
                    CreditHistory creditHistory = (CreditHistory) list.get(i);
                    System.out.println("충전금액 : " + creditHistory.getAmount() + " | 충전시간 : " + creditHistory.getCreatedAt());
                }
                break;
        }
    }
}