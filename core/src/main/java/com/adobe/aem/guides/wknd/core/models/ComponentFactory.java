package com.adobe.aem.guides.wknd.core.models;

public interface ComponentFactory {
    public String getServiceName() ;
    public int getServiceCount();
    public boolean isLiveData();
    public String[] getCountries();
    public String getRunModes();}
