/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.security.service;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.geoserver.security.ServiceAccessRule;
import org.geoserver.security.ServiceAccessRuleDAO;
import org.geoserver.web.wicket.GeoServerDataProvider;

/**
 * Page listing the rules contained in the service.properties file 
 */
@SuppressWarnings("serial")
public class ServiceAccessRuleProvider extends GeoServerDataProvider<ServiceAccessRule> {
    
    public static final Property<ServiceAccessRule> RULEKEY = new BeanProperty<ServiceAccessRule>("key", "key");
    public static final Property<ServiceAccessRule> ROLES = new BeanProperty<ServiceAccessRule>("roles", "value");

    @Override
    protected List<ServiceAccessRule> getItems() {
        return ServiceAccessRuleDAO.get().getRules();
    }

    @Override
    protected List<Property<ServiceAccessRule>> getProperties() {
        return Arrays.asList(RULEKEY, ROLES);
    }

    public IModel model(Object object) {
        return new Model((ServiceAccessRule) object);
    }

}
