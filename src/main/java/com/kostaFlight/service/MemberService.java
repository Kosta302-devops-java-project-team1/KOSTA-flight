package main.java.com.kostaFlight.service;

import main.java.com.kostaFlight.dto.CreditHistory;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.exception.EmailDuplicateException;
import main.java.com.kostaFlight.exception.InsufficientBalanceException;
import main.java.com.kostaFlight.exception.MemberNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {
    Member findById(long memberId) throws SQLException;

    void registerMember(Member member) throws SQLException, EmailDuplicateException;

    /**
     * 기존 DB에 같은 이메일이 있는지 체크하는 메서드.
     * true = 있음. false = 없음
     * @param email
     * @return
     * @throws SQLException
     */
    boolean emailDuplicateChk(String email) throws SQLException;

    boolean passwordChk(Member member, String password);
    /**
     * id, password로 로그인하는 메서드
     * @param member
     */
    Member login(Member member) throws SQLException, MemberNotFoundException;

    /**
     * SessionSet에서 현제 session 삭제. 즉. 로그아웃메서드
     * @param member
     */
    void logout(Member member);

    /**
     * db에서 password 업데이트 이후 기존에 SessionSet에 있던 Session을 지우고, 업데이트 된 새로운 Session을 추가
     * @param member
     * @param password
     * @return
     * @throws SQLException
     * @throws MemberNotFoundException
     */
    Member updatePassword(Member member, String password) throws SQLException, MemberNotFoundException;

    Member updateBalance(Member member) throws SQLException, InsufficientBalanceException, MemberNotFoundException;

    List<CreditHistory> viewAllMemberChargeDetail(long memberId) throws SQLException;
}
