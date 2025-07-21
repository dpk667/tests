package api.dto.datahubs;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//TODO написать доку по работе класса
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleOnly {
    private String id;

    public ModuleOnly() {}

    public ModuleOnly(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
