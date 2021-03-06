package org.geoserver.flow.config;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.geoserver.flow.ControllerPriorityComparator;
import org.geoserver.flow.FlowController;
import org.geoserver.flow.controller.BasicOWSController;
import org.geoserver.flow.controller.GlobalFlowController;
import org.geoserver.flow.controller.UserFlowController;
import org.geoserver.security.PropertyFileWatcher;

public class DefaultControlFlowConfigurationTest extends TestCase {

    
    public void testParsing() throws Exception {
        Properties p = new Properties();
        p.put("timeout", "10");
        p.put("ows.global", "100");
        p.put("ows.wms.getmap", "8");
        p.put("user", "6");
        
        DefaultControlFlowConfigurator configurator = new DefaultControlFlowConfigurator(new FixedWatcher(p));
        assertTrue(configurator.isStale());
        List<FlowController> controllers = configurator.buildFlowControllers();
        Collections.sort(controllers, new ControllerPriorityComparator());
        assertFalse(configurator.isStale());
        assertEquals(10 * 1000, configurator.getTimeout());
        
        assertEquals(3, controllers.size());
        assertTrue(controllers.get(0) instanceof UserFlowController);
        assertTrue(controllers.get(1) instanceof BasicOWSController);
        assertTrue(controllers.get(2) instanceof GlobalFlowController);
        
        UserFlowController uc = (UserFlowController) controllers.get(0);
        assertEquals(6, uc.getPriority());
        BasicOWSController oc = (BasicOWSController) controllers.get(1);
        assertEquals(8, oc.getPriority());
        assertEquals("wms", oc.getService());
        assertEquals("getmap", oc.getMethod());
        assertNull(oc.getOutputFormat());
        GlobalFlowController gc = (GlobalFlowController) controllers.get(2);
        assertEquals(100, gc.getPriority());
    }
    
    
    static class FixedWatcher extends PropertyFileWatcher {
        boolean stale = true;
        Properties properties;

        public FixedWatcher(Properties properties) {
            super(null);
            this.properties = properties;
        }
        
        @Override
        public boolean isStale() {
            if(stale) {
                stale = false;
                return true;
            } else {
                return false;
            }
        }
        
        @Override
        public Properties getProperties() throws IOException {
            return properties;
        }
    }
}
