package main.java.com.project.service;

import main.java.com.project.dto.CreditHistory;
import main.java.com.project.dto.Member;
import main.java.com.project.exception.EmailDuplicateException;
import main.java.com.project.exception.InsufficientBalanceException;
import main.java.com.project.exception.MemberNotFoundException;
import main.java.com.project.repository.ChargeDetailDao;
import main.java.com.project.repository.ChargeDetailDaoImpl;
import main.java.com.project.repository.MemberDao;
import main.java.com.project.repository.MemberDaoImpl;
import main.java.com.project.session.Session;
import main.java.com.project.session.SessionSet;

import java.sql.SQLException;
import java.util.List;

public class MemberServiceImpl implements MemberService{
    private static final MemberServiceImpl instance = new MemberServiceImpl();
    private MemberServiceImpl() {
    }
    public static MemberService getInstance(){
        return instance;
    }
    private final MemberDao memberDao = new MemberDaoImpl();
    private final ChargeDetailDao chargeDetailDao = new ChargeDetailDaoImpl();


    @Override
    public Member findById(long memberId) throws SQLException {
        return memberDao.findById(memberId);
    }

    @Override
    public void registerMember(Member member) throws SQLException, EmailDuplicateException {
        int result = memberDao.registerMember(member);
        if(result == 0){
            throw new SQLException("등록 실패");
        }
    }

    @Override
    public boolean emailDuplicateChk(String email) throws SQLException {
        Member byEmail = memberDao.findByEmail(email);
        return byEmail != null;
    }

    @Override
    public boolean passwordChk(Member member, String password) {
        return member.getPassword().equals(password);
    }

    @Override
    public Member login(Member member) throws SQLException, MemberNotFoundException {
        Member login = memberDao.login(member);
        if(login == null){
            throw new MemberNotFoundException("일치하는 계정정보가 없습니다.");
        }
        Session session = new Session(member.getEmail(), member);
        SessionSet sessionSet = SessionSet.getInstance();
        sessionSet.add(session);

        return login;
    }

    @Override
    public void logout(Member member) {
        Session session = new Session(member.getEmail(), member);
        SessionSet sessionSet = SessionSet.getInstance();
        sessionSet.remove(session);
    }

    @Override
    public Member updatePassword(Member member, String password) throws SQLException, MemberNotFoundException {
        return updateSession(member, memberDao.updatePassword(member, password));
    }

    @Override
    public Member updateBalance(Member member) throws SQLException, InsufficientBalanceException, MemberNotFoundException {
        return updateSession(member, memberDao.updateBalance(member));
    }

    private Member updateSession(Member member, Member updated){
        Session session = new Session(member.getEmail(), member);
        SessionSet sessionSet = SessionSet.getInstance();
        sessionSet.remove(session);
        session = new Session(updated.getEmail(), updated);
        sessionSet.add(session);
        return updated;
    }

    @Override
    public List<CreditHistory> viewAllMemberChargeDetail(long memberId) throws SQLException {
        List<CreditHistory> list = chargeDetailDao.selectByMemberId(memberId);
        return list;
    }
}
