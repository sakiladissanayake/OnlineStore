package net.app.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import net.app.rest.service.ProductResource;

@ApplicationPath("/online-store")
public class OnlineStoreApplication extends Application  {
 
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();
 
   public OnlineStoreApplication() {
      singletons.add(new ProductResource());
   }
 
   @Override
   public Set<Class<?>> getClasses() {
      return empty;
   }
 
   @Override
   public Set<Object> getSingletons() {
      return singletons;
   }
}
