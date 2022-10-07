package com.adobe.aem.guides.wknd.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;

@Model(adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FigureModel {

    @ValueMapValue
    private String figure;

    @ValueMapValue
    private double squareSideLength;

    @ValueMapValue
    private double rectangleSideLength;

    @ValueMapValue
    private double rectangleSideWidth;

    @ValueMapValue
    private double circleRadius;

    public String getFigure() {
        return figure;
    }

    public double getSquareArea() {
        return squareSideLength * squareSideLength;
    }

    public double getRectangleArea() {
        return rectangleSideLength * rectangleSideWidth;
    }

    public double getCircleArea() {
        return Math.PI * circleRadius * circleRadius;
    }

}

