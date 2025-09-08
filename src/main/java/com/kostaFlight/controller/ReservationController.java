package main.java.com.kostaFlight.controller;

import main.java.com.kostaFlight.dto.Flight;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.dto.Reservation;
import main.java.com.kostaFlight.dto.Ticket;
import main.java.com.kostaFlight.exception.AccessDeniedException;
import main.java.com.kostaFlight.exception.InsufficientBalanceException;
import main.java.com.kostaFlight.exception.MemberNotFoundException;
import main.java.com.kostaFlight.service.ReservationService;
import main.java.com.kostaFlight.service.ReservationServiceImpl;
import main.java.com.kostaFlight.view.FailView;
import main.java.com.kostaFlight.view.SuccessView;

import java.sql.SQLException;
import java.util.List;

public class ReservationController {
    private static final ReservationController instance = new ReservationController();
    private ReservationController() {
    }
    public static ReservationController getInstance(){
        return instance;
    }
    private final ReservationService reservationService = ReservationServiceImpl.getInstance();


    public int makeReservation(Member member, Flight flight, List<Ticket> tickets)  {
        try {
            reservationService.makeReservation(member, flight, tickets);
            SuccessView.printMessage(member.getEmail()+"님 "+flight.getFlight_id()+"편 "+tickets.size()+"장 예매완료");
            return 1;
        } catch (SQLException | InsufficientBalanceException | MemberNotFoundException e) {
            FailView.errorMessage(e.getMessage());
            return 0;
        }
    }

    public void deleteReservation(Member member, Reservation reservation){
        try {
            reservationService.cancelReservation(member, reservation);
            SuccessView.printMessage("예약 취소 성공");
        } catch (SQLException | InsufficientBalanceException | MemberNotFoundException e) {
            FailView.errorMessage(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deleteReservation(Member admin, Member member, Reservation reservation){
        try {
            reservationService.cancelReservation(admin, member, reservation);
            SuccessView.printMessage("예약 취소 성공");
        } catch (SQLException | InsufficientBalanceException | MemberNotFoundException | AccessDeniedException e) {
            FailView.errorMessage(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Reservation> viewAllReservation(Member member){
        List<Reservation> reservationList = null;
        try {
            reservationList = reservationService.selectAllReservation(member);

        } catch (SQLException | AccessDeniedException e) {
            FailView.errorMessage(e.getMessage());
        }
        return reservationList;
    }

    public List<Reservation> viewAllMemberReservation(Member member){
        List<Reservation> reservationList = null;
        try {
            reservationList = reservationService.selectMemberReservation(member.getId());
        } catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
        return reservationList;
    }

    public Reservation viewOneReservation(long reservationId){
        Reservation reservation = null;
        try {
            reservation = reservationService.selectOneReservation(reservationId);
        } catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
        return reservation;
    }

    public List<Ticket> viewAllTicket(Member member){
        return null;
    }
    public List<Ticket> viewMemberAllTicket(long memberId){
        return null;
    }
    public List<Ticket> viewMemberTicket(long reservationId){
        List<Ticket> tickets = null;
        try {
            tickets = reservationService.selectMemberTicket(reservationId);
        } catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
        return tickets;
    }

}
