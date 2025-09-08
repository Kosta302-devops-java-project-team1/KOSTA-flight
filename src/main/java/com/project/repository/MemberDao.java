package main.java.com.project.repository;

import main.java.com.project.dto.Member;
import main.java.com.project.exception.InsufficientBalanceException;
import main.java.com.project.exception.MemberNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

public interface MemberDao {
    /**
     * insert into members(email, password) values(?,?)
     * @throws SQLException
     */
    int registerMember(Member member) throws SQLException;

    /**
     * select * from members where email = ?
     * @param email
     * @return
     * @throws SQLException
     */
    Member findByEmail(String email) throws SQLException;

    Member findById(long memberId) throws SQLException;

    /**
     * select * from members where email = ? and password = ?
     * @param member
     * @return
     * @throws SQLException
     */
    Member login(Member member) throws SQLException;

    /**
     * update members set password = ? where member_id = ?
     * @param member
     * @param password
     * @return
     * @throws MemberNotFoundException
     * @throws SQLException
     */
    Member updatePassword(Member member, String password) throws MemberNotFoundException, SQLException;

    /**
     * update members set balance = ? where member_id = ?
     * 파라미터로 받은 member객체의 memberId로 DB에서 특정 member 정보를 select하고
     * view에서 입력받은 충전금액(amount)를 더한 뒤 updqte실행.
     * 이후 다시 db에서 정보를 조회해서 반환
     * @param member
     * @return
     * @throws SQLException
     * @throws MemberNotFoundException
     * @throws InsufficientBalanceException
     */
    Member updateBalance(Member member) throws SQLException, MemberNotFoundException, InsufficientBalanceException;
    /**
     * 트랜잭션을 위해 Connection을 파라미터로 받은 같은 메서드...
     * @param con
     * @param member
     * @return
     * @throws SQLException
     * @throws MemberNotFoundException
     * @throws InsufficientBalanceException
     */
    Member updateBalance(Connection con, Member member) throws SQLException, MemberNotFoundException, InsufficientBalanceException;
}
