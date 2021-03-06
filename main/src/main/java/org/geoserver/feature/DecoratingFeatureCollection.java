/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.feature;

import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;


/**
 * Base class for a feature collection with decorates another feature collection.
 * <p>
 * Subclasses should override methods as needed to "decorate" .
 * </p>
 *
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 * @deprecated use {@link org.geotools.feature.collection.DecoratingFeatureCollection}.
 */
public class DecoratingFeatureCollection 
   extends org.geotools.feature.collection.DecoratingFeatureCollection<SimpleFeatureType, SimpleFeature> { 
    
    public DecoratingFeatureCollection(FeatureCollection<SimpleFeatureType, SimpleFeature> delegate) {
        super( delegate );
    }

}
