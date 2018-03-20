package de.gishmo.gwtbootstartermvp4g2.server.resource;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class MediaTypeUtils {

  public static MediaType getMediaTypeForFileName(ServletContext servletContext,
                                                  String fileName) {
    String mineType = servletContext.getMimeType(fileName);
    try {
      MediaType mediaType = MediaType.parseMediaType(mineType);
      return mediaType;
    } catch (Exception e) {
      return MediaType.APPLICATION_OCTET_STREAM;
    }
  }
}
