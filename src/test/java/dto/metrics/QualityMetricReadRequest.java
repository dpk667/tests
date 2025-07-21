package dto.metrics;

import java.util.List;

public class QualityMetricReadRequest {

    private List<DateBusinessEntry> dateBusinessList;
    private List<String> codeMetricQualityList;

    public QualityMetricReadRequest(List<DateBusinessEntry> dateBusinessList, List<String> codeMetricQualityList) {
        this.dateBusinessList = dateBusinessList;
        this.codeMetricQualityList = codeMetricQualityList;
    }

    public QualityMetricReadRequest() {}

    public List<DateBusinessEntry> getDateBusinessList() {
        return dateBusinessList;
    }

    public void setDateBusinessList(List<DateBusinessEntry> dateBusinessList) {
        this.dateBusinessList = dateBusinessList;
    }

    public List<String> getCodeMetricQualityList() {
        return codeMetricQualityList;
    }

    public void setCodeMetricQualityList(List<String> codeMetricQualityList) {
        this.codeMetricQualityList = codeMetricQualityList;
    }
}
