package org.grits.toolbox.ms.om.io.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.grits.toolbox.ms.om.data.Feature;
import org.grits.toolbox.ms.om.data.GlycanFeature;

public class FeatureAdapter extends
XmlAdapter<FeatureAdapter.AdaptedFeature, Feature> {

@Override
public AdaptedFeature marshal(Feature feature)
    throws Exception {
    if (null == feature) {
        return null;
    }
    GlycanFeature gFeature = new GlycanFeature();
    AdaptedFeature adFeature = new AdaptedFeature();
    if (feature instanceof GlycanFeature) {
    	gFeature = (GlycanFeature) feature;
    	adFeature.glycanFeature = gFeature;
    } 
    return adFeature;
}

@Override
public Feature unmarshal(AdaptedFeature adFeature)
    throws Exception {
    if (null == adFeature) {
        return null;
    }
    if (null != adFeature.glycanFeature) {
    	GlycanFeature gFeature = new GlycanFeature();
    	gFeature = adFeature.glycanFeature;
        return gFeature;
    } 
    return null;
}

public static class AdaptedFeature {

    @XmlElement
    public GlycanFeature glycanFeature;

}


}
