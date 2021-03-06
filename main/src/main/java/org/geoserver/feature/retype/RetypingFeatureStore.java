/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.feature.retype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geoserver.feature.RetypingFeatureCollection;
import org.geoserver.feature.RetypingFeatureCollection.RetypingFeatureReader;
import org.geotools.data.FeatureLocking;
import org.geotools.data.FeatureReader;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.data.Transaction;
import org.geotools.feature.FeatureCollection;
import org.geotools.util.Converters;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.identity.FeatureId;

/**
 * Renaming wrapper for a {@link FeatureStore} instance, to be used along with {@link RetypingDataStore} 
 */
public class RetypingFeatureStore extends RetypingFeatureSource implements
        FeatureStore<SimpleFeatureType, SimpleFeature> {

    RetypingFeatureStore(RetypingDataStore ds,
            FeatureStore<SimpleFeatureType, SimpleFeature> wrapped, FeatureTypeMap typeMap) {
        super(ds, wrapped, typeMap);
    }
    
    RetypingFeatureStore(FeatureStore<SimpleFeatureType, SimpleFeature>  wrapped, FeatureTypeMap typeMap) throws IOException {
        super(wrapped, typeMap);
    }

    protected FeatureStore<SimpleFeatureType, SimpleFeature> featureStore() {
        return (FeatureStore<SimpleFeatureType, SimpleFeature>) wrapped;
    }

    public Transaction getTransaction() {
        return featureStore().getTransaction();
    }

    public void setTransaction(Transaction transaction) {
        featureStore().setTransaction(transaction);
    }

    public void modifyFeatures(AttributeDescriptor type, Object value, Filter filter) throws IOException {
    	modifyFeatures(new AttributeDescriptor[] {type}, new Object[] {value}, filter);
    }

    public void removeFeatures(Filter filter) throws IOException {
        featureStore().removeFeatures(store.retypeFilter(filter, typeMap));
    }

    public void modifyFeatures(AttributeDescriptor[] type, Object[] values, Filter filter)
            throws IOException {
    	
    	SimpleFeatureType schema = getSchema();
    	SimpleFeatureType original = typeMap.getOriginalFeatureType(); 
    	
    	// map back attribute types and values to the original values
    	AttributeDescriptor[] originalTypes = new AttributeDescriptor[type.length];
    	Object[] originalValues = new Object[values.length];
    	for (int i = 0; i < values.length; i++) {
			originalTypes[i] = original.getDescriptor(type[i].getName());
			if(values[i] != null) {
				Class<?> target = originalTypes[i].getType().getBinding();
				originalValues[i] = Converters.convert(values[i], target);
				if(originalValues[i] == null)
					throw new IOException("Could not map back " + values[i] + " to type " + target);
			}
		}
    	
        featureStore().modifyFeatures(originalTypes, originalValues, store.retypeFilter(filter, typeMap));
    }

    public void setFeatures(FeatureReader<SimpleFeatureType, SimpleFeature> reader)
            throws IOException {
        RetypingFeatureReader retypingFeatureReader;
        retypingFeatureReader = new RetypingFeatureReader(reader, typeMap.getOriginalFeatureType());
        featureStore().setFeatures(retypingFeatureReader);
    }

    public List<FeatureId> addFeatures(FeatureCollection<SimpleFeatureType, SimpleFeature> collection) throws IOException {
        List<FeatureId> ids = featureStore().addFeatures(
                new RetypingFeatureCollection(collection, typeMap.getOriginalFeatureType()));
        List<FeatureId> retyped = new ArrayList<FeatureId>();
        for (FeatureId id : ids) {
            retyped.add(RetypingFeatureCollection.reTypeId(id, typeMap.getOriginalFeatureType(), typeMap.getFeatureType()));
        }
        return retyped;
    }

}
