package tests.api;

import dto.datahubs.ApplicationRequest;
import dto.datahubs.ModuleWithMeta;
import io.qameta.allure.Allure;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import tests.base.BaseTest;
import tests.helpers.DataHubHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.ApiEndpoints.METADATA_OWNER;
import static constants.ApiEndpoints.V1_DH_DESCRIPTION;
import static constants.TestConstants.AQA_MODULE_ID_1;
import static tests.helpers.TestDataFactory.generateUniqueId;

public class TechModulesTest extends BaseTest {

    @Test
    @TmsLink("MISORC-T674")
    @Tags({@Tag("regress"), @Tag("api"), @Tag("rest")})
    @DisplayName("Назначение технического модуля датахабу")
    void addTechModulesForDH() {
        DataHubHelper dataHubHelper = new DataHubHelper(apiClient, assertionsHelper);
        dataHubHelper.clearDtaHubsFromDmonStorage(AQA_MODULE_ID_1);

        Allure.step("создание дх с  уникальным objId");
        List<String> objIds = List.of(generateUniqueId("AQA_TEST_DH"));

        ApplicationRequest<ModuleWithMeta> importRequest = dataHubHelper.createDataHubsInDmonStorage(objIds, AQA_MODULE_ID_1);
        ApplicationRequest<ModuleWithMeta> exportRequest = dataHubHelper.checkDataHubsExportToDmonStorage(AQA_MODULE_ID_1);

        assertionsHelper.assertJsonEquals(importRequest, exportRequest, "проверка ДХ");

        String guid = dataHubHelper.getGuidByObjId(objIds.get(0));

        Map<String, String> ownerParams = Map.of("krnlModuleCode", AQA_MODULE_ID_1, "datahubGUID", guid);
        Response ownerResponse = apiClient.post(METADATA_OWNER, new HashMap<>(), ownerParams);
        assertionsHelper.validateSuccessResponse(ownerResponse, "проверка назначения ДХ");

        Map<String, String> descriptionParams = Map.of("datahubGUID", guid);
        Response descriptionResponse = apiClient.get(V1_DH_DESCRIPTION, descriptionParams);
        assertionsHelper.validateSuccessResponse(descriptionResponse, "проверка описания ДХ");
    }
}
