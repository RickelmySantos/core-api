package com.br.core.util;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Bean {
  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
        .filter(propertyName -> {
          try {
            return wrappedSource.getPropertyValue(propertyName) == null;
          } catch (Exception e) {
            return false;
          }
        }).toArray(String[]::new);
  }

}
