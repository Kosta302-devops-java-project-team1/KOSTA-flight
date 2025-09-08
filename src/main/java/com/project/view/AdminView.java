package main.java.com.project.view;

import main.java.com.project.common.AirportCode;
import main.java.com.project.controller.BoardController;
import main.java.com.project.controller.FlightController;
import main.java.com.project.controller.MemberController;
import main.java.com.project.controller.ReservationController;
import main.java.com.project.dto.*;
import main.java.com.project.view.paging.CommonPaging;

import java.util.List;
import java.util.Scanner;

public class AdminView {
    MemberController memberController = MemberController.getInstance();
    BoardController boardController = BoardController.getInstance();
    ReservationController reservationController = ReservationController.getInstance();
    CommonView commonView = new CommonView();
    Scanner sc = new Scanner(System.in);
    private final static int PAGESIZE = 5;
    public void run(Member member){
        while(true){
            commonView.run();
            System.out.println("-----관리자모드-----");
            System.out.println("[1]예약 조회/관리 [2]이벤트 조회/관리 [3]로그아웃");
            System.out.println("---------------------------------");
            String menu = sc.nextLine();
            switch (menu){
                case "1" :
                    viewReservation(member);
                    break;
                case "2" :
                    viewEvent(member);
                    break;
                case "3" :
                    commonView.logoutView(member);
                    return;
            }
        }
    }

    public void viewEvent(Member member){
        int currentPage = 0;
        List<Board> boards = boardController.searchAllBoard();
        int lastPage = (boards.size() / PAGESIZE);
        while(true){
            CommonPaging.paging(PAGESIZE, currentPage, boards, "event");

            System.out.println("\n메뉴: [0] 이전페이지 [1~5] 상세보기 [6] 다음페이지 [7] 글 작성 [9] 이전메뉴");
            String menu = sc.nextLine();
            int number = 0;
            switch (menu) {
                case "7":
                    writeBoardView(member);
                    boards = boardController.searchAllBoard();
                    break;
                case "6":
                    if (currentPage < lastPage) {
                        currentPage++;
                    } else {
                        System.out.println("마지막페이지 입니다.");
                    }
                    break;
                case "0":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("첫페이지입니다.");
                    }
                    break;
                case "9":
                    return;
                case "1": case "2": case "3": case "4": case "5":
                    number = (currentPage * PAGESIZE) + (Integer.parseInt(menu) - 1);
                    if (number < boards.size()) {
                        boards = boardDetailView(member, boards.get(number), boards);
                    } else {
                        System.out.println("다시 입력해주세요.");
                    }
                    break;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    public void writeBoardView(Member member){
        System.out.println("내용 : ");
        String content = sc.nextLine();
        System.out.println("기한 : 형식(2025-10-20)");
        String eventEndAt = sc.nextLine();
        Board board = new Board(member.getId(), content, eventEndAt);
        boardController.writeBoard(member, board);
    }


    public void updateBoardView(Member member, Board board){
        System.out.println("현재 내용 : " + board.getContent());
        System.out.println("수정할 내용 :(수정하지 않을 시 Enter)");
        System.out.print(">");
        String content = sc.nextLine();
        System.out.println("현재 기한 : " + board.getEventEndAt());
        System.out.println("수정할 기한 : 형식(2025-10-20)(수정하지 않을 시 Enter)");
        System.out.print(">");
        String eventEndAt = sc.nextLine();
        Board update = new Board();
        update.setId(board.getId());
        update.setContent(content);
        update.setEventEndAt(eventEndAt);
        boardController.updateBoard(member, update);
    }

    public void deleteBoardView(Member member, long boardId){
        System.out.println("삭제하시겠습니까? y/n");
        System.out.print(">");
        String result = sc.nextLine();
        if(result.equals("y") | result.equals("Y")){
            boardController.deleteBoard(member, boardId);
        }
    }

    public List<Board> boardDetailView(Member member, Board board, List<Board> boards){
        System.out.println("번호 : " + board.getId());
        System.out.println("생성일 : " + board.getCreatedAt());
        System.out.println("수정일 : " + board.getUpdatedAt());
        System.out.println("내용 : " + board.getContent());
        System.out.println("기한 : " + board.getEventEndAt());
        while(true){
            System.out.println("[0] 뒤로가기 [1] 수정 [2] 삭제");
            String menu = sc.nextLine();
            switch (menu) {
                case "0" :
                    return boards;
                case "1" :
                    updateBoardView(member, board);
                    boards = boardController.searchAllBoard();
                    return boards;
                case "2" :
                    deleteBoardView(member, board.getId());
                    boards = boardController.searchAllBoard();
                    return boards;
                default :
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    public void viewReservation(Member member){
        int currentPage = 0;
        List<Reservation> reservations = reservationController.viewAllReservation(member);
        int lastPage = (reservations.size() / PAGESIZE);
        while(true){
            CommonPaging.paging(PAGESIZE, currentPage, reservations, "reservation");

            System.out.println("\n메뉴: [0] 이전페이지 [1~5] 상세보기 [6] 다음페이지 [9] 이전메뉴");
            String menu = sc.nextLine();
            int number = 0;
            switch (menu) {
                case "6":
                    if (currentPage < lastPage) {
                        currentPage++;
                    } else {
                        System.out.println("마지막페이지 입니다.");
                    }
                    break;
                case "0":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("첫페이지입니다.");
                    }
                    break;
                case "9":
                    return;
                case "1": case "2": case "3": case "4": case "5":
                    number = (currentPage * PAGESIZE) + (Integer.parseInt(menu) - 1);
                    if (number < reservations.size()) {
                        reservations = reservationDetailView(member, reservations.get(number), reservations);
                    } else {
                        System.out.println("다시 입력해주세요.");
                    }
                    break;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }

    }

    public List<Reservation> reservationDetailView(Member member, Reservation reservation, List<Reservation> reservations){
        List<Ticket> tickets = reservationController.viewMemberTicket(reservation.getReservationId());
        Ticket ticket = tickets.get(0);
        Flight flight = FlightController.searchOneFlight(ticket.getFlightId());
        System.out.println("예매번호 : " + reservation.getReservationId());
        System.out.println("예매시간 : " + reservation.getCreatedAt());
        System.out.println("출발 : (" + AirportCode.valueOf(flight.getDeparture_airport()).getName() + ") " + flight.getDeparture_time() +
                " -> 도착 : (" + AirportCode.valueOf(flight.getArrival_airport()).getName() + ") " + flight.getArrival_time());
        for(int i=0;i < tickets.size();i++){
            System.out.println((i+1)+". " + "["+tickets.get(i).getPassenger()+"] | [" +
                    tickets.get(i).getPhoneNumber() + "] | [" +
                    tickets.get(i).getPassportNumber() + "] | [" +
                    tickets.get(i).getSeats() + "]");
        }
        System.out.println("결제가격 : " + reservation.getTotal_amount());
        System.out.println("-------------------------------------");
        while(true){
            System.out.println("[0] 뒤로가기 [1] 예약취소");
            String menu = sc.nextLine();
            switch (menu) {
                case "0" :
                    return reservations;
                case "1" :
                    Member selected = memberController.searchMemberById(reservation.getMemberId());
                    reservationController.deleteReservation(member, selected, reservation);
                    reservations = reservationController.viewAllReservation(member);
                    return reservations;
                default :
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

}
