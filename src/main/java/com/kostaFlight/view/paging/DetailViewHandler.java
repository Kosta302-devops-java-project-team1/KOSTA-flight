package main.java.com.kostaFlight.view.paging;

import main.java.com.kostaFlight.dto.Member;

public interface DetailViewHandler<T> {
    void showDetail(T t, Member member, int adults);
}
