/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.ows;

import java.io.BufferedReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A collection of the informations collected and parsed by the
 * {@link Dispatcher} while doing its dispatching work. In case of dispatching
 * exceptions some fields may be left blank, depending how far the dispatching
 * went.
 * 
 * @author Justin DeOliveira
 * @author Andrea Aime
 */
public class Request {
    /**
     * Http request / response
     */
    protected HttpServletRequest httpRequest;

    protected HttpServletResponse httpResponse;

    /**
     * flag indicating if the request is get
     */
    protected boolean get;

    /**
     * Kvp parameters, only non-null if get = true
     */
    protected Map kvp;

    /**
     * raw kvp parameters, unparsed
     */
    protected Map rawKvp;

    /**
     * buffered input stream, only non-null if get = false
     */
    protected BufferedReader input;

    /**
     * The ows service,request,version
     */
    protected String service;

    protected String request;

    protected String version;

    /**
     * The output format of hte request
     */
    protected String outputFormat;

    /**
     * Any errors that occur tryinng to determine the service
     */
    protected Throwable error;

    /**
     * Returns the raw http request being handled by the {@link Dispatcher}
     * @return
     */
    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    /**
     * Returns the raw http response being handled by the {@link Dispatcher}
     * @return
     */
    public HttpServletResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * True if the request is a GET one
     * @return
     */
    public boolean isGet() {
        return get;
    }

    /**
     * The parsed key value pair map
     */
    public Map getKvp() {
        return kvp;
    }

    /**
     * The raw, un-parsed key value pair map
     */
    public Map getRawKvp() {
        return rawKvp;
    }

    /**
     * The input as read from the http request. The {@link Dispatcher} will perform some preventive
     * reading on the input so never use the raw {@link HttpServletRequest} one
     */
    public BufferedReader getInput() {
        return input;
    }

    /**
     * The service requested 
     * @return
     */
    public String getService() {
        return service;
    }

    /**
     * The operation requested against the service
     * @return
     */
    public String getRequest() {
        return request;
    }

    /**
     * The service version
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * The output format
     * @return
     */
    public String getOutputFormat() {
        return outputFormat;
    }

    /**
     * The eventual error thrown during request parsing, execution or output writing
     * @return
     */
    public Throwable getError() {
        return error;
    }

    public String toString() {
        return getService() + " " + getVersion() + " " + getRequest();
    }

    /**
     * Allows callbacks to override the http request
     * @param httpRequest
     */
    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * Allows callbacks to override the http response
     * @param httpRequest
     */
    public void setHttpResponse(HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    /**
     * Allows callbacks to change the GET status
     * @param httpRequest
     */
    public void setGet(boolean get) {
        this.get = get;
    }

    /**
     * Allows callbacks to change the parsed KVP map
     * @param kvp
     */
    public void setKvp(Map kvp) {
        this.kvp = kvp;
    }

    /**
     * Allows callbacks to override the parsed kvp map
     * @param rawKvp
     */
    public void setRawKvp(Map rawKvp) {
        this.rawKvp = rawKvp;
    }

    /**
     * Allows callbacks to override/wrap the input reader
     * @param input
     */
    public void setInput(BufferedReader input) {
        this.input = input;
    }
    
    /**
     * Allows callbacks to override the service
     * @param service
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Allows callbacks to override the requested operation
     * @param service
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * Allows callbacks to override the version
     * @param service
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Allows callbacks to override the output format
     * @param service
     */
    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    /**
     * Allows callbacks to override the operation execution error
     * @param service
     */
    public void setError(Throwable error) {
        this.error = error;
    }
}