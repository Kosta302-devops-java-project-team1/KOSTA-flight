package main.java.com.kostaFlight.service;

import main.java.com.kostaFlight.dto.Flight;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.dto.Reservation;
import main.java.com.kostaFlight.dto.Ticket;
import main.java.com.kostaFlight.exception.AccessDeniedException;
import main.java.com.kostaFlight.exception.InsufficientBalanceException;
import main.java.com.kostaFlight.exception.MemberNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ReservationService {
    boolean makeReservation(Member member, Flight flight, List<Ticket> tickets) throws SQLException, InsufficientBalanceException, MemberNotFoundException;

    boolean cancelReservation(Member member, Reservation reservation) throws SQLException, InsufficientBalanceException, MemberNotFoundException;

    boolean cancelReservation(Member admin, Member member, Reservation reservation) throws SQLException, InsufficientBalanceException, MemberNotFoundException, AccessDeniedException;

    List<Reservation> selectAllReservation(Member member) throws SQLException, AccessDeniedException;

    List<Reservation> selectMemberReservation(long memberId) throws SQLException;

    Reservation selectOneReservation(long reservationId) throws SQLException;

    List<Ticket> selectAllTicket(Member member) throws SQLException, AccessDeniedException;

    List<Ticket> selectMemberTicket(long reservationId) throws SQLException;

    Ticket selectAllMemberTicket(long memberId) throws SQLException;
}
