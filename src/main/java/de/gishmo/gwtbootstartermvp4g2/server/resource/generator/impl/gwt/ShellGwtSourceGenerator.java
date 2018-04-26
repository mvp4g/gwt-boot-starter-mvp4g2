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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.*;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractShellSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.File;

public class ShellGwtSourceGenerator
    extends AbstractShellSourceGenerator {

  private ShellGwtSourceGenerator(Builder builder) {
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
    return TypeName.get(Widget.class);
  }

  @Override
  protected void createFieldSpecsForViewClass(TypeSpec.Builder typeSpec) {
    typeSpec.addField(FieldSpec.builder(ClassName.get(ResizeLayoutPanel.class),
                                        "shell",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(DockLayoutPanel.class),
                                        "container",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimplePanel.class),
                                        "header",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimplePanel.class),
                                        "navigation",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimplePanel.class),
                                        "statusbar",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ClassName.get(Widget.class),
                                        "widget",
                                        Modifier.PRIVATE)
                               .build());
  }

  @Override
  protected void createSetAreaMethods(TypeSpec.Builder typeSpec) {
    // setContent method
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Widget.class,
                                               "widget")
                                 .beginControlFlow("if (this.widget != null) ")
                                 .addStatement("this.widget.removeFromParent()")
                                 .endControlFlow()
                                 .addStatement("this.container.add(widget)")
                                 .addStatement("this.widget = widget")
                                 .build());
    // setHeader method
    typeSpec.addMethod(MethodSpec.methodBuilder("setHeader")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Widget.class,
                                               "widget")
                                 .beginControlFlow("if (this.header.getWidget() != null) ")
                                 .addStatement("this.header.getWidget().removeFromParent()")
                                 .endControlFlow()
                                 .addStatement("this.header.setWidget(widget)")
                                 .build());
    // setNavigation method
    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Widget.class,
                                               "widget")
                                 .beginControlFlow("if (this.navigation.getWidget() != null) ")
                                 .addStatement("this.navigation.getWidget().removeFromParent()")
                                 .endControlFlow()
                                 .addStatement("this.navigation.setWidget(widget)")
                                 .build());
    // setHeader method
    typeSpec.addMethod(MethodSpec.methodBuilder("setStatusbar")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Widget.class,
                                               "widget")
                                 .beginControlFlow("if (this.statusbar.getWidget() != null) ")
                                 .addStatement("this.statusbar.getWidget().removeFromParent()")
                                 .endControlFlow()
                                 .addStatement("this.statusbar.setWidget(widget)")
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("forceLayout")
                                 .addModifiers(Modifier.PUBLIC)
                                 .build());
  }

  @Override
  protected void createSetShellMethodInPresenterClass(TypeSpec.Builder typeSpec) {
    typeSpec.addMethod(MethodSpec.methodBuilder("setShell")
                                 .addModifiers(Modifier.PUBLIC)
                                 .addAnnotation(Override.class)
                                 .addStatement("$T.get().add(view.asWidget())",
                                               RootLayoutPanel.class)
                                 .build());
  }

  @Override
  protected void createViewMethodForShell(TypeSpec.Builder typeSpec) {
    TypeSpec resizeHandler = TypeSpec.anonymousClassBuilder("")
                                     .addSuperinterface(ResizeHandler.class)
                                     .addMethod(MethodSpec.methodBuilder("onResize")
                                                          .addAnnotation(Override.class)
                                                          .addModifiers(Modifier.PUBLIC)
                                                          .addParameter(ResizeEvent.class,
                                                                        "event")
                                                          .addStatement("forceLayout()")
                                                          .build())
                                     .build();

    typeSpec.addMethod(MethodSpec.methodBuilder("createView")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addStatement("shell = new $T()",
                                               ClassName.get(ResizeLayoutPanel.class))
                                 .addStatement("shell.setSize(\"100%\", \"100%\")")
                                 .addStatement("shell.addResizeHandler($L)",
                                               resizeHandler)
                                 .addCode("")
                                 .addStatement("container = new $T($T.PX)",
                                               DockLayoutPanel.class,
                                               Style.Unit.class)
                                 .addStatement("container.setSize(\"100%\", \"100%\")")
                                 .addStatement("shell.add(container)")
                                 .addCode("")
                                 .addStatement("header = new $T()",
                                               SimplePanel.class)
                                 .addStatement("container.addNorth(header, 128)")
                                 .addCode("")
                                 .addStatement("navigation = new $T()",
                                               SimplePanel.class)
                                 .addStatement("container.addWest(navigation, 212)")
                                 .addCode("")
                                 .addStatement("statusbar = new $T()",
                                               SimplePanel.class)
                                 .addStatement("container.addSouth(statusbar, 42)")
                                 .build());
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;

    File directoryJava;

    String clientPackageJavaConform;

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

    public ShellGwtSourceGenerator build() {
      return new ShellGwtSourceGenerator(this);
    }
  }
}
