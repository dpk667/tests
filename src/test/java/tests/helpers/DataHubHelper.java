package tests.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dto.datahubs.*;
import dto.response.DataHubDescriptionResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import tests.base.BaseTest;
import tests.utils.AssertionsHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static constants.ApiEndpoints.*;
import static constants.TestConstants.DMON_STORAGE_META;

public class DataHubHelper extends BaseTest {

    private final ApiClient apiClient;
    private final AssertionsHelper assertionsHelper;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    public DataHubHelper(ApiClient apiClient, AssertionsHelper assertionsHelper) {
        this.apiClient = apiClient;
        this.assertionsHelper = assertionsHelper;
    }

    @Step("получение guid по коду дх {objId}")
    public String getGuidByObjId(String objID) {
        Response response = apiClient.get(V1_DH_GUID, Map.of("datahubCode", objID));

        String json = response.getBody().asString();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(json);
            JsonNode datahubList = root.get("datahubList");

            if (datahubList == null || !datahubList.isArray()) {
                throw  new RuntimeException("Некорректный ответ от сервера - нет массива datahubList");
            }
            for (JsonNode node : datahubList) {
                String code = node.get("code").asText();
                if (objID.equals(code)) {
                    return node.get("guid").asText();
                }
            }
            throw new RuntimeException("для данного objID " + objID + " не наййдене guid");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при разборе ответа: " +json, e);
        }
    }

    @Step("получение описания ДХ по guid: {guid}")
    public DataHubDescriptionResponse getDescriptionByGuid(String guid) {
        Response response = apiClient.get(V1_DH_DESCRIPTION, Map.of("datahubGUID", guid));
        return response.as(DataHubDescriptionResponse.class);
    }

    @Step("Создание ДХ с кодом {objId}")
    public ApplicationRequest<ModuleWithMeta> createDataHubsInDmonStorage(List<String> objIds, String moduleCode) {
        List<DataHub> hubs = objIds.stream()
                .map(id -> new DataHub(id, "Автотест: " + id, "public"))
                .collect(Collectors.toList());

        Meta meta = TestDataFactory.withDataHubs(DMON_STORAGE_META, hubs);
        ModuleWithMeta module = TestDataFactory.module(moduleCode, meta);
        ApplicationRequest<ModuleWithMeta> request = TestDataFactory.app(List.of(module));

        Response response = apiClient.post(V1_METADATA_IMPORT, request);
        Allure.step("проверка статуса ответа запроса {/v1/metadata/import}");
        assertionsHelper.validateSuccessResponse(response, "создание ДХ");

        return request;
    }



    @Step("Создание ДХ с кодом {objId}")
    public ApplicationRequest<ModuleWithMeta> createDataHubsAndReturnResponse(List<String> objIds, String moduleCode) {
        List<DataHub> hubs = objIds.stream()
                .map(id -> new DataHub(id, "Автотест: " + id, "public"))
                .collect(Collectors.toList());

        Meta meta = TestDataFactory.withDataHubs(DMON_STORAGE_META, hubs);
        ModuleWithMeta module = TestDataFactory.module(moduleCode, meta);
        ApplicationRequest<ModuleWithMeta> request = TestDataFactory.app(List.of(module));

        Response response = apiClient.post(V1_METADATA_IMPORT, request);
        Allure.step("проверка статуса ответа запроса {/v1/metadata/import}");
        assertionsHelper.validateSuccessResponse(response, "создангие ДХ");

        return  request;
    }

    @Step("проверка экспорта ДХ в модуле {moduleCode}")
    public ApplicationRequest<ModuleWithMeta> checkDataHubsExportToDmonStorage (String moduleCode) {
        ApplicationRequest<String> request = new ApplicationRequest<>();
        request.setAppClass("application");
        request.setModules(List.of(moduleCode));

        Response response = apiClient.post(V1_METADATA_EXPORT, request);
        assertionsHelper.validateSuccessResponse(response, "экспорт");

        try {
            return objectMapper.readValue(response.getBody().asString(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при десериализации JSON ответа" + e.getMessage(), e);
        }

    }

    @Step("Очистка модуля {moduleCode} от ДХ")
    public void clearDtaHubsFromDmonStorage(String moduleCode) {
        Meta meta = new Meta();
        meta.put(DMON_STORAGE_META, List.of());

        ModuleWithMeta module = TestDataFactory.module(moduleCode, meta);
        ApplicationRequest<ModuleWithMeta> request = TestDataFactory.app(List.of(module));

        Response response = apiClient.post(V1_METADATA_IMPORT, request);
        assertionsHelper.validateSuccessResponse(response, "очистка модуля от ДХ");
    }
}
