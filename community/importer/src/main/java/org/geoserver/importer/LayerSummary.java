/* Copyright (c) 2001 - 2008 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.importer;

import java.io.Serializable;

import org.geoserver.catalog.LayerInfo;

/**
 * Single layer import summary information
 */
@SuppressWarnings("serial")
public class LayerSummary implements Serializable {

    String layerName;

    LayerInfo layer;

    ImportStatus status;

    Exception error;

    public LayerSummary(String layerName, LayerInfo layer, ImportStatus status) {
        this.layerName = layerName;
        this.layer = layer;
        this.status = status;
    }

    public LayerSummary(String layerName, LayerInfo layer, Exception error) {
        this.layerName = layerName;
        this.layer = layer;
        this.status = ImportStatus.OTHER;
        this.error = error;
    }

    public Exception getError() {
        return error;
    }

    public ImportStatus getStatus() {
        return status;
    }

    public void setStatus(ImportStatus status) {
        this.status = status;
    }

    public String getLayerName() {
        return layerName;
    }

    /**
     * Returns the LayerInfo built during the config process. It will be available only if the layer
     * was partially configured but it was not saved due to some error (usually duplicate name or
     * lack of lat/lon bounding box)
     * 
     * @return
     */
    public LayerInfo getLayer() {
        return layer;
    }

}
