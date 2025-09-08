package main.java.com.kostaFlight.repository;

import main.java.com.kostaFlight.dto.CreditHistory;

import java.sql.SQLException;
import java.util.List;

public interface ChargeDetailDao {
    List<CreditHistory> selectByMemberId(long memberId) throws SQLException;
}
