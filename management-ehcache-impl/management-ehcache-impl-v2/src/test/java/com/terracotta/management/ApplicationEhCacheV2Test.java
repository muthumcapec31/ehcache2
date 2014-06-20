package com.terracotta.management;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Anthony Dahanne
 */
public class ApplicationEhCacheV2Test extends JerseyApplicationTestCommon {

  @Test
  public void testGetClasses() throws Exception {
    ApplicationEhCacheV2 applicationEhCache = new ApplicationEhCacheV2();
    Set<Class<?>> applicationClasses = applicationEhCache.getRestResourceClasses();
    Set<Class<?>> annotatedClasses = annotatedClassesFound();
    Set<Class<?>> classesToIgnoreDuringComparison = new HashSet<Class<?>>();

    if (applicationClasses.size() > annotatedClasses.size()) {
      for (Class<?> applicationClass : applicationClasses) {
        if(!annotatedClasses.contains(applicationClass)) {
          // one exception so far, because we filter jersey and jackson resources : SSeFeature
          if (applicationClass.equals(SseFeature.class)) {
            classesToIgnoreDuringComparison.add(applicationClass);
            continue;
          }
          fail("While scanning the classpath, we could not find " + applicationClass);
        }
      }
    } else {
      for (Class<?> annotatedClass : annotatedClasses) {
        if(!applicationClasses.contains(annotatedClass)) {
          fail("Should  " + annotatedClass + " be added to ApplicationEhCacheV2 ?");
        }
      }
    }
    applicationClasses.removeAll(classesToIgnoreDuringComparison);
    Assert.assertThat(annotatedClasses, equalTo(applicationClasses));
  }
}
