package org.nearbyshops.ModelSettings;

/**
 * Created by sumeet on 21/6/16.
 */
public class CurrentServiceConfiguration {

    private ServiceConfigurationLocal serviceConfigurationLocal;
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

    public ServiceConfigurationLocal getServiceConfigurationLocal() {
        return serviceConfigurationLocal;
    }

    public void setServiceConfigurationLocal(ServiceConfigurationLocal serviceConfigurationLocal) {
        this.serviceConfigurationLocal = serviceConfigurationLocal;
    }
}
