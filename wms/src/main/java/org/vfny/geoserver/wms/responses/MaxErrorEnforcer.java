/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.wms.responses;

import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.RenderListener;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Attaches itself to the renderer and ensures no more than a certain amount of errors occur, if
 * they do, the rendering process is stopped
 * @author Andrea Aime - OpenGeo
 */
public class MaxErrorEnforcer {

    GTRenderer renderer;

    int maxErrors;

    int errors;
    
    Exception lastException;

    /**
     * Builds a new max errors enforcer. If maxErrors is not positive the enforcer will do nothing
     * 
     * @param renderer
     * @param maxErrors
     */
    public MaxErrorEnforcer(GTRenderer renderer, int maxErrors) {
        this.renderer = renderer;
        this.maxErrors = maxErrors;
        this.errors = 0;

        if (maxErrors > 0) {
            renderer.addRenderListener(new RenderListener() {

                public void featureRenderer(SimpleFeature feature) {
                }

                public void errorOccurred(Exception e) {
                    errors++;
                    lastException = e;
                    if (errors > MaxErrorEnforcer.this.maxErrors) {
                        MaxErrorEnforcer.this.renderer.stopRendering();
                    }
                }
            });
        }
    }

    /**
     * True if the max error threshold was exceeded
     * @return
     */
    public boolean exceedsMaxErrors() {
        return maxErrors > 0 && errors > maxErrors;
    }
    
    /**
     * Returns the last exception occurred (or null if none happened)
     * @return
     */
    public Exception getLastException() {
        return lastException;
    }

}
