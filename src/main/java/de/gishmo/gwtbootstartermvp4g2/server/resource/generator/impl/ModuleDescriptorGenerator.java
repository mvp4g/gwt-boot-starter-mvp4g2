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

public class ModuleDescriptorGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private String               projectFolder;

  private ModuleDescriptorGenerator(Builder builder) {
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
      .append("<!--")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  When updating your version of GWT, you should also update this DTD reference,")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  so that your app can take advantage of the latest GWT module capabilities.")
      .append(GeneratorConstants.LINE_BREAK)
      .append("-->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<!DOCTYPE module PUBLIC \"-//Google Inc.//DTD Google Web Toolkit 2.7.0//EN\"\n" +
              "  \"http://gwtproject.org/doctype/2.8.2/gwt-module.dtd\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append("<module rename-to=\"" + GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId()) + "\">")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- Inherit the core Web Toolkit stuff.                        -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <inherits name='com.google.gwt.user.User'/>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK);
    if (WidgetLibrary.GWT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("  <!-- Inherit the default GWT style sheet.  You can change       -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <!-- the theme of your GWT application by uncommenting          -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <!-- any one of the following lines.                            -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <inherits name='com.google.gwt.user.theme.clean.Clean'/>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <!-- <inherits historyName='com.google.gwt.user.theme.standard.Standard'/> -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <!-- <inherits historyName='com.google.gwt.user.theme.chrome.Chrome'/> -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <!-- <inherits historyName='com.google.gwt.user.theme.dark.Dark'/>     -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append(GeneratorConstants.LINE_BREAK);
    }
    sb.append("  <!-- Other module inherits                                      -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <inherits name='com.github.mvp4g.mvp4g2.Mvp4g2'/>")
      .append(GeneratorConstants.LINE_BREAK);
    if (WidgetLibrary.DOMINO_UI == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("  <inherits name='org.dominokit.domino.ui.DominoUI'/>")
        .append(GeneratorConstants.LINE_BREAK);
    }
    if (WidgetLibrary.ELEMENTO == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("  <inherits name='org.jboss.gwt.elemento.Core'/>")
        .append(GeneratorConstants.LINE_BREAK);
    }
    if (WidgetLibrary.GXT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      sb.append("  <inherits name='com.sencha.gxt.ui.GXT'/>")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <inherits name='com.sencha.gxt.theme.neptune.Theme'/>")
        .append(GeneratorConstants.LINE_BREAK)
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <!-- GXT Stylesheet -->")
        .append(GeneratorConstants.LINE_BREAK)
        .append("  <stylesheet src=\"reset.css\" />")
        .append(GeneratorConstants.LINE_BREAK);
    }
    sb.append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- Specify the app entry point class.                         -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <entry-point class='" + this.mvp4g2GeneraterParms.getGroupId() + "." + GeneratorUtils.removeBadChracters(this.mvp4g2GeneraterParms.getArtefactId())
                                                                                                      .toLowerCase() + ".client." + GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + "'/>")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <!-- Specify the paths for translatable code                    -->")
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <source path='client'/>\n")
      .append(GeneratorConstants.LINE_BREAK)
      .append(GeneratorConstants.LINE_BREAK)
      .append("  <set-configuration-property name=\"mvp4g2.logging\" value=\"true\"/>")
      .append(GeneratorConstants.LINE_BREAK)
      .append("")
      .append("</module>");

    String moduleDescriptorContent = sb.toString();

    // create directory ...
    String pathToModuleDescriptor = this.projectFolder + File.separator + "src" + File.separator + "main";
    File folderModuleDescriptor = new File(pathToModuleDescriptor);
    try {
      if (!folderModuleDescriptor.exists()) {
        folderModuleDescriptor.mkdirs();
      }
      Files.write(Paths.get(folderModuleDescriptor + File.separator + "module.gwt.xml"),
                  moduleDescriptorContent.getBytes());
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + folderModuleDescriptor + File.separator + "module.gwt.xml" + "<< -> exception: " + e.getMessage());
    }
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

    public ModuleDescriptorGenerator build() {
      return new ModuleDescriptorGenerator(this);
    }
  }
}
