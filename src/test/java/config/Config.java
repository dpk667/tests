package config;

import java.util.Map;

public class Config {

    private static final  String ENV = System.getProperty("env", "ift");
    private static final String BROWSER = System.getProperty("browser", "chrome").toLowerCase();
    private static final int THREADS = Integer.parseInt(System.getProperty("threads", "1"));

    private static final Map<String, String> BASE_URLS = Map.of(
            "ift", "http://dmon-proxy-ci02877809-mis3i0.apps.ift-terra000023-eds.ocp.delta.sbrf.ru",
            "dev", "http://dmon-proxy-ci02877809-mis3s0.apps.ift-terra000003-ids.ocp.delta.sbrf.ru"
    );

    private static final Map<String, String> UI_URLS = Map.of(
            "ift", "",
            "dev", "",
            "st", ""
    );

    private static final Map<String, String> DB_URLS = Map.of(
            "ift", "",
            "dev", "",
            "st", ""
    );

    public static String getEnv(){ return ENV; }

    public static String getBaseUrl() {
        return BASE_URLS.getOrDefault(ENV, BASE_URLS.get("ift"));
    }

    public static String getUiUrl() { return UI_URLS.getOrDefault(ENV, UI_URLS.get("ift")); }

    public static String getDbUrl() { return DB_URLS.getOrDefault(ENV, DB_URLS.get("ift")); }

    public static String getBrowser() { return BROWSER; }

    public static int getThreads() { return THREADS; }
}
