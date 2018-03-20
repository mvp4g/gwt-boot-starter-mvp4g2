package de.gishmo.gwtbootstartermvp4g2.server.resource;

import de.gishmo.gwtbootstartermvp4g2.server.resource.model.ProjectZip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/loadZip")
public class DownloadZipSevice {

  @Autowired
  ProjectZip projectZip;

  @Autowired
  private ServletContext servletContext;

  @RequestMapping(method = RequestMethod.GET, path = "/download")
  public ResponseEntity<InputStreamResource> zip(@RequestParam("archive") String zipName)
    throws IOException {
    // get data from session
    String pathToGenerateProjectZip = projectZip.getPathToGenerateProjectZip();
    String nameOfProjectZip = projectZip.getNameOfProjectZip();
    // media type
    MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(servletContext,
                                                                 pathToGenerateProjectZip);

    File file = new File(pathToGenerateProjectZip);
    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

    return ResponseEntity.ok()
                         .header(HttpHeaders.CONTENT_DISPOSITION,
                                 "attachment;filename=" + file.getName())
                         .contentType(mediaType)
                         .contentLength(file.length())
                         .body(resource);
  }
}

