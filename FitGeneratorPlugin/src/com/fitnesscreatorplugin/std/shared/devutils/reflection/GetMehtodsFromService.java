package com.fitnesscreatorplugin.std.shared.devutils.reflection;

import java.lang.reflect.Method;

public class GetMehtodsFromService
{

   public Method[] runExtractMehtodsFromService(Class<?> obj)
   {
      Method[] ListMethods = obj.getDeclaredMethods();

      return ListMethods;

   }

}
