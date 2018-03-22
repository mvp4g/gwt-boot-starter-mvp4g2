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

    sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<project xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + "         xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" + "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    sb.append(this.generateHeadlines())
      .append(this.generateProperties())
      .append(this.generateDependencyManagement())
      .append(this.generateDependencies());

    sb.append("</project>");

    String pomContent = sb.toString();

    try {
      Files.write(Paths.get(new File(this.projectFolder) + File.separator + "pom.xml"),
                  pomContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + new File(this.projectFolder) + File.separator + "pom.xml" + "<< -> exception: " + e.getMessage());
    }
  }

  private String generateDependencyManagement() {
    StringBuilder sb = new StringBuilder();

    sb.append("  <dependencyManagement>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addDependency(6,
                                 "com.google.gwt",
                                 "gwt",
                                 "${gwt.version}",
                                 "pom",
                                 "import"))
      .append("    </dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </dependencyManagement>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateDependencies() {
    StringBuilder sb = new StringBuilder();

    sb.append("  <dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addDependency(4,
                                 "com.google.gwt",
                                 "gwt-user",
                                 null,
                                 null,
                                 "provided"))
      .append(this.addDependency(4,
                                 "com.google.gwt",
                                 "gwt-dev",
                                 null,
                                 null,
                                 "provided"))
      .append(this.addDependency(4,
                                 "com.google.gwt",
                                 "gwt-codeserver",
                                 null,
                                 null,
                                 "provided"))
      .append(this.addDependency(4,
                                 "com.google.gwt",
                                 "gwt-cservlet"))
      .append(this.addDependency(4,
                                 "de.gishmo.gwt",
                                 "mvp4g2",
                                 "${mvp4g2.version}"))
      .append(this.addDependency(4,
                                 "de.gishmo.gwt",
                                 "mvp4g2-processor",
                                 "${mvp4g2.version}"))
      .append("  </dependencies>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateHeadlines() {
    StringBuilder sb = new StringBuilder();

    sb.append(this.addAddrLine(2,
                               "version",
                               "4.0.0"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "groupId",
                               this.mvp4g2GeneraterParms.getGroupId()))
      .append(this.addAddrLine(2,
                               "artifactId",
                               this.mvp4g2GeneraterParms.getArtefactId()))
      .append(this.addAddrLine(2,
                               "version",
                               "1.0.0-SNAPSHOT"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "packaging",
                               "gwt-app"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addAddrLine(2,
                               "description",
                               "Mvp4g2 Boot Starter Project"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String generateProperties() {
    StringBuilder sb = new StringBuilder();

    sb.append("  <properties>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addCommentLine(4,
                                  "Convenience property to set the GWT version"))
      .append(this.addAddrLine(4,
                               "gwt.version",
                               this.mvp4g2GeneraterParms.getGwtVersion()))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addCommentLine(4,
                                  "mvp4g2 version"))
      .append(this.addAddrLine(4,
                               "mvp4g2.version",
                               "1.0.0-beta-3"))
      .append(GeneratorConstants.LINE_BREAK)
      .append(this.addCommentLine(4,
                                  "GWT needs at least java 1.6"))
      .append(this.addAddrLine(4,
                               "maven.compiler.source",
                               "1.8"))
      .append(this.addAddrLine(4,
                               "maven.compiler.target",
                               "1.8"))
      .append(GeneratorConstants.LINE_BREAK)

      .append(this.addAddrLine(4,
                               "generated.source.directory",
                               "${project.build.directory}/generated-sources/annotations"))
      .append("  </properties>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addAddrLine(int indent,
                             String tagName,
                             String value) {
    StringBuilder sb = new StringBuilder();

    sb.append(this.addIndent(indent))
      .append("<")
      .append(tagName)
      .append(">")
      .append(value)
      .append("</")
      .append(tagName)
      .append(">")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addCommentLine(int indent,
                                String comment) {
    StringBuilder sb = new StringBuilder();

    sb.append(addIndent(indent))
      .append("<!-- ")
      .append(comment)
      .append(" -->")
      .append(GeneratorConstants.LINE_BREAK);

    return sb.toString();
  }

  private String addDependency(int indent,
                               String groupId,
                               String artifactId,
                               String version) {
    return this.addDependency(indent,
                              groupId,
                              artifactId,
                              version,
                              null,
                              null);
  }

  private String addDependency(int indent,
                               String groupId,
                               String artifactId) {
    return this.addDependency(indent,
                              groupId,
                              artifactId,
                              null,
                              null,
                              null);
  }

  private String addDependency(int indent,
                               String groupId,
                               String artifactId,
                               String version,
                               String type,
                               String scope) {
    StringBuilder sb = new StringBuilder();

    sb.append(addIndent(indent))
      .append("<dependency>")
      .append(GeneratorConstants.LINE_BREAK);
    if (groupId != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "groupId",
                                 groupId));
    }
    if (artifactId != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "artifactId",
                                 artifactId));
    }
    if (version != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "version",
                                 version));
    }
    if (type != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "type",
                                 type));
    }
    if (scope != null) {
      sb.append(this.addAddrLine(indent + 2,
                                 "scope",
                                 scope));
    }
    sb.append(addIndent(indent))
      .append("</dependency>")
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

  private String addIndent(int indent) {
    String s = "";
    for (int i = 0; i < indent; i++) {
      s = s + " ";
    }
    return s;
  }
}
