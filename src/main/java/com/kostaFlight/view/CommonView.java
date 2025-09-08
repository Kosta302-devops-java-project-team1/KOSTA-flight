package main.java.com.kostaFlight.view;

import main.java.com.kostaFlight.controller.BoardController;
import main.java.com.kostaFlight.controller.MemberController;
import main.java.com.kostaFlight.dto.Board;
import main.java.com.kostaFlight.dto.Member;
import main.java.com.kostaFlight.session.Session;
import main.java.com.kostaFlight.session.SessionSet;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CommonView {
    MemberController memberController = MemberController.getInstance();
    BoardController boardController = BoardController.getInstance();
    private static final Map<Character, String[]> FONT = new HashMap<>();
    static {
        FONT.put('K', new String[]{
                "1001",
                "1010",
                "1100",
                "1010",
                "1001"
        });
        FONT.put('O', new String[]{
                "0110",
                "1001",
                "1001",
                "1001",
                "0110"
        });
        FONT.put('S', new String[]{
                "0111",
                "1000",
                "0110",
                "0001",
                "1110"
        });
        FONT.put('T', new String[]{
                "1111",
                "0100",
                "0100",
                "0100",
                "0100"
        });
        FONT.put('A', new String[]{
                "0110",
                "1001",
                "1111",
                "1001",
                "1001"
        });
        FONT.put('F', new String[]{
                "1111",
                "1000",
                "1110",
                "1000",
                "1000"
        });
        FONT.put('L', new String[]{
                "1000",
                "1000",
                "1000",
                "1000",
                "1111"
        });
        FONT.put('I', new String[]{
                "1110",
                "0100",
                "0100",
                "0100",
                "1110"
        });
        FONT.put('G', new String[]{
                "0111",
                "1000",
                "1011",
                "1001",
                "0110"
        });
        FONT.put('H', new String[]{
                "1001",
                "1001",
                "1111",
                "1001",
                "1001"
        });
        FONT.put('-', new String[]{
                "0000",
                "0000",
                "1111",
                "0000",
                "0000"
        });
        FONT.put(' ', new String[]{
                "0000",
                "0000",
                "0000",
                "0000",
                "0000"
        });
    }
    public void run(){
        /*System.out.println("------------------------------");
        System.out.println("----------사이트 상표 자리------------");
        System.out.println("------------------------------");*/
        String text = "kosta-flights"; // 원하는 문구로 변경 가능함
        int scale = 1;                  // 1 이상 정수. 2~3 권장
        char on = '█';                  // 채움 문자(예: '█', '■', '#', '●', 'o')
        char off = ' ';                 // 빈칸 문자(예: ' ', '.')
        render(text, scale, on, off);
        System.out.println("------------------------------이벤트-----------------------------");
        List<Board> list = viewEvent();
        for(Board b : list){
            System.out.println(b.getContent());
        }
        System.out.println("----------------------------------------------------------------");
    }


    public void logoutView(Member member){
        memberController.logout(member);
    }

    public List<Board> viewEvent(){
        List<Board> list = boardController.searchNotClosedBoard();
        return list;
    }

    public Member getMemberFromSessionById(long id){
        SessionSet sessionSet = SessionSet.getInstance();
        Session session = sessionSet.getById(id);
        return session.getSessionMember();
    }

    private static void render(String text, int scale, char on, char off) {
        char[] chars = text.toUpperCase(Locale.ROOT).toCharArray();
        String interCharGap = repeat(String.valueOf(off), scale);

        // 세로 5줄
        for (int row = 0; row < 5; row++) {
            for (int v = 0; v < scale; v++) {
                StringBuilder line = new StringBuilder();
                for (int ci = 0; ci < chars.length; ci++) {
                    String[] pattern = FONT.getOrDefault(chars[ci], FONT.get(' '));
                    String bits = pattern[row];
                    for (int col = 0; col < bits.length(); col++) {
                        char pix = (bits.charAt(col) == '1') ? on : off;
                        line.append(repeat(String.valueOf(pix), scale));
                    }
                    if (ci != chars.length - 1) line.append(interCharGap);
                }
                System.out.println(line);
            }
        }
    }

    private static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder(s.length() * n);
        for (int i = 0; i < n; i++) sb.append(s);
        return sb.toString();
    }

}
