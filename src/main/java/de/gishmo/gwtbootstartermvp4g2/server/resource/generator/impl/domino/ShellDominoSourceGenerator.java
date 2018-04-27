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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.domino;

import java.io.File;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractShellSourceGenerator;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.Node;
import org.jboss.gwt.elemento.core.Elements;

public class ShellDominoSourceGenerator
    extends AbstractShellSourceGenerator {

  private ShellDominoSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  protected TypeName getBaseElement() {
    return TypeName.get(Element.class);
  }

  @Override
  protected void createFieldSpecsForViewClass(TypeSpec.Builder typeSpec) {
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
  }

  @Override
  protected void createSetAreaMethods(TypeSpec.Builder typeSpec) {
    // setContent method
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(getBaseElement(),
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
                                 .addParameter(getBaseElement(),
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
                                 .addParameter(getBaseElement(),
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
                                 .addParameter(getBaseElement(),
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
  }

  @Override
  protected void createSetShellMethodInPresenterClass(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("setShell")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("$T.document.body.appendChild(view.asWidget())",
                                               DomGlobal.class)
                                 .build());
  }

  @Override
  protected void createViewMethodForShell(TypeSpec.Builder typeSpec) {
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

    public ShellDominoSourceGenerator build() {
      return new ShellDominoSourceGenerator(this);
    }
  }
}
