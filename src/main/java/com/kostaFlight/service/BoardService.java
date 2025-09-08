package main.java.com.kostaFlight.service;

import main.java.com.kostaFlight.dto.Board;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.exception.AccessDeniedException;

import java.sql.SQLException;
import java.util.List;

public interface BoardService {
    /**
     * 이벤트 기간이 끝나지 않은 게시글을 가지고 옴.
     * @return
     * @throws SQLException
     */
    List<Board> searchNotClosedBoard() throws SQLException;

    void writeBoard(Member member, Board board) throws SQLException, AccessDeniedException;

    List<Board> searchAllBoard() throws SQLException;

    Board searchOneBoard(long boardId) throws SQLException;

    void deleteBoard(Member member, long boardId) throws SQLException, AccessDeniedException;

    void updateBoard(Member member, Board board) throws SQLException, AccessDeniedException;
}
