package main.java.com.project.view;

import main.java.com.project.common.AirportCode;
import main.java.com.project.common.SessionManger;
import main.java.com.project.controller.FlightController;
import main.java.com.project.controller.MemberController;
import main.java.com.project.controller.ReservationController;
import main.java.com.project.dto.*;
import main.java.com.project.session.Session;
import main.java.com.project.session.SessionSet;
import main.java.com.project.view.paging.CommonPaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberView {
    MemberController memberController = MemberController.getInstance();
    ReservationController reservationController = ReservationController.getInstance();
    private final int PAGESIZE = 5;
    CommonView commonView = new CommonView();
    Scanner sc = new Scanner(System.in);

    public void run(Member member){
        while(true){
            commonView.run();
            System.out.println("-----"+member.getEmail()+"-----");
            System.out.println("[1]항공편 검색 [2]개인정보수정 [3]예매내역확인 [4]크레딧 [5]로그아웃");
            System.out.println("---------------------------------");
            String menu = sc.nextLine();
            switch (menu){
                case "1" :
                    FlightSearchMenuView.search(member);
                    break;
                case "2" :
                    member = updatePassword(member);
                    break;
                case "3" :
                    viewAllMyReservation(member);
                    break;
                case "4" :
                    member = checkBalance(member);
                    break;
                case "5" :
                    logoutView(member);
                    break;
            }
            if(SessionManger.getMember(member.getId()) == null){
                return;
            }
        }
    }

    public void logoutView(Member member){
        memberController.logout(member);
    }

    public Member updatePassword(Member member){
        while(true){
            System.out.println("현재 비밀번호를 입력");
            System.out.println("0.뒤로가기");
            System.out.print(">");
            String currentPassword = sc.nextLine();
            if(currentPassword.equals("0")){
                return member;
            }
            if(memberController.passwordChk(member, currentPassword)){ //현재 패스워드가 맞는지 체크 후 맞으면 break
                break;
            }
            System.out.println("비밀번호가 맞지 않음");
        }
        System.out.println("변경할 비밀번호를 입력");
        System.out.println("0.뒤로가기");
        System.out.print(">");
        String password = passwordChk(); //비밀번호가 4자리 이상일때 password로 받아옴
        if(member.getPassword().equals(password)){
            return member;
        }
        Member updated = memberController.updatePassword(member, password);// 실제 패스워드 수정후 member객체 받아옴
        return updated;
    }
    public Member checkBalance(Member member){
        System.out.println("현재 크레딧");
        System.out.println(">" + member.getBalance());
        System.out.println("[1] 충전 [2] 충전내역확인 [9] 뒤로가기");
        while(true){
            String menu = sc.nextLine();
            if(menu.equals("1")){
                return chargeBalance(member);
            } else if (menu.equals("2")) {
                chargeDetailView(member);
                return member;
            } else if (menu.equals("9")) {
                return member;
            } else {
                System.out.println("잘못 입력함");
            }
        }
    }

    public Member chargeBalance(Member member){
        System.out.println("충전할 금액을 입력하세요");
        System.out.println(">");
        long charge = 0;
        while(true){
            charge = Long.parseLong(sc.nextLine());
            if(charge > 0){
                break;
            }
            System.out.println("0이거나 음수임");
        }
        return updateBalance(member.getId(), member.getEmail(), charge);
    }

    public void chargeDetailView(Member member){
        List<CreditHistory> creditHistories = memberController.viewAllMemberChargeDetail(member.getId());
        int currentPage = 0;
        int lastPage = (creditHistories.size() / PAGESIZE);
        while(true){
            CommonPaging.paging(PAGESIZE, currentPage, creditHistories, "chargeDetail");
            System.out.println("\n메뉴: [0] 이전페이지 [6] 다음페이지 [9] 이전메뉴");
            String menu = sc.nextLine();
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
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    public void viewAllMyReservation(Member member){
        List<Reservation> reservationList = reservationController.viewAllMemberReservation(member);
        int currentPage = 0;
        int lastPage = (reservationList.size() / PAGESIZE);
        while (true){
            CommonPaging.paging(PAGESIZE, currentPage, reservationList, "reservation");
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
                    if (number < reservationList.size()) {
                        reservationList = reservationDetailView(member, reservationList.get(number), reservationList);
                    } else {
                        System.out.println("다시 입력해주세요.");
                    }
                    break;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    public List<Reservation> reservationDetailView(Member member, Reservation reservation, List<Reservation> list){
        List<Ticket> tickets = reservationController.viewMemberTicket(reservation.getReservationId());
        Ticket ticket = tickets.get(0);
        Flight flight = FlightController.searchOneFlight(ticket.getFlightId());
        System.out.println("예매번호 : " + reservation.getReservationId());
        System.out.println("예매시간 : " + reservation.getCreatedAt());
        System.out.println("출발 : (" + flight.getDeparture_airport() + ") " + flight.getDeparture_time() +
                " -> 도착 : (" + flight.getArrival_airport() + ") " + flight.getArrival_time());
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
                    return list;
                case "1" :
                    reservationController.deleteReservation(member, reservation);
                    list = reservationController.viewAllMemberReservation(member);
                    return list;
                default :
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    private Member updateBalance(long id, String email, long charge){
        Member member = new Member(id, email, null, null, charge, false);
        return memberController.updateBalance(member);
    }

    private String passwordChk(){
        String password = null;
        while(true){
            password = sc.nextLine();
            if(password.equals("0")){
                return password;
            }
            if(password.length() >= 4){
                break;
            }
            System.out.println("비밀번호는 4자리 이상");
        }
        return password;
    }

    public void testCancle(Member member){
        Reservation reservation = new Reservation();
        reservation.setReservationId(8);
        reservation.setMemberId(6);
        reservation.setCount(2);
        reservation.setTotal_amount(181200);
        reservationController.deleteReservation(member, reservation);
    }
    public void testPrint(){
        AirportCode.printMenu();
    }

}
