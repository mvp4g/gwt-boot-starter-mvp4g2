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

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Debug;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Start;
import com.github.mvp4g.mvp4g2.core.history.annotation.InitHistory;
import com.github.mvp4g.mvp4g2.core.history.annotation.NotFoundHistory;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractEventBusSourceGenerator
    extends AbstractSourceGenerator {

  public void generate()
      throws GeneratorException {

    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder(GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.EVENT_BUS))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ClassName.get(IsEventBus.class))
                                        .addAnnotation(AnnotationSpec.builder(EventBus.class)
                                                                     .addMember("shell",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                              "ShellPresenter"))
                                                                     .build());
    if (mvp4g2GeneraterParms.isDebug()) {
      typeSpec.addAnnotation(AnnotationSpec.builder(Debug.class)
                                           .addMember("logLevel",
                                                      "$T.LogLevel.DETAILED",
                                                      ClassName.get(Debug.class))
                                           .build());
    }
    // event: start
    typeSpec.addMethod(MethodSpec.methodBuilder("start")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addJavadoc("This event will be fire by the framework as first event\n" +
                                                 "of the application.\n" +
                                                 "\n" +
                                                 "We will use this event to initiate the setting of the\n" +
                                                 "navigation in the west area of the shell by using the bind-attribute.\n" +
                                                 "By using the start event to bind the navigation, we make sure\n" +
                                                 "that the navigation will be updated before the content area is updated.\n")
                                 .addAnnotation(Start.class)
                                 .addAnnotation(AnnotationSpec.builder(Event.class)
                                                              .addMember("bind",
                                                                         "{ $T.class, $T.class, $T.class }",
                                                                         ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                       "NavigationPresenter"),
                                                                         ClassName.get(this.clientPackageJavaConform + ".ui.header",
                                                                                       "HeaderPresenter"),
                                                                         ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                       "StatusbarPresenter"))
                                                              .build())
                                 .build());
    // event: setContent
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addJavadoc("This event will set the element (parameter) in the content\n" +
                                                 "area of the shell. We will use this event to update the shell\n" +
                                                 "with the current content area.\n" +
                                                 "\n" +
                                                 "@param widget the element of the widget, that will be\n" +
                                                 "              displayed inside the content area of the shell.\n")
                                 .addAnnotation(Event.class)
                                 .addParameter(getWidgetParameterSpec())
                                 .build())
            .build();
    // event: setHeader
    typeSpec.addMethod(MethodSpec.methodBuilder("setHeader")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addJavadoc("This event will set the element (parameter) in the north\n" +
                                                 "area of the shell.\n" +
                                                 "\n" +
                                                 "@param widget the element of the widget, that will be\n" +
                                                 "              displayed inside the north area of the shell.\n")
                                 .addAnnotation(Event.class)
                                 .addParameter(getWidgetParameterSpec())
                                 .build());
    // event: setNavigation
    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addJavadoc("This event will set the element (parameter) in the west\n" +
                                                 "area of the shell.\n" +
                                                 "\n" +
                                                 "@param widget the element of the widget, that will be\n" +
                                                 "              displayed inside the west area of the shell.\n")
                                 .addAnnotation(Event.class)
                                 .addParameter(getWidgetParameterSpec())
                                 .build());
    // event: setHeader
    typeSpec.addMethod(MethodSpec.methodBuilder("setStatusbar")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addJavadoc("This event will set the element (parameter) in the south\n" +
                                                 "area of the shell.\n" +
                                                 "\n" +
                                                 "@param widget the element of the widget, that will be\n" +
                                                 "              displayed inside the south area of the shell.\n")
                                 .addAnnotation(Event.class)
                                 .addParameter(getWidgetParameterSpec())
                                 .build());
    // event: updateStatus
    typeSpec.addMethod(MethodSpec.methodBuilder("updateStatus")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addJavadoc("This event will update the status in the south\n" + "area of the shell.\n" + "\n" + "@param text to display.\n")
                                 .addAnnotation(Event.class)
                                 .addParameter(ParameterSpec.builder(String.class,
                                                                     "statusMessage")
                                                            .build())
                                 .build());
    // event initHistory
    if (this.mvp4g2GeneraterParms.isHistory()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("initHistory")
                                   .addModifiers(Modifier.PUBLIC,
                                                 Modifier.ABSTRACT)
                                   .addJavadoc("This event will be used in case:\n" + "\n" + "* there is not history-token\n" + "* the token is not valid\n")
                                   .addAnnotation(InitHistory.class)
                                   .addAnnotation(NotFoundHistory.class)
                                   .addAnnotation(Event.class)
                                   .build());
    }
    // Event: goto-Event
    this.mvp4g2GeneraterParms.getPresenters()
                             .forEach(presenterData -> {
                               AnnotationSpec.Builder eventAnnotation = AnnotationSpec.builder(Event.class);
                               if (this.mvp4g2GeneraterParms.isHistory()) {
                                 if (!Event.DEFAULT_HISTORY_NAME.equals(presenterData.getHistoryName())) {
                                   eventAnnotation.addMember("historyName",
                                                             "$S",
                                                             presenterData.getHistoryName());
                                 }
                                 eventAnnotation.addMember("historyConverter",
                                                           "$T.class",
                                                           ClassName.get(this.clientPackageJavaConform + ".history",
                                                                         "DefaultHistoryConverter"));
                                 if (this.mvp4g2GeneraterParms.hasNavigationConfirmation()) {
                                   eventAnnotation.addMember("navigationEvent",
                                                             "true");
                                 }
                               }
                               typeSpec.addMethod(MethodSpec.methodBuilder("goto" + GeneratorUtils.setFirstCharacterToUpperCase(presenterData.getName()))
                                                            .addModifiers(Modifier.PUBLIC,
                                                                          Modifier.ABSTRACT)
                                                            .addJavadoc(createJavaDocComment())
                                                            .addAnnotation(eventAnnotation.build())
                                                            .build());
                             });

    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.EVENT_BUS) + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract ParameterSpec getWidgetParameterSpec();

  private String createJavaDocComment() {
    String javaDocComment = "This event will display the detail screen inside the content of\n" + "the shell. The given id will be used to get the person from server\n" + "and display the view with the data, read rom the server.\n" + "\n";
    if (this.mvp4g2GeneraterParms.isHistory()) {
      javaDocComment += "We use the DetailHistoryConverter to convert the event to\n " +
          "the token which the framework will display after the url.\n" +
          "\n" +
          "We will use the String representated by HistoryName.DETAIL\n" +
          "instead the event name inside the token.\n" +
          "\n";
    }
    javaDocComment += "This event will change the screen displayed inside the\n" +
        "content area. From the mvp4g2 point of view, it is a\n" +
        "navigation event. If there is a confirm-presenter defined,\n" +
        "this presenter will be asked before the view changed.\n";
    return javaDocComment;
  }
}
