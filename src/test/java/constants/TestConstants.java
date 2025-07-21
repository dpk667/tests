package constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestConstants {

    public static final String SMOKE_DH_ID = generateUniqueDHobjID();
    public static final String AQA_MODULE_ID_1 = "AQA_TEST_01";
    public static final String AQA_MODULE_ID_2 = "AQA_TEST_02";
    public static final String AQA_MODULE_ID_3 = "AQA_TEST_03";
    public static final String AQA_MODULE_SMOKE = "AQA_TEST_03";
    public static final String AQA_MODULE_REGRESS = "AQA_TEST_03";
    public static final String DMON_STORAGE_META = "dmon.storage.datahub";
    public static final String DRV = "http://dmon-drv-capmeta-proxy-service-ci02877809-mis3i0.apps.ift-terra000023-eds.ocp.delta.sbrf.ru";

    private static String generateUniqueDHobjID() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yy_HH_mm_ss_SSS");
        return "AQA_TEST_DH_" + LocalDateTime.now().format(formatter);
    }

}
