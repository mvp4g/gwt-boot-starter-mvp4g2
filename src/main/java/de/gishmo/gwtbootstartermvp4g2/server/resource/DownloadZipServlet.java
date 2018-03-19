package de.gishmo.gwtbootstartermvp4g2.server.resource;

import de.gishmo.gwtbootstartermvp4g2.server.resource.model.ProjectZip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/loadZip")
public class DownloadZipServlet {

  @Autowired
  ProjectZip projectZip;

  @RequestMapping(method = RequestMethod.GET, path = "/download", produces = "application/zip")
  public void zip(HttpServletResponse response)
    throws IOException {
    // get data from session
    String pathToGenerateProjectZip = projectZip.getPathToGenerateProjectZip();
    String nameOfProjectZip = projectZip.getNameOfProjectZip();
    //setting headers
    response.setStatus(HttpServletResponse.SC_OK);
    response.addHeader("Content-Type",
                       "application/zip, application/octet-stream");
    response.addHeader("Content-Disposition",
                       "attachment; filename=\"" + nameOfProjectZip + "\"");

    if (pathToGenerateProjectZip != null && nameOfProjectZip != null) {
      Path pathToZipFile = Paths.get(pathToGenerateProjectZip);
      byte[] bytes = Files.readAllBytes(pathToZipFile);

      ServletOutputStream servletOutputStream = response.getOutputStream();
      response.setContentType("application/zip, application/octet-stream");
      response.setContentLength(bytes.length);
      servletOutputStream.write(bytes);
      servletOutputStream.close();
    }
  }
}

