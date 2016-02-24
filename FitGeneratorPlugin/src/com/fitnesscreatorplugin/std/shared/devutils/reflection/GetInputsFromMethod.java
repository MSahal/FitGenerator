package com.fitnesscreatorplugin.std.shared.devutils.reflection;

import java.lang.reflect.Method;

public class GetInputsFromMethod
{

   @SuppressWarnings("rawtypes")
   public Class[] runExtractInputsFromMethod(Method methode)
   {
      Class[] params = methode.getParameterTypes();

      return params;
   }

}
