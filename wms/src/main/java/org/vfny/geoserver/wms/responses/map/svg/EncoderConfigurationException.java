/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.wms.responses.map.svg;

import org.vfny.geoserver.wms.WmsException;


/**
 * @author Gabriel Rold?n
 * @version $Id$
 */
public class EncoderConfigurationException extends WmsException {
    public EncoderConfigurationException(String message) {
        super(message);
    }

    public EncoderConfigurationException(String message, String locator) {
        super(message, locator);
    }

    public EncoderConfigurationException(Throwable e, String message, String locator) {
        super(e, message, locator);
    }
}
