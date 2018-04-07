package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;

// TODO CSS generieren !
public class HostPageSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryResourcesStatic;

  private HostPageSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryResourcesStatic = builder.directoryResourcesStatic;
  }

//  public static void main(String[] args) {
//    Mvp4g2GeneraterParms model = new Mvp4g2GeneraterParms();
//    model.setHistoryOnStart(true);
//    model.setArtefactId("TestProject");
//    try {
//      HostPageSourceGenerator.builder()
//                             .mvp4g2GeneraterParms(model)
//                             .build()
//                             .generate();
//    } catch (GeneratorException e) {
//      e.printStackTrace();
//    }
//  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {
    this.generateHostPage();
  }

  private void generateHostPage()
    throws GeneratorException {

    String title = "Mvp4g2 Boot Starter Project ==> " + this.mvp4g2GeneraterParms.getArtefactId();
    String srcScript = this.mvp4g2GeneraterParms.getArtefactId()
                                                .toLowerCase() + "/" + this.mvp4g2GeneraterParms.getArtefactId()
                                                                                                .toLowerCase() + ".nocache.js";

    StringBuilder sb = new StringBuilder();

    sb.append("<!doctype html>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- The DOCTYPE declaration above will set the     -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- browser's rendering engine into                -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- \"Standards Mode\". Replacing this declaration   -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!-- with a \"Quirks Mode\" doctype is not supported. -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("<html>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <head>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- Any title is fine (please update)               -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <title>")
      .append(title)
      .append("</title>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- Consider inlining CSS to reduce the number of requested files -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"")
      .append(this.mvp4g2GeneraterParms.getArtefactId())
      .append(".css\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- This script loads your compiled module.   -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- If you add any GWT meta tags, they must   -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- be added before this line.                -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <script type=\"text/javascript\" language=\"javascript\" src=\"")
      .append(srcScript)
      .append("\"></script>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </head>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- The body can have arbitrary html, or      -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- you can leave the body empty if you want  -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- to create a completely dynamic UI.        -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <body>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <noscript>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      Your web browser must have JavaScript enabled")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      in order for this application to display correctly.")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    </noscript>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  </body>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("</html>")
      .append(GeneratorConstants.LINE_BREAK);

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryResourcesStatic.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".html"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryResourcesStatic.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".html") + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File                 directoryResourcesStatic;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder directoryResourcesStatic(File directoryResourcesStatic) {
      this.directoryResourcesStatic = directoryResourcesStatic;
      return this;
    }

    public HostPageSourceGenerator build() {
      return new HostPageSourceGenerator(this);
    }
  }
}
