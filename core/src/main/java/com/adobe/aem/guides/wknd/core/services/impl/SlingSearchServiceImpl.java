package com.adobe.aem.guides.wknd.core.services.impl;


import com.adobe.aem.guides.wknd.core.services.ServiceResolver;
import com.adobe.aem.guides.wknd.core.services.SlingSearchService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.query.SlingQuery;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Component(service = SlingSearchService.class, immediate = true)
@Designate(ocd = SlingSearchServiceImpl.SearchServiceConfiguration.class)
public class SlingSearchServiceImpl implements SlingSearchService {

    private SlingSearchServiceImpl.SearchServiceConfiguration configuration;
    private static final Logger LOG = LoggerFactory.getLogger(NodeManagerImpl.class);

    @Reference
    private ServiceResolver serviceResolver;

    @Activate
    @Modified
    public void activate(SlingSearchServiceImpl.SearchServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Resource searchChildren() {
        Resource resourceWithList = null;
        try (ResourceResolver resourceResolver = serviceResolver.getServiceResourceResolver()) {
            Resource resource = resourceResolver.getResource(configuration.pathSearch());
            SlingQuery slingQuery;
//            slingQuery = SlingQuery.$(resource).closest("cq:Page[jcr:content/cq:template=my/template]");
//            resourceWithList = slingQuery.asList().get(0);

        } catch (LoginException e) {
            LOG.info(e.getMessage(), e);
        }
        return resourceWithList;
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Search Service Configuration")
    public @interface SearchServiceConfiguration {
        @AttributeDefinition(
                name = "path search",
                description = "Path search",
                type = AttributeType.STRING)
        String pathSearch() default "/content/wknd/us/en";
    }
}
