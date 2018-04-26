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
import com.github.mvp4g.mvp4g2.core.ui.IsShell;
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

public abstract class AbstractShellSourceGenerator
    extends AbstractSourceGenerator {
  private String presenterPackage;

  public void generate()
      throws GeneratorException {

    this.presenterPackage = this.clientPackageJavaConform + ".ui.shell";

    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();
  }

  private void generateIViewClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("IShellView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                                   "IShellView.Presenter")))
                                        .addMethod(MethodSpec.methodBuilder("asWidget")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(getBaseElement())
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(getBaseElement(),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(getBaseElement(),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setHeader")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(getBaseElement(),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setStatusbar")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(getBaseElement(),
                                                                     "widget")
                                                            .build())
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
      throw new GeneratorException("Unable to write generated file: >>IShellView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract TypeName getBaseElement();

  private void generateViewClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("ShellView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                            "IShellView.Presenter")))

                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                         "IShellView"));
    createFieldSpecsForViewClass(typeSpec);
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
                                 .addStatement("return shell")
                                 .build());
    // createView method
    createViewMethodForShell(typeSpec);
    // create set area methods ...
    createSetAreaMethods(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>ShellView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract void createViewMethodForShell(TypeSpec.Builder typeSpec);

  protected abstract void createFieldSpecsForViewClass(TypeSpec.Builder typeSpec);

  protected abstract void createSetAreaMethods(TypeSpec.Builder typeSpec);

  protected abstract void createSetShellMethodInPresenterClass(TypeSpec.Builder typeSpec);

  private void generatePresenterClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("ShellPresenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Presenter.class)
                                                                     .addMember("viewClass",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                              "ShellView"))
                                                                     .addMember("viewInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                              "IShellView"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                            "IShellView")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                         "IShellView.Presenter"))
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsShell.class),
                                                                                     ClassName.get(this.clientPackageJavaConform,
                                                                                                   GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                                   "IShellView")))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onBeforeEvent")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addParameter(String.class,
                                                                           "eventName")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetContent")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(getBaseElement(),
                                                                           "widget")
                                                             .addStatement("view.setContent(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetHeader")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(getBaseElement(),
                                                                           "widget")
                                                             .addStatement("view.setHeader(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetNavigation")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(getBaseElement(),
                                                                           "widget")
                                                             .addStatement("view.setNavigation(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetStatusbar")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(getBaseElement(),
                                                                           "widget")
                                                             .addStatement("view.setStatusbar(widget)")
                                                             .build());
    createSetShellMethodInPresenterClass(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>ShellPresenter" + "<< -> " + "exception: " + e.getMessage());
    }
  }
}
