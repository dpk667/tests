package dto.datahubs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class", "modules"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationRequest<T> {

    @JsonProperty("class")
    private String appClass;
    private List<T> modules;

    public String getAppClass() {
        return appClass;
    }

    public void setAppClass(String appClass) {
        this.appClass = appClass;
    }

    public List<T> getModules() {
        return modules;
    }

    public void setModules(List<T> modules) {
        this.modules = modules;
    }

    public static <T> ApplicationRequest<T> of(String appClass, List<T> modules) {
        ApplicationRequest<T> req = new ApplicationRequest<>();
        req.setAppClass(appClass);
        req.setModules(modules);
        return req;
    }
}
