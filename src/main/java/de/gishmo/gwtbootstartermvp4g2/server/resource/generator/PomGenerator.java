package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

public class PomGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private String               projectFolder;

  private PomGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.projectFolder = builder.projectFolder;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {

    StringBuilder sb = new StringBuilder();

    sb.append(this.generateHeadlines())
      .append(this.generateProperties());

    String pomContent = sb.toString();

    try {
      Files.write(Paths.get(new File(this.projectFolder) + File.separator + "pom.xml"),
                  pomContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + new File(this.projectFolder) + File.separator + "pom.xml" + "<< -> exception: " + e.getMessage());
    }
  }

  private String generateHeadlines() {
    StringBuilder sb = new StringBuilder();

    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<project xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + "         xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" + "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine("version",
                               "4.0.0"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine("groupId",
                               this.mvp4g2GeneraterParms.getGroupId()))
      .append(this.addAddrLine("artifactId",
                               this.mvp4g2GeneraterParms.getArtefactId()))
      .append(this.addAddrLine("version",
                               "1.0.0-SNAPSHOT"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine("packaging",
                               "gwt-app"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine("description",
                               "Mvp4g2 Boot Starter Project"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateProperties() {
    StringBuilder sb = new StringBuilder();

    sb.append("<properties>")
      .append("</properties>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addAddrLine(String tagName,
                             String value) {
    StringBuilder sb = new StringBuilder();

    sb.append("<")
      .append(tagName)
      .append(">")
      .append(value)
      .append("</")
      .append(tagName)
      .append(">")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    String               projectFolder;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder projectFolder(String projectFolder) {
      this.projectFolder = projectFolder;
      return this;
    }

    public PomGenerator build() {
      return new PomGenerator(this);
    }
  }
}
