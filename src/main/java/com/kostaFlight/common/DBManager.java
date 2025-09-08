package main.java.com.kostaFlight.common;
/**
 * JDBC를 위한 로드, 연결, 닫기
 *
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBManager {
    private static Properties prop = new Properties();

    /**
     * 로드
     * */
    static {

        try {
            prop.load(new FileInputStream("resources/dbInfo.properties"));
            Class.forName(prop.getProperty("driverName"));
        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 연결
     * */

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(prop.getProperty("url"),
                prop.getProperty("userName"), prop.getProperty("userPass"));
    }

    /**
     * 닫기(DML전용)
     * */
    public static void releaseConnection(Connection con, Statement st) {
        try {
            if (st != null) st.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 닫기(select전용)
     * */
    public static void releaseConnection(Connection con, Statement st, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        releaseConnection(con, st);
    }

}





