/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.security;

import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.Authentication;
import org.geoserver.catalog.LayerInfo;
import org.geoserver.catalog.ResourceInfo;
import org.geoserver.catalog.WorkspaceInfo;

/**
 * Data access manager provides the {@link SecureCatalogImpl} with directives on
 * what the specified user can access.
 * @author Andrea Aime - TOPP
 *
 */
public interface DataAccessManager {
    
    /**
     * The security mode in which the catalog should be operating
     * @author Administrator
     *
     */
    public enum CatalogMode {
        /**
         * If the user does not have enough authorities, just pretend the layers that cannot be
         * read are not there, and those that cannot be written are read only (never ask for
         * authentication, which should be performed prior to data access)
         */
        HIDE,
        /**
         * Always list of all the layers and allow access to each layer metadata.
         * If a user tries to access the data and she cannot read, or to write data and
         * she cannot write, challenge her with an authentication request. 
         * This mode does not hide the existence of layers, and
         * should work fine with most applications requiring authentication. 
         */
        CHALLENGE,
        /**
         * A mixed approach. The methods that do list the contents of the catalog do not
         * report the layers the current user cannot access to, but trying to access the
         * layer directly generates an {@link AcegiSecurityException} that will challenge
         * the user for authentication. This approach assumes the capabilities requests
         * are using the listing methods, whilst any access by name is performed using
         * the direct access methods. This is reasonable, but cannot be guaranteed, so
         * this approach is bound to be more fragile than the other two, given it's
         * based on a programming convention that cannot be enforced.
         */
        MIXED
    }
    
    /**
     * Returns the security mode in which the secure catalog must operate
     * @return
     */
    public CatalogMode getMode();

    /**
     * Returns true if user can access the workspace in the specified mode
     */
    public boolean canAccess(Authentication user, WorkspaceInfo workspace, AccessMode mode);
    
    /**
     * Returns true if user can access the layer in the specified mode
     */
    public boolean canAccess(Authentication user, LayerInfo layer, AccessMode mode);
    
    /**
     * Returns true if user can access the resource in the specified mode
     */
    public boolean canAccess(Authentication user, ResourceInfo resource, AccessMode mode);
}
