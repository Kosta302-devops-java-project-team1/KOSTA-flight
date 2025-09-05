package main.java.com.project.service;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.Flight;
import main.java.com.project.dto.Member;
import main.java.com.project.dto.Reservation;
import main.java.com.project.dto.Ticket;
import main.java.com.project.exception.AccessDeniedException;
import main.java.com.project.exception.InsufficientBalanceException;
import main.java.com.project.exception.MemberNotFoundException;
import main.java.com.project.repository.*;
import main.java.com.project.session.Session;
import main.java.com.project.session.SessionSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReservationServiceImpl implements ReservationService{
    private static final ReservationServiceImpl instance = new ReservationServiceImpl();
    private ReservationServiceImpl() {
    }
    public static ReservationService getInstance(){
        return instance;
    }
    private final ReservationDao reservationDao = new ReservationDaoImpl();
    private final MemberDao memberDao = new MemberDaoImpl();
    private final TicketDao ticketDao = new TicketDaoImpl();
    private final SeatDao seatDao = new SeatDaoImpl();
    private final FlightDao flightDao = new FlightDaoImpl();


    @Override
    public boolean makeReservation(Member member, Flight flight, List<Ticket> tickets) throws SQLException, InsufficientBalanceException, MemberNotFoundException {
        Connection con = DBManager.getConnection();
        con.setAutoCommit(false);
        int count = tickets.size();
        int totalAmount = (int)Math.round(flight.getPrice()) * count;
        member.setBalance(-totalAmount);
        long reservationId = reservationDao.insertReservation(con, member.getId(), count, totalAmount);
        for(Ticket t : tickets){
            t.setReservationId(reservationId);
            t.setFlightId(flight.getFlight_id());
        }
        ticketDao.insertTicket(con, tickets);
        for(Ticket t : tickets){
            seatDao.update(con, t.getFlightId(), t.getSeats(), 0);
        }
        flightDao.updateSeatCount(con, flight.getFlight_id());
        Member updated = memberDao.updateBalance(con, member);
        if(updated != null){
            Session session = new Session(member.getEmail(), member);
            SessionSet ss = SessionSet.getInstance();
            ss.remove(session);
            session = new Session(updated.getEmail(), updated);
            ss.add(session);
            member.setBalance(updated.getBalance());
        }
        con.commit();
        DBManager.releaseConnection(con, null);
        return true;
    }

    @Override
    public boolean cancleReservation(Member member, Reservation reservation) throws SQLException, InsufficientBalanceException, MemberNotFoundException {
        Connection con = DBManager.getConnection();
        List<Ticket> ticketList = ticketDao.selectByReservationId(con, reservation.getReservationId());
        Ticket ticket = ticketList.get(0);
        member.setBalance(reservation.getTotal_amount());
        long flightId = ticket.getFlightId();
        ticketDao.deleteTicket(con, reservation.getReservationId());
        reservationDao.deleteReservation(con, reservation.getReservationId());
        for(Ticket t : ticketList){
            seatDao.update(con, flightId, t.getSeats(), 1);
        }
        flightDao.updateSeatCount(con, flightId);
        Member updated = memberDao.updateBalance(con, member);
        if(updated != null){
            Session session = new Session(member.getEmail(), member);
            SessionSet ss = SessionSet.getInstance();
            ss.remove(session);
            session = new Session(updated.getEmail(), updated);
            ss.add(session);
            member.setBalance(updated.getBalance());
        }
        return true;
    }

    @Override
    public List<Reservation> selectAllReservation(Member member) throws SQLException, AccessDeniedException {
        if(!member.isAdmin()){
            throw new AccessDeniedException("관리자가 아닙니다.");
        }
        return reservationDao.selectAll();
    }

    @Override
    public List<Reservation> selectMemberReservation(long memberId) throws SQLException {
        return reservationDao.selectAllByMemberId(memberId);
    }

    @Override
    public Reservation selectOneReservation(long reservationId) throws SQLException {
        return reservationDao.selectOneByReservationId(reservationId);
    }

    @Override
    public List<Ticket> selectAllTicket(Member member) throws SQLException, AccessDeniedException {
        return List.of();
    }

    @Override
    public List<Ticket> selectMemberTicket(long reservationId) throws SQLException {
        return ticketDao.selectByReservationId(reservationId);
    }

    @Override
    public Ticket selectAllMemberTicket(long memberId) throws SQLException {
        return null;
    }
}
