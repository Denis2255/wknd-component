package com.adobe.aem.guides.wknd.core.services.impl;

import com.adobe.aem.guides.wknd.core.services.NodeManager;
import com.adobe.aem.guides.wknd.core.services.ServiceResourceResolver;
import com.day.crx.JcrConstants;
import org.apache.sling.api.resource.ResourceResolver;
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

import javax.jcr.Node;
import java.util.Optional;


@Component(service = NodeManager.class, immediate = true)
@Designate(ocd = NodeManagerImpl.ManagerNodeConfiguration.class)
public class NodeManagerImpl implements NodeManager {

    private ManagerNodeConfiguration configuration;
    private static final Logger LOG = LoggerFactory.getLogger(NodeManagerImpl.class);

    @Reference
    private ServiceResourceResolver serviceResourceResolver;

    @Activate
    @Modified
    public void activate(ManagerNodeConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean createNode() {
        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
            boolean isSet = Optional.ofNullable(resourceResolver.getResource(configuration.nodePath()))
                    .map(r -> r.adaptTo(Node.class))
                    .map(node -> setPropertyForNode(node, configuration.propertyName(), configuration.propertyValue()))
                    .orElse(false);
            if (isSet) {
                resourceResolver.commit();
                return true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return false;

//        try (ResourceResolver resourceResolver = serviceResourceResolver.getServiceResourceResolver()) {
//            Node node = Optional.ofNullable(resourceResolver.getResource(configuration.nodePath()))
//                    .map(r -> r.adaptTo(Node.class)).orElse(null);
//            if (node == null) {
//                LOG.info("Node is null");
//                return false;
//            }
//            Node createdNode = node.getNode(JcrConstants.JCR_CONTENT);
//            createdNode.setProperty(configuration.propertyName(), configuration.propertyValue());
//            resourceResolver.commit();
//            return true;
//        } catch (Exception e) {
//            LOG.info(e.getMessage(), e);
//        }
//        return false;
    }

    private boolean setPropertyForNode(Node node, String propertyName, String value) {
        try {
            Node createdNode = node.getNode(JcrConstants.JCR_CONTENT);
            createdNode.setProperty(propertyName, value);
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @ObjectClassDefinition(name = "WKND_CUSTOM - Node Manager Configuration", description = "Node manager configuration")
    public @interface ManagerNodeConfiguration {
        @AttributeDefinition(
                name = "property name",
                description = "name",
                type = AttributeType.STRING)
        String propertyName();

        @AttributeDefinition(
                name = "property value",
                description = "value",
                type = AttributeType.STRING)
        String propertyValue();

        @AttributeDefinition(
                name = "node path",
                description = "Node path",
                type = AttributeType.STRING)
        String nodePath();

    }
}
