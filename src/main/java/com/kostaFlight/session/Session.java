package main.java.com.kostaFlight.session;

import main.java.com.kostaFlight.dto.Member;

public class Session {
    private String sessionId;
    private long sessionLongId;
    private Member sessionMember; // 사용자 계정정보 session 저장용

    public Session() {
    }

    public Session(Member member) {
        this.sessionId = member.getEmail();
        this.sessionLongId = member.getId();
        this.sessionMember = member;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Member getSessionMember() {
        return sessionMember;
    }

    public void setSessionMember(Member sessionMember) {
        this.sessionMember = sessionMember;
    }

    public long getSessionLongId() {return sessionLongId;}

    public void setSessionLongId(long sessionLongId) {this.sessionLongId = sessionLongId;}


    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return sessionId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Session other = (Session) obj;
        if(sessionId.equals(other.sessionId)){
            return true;
        }else{
            return false;
        }
    }
}
