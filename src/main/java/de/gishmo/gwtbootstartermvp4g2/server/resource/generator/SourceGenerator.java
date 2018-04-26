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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator;

import java.io.File;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.ApplicationLoaderSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.ApplicationSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.EntryPointSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.HistoryConverterGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.HostPageSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.ModelSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.WebXmlSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.CssPageElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.EventBusElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.HeaderElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.NavigationElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.PresenterViewElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.ShellElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento.StatusBarElementoSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.CssPageGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.EventBusGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.HeaderGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.NavigationGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.PresenterViewGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.ShellGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt.StatusBarGwtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.CssPageGxtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.EventBusGxtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.HeaderGxtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.NavigationGxtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.PresenterViewGxtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.ShellGxtSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt.StatusBarGxtSourceGenerator;

public class SourceGenerator {

  private static final String SRC_MAIN_JAVA   = "src" + File.separator + "main" + File.separator + "java";
  private static final String SRC_MAIN_WEBAPP = "src" + File.separator + "main" + File.separator + "webapp";

  private static final String CLIENT = "client";

  private File directoryJava;
  private File directoryWebapp;

  private String clientPackageJavaConform;

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private String               projectFolder;

  private SourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.projectFolder = builder.projectFolder;
  }

  public static Builder builder() {
    return new SourceGenerator.Builder();
  }

  public void generate()
    throws GeneratorException {

    createBasicStructure();
    createDataDependingStructure();

    // Hostpage ...
    HostPageSourceGenerator.builder()
                           .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                           .directoryWebapp(this.directoryWebapp)
                           .build()
                           .generate();
    // web.xml ...
    WebXmlSourceGenerator.builder()
                         .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                         .directoryWebapp(this.directoryWebapp)
                         .build()
                         .generate();
    // EntryPoint
    EntryPointSourceGenerator.builder()
                             .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                             .clientPackageJavaConform(this.clientPackageJavaConform)
                             .directoryJava(this.directoryJava)
                             .build()
                             .generate();
    // Application
    ApplicationSourceGenerator.builder()
                              .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();
    // Application Loader class (if requested)
    if (mvp4g2GeneraterParms.isApplicationLoader()) {
      ApplicationLoaderSourceGenerator.builder()
                                      .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                      .clientPackageJavaConform(this.clientPackageJavaConform)
                                      .directoryJava(this.directoryJava)
                                      .build()
                                      .generate();
    }
    // Model
    ModelSourceGenerator.builder()
                        .clientPackageJavaConform(this.clientPackageJavaConform)
                        .directoryJava(this.directoryJava)
                        .build()
                        .generate();
    // History
    if (mvp4g2GeneraterParms.isHistory()) {
      HistoryConverterGenerator.builder()
                               .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                               .clientPackageJavaConform(this.clientPackageJavaConform)
                               .directoryJava(this.directoryJava)
                               .build()
                               .generate();
    }
    if (WidgetLibrary.GWT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      // Css file
      CssPageGwtSourceGenerator.builder()
                               .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                               .directoryWebapp(this.directoryWebapp)
                               .build()
                               .generate();
      // EventBus
      EventBusGwtSourceGenerator.builder()
                                .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                .clientPackageJavaConform(this.clientPackageJavaConform)
                                .directoryJava(this.directoryJava)
                                .build()
                                .generate();
      // generate shell
      ShellGwtSourceGenerator.builder()
                             .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                             .clientPackageJavaConform(this.clientPackageJavaConform)
                             .directoryJava(this.directoryJava)
                             .build()
                             .generate();
      // generate header
      HeaderGwtSourceGenerator.builder()
                              .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();
      // generate Statusbar
      StatusBarGwtSourceGenerator.builder()
                                 .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                 .clientPackageJavaConform(this.clientPackageJavaConform)
                                 .directoryJava(this.directoryJava)
                                 .build()
                                 .generate();
      // generate navigation
      NavigationGwtSourceGenerator.builder()
                                  .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                  .clientPackageJavaConform(this.clientPackageJavaConform)
                                  .directoryJava(this.directoryJava)
                                  .build()
                                  .generate();

    } else if (WidgetLibrary.ELEMENTO == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      // Css file
      CssPageElementoSourceGenerator.builder()
                                    .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                    .directoryWebapp(this.directoryWebapp)
                                    .build()
                                    .generate();
      // EventBus
      EventBusElementoSourceGenerator.builder()
                                     .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                     .clientPackageJavaConform(this.clientPackageJavaConform)
                                     .directoryJava(this.directoryJava)
                                     .build()
                                     .generate();
      // generate shell
      ShellElementoSourceGenerator.builder()
                                  .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                  .clientPackageJavaConform(this.clientPackageJavaConform)
                                  .directoryJava(this.directoryJava)
                                  .build()
                                  .generate();
      // generate header
      HeaderElementoSourceGenerator.builder()
                                   .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                   .clientPackageJavaConform(this.clientPackageJavaConform)
                                   .directoryJava(this.directoryJava)
                                   .build()
                                   .generate();
      // generate Statusbar
      StatusBarElementoSourceGenerator.builder()
                                      .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                      .clientPackageJavaConform(this.clientPackageJavaConform)
                                      .directoryJava(this.directoryJava)
                                      .build()
                                      .generate();
      // generate navigation
      NavigationElementoSourceGenerator.builder()
                                       .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                       .clientPackageJavaConform(this.clientPackageJavaConform)
                                       .directoryJava(this.directoryJava)
                                       .build()
                                       .generate();

    } else if (WidgetLibrary.GXT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
      // Css file
      CssPageGxtSourceGenerator.builder()
                               .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                               .directoryResourcesStatic(this.directoryWebapp)
                               .build()
                               .generate();
      // EventBus
      EventBusGxtSourceGenerator.builder()
                                .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                .clientPackageJavaConform(this.clientPackageJavaConform)
                                .directoryJava(this.directoryJava)
                                .build()
                                .generate();
      // generate shell
      ShellGxtSourceGenerator.builder()
                             .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                             .clientPackageJavaConform(this.clientPackageJavaConform)
                             .directoryJava(this.directoryJava)
                             .build()
                             .generate();
      // generate header
      HeaderGxtSourceGenerator.builder()
                              .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                              .clientPackageJavaConform(this.clientPackageJavaConform)
                              .directoryJava(this.directoryJava)
                              .build()
                              .generate();
      // generate Statusbar
      StatusBarGxtSourceGenerator.builder()
                                 .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                 .clientPackageJavaConform(this.clientPackageJavaConform)
                                 .directoryJava(this.directoryJava)
                                 .build()
                                 .generate();
      // generate navigation
      NavigationGxtSourceGenerator.builder()
                                  .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                  .clientPackageJavaConform(this.clientPackageJavaConform)
                                  .directoryJava(this.directoryJava)
                                  .build()
                                  .generate();
    }
    // generate presenter & views for every screen
    for (PresenterData presenterData : this.mvp4g2GeneraterParms.getPresenters()) {
      if (WidgetLibrary.GWT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
        PresenterViewGwtSourceGenerator.builder()
                                       .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                       .clientPackageJavaConform(this.clientPackageJavaConform)
                                       .directoryJava(this.directoryJava)
                                       .presenterData(presenterData)
                                       .build()
                                       .generate();
      } else if (WidgetLibrary.ELEMENTO == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
        PresenterViewElementoSourceGenerator.builder()
                                            .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                            .clientPackageJavaConform(this.clientPackageJavaConform)
                                            .directoryJava(this.directoryJava)
                                            .presenterData(presenterData)
                                            .build()
                                            .generate();
      } else if (WidgetLibrary.GXT == this.mvp4g2GeneraterParms.getWidgetLibrary()) {
        PresenterViewGxtSourceGenerator.builder()
                                       .mvp4g2GeneraterParms(this.mvp4g2GeneraterParms)
                                       .clientPackageJavaConform(this.clientPackageJavaConform)
                                       .directoryJava(this.directoryJava)
                                       .presenterData(presenterData)
                                       .build()
                                       .generate();
      }
    }
    // TODO generate model
  }

  private void createBasicStructure() {
    // create Java directory
    directoryJava = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_JAVA);
    directoryJava.mkdirs();
    // create webapp directory
    directoryWebapp = new File(this.projectFolder + File.separator + SourceGenerator.SRC_MAIN_WEBAPP);
    directoryWebapp.mkdirs();
  }

  private void createDataDependingStructure() {
    // create Java package
    String srcPackage = mvp4g2GeneraterParms.getGroupId()
                                            .replace(".",
                                                     File.separator);
    srcPackage = srcPackage + File.separator + GeneratorUtils.removeBadChracters(mvp4g2GeneraterParms.getArtefactId())
                                                             .toLowerCase();

    String clientPackage = srcPackage + File.separator + SourceGenerator.CLIENT;
    this.clientPackageJavaConform = clientPackage.replace(File.separator,
                                                          ".");
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

    public SourceGenerator build() {
      return new SourceGenerator(this);
    }
  }
}
