package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "service")
public class Service
{
   /**
 * @uml.property  name="methode"
 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.linedata.ekip.devutils.config.fitnesscreatorplugin.model.jaxb.unmarshall.MethodModelImpl"
 */
List<MethodModelImpl> methode;
   
   @XmlElement(name = "methode")
   public List<MethodModelImpl> getMethodes() {
	return methode;
}

public void setMethodes(List<MethodModelImpl> methodes) {
	this.methode = methodes;
}



}
