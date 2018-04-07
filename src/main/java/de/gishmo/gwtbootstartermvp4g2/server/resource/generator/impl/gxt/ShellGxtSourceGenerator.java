package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.IsShell;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
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

public class ShellGxtSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private File directoryJava;

  private String clientPackageJavaConform;

  private String presenterPackage;

  private ShellGxtSourceGenerator(Builder builder) {
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
                                                             .returns(ClassName.get(Widget.class))
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
                                                             .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setHeader")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
                                                                     "widget")
                                                            .build())
                                 .build());
    typeSpec.addMethod(MethodSpec.methodBuilder("setStatusbar")
                                 .addModifiers(Modifier.PUBLIC,
                                               Modifier.ABSTRACT)
                                 .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
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
    typeSpec.addField(FieldSpec.builder(Viewport.class,
                                        "shell",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(BorderLayoutContainer.class,
                                        "container",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ContentPanel.class,
                                        "header",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ContentPanel.class,
                                        "navigation",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(SimpleContainer.class,
                                        "statusbar",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(ContentPanel.class,
                                        "content",
                                        Modifier.PRIVATE)
                               .build());
    typeSpec.addField(FieldSpec.builder(Widget.class,
                                        "widget",
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
                                 .returns(Widget.class)
                                 .addStatement("return shell")
                                 .build());
    // createView method
    this.createViewMethodForShell(typeSpec);
    // setContent method
    typeSpec.addMethod(MethodSpec.methodBuilder("setContent")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .addParameter(Widget.class,
                                               "widget")
                                 .beginControlFlow("if (this.widget != null) ")
                                 .addStatement("this.widget.removeFromParent()")
                                 .endControlFlow()
                                 .addStatement("this.content.setWidget(widget)")
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
                                                             .addParameter(Widget.class,
                                                                           "widget")
                                                             .addStatement("view.setContent(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetHeader")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(Widget.class,
                                                                           "widget")
                                                             .addStatement("view.setHeader(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetNavigation")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(Widget.class,
                                                                           "widget")
                                                             .addStatement("view.setNavigation(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("onSetStatusbar")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(EventHandler.class)
                                                             .addParameter(Widget.class,
                                                                           "widget")
                                                             .addStatement("view.setStatusbar(widget)")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("setShell")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(Override.class)
                                                             .addStatement("$T.get().add(view.asWidget())",
                                                                           RootLayoutPanel.class)
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
                                               Viewport.class)
                                 .addStatement("shell.setSize(\"100%\", \"100%\")")
                                 .addStatement("container = new $T()",
                                               BorderLayoutContainer.class)
                                 .addStatement("container.setSize(\"100%\", \"100%\")")
                                 .addStatement("shell.add(container)")
                                 .addCode("")
                                 .addStatement("header = new $T()",
                                               ContentPanel.class)
                                 .addStatement("header.setHeading($T.fromTrustedString(\"Your Header\"))",
                                               SafeHtmlUtils.class)
                                 .addStatement("header.setBodyStyle(\"background: whitesmoke; padding: 12px;\")")
                                 .addStatement("$T northData = new $T(128)",
                                               BorderLayoutContainer.BorderLayoutData.class,
                                               BorderLayoutContainer.BorderLayoutData.class)
                                 .addStatement("container.setNorthWidget(header, northData)")
                                 .addCode("")
                                 .addStatement("navigation = new $T()",
                                               ContentPanel.class)
                                 .addStatement("navigation.setHeading($T.fromTrustedString(\"Navigation\"))",
                                               SafeHtmlUtils.class)
                                 .addStatement("navigation.setBodyStyle(\"background: snow; padding: 12px;\")")
                                 .addStatement("$T westData = new $T(212)",
                                               BorderLayoutContainer.BorderLayoutData.class,
                                               BorderLayoutContainer.BorderLayoutData.class)
                                 .addStatement("container.setWestWidget(navigation, westData)")
                                 .addCode("")
                                 .addStatement("content = new $T()",
                                               ContentPanel.class)
                                 .addStatement("content.setHeading($T.fromTrustedString(\"Content\"))",
                                               SafeHtmlUtils.class)
                                 .addStatement("content.setBodyStyle(\"background: white; padding: 12px;\")")
                                 .addStatement("container.setWidget(content)")
                                 .addCode("")
                                 .addStatement("statusbar = new $T()",
                                               SimpleContainer.class)
                                 .addStatement("$T southData = new $T(42)",
                                               BorderLayoutContainer.BorderLayoutData.class,
                                               BorderLayoutContainer.BorderLayoutData.class)
                                 .addStatement("container.setSouthWidget(statusbar, southData)")
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

    public ShellGxtSourceGenerator build() {
      return new ShellGxtSourceGenerator(this);
    }
  }
}