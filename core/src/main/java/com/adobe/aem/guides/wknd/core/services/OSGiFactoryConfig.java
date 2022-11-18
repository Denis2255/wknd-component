package com.adobe.aem.guides.wknd.core.services;

import java.util.List;

public interface OSGiFactoryConfig {
    public String getServiceName();
    public int getServiceCount();
    public boolean isLiveData();
    public String[] getCountries() ;
    public String getRunModes();

}
