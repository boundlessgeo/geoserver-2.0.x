/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.platform;

import java.util.ArrayList;
import java.util.List;

import org.geotools.util.Version;


/**
 * A service descriptor which provides metadata such as id, and version.
 * <p>
 * Service descriptors are identified by an id, version pair. Two service
 * descriptors are considered equal if they have the same id, and version.
 * </p>
 * <p>
 * The underlying service implementation is a plain old java object,
 * available via {@link #service}.
 * </p>
 *
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 *
 */
public final class Service {
    /**
     * Identifier for the service.
     */
    final String id;

    /**
     * The service implementation.
     */
    final Object service;

    /**
     * The service version
     */
    final Version version;

    /**
     * List of operations provided by the service
     */
    final List<String> operations;

    /**
     * Creates a new service descriptor.
     *
     * @param id A string identifing the service.
     * @param service The object implementing the service.
     * @param version The version of the service.
     */
    public Service(String id, Object service, Version version, List<String> operations) {
        this.id = id;
        this.service = service;
        this.version = version;
        this.operations = operations;
        
        if (id == null) {
            throw new NullPointerException("id");
        }
    }

    public String getId() {
        return id;
    }

    public Object getService() {
        return service;
    }

    public Version getVersion() {
        return version;
    }

    public List<String> getOperations() {
        return operations;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Service)) {
            return false;
        }

        Service other = (Service) obj;

        if (!id.equals(other.id)) {
            return false;
        }

        if (version == null) {
            if (other.version != null) {
                return false;
            }
        } else {
            if (!version.equals(other.version)) {
                return false;
            }
        }

        return operations.equals( other.operations );
    }

    public int hashCode() {
        int result = 0;

        result = id.hashCode();

        if (version != null) {
            result = (result * 17) + version.hashCode();
        }

        result = (result * 17) + operations.hashCode();

        return result;
    }

    public String toString() {
        return "Service( " + id + ", " + version + " )";
    }
}
