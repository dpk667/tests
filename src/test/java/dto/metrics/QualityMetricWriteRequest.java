package dto.metrics;

import java.util.List;

public class QualityMetricWriteRequest {

    private Integer guid;
    private String krnlModuleCode;
    private String dateLastUpdate;
    private List<QualityMetricData> qualityMetricDataList;

    public QualityMetricWriteRequest(Integer guid, String krnlModuleCode, String dateLastUpdate, List<QualityMetricData> qualityMetricDataList) {
        this.guid = guid;
        this.krnlModuleCode = krnlModuleCode;
        this.dateLastUpdate = dateLastUpdate;
        this.qualityMetricDataList = qualityMetricDataList;
    }

    public QualityMetricWriteRequest() {}

    public Integer getGuid() {
        return guid;
    }

    public void setGuid(Integer guid) {
        this.guid = guid;
    }

    public String getKrnlModuleCode() {
        return krnlModuleCode;
    }

    public void setKrnlModuleCode(String krnlModuleCode) {
        this.krnlModuleCode = krnlModuleCode;
    }

    public String getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(String dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public List<QualityMetricData> getQualityMetricDataList() {
        return qualityMetricDataList;
    }

    public void setQualityMetricDataList(List<QualityMetricData> qualityMetricDataList) {
        this.qualityMetricDataList = qualityMetricDataList;
    }
}
