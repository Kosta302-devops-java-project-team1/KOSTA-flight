package main.java.com.kostaFlight.common;

import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.session.Session;
import main.java.com.kostaFlight.session.SessionSet;

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
        if(session == null){
            return null;
        }
        return session.getSessionMember();
    }


}
