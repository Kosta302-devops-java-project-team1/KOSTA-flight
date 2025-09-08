package main.java.com.project.repository;

import main.java.com.project.common.DBManager;
import main.java.com.project.dto.CreditHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChargeDetailDaoImpl implements ChargeDetailDao{
    @Override
    public List<CreditHistory> selectByMemberId(long memberId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CreditHistory> list = new ArrayList<>();
        String sql = "select * from charge_detail where member_id = ? order by charge_id desc";

        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setLong(1, memberId);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(new CreditHistory(rs.getLong("charge_id"),
                        rs.getLong("member_id"),
                        rs.getLong("amount"),
                        rs.getString("created_at")));
            }
        } catch (SQLException e) {
            throw new SQLException("충전내역 불러오지 못함");
        } finally {
            DBManager.releaseConnection(con, ps, rs);
        }
        return list;
    }
}
