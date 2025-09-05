package main.java.com.project.service;

import main.java.com.project.dto.Seat;
import main.java.com.project.repository.SeatDao;
import main.java.com.project.repository.SeatDaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatServiceImpl implements SeatService{
    private static final SeatDao seatDao = new SeatDaoImpl();

    @Override
    public void initSeats(long flight_id) throws SQLException {
        // 42개의 좌석을 자동 생성해준다 | insert가 아닌 update는 flight_id를 0을 반환한다.
        seatDao.save(flight_id);
    }

    @Override
    public List<Seat> findAllByFlightId(long flight_id) throws SQLException {
        List<Seat> seats = new ArrayList<>();

        seats = seatDao.findByFlightId(flight_id);

        return seats;
    }

    @Override
    public void changeIsAvailable(long seat_id) throws SQLException {
        int check = checkAvailable(seat_id);

        if (check == 1) {
            seatDao.update(seat_id, 0);
        } else {
            seatDao.update(seat_id, 1);
        }

    }

    private int checkAvailable(long seatId) throws SQLException {
        return seatDao.findBySeatId(seatId);
    }
}
