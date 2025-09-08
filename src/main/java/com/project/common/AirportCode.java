package main.java.com.project.common;

public enum AirportCode {
    ICN("인천"),
    GMP("김포"),
    TPE("타이완"),
    NRT("도쿄"),
    KIX("오사카"),
    PEK("베이징"),
    SHA("상하이"),
    FCO("로마"),
    BER("베를린"),
    CDG("파리"),
    LHR("런던"),
    GRU("상파울루"),
    YVR("벤쿠버"),
    JFK("뉴욕"),
    LAX("로스앤젤레스");

    private final String name;

    AirportCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code){
        try {
            return AirportCode.valueOf(code).getName();
        } catch (IllegalArgumentException e) {
            return code;
        }
    }

    public static String getCodeByMenuNumber(int menu) {
        AirportCode[] airports = values();
        if (menu >= 1 && menu <= airports.length) {
            return airports[menu - 1].name(); // 1번 = 첫번째 enum
        }
        return null;
    }

    public static void printMenu() {
        AirportCode[] airports = values();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < airports.length; i++) {
            sb.append("[").append(i+1).append("][").append(airports[i].getName()).append("] ");
            if((i+1)%5 == 0){
                sb.append("\n");
            }
        }
        System.out.println(sb.toString());
    }
}
