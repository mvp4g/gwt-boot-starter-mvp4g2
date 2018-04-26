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
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractNavigationSourceGenerator
    extends AbstractSourceGenerator {

  private String presenterPackage;

  public void generate()
      throws GeneratorException {

    this.presenterPackage = this.clientPackageJavaConform + ".ui.navigation";

    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();
  }

  private void generateIViewClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("INavigationView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                                   "INavigationView.Presenter")))
                                        .addMethod(MethodSpec.methodBuilder("asWidget")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(getBaseElement())
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build());

    typeSpec.addType(TypeSpec.interfaceBuilder("Presenter")
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .addMethod(MethodSpec.methodBuilder("doNavigateTo")
                                                  .addParameter(ParameterSpec.builder(String.class,
                                                                                      "target")
                                                                             .build())
                                                  .addModifiers(Modifier.PUBLIC,
                                                                Modifier.ABSTRACT)
                                                  .build())
                             .build());

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>INavigationView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract TypeName getBaseElement();

  private void generateViewClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("NavigationView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                            "INavigationView.Presenter")))

                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                         "INavigationView"));
    typeSpec.addField(getContainerFieldSpec());
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
    // createView method
    createViewMethod(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>NavigationView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract void createViewMethod(TypeSpec.Builder typeSpec);

  protected abstract FieldSpec getContainerFieldSpec();

  private void generatePresenterClass()
      throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("NavigationPresenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Presenter.class)
                                                                     .addMember("viewClass",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                              "NavigationView"))
                                                                     .addMember("viewInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                              "INavigationView"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                                            "INavigationView")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
                                                                         "INavigationView.Presenter"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onBeforeEvent")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(Override.class)
                                                             .addParameter(String.class,
                                                                           "eventName")
                                                             .addComment("This method will be call in case the presenter will handle a event and before the event handling")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("bind")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(Override.class)
                                                             .addStatement("eventBus.setNavigation(view.asWidget())")
                                                             .build());

    MethodSpec.Builder doNavigateToMethod = MethodSpec.methodBuilder("doNavigateTo")
                                                      .addModifiers(Modifier.PUBLIC)
                                                      .addParameter(ParameterSpec.builder(String.class,
                                                                                          "target")
                                                                                 .build());
    doNavigateToMethod.beginControlFlow("switch (target)");
    this.mvp4g2GeneraterParms.getPresenters()
                             .forEach(presenterData -> doNavigateToMethod.addCode("case $S:\n",
                                                                                  presenterData.getName())
                                                                         .addStatement("eventBus.goto$L()",
                                                                                       GeneratorUtils.setFirstCharacterToUpperCase(presenterData.getName()))
                                                                         .addStatement("break"));
    doNavigateToMethod.endControlFlow();
    typeSpec.addMethod(doNavigateToMethod.build());

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>NavigationPresenter" + "<< -> " + "exception: " + e.getMessage());
    }
  }
}
