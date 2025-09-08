package main.java.com.project.service;

import main.java.com.project.dto.Flight;
import main.java.com.project.dto.Member;
import main.java.com.project.dto.Reservation;
import main.java.com.project.dto.Ticket;
import main.java.com.project.exception.AccessDeniedException;
import main.java.com.project.exception.InsufficientBalanceException;
import main.java.com.project.exception.MemberNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface ReservationService {
    boolean makeReservation(Member member, Flight flight, List<Ticket> tickets) throws SQLException, InsufficientBalanceException, MemberNotFoundException;

    boolean cancleReservation(Member member, Reservation reservation) throws SQLException, InsufficientBalanceException, MemberNotFoundException;

    boolean cancleReservation(Member admin, Member member, Reservation reservation) throws SQLException, InsufficientBalanceException, MemberNotFoundException;

    List<Reservation> selectAllReservation(Member member) throws SQLException, AccessDeniedException;

    List<Reservation> selectMemberReservation(long memberId) throws SQLException;

    Reservation selectOneReservation(long reservationId) throws SQLException;

    List<Ticket> selectAllTicket(Member member) throws SQLException, AccessDeniedException;

    List<Ticket> selectMemberTicket(long reservationId) throws SQLException;

    Ticket selectAllMemberTicket(long memberId) throws SQLException;
}
