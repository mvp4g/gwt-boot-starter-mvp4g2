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

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractStatusSourceGenerator
    extends AbstractSourceGenerator {

  protected String presenterPackage;

  public void generate()
      throws GeneratorException {
    this.presenterPackage = this.clientPackageJavaConform + ".ui.statusbar";

    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();
  }

  private void generatePresenterClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("StatusbarPresenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Presenter.class)
                                                                     .addMember("viewClass",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                              "StatusbarView"))
                                                                     .addMember("viewInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                              "IStatusbarView"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                            "IStatusbarView")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                         "IStatusbarView.Presenter"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("bind")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(Override.class)
                                                             .addStatement("eventBus.setStatusbar(view.asWidget())")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onUpdateStatus")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(ParameterSpec.builder(String.class,
                                                                                                 "statusMessage")
                                                                                        .build())
                                                             .addStatement("view.edit(statusMessage)")
                                                             .build());

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>StatusbarPresenter" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateViewClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("StatusbarView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                            "IStatusbarView.Presenter")))

                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                         "IStatusbarView"));
    typeSpec.addField(getContainerFieldSpec());
    typeSpec.addField(getLabelFieldSpec());
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // asWidget method
    typeSpec.addMethod(MethodSpec.methodBuilder("asWidget")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(getBaseElement())
                                 .addStatement("return container")
                                 .build());
    // edit(String) method
    typeSpec.addMethod(MethodSpec.methodBuilder("edit")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(Void.class)
                                 .addParameter(ParameterSpec.builder(String.class,
                                                                     "message")
                                                            .build())
                                 .addStatement(getSetLabelValueStatement())
                                 .build());
    // createView method
    createViewMethod(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>StatusbarView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract String getSetLabelValueStatement();

  protected abstract void createViewMethod(TypeSpec.Builder typeSpec);

  protected abstract TypeName getBaseElement();

  protected abstract FieldSpec getContainerFieldSpec();

  protected abstract FieldSpec getLabelFieldSpec();

  private void generateIViewClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("IStatusbarView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                                   "IStatusbarView.Presenter")))
                                        .addMethod(MethodSpec.methodBuilder("asWidget")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(getBaseElement())
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("edit")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(Void.class)
                                                             .addParameter(ParameterSpec.builder(String.class,
                                                                                                 "message")
                                                                                        .build())
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build());

    typeSpec.addType(TypeSpec.interfaceBuilder("Presenter")
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .build());

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>IStatusbarView" + "<< -> " + "exception: " + e.getMessage());
    }
  }
}
