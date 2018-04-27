/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

public class HostPageSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryWebapp;

  private HostPageSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryWebapp = builder.directoryWebapp;
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
    String srcScript = GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId()) + "/" + GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId()) + ".nocache.js";

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
      .append(GeneratorConstants.LINE_BREAK);
    if (WidgetLibrary.DOMINO_UI == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("    <meta content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" name=\"viewport\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append(GeneratorConstants.LINE_BREAK);
    }
    sb.append("    <!-- Any title is fine (please update)               -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <title>")
      .append(title)
      .append("</title>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <!-- Consider inlining CSS to reduce the number of requested files -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"")
      .append(GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()))
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
      .append(GeneratorConstants.LINE_BREAK);
    if (WidgetLibrary.DOMINO_UI == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("    <!-- CSS used by Domino UI                     -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/font/material-icons.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/plugins/bootstrap/css/bootstrap.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/plugins/node-waves/waves.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/plugins/animate-css/animate.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/plugins/waitme/waitMe.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/plugins/bootstrap-select/css/bootstrap-select.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/css/materialize.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/css/style.css\">")
        .append(GeneratorConstants.LINE_BREAK)
        .append("    <link type=\"text/css\" rel=\"stylesheet\" href=\"/static/css/themes/all-themes.css\">")
        .append(GeneratorConstants.LINE_BREAK);
    }
    sb.append("  </head>")
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
      Files.write(Paths.get(directoryWebapp.getPath() + File.separator + GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + ".html"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryWebapp.getPath() + GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + ".htnl") + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File                 directoryWebapp;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder directoryWebapp(File directoryWebapp) {
      this.directoryWebapp = directoryWebapp;
      return this;
    }

    public HostPageSourceGenerator build() {
      return new HostPageSourceGenerator(this);
    }
  }
}
