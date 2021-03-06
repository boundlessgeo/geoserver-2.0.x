/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.wms.responses.map.kml;

import java.sql.Connection;
import java.util.Map;

import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.wms.MapLayerInfo;
import org.geotools.data.DefaultQuery;
import org.geotools.data.FeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.MapLayer;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.filter.FilterFactory;
import org.opengis.filter.sort.SortBy;
import org.opengis.filter.sort.SortOrder;
import org.opengis.filter.spatial.BBOX;
import org.vfny.geoserver.wms.WMSMapContext;
import org.vfny.geoserver.wms.WmsException;

/**
 * An attribute based regionating strategy assuming it's possible (and fast) to
 * sort on the user specified attribute. Features with higher values of the
 * attribute will be found in higher tiles.
 * 
 * @author Andrea Aime
 * 
 */
public class NativeSortRegionatingStrategy extends
        CachedHierarchyRegionatingStrategy {

    public NativeSortRegionatingStrategy(GeoServer gs) {
        super(gs);
    }

    static final FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);

    String attribute;

    FeatureSource fs;

    @Override
    protected String getDatabaseName(WMSMapContext con, MapLayer layer)
            throws Exception {
        fs = layer.getFeatureSource();
        SimpleFeatureType type = (SimpleFeatureType) fs.getSchema();

        // find out which attribute we're going to use
        Map options = con.getRequest().getFormatOptions();
        attribute = (String) options.get("regionateAttr");
        if (attribute == null)
            attribute = MapLayerInfo.getRegionateAttribute( featureType );
        if (attribute == null)
            throw new WmsException("Regionating attribute has not been specified");

        // Make sure the attribute is actually there
        AttributeType attributeType = type.getType(attribute);
        if (attributeType == null) {
            throw new WmsException("Could not find regionating attribute "
                    + attribute + " in layer " + featureType.getName());
        }
        
        // check we can actually sort on that attribute
        if(!fs.getQueryCapabilities().supportsSorting(new SortBy[] {ff.sort(attribute, SortOrder.DESCENDING)}))
            throw new WmsException("Native sorting on the " + attribute 
                    + " is not possible for layer " + featureType.getName());
            

        // make sure a special db for this layer and attribute will be created
        return super.getDatabaseName(con, layer) + "_" + attribute;
    }

    @Override
    protected String getDatabaseName(FeatureTypeInfo cfg) throws Exception {
        return super.getDatabaseName(cfg) + "_" +  MapLayerInfo.getRegionateAttribute(cfg);
    }

    public FeatureIterator getSortedFeatures(GeometryDescriptor geom,
    		ReferencedEnvelope latLongEnv, ReferencedEnvelope nativeEnv, 
    		Connection cacheConn) throws Exception {
        // build the bbox filter
        FilterFactory ff = CommonFactoryFinder.getFilterFactory(null);
        BBOX filter = ff.bbox(geom.getLocalName(), nativeEnv.getMinX(),
                nativeEnv.getMinY(), nativeEnv.getMaxX(), nativeEnv.getMaxY(), null);

        // build an optimized query (only the necessary attributes
        DefaultQuery q = new DefaultQuery();
        q.setFilter(filter);
        q.setPropertyNames(new String[] { geom.getLocalName(), attribute });
        // TODO: enable this when JTS learns how to compute centroids
        // without triggering the
        // generation of Coordinate[] out of the sequences...
        // q.setHints(new Hints(Hints.JTS_COORDINATE_SEQUENCE_FACTORY,
        // PackedCoordinateSequenceFactory.class));
        q.setSortBy(new SortBy[] { ff.sort(attribute, SortOrder.DESCENDING) });

        // return the reader
        return fs.getFeatures(q).features();
    }

}
