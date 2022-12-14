package org.grits.toolbox.ms.om.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="scanFeatures")
public class ScanFeatures {
	private int scanId;
	@XmlElement(name="peak")
	private Set<Peak> scanPeaks = new HashSet<Peak>();
	@XmlElement(name="feature")
	private List<Feature> features = new ArrayList<Feature>();
	
	@XmlTransient
	public List<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	@XmlTransient
	public Set<Peak> getScanPeaks() {
		return scanPeaks;
	}
	public void setScanPeaks(Set<Peak> scanPeaks) {
		this.scanPeaks = scanPeaks;
	}
	public int getScanId() {
		return scanId;
	}
	@XmlAttribute(name="Id")
	public void setScanId(int scanId) {
		this.scanId = scanId;
	}

}
