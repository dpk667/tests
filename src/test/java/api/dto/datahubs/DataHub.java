package api.dto.datahubs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"objID", "description", "scope"})
public class DataHub {
    private String objID;

    @JsonProperty("description")
    private String description;
    private String scope;

    public DataHub(String objID, String description, String scope) {
        this.objID = objID;
        this.description = description;
        this.scope = scope;
    }

    public DataHub() {}

    public String getObjID() {
        return objID;
    }

    public void setObjID(String objID) {
        this.objID = objID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public static DataHub of(String id, String desc, String scope) {
        return new DataHub(id, desc, scope);
    }
}
