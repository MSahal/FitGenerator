package com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.service.identity;

import java.util.ArrayList;

import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall.MethodModelImpl;
import com.linedata.ekip.fitnesscreatorplugin.std.shared.devutils.config.model.jaxb.unmarshall.Service;



public class ServiceIdentifier
{

   public String compare(Service service, String methodeName)
   {

      ArrayList<MethodModelImpl> methodList = (ArrayList<MethodModelImpl>) service.getMethodes();
      

      for (int i = 0; i < methodList.size(); i++)
      {
         if ((methodList.get(i).getName().equals(methodeName)) == true) return methodList.get(i).getType();
      }

     
      return null;

   }
}
