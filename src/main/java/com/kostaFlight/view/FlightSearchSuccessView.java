package main.java.com.kostaFlight.view;

import main.java.com.kostaFlight.controller.FlightController;
import main.java.com.kostaFlight.dto.Flight;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.view.paging.FlightDetailHandler;
import main.java.com.kostaFlight.view.paging.FlightPagingView;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

// todo 뷰수정
public class FlightSearchSuccessView {
    private static final Scanner sc = new Scanner(System.in);

    public static void printFlightList(List<Long> flightIds, Member member, int adults) {
        sortedPage(flightIds, member, adults);
    }

    public static void printFlightList(List<Long> flightIds, List<Long> returnFlightIds, Member member, int adults) {
        System.out.println("------------출발행-------------");
        sortedPage(flightIds, member, adults);

        System.out.println("------------복귀행-------------");
        sortedPage(returnFlightIds, member, adults);

    }

    private static void sortedPage(List<Long> flightIds, Member member, int adults) {
        String choice;
        List<Flight> flights = FlightController.searchFlights(flightIds);
        while (true) {
            System.out.println("정렬 | [1] 가격순 [2] 최단 시간순 [3] 출발 시간순 [4] 도착 시간순 [0] 나가기");
            choice = sc.nextLine();

            if (choice.equals("1")) {
                flights.sort(Comparator.comparingDouble(Flight::getPrice));
            } else if (choice.equals("2")) {
                // todo duration 정제후 비교 오류 확인
                flights.sort(Comparator.comparing(Flight::getDuration));
            } else if (choice.equals("3")) {
                flights.sort(Comparator.comparing(Flight::getDeparture_time));
            } else if (choice.equals("4")) {
                // todo duration 정제후 비교 오류 확인
                flights.sort(Comparator.comparing(Flight::getArrival_time));
            } else if (choice.equals("0")) {
                return;
            } else {
                System.out.println("다시 선택해주세요.");
                continue;
            }
            FlightPagingView.paging(5, flights, new FlightDetailHandler(), member, adults);
        }
    }

}
