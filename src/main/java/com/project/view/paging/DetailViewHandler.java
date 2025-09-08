package main.java.com.project.view.paging;

import main.java.com.project.dto.Member;

public interface DetailViewHandler<T> {
    void showDetail(T t, Member member, int adults);
}
