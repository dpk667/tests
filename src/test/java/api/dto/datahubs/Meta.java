package api.dto.datahubs;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO написать доку по работе класса
@JsonDeserialize(using = MetaDeserializer.class)
public class Meta {

    @JsonAnySetter
    private final Map<String, Object> metaMap = new HashMap<>();

    public Meta() {}

    public Meta(Map<String, ?> initialData) {
        if (initialData != null) {
            metaMap.putAll(initialData);
        }
    }

    @JsonAnyGetter
    public Map<String, Object> getMetaMap() {return metaMap; }

    @JsonAnySetter
    public void put(String  key, Object value) {
        metaMap.put(key ,value);
    }

    public void setMappingDH(List<MappingDataHub> list) {
        metaMap.put("dmon.drvcapmeta.mappingDH", list);
    }

    public void  setDataHub(List<DataHub> list) {
        metaMap.put("dmon.storage.datahub", list);
    }

    public static Meta withDataHub(String key, List<DataHub> hubs) {
        Meta m = new Meta();
        m.put(key, hubs);
        return m;
    }

    public static Meta withMappingDH(List<MappingDataHub> list) {
        Meta m = new Meta();
        m.setMappingDH(list);
        return m;
    }
}
