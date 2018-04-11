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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;

public class CssPageElementoSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryWebapp;

  private CssPageElementoSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryWebapp = builder.directoryWebapp;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {
    this.generateCssFile();
  }

  private void generateCssFile()
    throws GeneratorException {

    StringBuilder sb = new StringBuilder();
    sb.append("@CHARSET \"UTF-8\";")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)

      .append("body {")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      font-family: Arial Unicode MS, Arial, sans-serif;\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      font-size: small;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      color: black;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      margin: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      padding: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("}")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)

      .append(".shell {")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      height: auto;\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      width: 100%;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      margin: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      background-color: white;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("}")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)

      .append(".shellHeader {")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      position: absolute;\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      overflow: hidden;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      height: 104px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      top: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      right: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      left: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      width: 100%;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      padding: 12px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      background-color: whitesmoke;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("}")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)

      .append(".shellNavigation {")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      position: absolute;\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      overflow: hidden;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      top: 128px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      bottom: 0px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      left: 0;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      width: 212px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      padding: 12px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      background-color: snow;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("}")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)

      .append(".shellContent {")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      position: absolute;\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      overflow: hidden;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      top: 128px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      bottom: 42px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      left: 224px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      right: 0px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      padding: 12px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("}")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)

      .append(".shellFooter {")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      position: absolute;\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      overflow: hidden;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      height: 42px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      bottom: 0px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      left: 224px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      right: 0px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      background-color: whitesmoke;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("      padding: 12px;")
      .append(GeneratorConstants.LINE_BREAK)
      .append("}")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);
//
//.shellHeaderImage {
//      margin: 12px;
//    }
//
//.shellFooter {
//      position: absolute;
//      overflow: hidden;
//      height: 42px;
//      bottom: 0;
//      right: 0;
//      left: 0;
//      width: 100%;
//      border-top: black 1px solid;
//    }
//
//.shellFooterLeft {
//      float: left;
//      padding: 12px;
//    }
//
//.shellFooterRight {
//      float: right;
//      padding: 12px;
//    }
//
//.shellFooterLabel {
//      font-family: arial, sans-serif;
//      font-size: 12px;
//      font-weight: normal;
//    }
//
//.shellFooterStatus {
//      font-family: arial, sans-serif;
//      font-size: 12px;
//      font-weight: normal;
//    }
//
//.textFieldWidgetContainer {
//      margin: 12px;
//      border: 1px solid #4200fe;
//      border-radius: 6px;
//      background-color: #d3f4fa;
//    }
//
//.textFieldLabel {
//      margin: 6px 4px 6px 12px;
//      font-family: arial, sans-serif;
//      font-size: 14px;
//      font-weight: bold;
//      color: #4200fe;
//    }
//
//.textFieldTextBox {
//      font-family: arial, sans-serif;
//      margin: 2px 12px 2px 12px;
//      padding: 6px;
//      font-size: 14px;
//      font-weight: normal;
//      color: #4200fe;
//      flex: 1;
//      width: 100%;
//    }
//
//.textFieldDivElement {
//      display: flex;
//      width: 100%;
//    }
//
//.textFieldOneRow {
//      height: 48px
//    }
//
//.textFieldTwoRow {
//      height: 70px
//    }
//
//.hidden {
//      visibility: hidden;
//    }
//
//.visible {
//      visibility: visible;
//    }
//
//.yellow {
//      background-color: yellow;
//    }
//
//.navigationButton {
//      width: 188px;
//      height: 36px;
//      background-color: lightgrey;
//      color: black;
//      font-family: arial, sans-serif;
//      font-size: 14px;
//      font-weight: bold;
//      border-top-left-radius: 12px;
//      border-bottom-right-radius: 12px;
//      border: 2px solid darkgray;
//      margin: 12px;
//    }
//
//.button {
//      width: 112px;
//      height: 36px;
//      margin: 6px 12px 6px 12px;
//    }
//
//.headline {
//      font-family: arial, sans-serif;
//      font-size: 24px;
//      font-weight: bold;
//      color: #4200fe;
//      margin: 12px;
//    }
//
//.buttonBar {
//      float: left;
//      text-align: right;
//      width: 100%;
//    }
//
//.resultTable {
//      display: table;
//      border-collapse: separate;
//      border-spacing: 2px;
//      border-color: gray;
//      font-family: Arial, sans-serif;
//      font-size: 14px;
//      margin: 12px;
//      width: 100%;
//    }
//
//.resultTableHeader {
//      font-weight: bold;
//      font-size: 16px;
//      border-bottom: 2px solid darkgray;
//    }
//
//.resultTableLink {
//      font-weight: bold;
//      text-decoration: underline;
//      cursor: pointer;
//    }

    String fileContent = sb.toString();

    try {
      Files.write(Paths.get(directoryWebapp.getPath() + File.separator + this.mvp4g2GeneraterParms.getArtefactId() + ".css"),
                  fileContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + Paths.get(directoryWebapp.getPath() + this.mvp4g2GeneraterParms.getArtefactId() + ".css") + "<< -> exception: " + e.getMessage());
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

    public CssPageElementoSourceGenerator build() {
      return new CssPageElementoSourceGenerator(this);
    }
  }
}
