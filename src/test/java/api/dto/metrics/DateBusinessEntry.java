package api.dto.metrics;

public class DateBusinessEntry {

    private String dateBusiness;

    public DateBusinessEntry(String dateBusiness) {
        this.dateBusiness = dateBusiness;
    }

    public DateBusinessEntry() {}

    public String getDateBusiness() {
        return dateBusiness;
    }

    public void setDateBusiness(String dateBusiness) {
        this.dateBusiness = dateBusiness;
    }
}
