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

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.IsShell;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;
import org.jboss.gwt.elemento.core.Elements;

public class ShellElementoSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private File directoryJava;

  private String clientPackageJavaConform;

  private String presenterPackage;

  private ShellElementoSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }

  public static Builder builder() {
    return new Builder();
  }

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
                                                             .returns(ClassName.get(Element.class))
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Element.class),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Element.class),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setHeader")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Element.class),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setStatusbar")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Element.class),
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLElement.class),
                                        "shell",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLElement.class),
                                        "content",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLElement.class),
                                        "header",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLElement.class),
                                        "navigation",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLElement.class),
                                        "statusbar",
                                        Modifier.PRIVATE)
                               .build());
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
    // asWidget method
    typeSpec.addMethod(MethodSpec.methodBuilder("asWidget")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(Element.class)
                                 .addStatement("return shell")
                                 .build());
    // createView method
    this.createViewMethodForShell(typeSpec);
    // setContent method
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Element.class,
                                               "widget")
                                 .beginControlFlow("if (this.content.childElementCount > 0) ")
                                 .beginControlFlow("for (int i = 0; i < this.content.childNodes.length; i++)")
                                 .addStatement("$T oldChild = this.content.childNodes.item(i)",
                                               Node.class)
                                 .addStatement("this.content.removeChild(oldChild)")
                                 .endControlFlow()
                                 .endControlFlow()
                                 .addStatement("this.content.appendChild(widget)")
                                 .build());
    // setHeader method
    typeSpec.addMethod(MethodSpec.methodBuilder("setHeader")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Element.class,
                                               "widget")
                                 .beginControlFlow("if (this.header.childElementCount > 0) ")
                                 .beginControlFlow("for (int i = 0; i < this.header.childNodes.length; i++)")
                                 .addStatement("$T oldChild = this.header.childNodes.item(i)",
                                               Node.class)
                                 .addStatement("this.header.removeChild(oldChild)")
                                 .endControlFlow()
                                 .endControlFlow()
                                 .addStatement("this.header.appendChild(widget)")
                                 .build());
    // setNavigation method
    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Element.class,
                                               "widget")
                                 .beginControlFlow("if (this.navigation.childElementCount > 0) ")
                                 .beginControlFlow("for (int i = 0; i < this.navigation.childNodes.length; i++)")
                                 .addStatement("$T oldChild = this.navigation.childNodes.item(i)",
                                               Node.class)
                                 .addStatement("this.navigation.removeChild(oldChild)")
                                 .endControlFlow()
                                 .endControlFlow()
                                 .addStatement("this.navigation.appendChild(widget)")
                                 .build());
    // setHeader method
    typeSpec.addMethod(MethodSpec.methodBuilder("setStatusbar")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Element.class,
                                               "widget")
                                 .beginControlFlow("if (this.statusbar.childElementCount > 0) ")
                                 .beginControlFlow("for (int i = 0; i < this.statusbar.childNodes.length; i++)")
                                 .addStatement("$T oldChild = this.statusbar.childNodes.item(i)",
                                               Node.class)
                                 .addStatement("this.statusbar.removeChild(oldChild)")
                                 .endControlFlow()
                                 .endControlFlow()
                                 .addStatement("this.statusbar.appendChild(widget)")
                                 .build());

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
                                                                                            GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                                            "IShellView")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.shell",
                                                                         "IShellView.Presenter"))
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsShell.class),
                                                                                     ClassName.get(this.clientPackageJavaConform,
                                                                                                   GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
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
                                                             .addParameter(Element.class,
                                                                           "widget")
                                                             .addStatement("view.setContent(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetHeader")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(Element.class,
                                                                           "widget")
                                                             .addStatement("view.setHeader(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetNavigation")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(Element.class,
                                                                           "widget")
                                                             .addStatement("view.setNavigation(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetStatusbar")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(Element.class,
                                                                           "widget")
                                                             .addStatement("view.setStatusbar(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("setShell")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(Override.class)
                                                             .addStatement("$T.document.body.appendChild(view.asWidget())",
                                                                           DomGlobal.class)
                                                             .build());


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

  private void createViewMethodForShell(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("createView")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("this.shell = $T.div().css(\"shell\")" +
                                               ".add(header = $T.div().css(\"shellHeader\").asElement())" +
                                               ".add(navigation = $T.div().css(\"shellNavigation\").asElement())" +
                                               ".add(statusbar = $T.div().css(\"shellFooter\").asElement())" +
                                               ".add(content = $T.div().css(\"shellContent\").asElement())" +
                                               ".asElement()",
                                               Elements.class,
                                               Elements.class,
                                               Elements.class,
                                               Elements.class,
                                               Elements.class)
                                 .build());
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File                 directoryJava;
    String               clientPackageJavaConform;

    public Builder mvp4g2GeneraterParms(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
      this.mvp4g2GeneraterParms = mvp4g2GeneraterParms;
      return this;
    }

    public Builder directoryJava(File directoryJava) {
      this.directoryJava = directoryJava;
      return this;
    }

    public Builder clientPackageJavaConform(String clientPackageJavaConform) {
      this.clientPackageJavaConform = clientPackageJavaConform;
      return this;
    }

    public ShellElementoSourceGenerator build() {
      return new ShellElementoSourceGenerator(this);
    }
  }
}
