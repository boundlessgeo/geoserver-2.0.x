package org.geoserver.monitor;

import java.util.List;

public interface MonitorDAO {

    RequestData add(RequestData data);
    
    void update(RequestData data);
    
    void save(RequestData data);
    
    RequestData getRequest(long id);
    
    List<RequestData> getRequests();
    
    List<RequestData> getRequests(MonitorQuery query);
    
    List<RequestData> getOwsRequests();
    
    List<RequestData> getOwsRequests(String service, String operation, String version);
    
    void clear();
    
    void dispose();
}
