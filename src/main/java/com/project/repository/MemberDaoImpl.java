package main.java.com.project.repository;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.Member;
import main.java.com.project.exception.InsufficientBalanceException;
import main.java.com.project.exception.MemberNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDaoImpl implements MemberDao {

    @Override
    public int registerMember(Member member) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into members(email, password) values(?,?)";
        int result = 0;
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps);
        }
        return result;
    }

    public Member findByEmail(String email) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Member member = null;
        String sql = "select * from members where email = ?";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                member = new Member(rs.getLong("member_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getLong("balance"),
                        (rs.getInt("is_admin") != 0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return member;
    }

    @Override
    public Member findById(long memberId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Member member = null;
        String sql = "select * from members where member_id = ?";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, memberId);
            rs = ps.executeQuery();
            if(rs.next()){
                member = new Member(rs.getLong("member_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getLong("balance"),
                        (rs.getInt("is_admin") != 0));
            }
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return member;
    }

    @Override
    public Member login(Member member) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Member login = null;
        String sql = "select * from members where email = ? and password = ?";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            rs = ps.executeQuery();
            if(rs.next()){
                login = new Member(rs.getLong("member_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getLong("balance"),
                        (rs.getInt("is_admin") != 0));
            }
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return login;
    }

    @Override
    public Member updatePassword(Member member, String password) throws MemberNotFoundException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        Member updated = null;
        String sql = "update members set password = ? where member_id = ?";

        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setLong(2, member.getId());
            int result = ps.executeUpdate();
            if(result == 0){
                throw new MemberNotFoundException("로그인 상태가 아닌듯..?");
            }
            updated = findByEmail(member.getEmail());
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps);
        }
        return updated;
    }

    @Override
    public Member updateBalance(Member member) throws SQLException, MemberNotFoundException, InsufficientBalanceException {
        Connection con = null;
        PreparedStatement ps = null;
        Member updated = null;
        String sql = "update members set balance = ? where member_id = ?";
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);
            updated = findById(con, member.getId());
            if(updated == null){
                throw new MemberNotFoundException("id가 없음");
            }
            long updateCharge = updated.getBalance() + member.getBalance();
            if(updateCharge < 0){
                con.rollback();
                throw new InsufficientBalanceException("잔액이 부족합니다.");
            }
            ps = con.prepareStatement(sql);
            ps.setLong(1, updateCharge);
            ps.setLong(2, member.getId());
            int result = ps.executeUpdate();
            if(result == 0){
                con.rollback();
                throw new SQLException("수정되지 않았습니다.");
            }
            if(member.getBalance() > 0){
                result = insertChargeDetail(con, member.getId(), member.getBalance());
                if(result == 0){
                    con.rollback();
                    throw new SQLException("충전내역에 insert되지 못함");
                }
            }
            
            updated = findById(con, updated.getId());
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps);
        }
        return updated;
    }

    @Override
    public Member updateBalance(Connection con, Member member) throws SQLException, MemberNotFoundException, InsufficientBalanceException {
        PreparedStatement ps = null;
        Member updated = null;
        String sql = "update members set balance = ? where member_id = ?";
        try {
            updated = findById(con, member.getId());
            if(updated == null){
                throw new MemberNotFoundException("id가 없음");
            }
            long updateCharge = updated.getBalance() + member.getBalance();
            if(updateCharge < 0){
                con.rollback();
                throw new InsufficientBalanceException("잔액이 부족합니다.");
            }
            ps = con.prepareStatement(sql);
            ps.setLong(1, updateCharge);
            ps.setLong(2, member.getId());
            int result = ps.executeUpdate();
            if(result == 0){
                con.rollback();
                throw new SQLException("수정되지 않았습니다.");
            }
            updated = findById(con, updated.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            con.rollback();
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(null, ps);
        }
        return updated;
    }

    private int insertChargeDetail(Connection con, long memberId, long charge) throws SQLException{
        PreparedStatement ps = null;
        String sql = "insert into charge_detail(member_id, amount) values (?,?)";
        int result = 0;
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, memberId);
            ps.setLong(2, charge);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(null, ps);
        }
        return result;
    }

    private Member findById(Connection con, long id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Member member = null;
        String sql = "select * from members where member_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                member = new Member(rs.getLong("member_id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getLong("balance"),
                        (rs.getInt("is_admin") != 0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(null, ps, rs);
        }
        return member;
    }
}
