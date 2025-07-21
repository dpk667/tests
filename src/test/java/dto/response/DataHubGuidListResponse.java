package dto.response;

import java.util.List;

public class DataHubGuidListResponse {
    private List<DataHubGuidResponse> datahubList;

    private List<DataHubGuidResponse> getDataHubList() {
        return datahubList;
    }

    public void setDatahubList(List<DataHubGuidResponse> datahubList) {
        this.datahubList = datahubList;
    }
}
