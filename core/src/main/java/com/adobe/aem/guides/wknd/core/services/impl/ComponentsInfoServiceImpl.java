package com.adobe.aem.guides.wknd.core.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jcr.Session;

import com.adobe.aem.guides.wknd.core.services.ComponentsInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationStatus;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.adobe.aem.guides.wknd.core.utils.ResolverUtil;

@Component(immediate = true, service = ComponentsInfoService.class)
@Designate(ocd = ComponentsInfoServiceImpl.ComponentInfoServiceConfig.class)
public class ComponentsInfoServiceImpl implements ComponentsInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentsInfoServiceImpl.class);
    private String serviceName;
    @Reference
    private ResourceResolverFactory resourceResolverFactory;


    @Activate
    protected void activate(OSGiConfigImpl.ServiceConfig serviceConfig) {
        serviceName = serviceConfig.serviceName();
    }

    @Override
    public String getIdUser()  {
        String userID = null;
        try (ResourceResolver resolver  = ResolverUtil.newResolver(resourceResolverFactory)){
        userID = resolver.getUserID();
        } catch (Exception e) {
            LOGGER.info("Error occurred:{}",e.getMessage());
        }
        return userID;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @ObjectClassDefinition(name = "AEM WKND - OSGi Configuration", description = "He")
    public @interface ComponentInfoServiceConfig {

        @AttributeDefinition(
                name = "Service Name",
                description = "Enter service name.",
                type = AttributeType.STRING)
        String serviceName() default "Dennis";

    }
}

