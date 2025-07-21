package dto.datahubs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MappingDataHub {

    @JsonProperty("DMONdatahubObjID")
    private String dmondatahubObjID;

    @JsonProperty("METAdatahubObjID")
    private String metadatahubObjID;

    @JsonProperty("mappingType")
    private String mappingType;

    public MappingDataHub() {

    }

    public MappingDataHub( String dmondatahubObjID, String metadatahubObjID, String mappingType) {
        this.dmondatahubObjID = dmondatahubObjID;
        this.metadatahubObjID = metadatahubObjID;
        this.mappingType = mappingType;
    }

    public String getDmondatahubObjID() {
        return dmondatahubObjID;
    }

    public void setDmondatahubObjID(String dmondatahubObjID) {
        this.dmondatahubObjID = dmondatahubObjID;
    }

    public String getMetadatahubObjID() {
        return metadatahubObjID;
    }

    public void setMetadatahubObjID(String metadatahubObjID) {
        this.metadatahubObjID = metadatahubObjID;
    }

    public String getMappingType() {
        return mappingType;
    }

    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }
}
