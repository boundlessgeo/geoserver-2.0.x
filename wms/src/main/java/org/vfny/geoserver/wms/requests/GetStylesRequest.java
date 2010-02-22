package org.vfny.geoserver.wms.requests;

import java.util.List;

import org.geoserver.wms.WMS;

public class GetStylesRequest extends WMSRequest {

    List<String> layers;

    String sldVer;
    
    public GetStylesRequest(WMS wms) {
        super("GetStyles", wms);
    }

    public String getSldVer() {
        return sldVer;
    }

    public void setSldVer(String sldVer) {
        this.sldVer = sldVer;
    }

    public List<String> getLayers() {
        return layers;
    }

    public void setLayers(List<String> layers) {
        this.layers = layers;
    }

}
