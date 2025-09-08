package main.java.com.project.session;

import java.util.HashSet;
import java.util.Set;

public class SessionSet {
    private static SessionSet instance = new SessionSet();
    private Set<Session> set;

    private SessionSet() {
        set = new HashSet<>();
    }

    public static SessionSet getInstance(){
        return instance;
    }

    public Session get(String sessionId){
        for(Session session : set){
            if(session.getSessionId().equals(sessionId)){
                return session;
            }
        }
        return null;
    }

    public Session getById(long id){
        for(Session session : set){
            if(session.getSessionLongId() == id){
                return session;
            }
        }
        return null;
    }

    public Set<Session> getSet() {
        return set;
    }

    public void add(Session session){
        set.add(session);
    }

    public void remove(Session session){
        set.remove(session);
    }

}
