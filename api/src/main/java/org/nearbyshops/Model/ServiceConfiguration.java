package org.nearbyshops.Model;

/**
 * Created by sumeet on 21/6/16.
 */
public class ServiceConfiguration {


    Service service;

    int configurationID;
    String name;
    Integer serviceID; // foreign key



    public int getConfigurationID() {
        return configurationID;
    }

    public void setConfigurationID(int configurationID) {
        this.configurationID = configurationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
