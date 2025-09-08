package main.java.com.kostaFlight.service;

import main.java.com.kostaFlight.dto.Board;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.exception.AccessDeniedException;
import main.java.com.kostaFlight.repository.BoardDao;
import main.java.com.kostaFlight.repository.BoardDaoImpl;

import java.sql.SQLException;
import java.util.List;

public class BoardServiceImpl implements BoardService{
    private static final BoardServiceImpl instance = new BoardServiceImpl();
    private BoardServiceImpl() {
    }
    public static BoardServiceImpl getInstance(){
        return instance;
    }
    BoardDao boardDao = new BoardDaoImpl();

    @Override
    public List<Board> searchNotClosedBoard() throws SQLException {
        return boardDao.findNotClosedBoard();
    }

    @Override
    public void writeBoard(Member member, Board board) throws SQLException, AccessDeniedException {
        if(!member.isAdmin()){
            throw new AccessDeniedException("관리자가 아닙니다.");
        }
        boardDao.insertBoard(member.getId(), board);
    }

    @Override
    public List<Board> searchAllBoard() throws SQLException {
        return boardDao.findAllBoard();
    }

    @Override
    public Board searchOneBoard(long boardId) throws SQLException {
        return boardDao.findOneBoard(boardId);
    }

    @Override
    public void deleteBoard(Member member, long boardId) throws SQLException, AccessDeniedException {
        if(!member.isAdmin()){
            throw new AccessDeniedException("관리자가 아닙니다.");
        }
        boardDao.deleteBoard(boardId);
    }

    @Override
    public void updateBoard(Member member, Board board) throws SQLException, AccessDeniedException {
        if(!member.isAdmin()){
            throw new AccessDeniedException("관리자가 아닙니다.");
        }
        Board searched = boardDao.findOneBoard(board.getId());
        if(board.getContent().isEmpty()){
            board.setContent(searched.getContent());
        }
        if(board.getEventEndAt().isEmpty()){
            board.setEventEndAt(searched.getEventEndAt());
        }
        boardDao.updateBoard(board);
    }
}
