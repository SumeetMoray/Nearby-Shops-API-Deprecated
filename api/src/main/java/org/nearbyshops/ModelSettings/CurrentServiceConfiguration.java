package org.nearbyshops.ModelSettings;

import org.nearbyshops.ModelSettings.ServiceConfiguration;

/**
 * Created by sumeet on 21/6/16.
 */
public class CurrentServiceConfiguration {

    private ServiceConfiguration serviceConfiguration;
    private int configurationID;
    private String name;
    private Integer serviceID; // foreign key


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

    public ServiceConfiguration getServiceConfiguration() {
        return serviceConfiguration;
    }

    public void setServiceConfiguration(ServiceConfiguration serviceConfiguration) {
        this.serviceConfiguration = serviceConfiguration;
    }
}
