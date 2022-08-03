package org.grits.toolbox.ms.om.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class AnalyteSettings
{
    @XmlElement(name="GlycanSettings")
    private GlycanSettings m_glycanSettings = null;
    @XmlElement(name="PeptideSettings")
    private PeptideSettings m_peptideSettings = null;
    
    @XmlTransient
    public GlycanSettings getGlycanSettings()
    {
        return m_glycanSettings;
    }
    public void setGlycanSettings(GlycanSettings a_glycanSettings)
    {
        m_glycanSettings = a_glycanSettings;
    }
    
    @XmlTransient
    public PeptideSettings getPeptideSettings()
    {
        return m_peptideSettings;
    }
    public void setPeptideSettings(PeptideSettings a_peptideSettings)
    {
        m_peptideSettings = a_peptideSettings;
    }
}
