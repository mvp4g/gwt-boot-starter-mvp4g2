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

import com.github.mvp4g.mvp4g2.core.history.IsNavigationConfirmation;
import com.github.mvp4g.mvp4g2.core.history.NavigationEventCommand;
import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.IsViewCreator;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public abstract class AbstractPresenterViewSourceGenerator
  extends AbstractSourceGenerator {

  protected PresenterData presenterData;

  private String presenterPackage;

  public void generate()
      throws GeneratorException {

    this.presenterPackage = this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                  .toLowerCase();

    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();
  }

  private void generateIViewClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                         .toLowerCase(),
                                                                                                   "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View.Presenter")))
                                        .addMethod(MethodSpec.methodBuilder("asWidget")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(getBaseElement())
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build());
    if (presenterData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("isDirty")
                                   .addModifiers(Modifier.PUBLIC,
                                                 Modifier.ABSTRACT)
                                   .returns(TypeName.BOOLEAN)
                                   .build());
    }
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
      throw new GeneratorException("Unable to write generated file: >>I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract TypeName getBaseElement();

  private void generateViewClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                  .toLowerCase(),
                                                                                            "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View.Presenter")))

                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                               .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"));
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
    this.createViewMethod(typeSpec);

    if (presenterData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("isDirty")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(TypeName.BOOLEAN)
                                   .addStatement("return true")
                                   .build());
    }

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract void createViewMethod(TypeSpec.Builder typeSpec);

  protected abstract FieldSpec getContainerFieldSpec();

  private void generatePresenterClass()
    throws GeneratorException {
    AnnotationSpec.Builder presenterAnnotation = AnnotationSpec.builder(Presenter.class)
                                                               .addMember("viewClass",
                                                                          "$T.class",
                                                                          ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                              .toLowerCase(),
                                                                                        GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"))
                                                               .addMember("viewInterface",
                                                                          "$T.class",
                                                                          ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                              .toLowerCase(),
                                                                                        "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"));
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      presenterAnnotation.addMember("viewCreator",
                                    "$T.VIEW_CREATION_METHOD.PRESENTER",
                                    Presenter.class);
    }
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "Presenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(presenterAnnotation.build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                  .toLowerCase(),
                                                                                            "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                               .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View.Presenter"));
    if (presenterData.isConfirmation()) {
      typeSpec.addSuperinterface(IsNavigationConfirmation.class);
    }
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      typeSpec.addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsViewCreator.class),
                                                           ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                               .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View")));
    }
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addModifiers(Modifier.PUBLIC)
                                 .build())
            .addMethod(MethodSpec.methodBuilder("onBeforeEvent")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addParameter(String.class,
                                               "eventName")
                                 .addComment("This method will be call in case the presenter will handle a event and before the event handling")
                                 .build());

    MethodSpec.Builder onGotoMethod = MethodSpec.methodBuilder("onGoto" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()))
                                                .addModifiers(Modifier.PUBLIC)
                                                .addAnnotation(EventHandler.class)
                                                .addStatement("eventBus.setContent(view.asWidget())");
    if (presenterData.isConfirmation()) {
      onGotoMethod.addStatement("eventBus.setNavigationConfirmation(this)");
    }
    typeSpec.addMethod(onGotoMethod.build());
    if (!this.mvp4g2GeneraterParms.isHistory()) {
      if (presenterData.isShowPresenterAtStart()) {
        typeSpec.addMethod(MethodSpec.methodBuilder("onStart")
                                     .addModifiers(Modifier.PUBLIC)
                                     .addAnnotation(EventHandler.class)
                                     .addStatement("eventBus.goto$L()",
                                                   GeneratorUtils.setFirstCharacterToUpperCase(presenterData.getName()))
                                     .build());
      }
    } else {
      if (presenterData.isShowPresenterAtStart()) {
        typeSpec.addMethod(MethodSpec.methodBuilder("onInitHistory")
                                     .addModifiers(Modifier.PUBLIC)
                                     .addAnnotation(EventHandler.class)
                                     .addStatement("eventBus.goto$L()",
                                                   GeneratorUtils.setFirstCharacterToUpperCase(presenterData.getName()))
                                     .build());
      }
    }
    createConfirmMethod(typeSpec);
    createViewCreationMethod(typeSpec);

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "Presenter" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  protected abstract void createViewCreationMethod(TypeSpec.Builder typeSpec);

  protected abstract void createConfirmMethod(TypeSpec.Builder typeSpec);
}
