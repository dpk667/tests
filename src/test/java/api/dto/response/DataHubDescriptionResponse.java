package api.dto.response;

public class DataHubDescriptionResponse {
    private String guid;
    private String code;
    private String description;
    private String moduleOwner;
    private String dateRegistration;
    private boolean isIntegral;
    private String scope;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModuleOwner() {
        return moduleOwner;
    }

    public void setModuleOwner(String moduleOwner) {
        this.moduleOwner = moduleOwner;
    }

    public String getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(String dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public boolean isIntegral() {
        return isIntegral;
    }

    public void setIntegral(boolean integral) {
        isIntegral = integral;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
