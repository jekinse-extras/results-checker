package org.jboss.qe.collector.filterslists;

import com.google.inject.Inject;
import org.jboss.qe.collector.Tools;
import org.jboss.qe.collector.filter.Filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *  Created by fjerabek on 14.9.16.
 */
public class FiltersFromFile implements FiltersList {
   private List<Filter> filters;

   @Inject
   public FiltersFromFile() {
      this.filters = getFilterFromFile(new File(Tools.getEnvironmentVariable("JAR_PATH")),
          Tools.getEnvironmentVariable("PACKAGE"));
   }

   private List<Filter> getFilterFromFile(File file, String pckg) {

      List<Filter> filters = new ArrayList<>();
      List<Class<?>> classes;
      try {
         JarFile jarFile = new JarFile(file);
         classes = checkJarFile(jarFile, pckg);
         for (Class<?> aClass : classes) {
            if (Filter.class.isAssignableFrom(aClass))
               filters.add((Filter) aClass.newInstance());
         }
      } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
         e.printStackTrace();
      }
      return filters;
   }

   private List<Class<?>> checkJarFile(JarFile jarFile,
                                       String pckgname)
       throws ClassNotFoundException, IOException {
      ArrayList<Class<?>> classes = new ArrayList<>();
      final Enumeration<JarEntry> entries = jarFile.entries();
      String name;

      for (JarEntry jarEntry; entries.hasMoreElements()
          && ((jarEntry = entries.nextElement()) != null);) {
         name = jarEntry.getName();

         if (name.contains(".class")) {
            name = name.substring(0, name.length() - 6).replace('/', '.');

            if (name.contains(pckgname)) {
               classes.add(Class.forName(name));
            }
         }
      }
      return classes;
   }

   @Override
   public List<Filter> getFilters() {
      return filters;
   }
}
