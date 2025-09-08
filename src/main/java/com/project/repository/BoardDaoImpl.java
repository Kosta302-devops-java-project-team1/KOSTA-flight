package main.java.com.project.repository;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.Board;
import main.java.com.project.dto.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao{
    @Override
    public List<Board> findNotClosedBoard() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Board> list = new ArrayList<>();
        String sql = "select * from boards where is_closed = 0 order by board_id desc";
        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);
            List<Board> boards = selectIsClosed(con); // 이벤트 기간이 끝났지만 isClosed가 아직 0인 이벤트글 select
            if(!boards.isEmpty()){
                int[] result = updateIsClosed(con, boards);
                for(int i : result){
                    if(i == 0){
                        con.rollback();
                        throw new SQLException("isClosed 수정 안됨");
                    }
                }
            }
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Board board = new Board(rs.getLong("board_id"),
                        rs.getLong("member_id"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("event_end_at"),
                        rs.getInt("is_closed") != 0);
                list.add(board);
            }
            con.commit();
        } catch (SQLException e) {
            throw new SQLException("db 오류");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return list;
    }

    /**
     * 이벤트 기간이 끝났찌만 아직 is_closed가 0인 게시글 select
     * @param con
     * @return
     * @throws SQLException
     */
    private List<Board> selectIsClosed(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Board> list = new ArrayList<>();
        String sql = "select * from boards where event_end_at < now() and is_closed = 0";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Board board = new Board(rs.getLong("board_id"),
                        rs.getLong("member_id"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("event_end_at"),
                        rs.getInt("is_closed") != 0);
                list.add(board);
            }
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(null, ps, rs);
        }
        return list;
    }

    /**
     * list로 받은 board들의 is_closed를 1로 업데이트(기간 종료 세팅)
     * @param con
     * @param list
     * @return
     * @throws SQLException
     */
    private int[] updateIsClosed(Connection con, List<Board> list) throws SQLException {
        PreparedStatement ps = null;
        int[] result = null;
        String sql = "update boards set is_closed = 1 where board_id = ?";

        try {
            ps = con.prepareStatement(sql);
            for(Board b : list){
                ps.setLong(1, b.getId());
                ps.addBatch();
            }

            result = ps.executeBatch();
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(null, ps);
        }
        return result;
    }

    @Override
    public void insertBoard(long memberId, Board board) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        String sql = "insert into boards(member_id, content, event_end_at) values(?, ?, ?)";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, memberId);
            ps.setString(2, board.getContent());
            ps.setString(3, board.getEventEndAt());
            result = ps.executeUpdate();
            if(result == 0){
                throw new SQLException("등록하지 못함");
            }
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps);
        }


    }

    @Override
    public List<Board> findAllBoard() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Board> list = new ArrayList<>();
        String sql = "select * from boards order by board_id desc";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Board board = new Board(rs.getLong("board_id"),
                        rs.getLong("member_id"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("event_end_at"),
                        rs.getInt("is_closed") != 0);
                list.add(board);
            }
        } catch (SQLException e) {
            throw new SQLException("db 오류");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return list;
    }

    @Override
    public Board findOneBoard(long boardId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Board board = null;
        String sql = "select * from boards where board_id = ?";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, boardId);
            rs = ps.executeQuery();
            if(rs.next()){
                board = new Board(rs.getLong("board_id"),
                        rs.getLong("member_id"),
                        rs.getString("content"),
                        rs.getString("created_at"),
                        rs.getString("updated_at"),
                        rs.getString("event_end_at"),
                        rs.getInt("is_closed") != 0);
            }
        } catch (SQLException e) {
            throw new SQLException("db 오류");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return board;
    }

    @Override
    public void deleteBoard(long boardId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        String sql = "delete from boards where board_id = ?";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, boardId);
            result = ps.executeUpdate();
            if(result == 0){
                throw new SQLException("삭제하지 못함");
            }
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps);
        }
    }

    @Override
    public void updateBoard(Board board) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        int result = 0;
        String sql = "update boards set content = ?, updated_at = now(), event_end_at = ? where board_id = ?";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, board.getContent());
            ps.setString(2, board.getEventEndAt());
            ps.setLong(3, board.getId());
            result = ps.executeUpdate();
            if(result == 0){
                throw new SQLException("수정하지 못함");
            }
        } catch (SQLException e) {
            throw new SQLException("db오류");
        } finally {
            DBManager.releaseConnection(con, ps);
        }
    }
}
