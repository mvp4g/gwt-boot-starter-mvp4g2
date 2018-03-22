package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// TODO CSS generieren !
public class HostPageSourceGenerator {

  private static final String LINE_BREAK = "\n";


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
    this.generateCssFile();
  }

  private void generateHostPage()
    throws GeneratorException {

    String title = "Mvp4g2 Boot Starter Project ==> " + this.mvp4g2GeneraterParms.getArtefactId();
    String srcScript = this.mvp4g2GeneraterParms.getArtefactId()
                                                .toLowerCase() + "/" + this.mvp4g2GeneraterParms.getArtefactId()
                                                                                                .toLowerCase() + ".nocache.js";

    StringBuilder sb = new StringBuilder();

    sb.append("<!doctype html>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(GeneratorConstants.COPYRIGHT_HTML)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("<!-- The DOCTYPE declaration above will set the     -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("<!-- browser's rendering engine into                -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("<!-- \"Standards Mode\". Replacing this declaration   -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("<!-- with a \"Quirks Mode\" doctype is not supported. -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("<html>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  <head>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <!-- Any title is fine (please update)               -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <title>")
      .append(title)
      .append("</title>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <!-- Consider inlining CSS to reduce the number of requested files -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"Mvp4g2SpringBoot.css\">")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <!-- This script loads your compiled module.   -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <!-- If you add any GWT meta tags, they must   -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <!-- be added before this line.                -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <script type=\"text/javascript\" language=\"javascript\" src=\"")
      .append(srcScript)
      .append("\"></script>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  </head>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  <!-- The body can have arbitrary html, or      -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  <!-- you can leave the body empty if you want  -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  <!-- to create a completely dynamic UI.        -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  <body>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    <noscript>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("      Your web browser must have JavaScript enabled")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("      in order for this application to display correctly.")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("    </noscript>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("  </body>")
      .append(HostPageSourceGenerator.LINE_BREAK)
      .append("</html>")
      .append(HostPageSourceGenerator.LINE_BREAK);

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryResourcesStatic.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".html"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryResourcesStatic.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".html") + "<< -> exception: " + e.getMessage());
    }
  }

  private void generateCssFile()
    throws GeneratorException {

    StringBuilder sb = new StringBuilder();

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryResourcesStatic.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".css"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryResourcesStatic.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".css") + "<< -> exception: " + e.getMessage());
    }


//    String srcScript = this.mvp4g2GeneraterParms.getArtefactId()
//                                                .toLowerCase() + "/" + this.mvp4g2GeneraterParms.getArtefactId()
//                                                                                                .toLowerCase() + ".nocache.js";
//
//    String html = GeneratorConstants.COPYRIGHT_HTML + HostPageSourceGenerator.LINE_BREAK + HostPageSourceGenerator.LINE_BREAK;
//
//
//    html = html + document().render() + HostPageSourceGenerator.LINE_BREAK + HostPageSourceGenerator.LINE_BREAK +
//           html().with(
//             head().with(
//               meta().attr("http-equiv",
//                           "content-type")
//                     .attr("content",
//                           "text/html; charset=UTF-8"),
//               title("Mvp4g2 Boot Starter Project: " + mvp4g2GeneraterParms.getArtefactId()),
//               link().attr("type",
//                           "text/css")
//                     .attr("rel",
//                           "stylesheet")
//                     .attr("href",
//                           this.mvp4g2GeneraterParms.getArtefactId() + ".css"),
//               script().attr("type",
//                             "text/javascript")
//                       .attr("language",
//                             "javascript")
//                       .attr("src",
//                             srcScript)
//
//             ),
//             body(
//               noscript(
//                 div(
//                   "Your web browser must have JavaScript enabled in order for this application to display correctly."
//                 ).attr("style",
//                        "width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif")
//               )
//             )
//           )
//                 .renderFormatted();
//
//    try {
//      Files.write(Paths.get(directoryResourcesStatic.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".html"),
//                  html.getBytes());
//    } catch (IOException e) {
//      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryResourcesStatic.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".html") + "<< -> exception: " + e.getMessage());
//    }
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
