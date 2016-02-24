package com.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "methode")
public class MethodModelImpl implements MethodeModel
{

   /**
 * @uml.property  name="name"
 */
private String name;
   /**
 * @uml.property  name="type"
 */
private String type;
   
   /**
 * @return
 * @uml.property  name="name"
 */
@XmlElement
   public String getName()
   {
      return name;
   }

   /**
 * @param name
 * @uml.property  name="name"
 */
public void setName(String name)
   {
      this.name = name;
   }

   /**
 * @return
 * @uml.property  name="type"
 */
@XmlAttribute
   public String getType()
   {
      return type;
   }

   /**
 * @param type
 * @uml.property  name="type"
 */
public void setType(String type)
   {
      this.type = type;
   }


}
