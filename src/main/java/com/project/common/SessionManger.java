package main.java.com.project.common;

import main.java.com.project.dto.Member;
import main.java.com.project.session.Session;
import main.java.com.project.session.SessionSet;

public class SessionManger {
    private static final SessionSet sessionSet =  SessionSet.getInstance();;


    public static void login(Member member) {
        Session session = new Session(member);
        sessionSet.add(session);
    }

    public static void updateSession(Member oldMember, Member newMember){
        Session oldSession = new Session(oldMember);
        sessionSet.remove(oldSession);

        Session newSession = new Session(newMember);
        sessionSet.add(newSession);
    }

    public static void logout(Member member){
        Session session = new Session(member);
        sessionSet.remove(session);
    }

    public static Member getMember(long id){
        Session session = sessionSet.getById(id);
        return session.getSessionMember();
    }


}
