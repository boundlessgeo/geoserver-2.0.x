package org.geoserver.monitor;

import static org.geoserver.monitor.MonitorServletRequestTest.data;

import java.io.IOException;

import org.geoserver.monitor.MonitorServletResponse.MonitorOutputStream;

import com.mockrunner.mock.web.MockServletOutputStream;

import junit.framework.TestCase;

public class MonitorServletResponseTest extends TestCase {

    public void testOutputStream() throws IOException {
        byte[] data = data();
        
        MockServletOutputStream mock = new MockServletOutputStream();
        MonitorOutputStream out = new MonitorOutputStream(mock);
        out.write(data);
        
        assertEquals(data.length, mock.getBinaryContent().length);
        assertEquals(data.length, out.getBytesWritten());
    }
}
