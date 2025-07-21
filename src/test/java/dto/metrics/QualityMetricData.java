package dto.metrics;

public class QualityMetricData {
    private String codeMetric;
    private String metricDesc;
    private String metricValue;
    private String dateBusinessFrom;
    private String dateBusinessTo;
    private String dateActLastUpdate;

    public QualityMetricData(String codeMetric, String metricDesc, String metricValue, String dateBusinessFrom, String dateBusinessTo, String dateActLastUpdate) {
        this.codeMetric = codeMetric;
        this.metricDesc = metricDesc;
        this.metricValue = metricValue;
        this.dateBusinessFrom = dateBusinessFrom;
        this.dateBusinessTo = dateBusinessTo;
        this.dateActLastUpdate = dateActLastUpdate;
    }

    public QualityMetricData() {}

    public String getCodeMetric() {
        return codeMetric;
    }

    public void setCodeMetric(String codeMetric) {
        this.codeMetric = codeMetric;
    }

    public String getMetricDesc() {
        return metricDesc;
    }

    public void setMetricDesc(String metricDesc) {
        this.metricDesc = metricDesc;
    }

    public String getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(String metricValue) {
        this.metricValue = metricValue;
    }

    public String getDateBusinessFrom() {
        return dateBusinessFrom;
    }

    public void setDateBusinessFrom(String dateBusinessFrom) {
        this.dateBusinessFrom = dateBusinessFrom;
    }

    public String getDateBusinessTo() {
        return dateBusinessTo;
    }

    public void setDateBusinessTo(String dateBusinessTo) {
        this.dateBusinessTo = dateBusinessTo;
    }

    public String getDateActLastUpdate() {
        return dateActLastUpdate;
    }

    public void setDateActLastUpdate(String dateActLastUpdate) {
        this.dateActLastUpdate = dateActLastUpdate;
    }
}
