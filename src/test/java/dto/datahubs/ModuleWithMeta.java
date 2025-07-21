package dto.datahubs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//TODO написать доку по работе класса
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModuleWithMeta {
    private String id;
    private Meta meta;

    public ModuleWithMeta() {

    }

    public ModuleWithMeta(String id, Meta meta) {
        this.id = id;
        this.meta = meta;
    }

    public static ModuleWithMeta of(String id, Meta meta) {
        return new ModuleWithMeta(id , meta);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
