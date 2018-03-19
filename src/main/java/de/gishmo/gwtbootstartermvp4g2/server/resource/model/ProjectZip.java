package de.gishmo.gwtbootstartermvp4g2.server.resource.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProjectZip {

  private String pathToGenerateProjectZip;
  private String nameOfProjectZip;

  public ProjectZip() {
  }

  public ProjectZip(String pathToGenerateProjectZip,
                    String nameOfProjectZip) {
    this.pathToGenerateProjectZip = pathToGenerateProjectZip;
    this.nameOfProjectZip = nameOfProjectZip;
  }

  public String getPathToGenerateProjectZip() {
    return pathToGenerateProjectZip;
  }

  public void setPathToGenerateProjectZip(String pathToGenerateProjectZip) {
    this.pathToGenerateProjectZip = pathToGenerateProjectZip;
  }

  public String getNameOfProjectZip() {
    return nameOfProjectZip;
  }

  public void setNameOfProjectZip(String nameOfProjectZip) {
    this.nameOfProjectZip = nameOfProjectZip;
  }
}
