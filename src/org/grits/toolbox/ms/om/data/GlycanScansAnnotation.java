package org.grits.toolbox.ms.om.data;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.grits.toolbox.ms.om.io.xml.MapOfListsAdapter;

@XmlRootElement(name="glycanAnnotations")
public class GlycanScansAnnotation {
	private HashMap<Integer,List<GlycanFeature>> scanAnnotations = new HashMap<Integer,List<GlycanFeature>>();
    private Integer annotationId = null;
    private String glycanId = null;
	@XmlJavaTypeAdapter(MapOfListsAdapter.class)
	public HashMap<Integer,List<GlycanFeature>> getScanAnnotations() {
		return scanAnnotations;
	}

	public void setScanAnnotations(HashMap<Integer,List<GlycanFeature>> scanAnnotations) {
		this.scanAnnotations = scanAnnotations;
	}

	public Integer getAnnotationId() {
		return annotationId;
	}
	 @XmlAttribute(name="annId")
	public void setAnnotationId(Integer annotationId) {
		this.annotationId = annotationId;
	}

	public String getGlycanId() {
		return glycanId;
	}
    
	 @XmlAttribute(name="glycanId")
	public void setGlycanId(String glycanId) {
		this.glycanId = glycanId;
	}

}
