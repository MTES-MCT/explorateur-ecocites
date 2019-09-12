/**
 * Orkis_WebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.efficacity.explorateurecocites.ajaris.generated;

public class Orkis_WebServiceLocator extends org.apache.axis.client.Service implements com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebService {

    public Orkis_WebServiceLocator() {
    }


    public Orkis_WebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Orkis_WebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Orkis_WebServicePort
    private java.lang.String Orkis_WebServicePort_address = "http://mineco-ws.ajaris.com/4DSOAP/";

    public java.lang.String getOrkis_WebServicePortAddress() {
        return Orkis_WebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Orkis_WebServicePortWSDDServiceName = "Orkis_WebServicePort";

    public java.lang.String getOrkis_WebServicePortWSDDServiceName() {
        return Orkis_WebServicePortWSDDServiceName;
    }

    public void setOrkis_WebServicePortWSDDServiceName(java.lang.String name) {
        Orkis_WebServicePortWSDDServiceName = name;
    }

    public com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceRPC getOrkis_WebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Orkis_WebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOrkis_WebServicePort(endpoint);
    }

    public com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceRPC getOrkis_WebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceBindingStub _stub = new com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceBindingStub(portAddress, this);
            _stub.setPortName(getOrkis_WebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOrkis_WebServicePortEndpointAddress(java.lang.String address) {
        Orkis_WebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceRPC.class.isAssignableFrom(serviceEndpointInterface)) {
                com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceBindingStub _stub = new com.efficacity.explorateurecocites.ajaris.generated.Orkis_WebServiceBindingStub(new java.net.URL(Orkis_WebServicePort_address), this);
                _stub.setPortName(getOrkis_WebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Orkis_WebServicePort".equals(inputPortName)) {
            return getOrkis_WebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.orkis.com/ajarisnamespace", "Orkis_WebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.orkis.com/ajarisnamespace", "Orkis_WebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Orkis_WebServicePort".equals(portName)) {
            setOrkis_WebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
