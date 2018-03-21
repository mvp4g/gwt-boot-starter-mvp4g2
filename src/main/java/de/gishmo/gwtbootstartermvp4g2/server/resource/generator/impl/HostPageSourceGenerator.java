package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.document;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.meta;
import static j2html.TagCreator.noscript;
import static j2html.TagCreator.script;
import static j2html.TagCreator.title;

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

  private void generateCssFile()
    throws GeneratorException {

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

  private void generateHostPage()
    throws GeneratorException {

    String srcScript = this.mvp4g2GeneraterParms.getArtefactId()
                                                .toLowerCase() + "/" + this.mvp4g2GeneraterParms.getArtefactId()
                                                                                                .toLowerCase() + ".nocache.js";

    String html = GeneratorConstants.COPYRIGHT_HTML + HostPageSourceGenerator.LINE_BREAK + HostPageSourceGenerator.LINE_BREAK;


    html = html + document().render() + HostPageSourceGenerator.LINE_BREAK + HostPageSourceGenerator.LINE_BREAK +
           html().with(
             head().with(
               meta().attr("http-equiv",
                           "content-type")
                     .attr("content",
                           "text/html; charset=UTF-8"),
               title("Mvp4g2 Boot Starter Project: " + mvp4g2GeneraterParms.getArtefactId()),
               link().attr("type",
                           "text/css")
                     .attr("rel",
                           "stylesheet")
                     .attr("href",
                           this.mvp4g2GeneraterParms.getArtefactId() + ".css"),
               script().attr("type",
                             "text/javascript")
                       .attr("language",
                             "javascript")
                       .attr("src",
                             srcScript)

             ),
             body(
               noscript(
                 div(
                   "Your web browser must have JavaScript enabled in order for this application to display correctly."
                 ).attr("style",
                        "width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif")
               )
             )
           )
                 .renderFormatted();

    try {
      Files.write(Paths.get(directoryResourcesStatic.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".html"),
                  html.getBytes());
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
