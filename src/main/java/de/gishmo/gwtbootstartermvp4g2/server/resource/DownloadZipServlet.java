package de.gishmo.gwtbootstartermvp4g2.server.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.model.ProjectZip;

@Controller
@RequestMapping("/loadZip")
public class DownloadZipServlet {

  @Autowired
  ProjectZip projectZip;

//  @RequestMapping(method = RequestMethod.POST, path = "/download/*")
//  @ResponseBody
//  public synchronized ResponseEntity<String> download()
//    throws IOException {
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
  @RequestMapping(method = RequestMethod.GET, path = "/download", produces =
    "application/zip")
  @ResponseBody
  public void zip(@RequestParam("zipName") String zipName,
                  HttpServletResponse response)
    throws IOException {
    // get data from session
    String pathToGenerateProjectZip = projectZip.getPathToGenerateProjectZip();
    String nameOfProjectZip         = projectZip.getNameOfProjectZip();
    //setting headers
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Type",
                       "application/zip");
    response.addHeader("Content-Disposition",
                       "attachment; filename=\"" + nameOfProjectZip + "\"");

    if (pathToGenerateProjectZip != null && nameOfProjectZip != null) {
      Path   pathToZipFile = Paths.get(pathToGenerateProjectZip);
      byte[] bytes         = Files.readAllBytes(pathToZipFile);

      ServletOutputStream servletOutputStream = response.getOutputStream();
      response.setContentType("application/zip, application/octet-stream");
      response.setContentLength(bytes.length);
      servletOutputStream.write(bytes);
      servletOutputStream.close();
    }
  }
}

