package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

public class GeneratorUtils {

  public static String setFirstCharacterToUperCase(String value) {
    return value.substring(0,
                           1)
                .toUpperCase() + value.substring(1);
  }

}
