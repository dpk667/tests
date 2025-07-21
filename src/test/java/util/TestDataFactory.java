package util;

import api.dto.datahubs.*;
import api.dto.metrics.DateBusinessEntry;
import api.dto.metrics.QualityMetricData;
import api.dto.metrics.QualityMetricReadRequest;
import api.dto.metrics.QualityMetricWriteRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestDataFactory {

    public static <T> ApplicationRequest<T> app(List<T> modules) {
        ApplicationRequest<T> req = new ApplicationRequest<>();
        req.setAppClass("application");
        req.setModules(modules);
        return req;
    }

    public static String generateUniqueId(String prefix) {
        return prefix + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static DataHub dataHub(String prefix) {
        return new DataHub(generateUniqueId(prefix), "Тестовый для капмета import", "public");
    }

    public static List<DataHub> dataHubs(String prefix, String description, String scope, int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> new DataHub(prefix + "_" + i, description, scope))
                .collect(Collectors.toList());
    }

    public static List<DataHub> dataHubs(String description, Map<String, String> scope) {
        return scope.entrySet().stream()
                .map(entry -> new DataHub(entry.getKey(), description, entry.getValue()))
                .collect(Collectors.toList());
    }

    public static List<DataHub> dataHubs(String description, String scope, String... names) {
        return Arrays.stream(names)
                .map(name -> new DataHub(name, description, scope))
                .collect(Collectors.toList());
    }

    public static List<DataHub> dataHubList(String prefix, int count) {
        return new ArrayList<>(Collections.nCopies(count, null)).stream()
                .map(i -> dataHub(prefix))
                .collect(Collectors.toList());
    }

    public static MappingDataHub mapping(String dmonId, String metaId, String type) {
        return new MappingDataHub(dmonId, metaId, type);
    }

    public static List<MappingDataHub> mappingList(String metaId, String type, int count) {
        return new ArrayList<>(Collections.nCopies(count, null)).stream()
                .map(i -> mapping(generateUniqueId("DMON"), metaId, type))
                .collect(Collectors.toList());
    }

    public static Meta withDataHubs(String key, List<DataHub> hubs) {
        return Meta.withDataHub(key, hubs);
    }

    public static ModuleWithMeta module(String id, Meta meta) {
        return new ModuleWithMeta(id, meta);
    }

    public static ModuleOnly moduleOnly(String id) {
        return new ModuleOnly(id);
    }

    public static ApplicationRequest<ModuleWithMeta> fullApp(ModuleWithMeta... modules) {
        return ApplicationRequest.of("application", Arrays.asList(modules));
    }

    public static ApplicationRequest<ModuleOnly> appWithModuleIds(String... ids) {
        List<ModuleOnly> list = Arrays.stream(ids)
                .map(TestDataFactory::moduleOnly)
                .collect(Collectors.toList());

        return ApplicationRequest.of("application", list);
    }

    public static ApplicationRequest<String> exportApp(String... modulesIds) {
        return ApplicationRequest.of("application", List.of(modulesIds));
    }

    public static Map<String, Object> availabilityMetricParams(String moduleCode, Object datahubGuid, boolean isAvailable, String dateLastUpdate, String metaobjRealeaseId) {
        return Map.of("krnlModuleCode", moduleCode,
                "datahubGuid", datahubGuid,
                "isAvailable", Boolean.toString(isAvailable),
                "dateLastUpdate", dateLastUpdate,
                "x-ce2-metaobj-release-id", metaobjRealeaseId);
    }

    public static Map<String, Object> actualityMetricParams(String moduleCode, Object datahubGuid, boolean isActual, String dateBusinessFrom, String dateBusinessTo, String dateLastUpdate) {
        return Map.of("krnlModuleCode", moduleCode,
                "datahubGuid", datahubGuid,
                "isActual", Boolean.toString(isActual),
                "dateBusinessFrom", dateBusinessFrom,
                "dateBusinessTo", dateBusinessTo,
                "dateLastUpdate", dateLastUpdate);
    }

    public static QualityMetricWriteRequest qualityMetricWriteRequest(int datahubGuid, String moduleCode, String requestTimestamp, List<QualityMetricData> metrics) {
        QualityMetricWriteRequest request = new QualityMetricWriteRequest();
        request.setGuid(datahubGuid);
        request.setKrnlModuleCode(moduleCode);
        request.setDateLastUpdate(requestTimestamp);
        request.setQualityMetricDataList(metrics);
        return request;
    }

    public static QualityMetricData qualityMetric(String codeMetric, String desc, String value, String dateBusinessFrom, String dateBusinessTo, String dateLastUpdate) {
        QualityMetricData metric = new QualityMetricData();
        metric.setCodeMetric(codeMetric);
        metric.setMetricDesc("Тестовая метрика " + desc);
        metric.setMetricValue(value);
        metric.setDateBusinessFrom(dateBusinessFrom);
        metric.setDateBusinessTo(dateBusinessTo);
        metric.setDateActLastUpdate(dateLastUpdate);
        return metric;
    }

    public static Map<String, String> availabilityReadParams(String datahubGuid) {
        return Map.of("datahubGUID", datahubGuid);
    }

    public static Map<String, String> actualityReadParams(String datahubGuid, String dateBusinessFrom) {
        return Map.of("datahubGUID", datahubGuid, "dateBusinessFrom", dateBusinessFrom);
    }

    public static QualityMetricReadRequest qualityReadRequest(List<String> code, List<String> businessDates) {
        QualityMetricReadRequest request = new QualityMetricReadRequest();
        request.setCodeMetricQualityList(code);
        request.setDateBusinessList(businessDates.stream().map(DateBusinessEntry::new).collect(Collectors.toList()));
        return request;
    }

    public static List<MappingDataHub> mappingList(Map<String, String> mapping, String... mappingTypes) {
        List<MappingDataHub> result = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            String mappingType = mappingTypes.length > index ? mappingTypes[index] : mappingTypes[0];
            result.add(new MappingDataHub(entry.getKey(), entry.getValue(), mappingType));
            index++;
        }
        return result;
    }

    public static ApplicationRequest<ModuleWithMeta> mappingImportRequest(String moduleId, List<MappingDataHub> mappingList) {
        Meta meta = new Meta();
        meta.setMappingDH(mappingList);

        ModuleWithMeta module = new ModuleWithMeta();
        module.setId(moduleId);
        module.setMeta(meta);

        ApplicationRequest<ModuleWithMeta> request = new ApplicationRequest<>();
        request.setAppClass("application");
        request.setModules(List.of(module));

        return request;
    }

    public static ApplicationRequest<ModuleWithMeta> mappingCleanRequest(String moduleId) {
        return mappingImportRequest(moduleId, new ArrayList<>());
    }

    public static Meta withObjIds(String className, List<String> objIds) {
        List<DataHub> dataHubs = objIds.stream()
                .map(id -> new DataHub(id, "Автотест: " + id, "public"))
                .collect(Collectors.toList());

        Meta meta = new Meta();
        meta.put(className, dataHubs);

        return meta;
    }

}
