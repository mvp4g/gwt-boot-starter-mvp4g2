package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
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
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

public class PresenterViewSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private File directoryJava;

  private String clientPackageJavaConform;

  private String presenterPackage;

  private File presenterPackageFile;

  private PresenterData presenterData;

  private PresenterViewSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
    this.presenterData = builder.presenterData;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws GeneratorException {

    this.presenterPackage = this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                  .toLowerCase();
    this.presenterPackageFile = new File(this.directoryJava + File.separator + "ui" + File.separator + presenterData.getName()
                                                                                                                    .toLowerCase() + File.separator);
    if (!this.presenterPackageFile.exists()) {
      this.presenterPackageFile.mkdirs();
    }

    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();
  }

  private void generateIViewClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                         .toLowerCase(),
                                                                                                   "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter")))
                                        .addMethod(MethodSpec.methodBuilder("asWidget")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(ClassName.get(Widget.class))
                                                             .addJavadoc("mvp4g2 does not know Widget-, Element- or any other class. So, the\n" + "presenter have to manage the widget by themselves. The method will\n" + "enable the presenter to get the view. (In our case it is a\n" + "GWT widget)\n" + "\n" + "@return The shell widget\n")
                                                             .build());
    if (this.presenterData.isShell()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("setCenter")
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
      throw new GeneratorException("Unable to write generated file: >>I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateViewClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                  .toLowerCase(),
                                                                                            "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter")))

                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                               .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter"));
    if (this.presenterData.isShell()) {
      typeSpec.addField(FieldSpec.builder(ClassName.get(ResizeLayoutPanel.class),
                                          "shell",
                                          Modifier.PRIVATE)
                                 .build());
      typeSpec.addField(FieldSpec.builder(ClassName.get(DockLayoutPanel.class),
                                          "container",
                                          Modifier.PRIVATE)
                                 .build());
    } else {

    }
    // constrcutor
    typeSpec.addMethod(MethodSpec.constructorBuilder()
                                 .addStatement("super()")
                                 .build());
    // createView method
    if (this.presenterData.isShell()) {
      this.createViewMethodForShell(typeSpec);
    } else {

    }

    //    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")
    //                                        .addJavadoc(CodeBlock.builder()
    //                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
    //                                                             .build())
    //                                        .addModifiers(Modifier.PUBLIC)
    //                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
    //                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.shell",
    //                                                                                            "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter")))
    //
    //                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.shell",
    //                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter"))
    //                                        .addField(container)
    //                                        .addMethod(MethodSpec.constructorBuilder()
    //                                                             .addStatement("super()")
    //                                                             .build());
    //                                        .addMethod(MethodSpec.methodBuilder("asWidget")
    //                                                             .addModifiers(Modifier.PUBLIC,
    //                                                                           Modifier.ABSTRACT)
    //                                                             .returns(ClassName.get(Widget.class))
    //                                                             .addJavadoc("mvp4g2 does not know Widget-, Element- or any other class. So, the\n" + "presenter have to manage the widget by themselves. The method will\n" + "enable the presenter to get the view. (In our case it is a\n" + "GWT widget)\n" + "\n" + "@return The shell widget\n")
    //                                                             .build());
    // TODO

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void createViewMethodForShell(TypeSpec.Builder typeSpec) {
    TypeSpec resizeHandler = TypeSpec.anonymousClassBuilder("")
                                     .addSuperinterface(ResizeHandler.class)
                                     .addMethod(MethodSpec.methodBuilder("onResize")
                                                          .addAnnotation(Override.class)
                                                          .addParameter(ResizeEvent.class,
                                                                        "event")
                                                          .addStatement("forceLayout()")
                                                          .build())
                                     .build();

    typeSpec.addMethod(MethodSpec.methodBuilder("createView")
                                 .addAnnotation(Override.class)
                                 .addStatement("shell = new $T",
                                               ClassName.get(ResizeLayoutPanel.class))
                                 .addStatement("shell.setSize(\"100%\", \"100%\")")
                                 .addStatement("shell.addResizeHandler(new $T)",
                                               resizeHandler)
                                 .addCode("")
                                 .addStatement("panel = new $T($T.PX)",
                                               DockLayoutPanel.class,
                                               Style.Unit.class)
                                 .addStatement("panel.setSize(\"100%\", \"100%\")")
                                 .addStatement("shell.add(panel)")
                                 .addCode("")
                                 .addStatement("$T header = createNorth()",
                                               Widget.class)
                                 .addStatement("panel.addNorth(header, 128)")
                                 .addCode("")
                                 .addStatement("$T footer = createSouth()",
                                               Widget.class)
                                 .addStatement("panel.addSouth(footer, 42)")
                                 .build());

    // addNorth method
    typeSpec.addMethod(MethodSpec.methodBuilder("createNorth")
                                 .returns(Widget.class)
                                 .addStatement("return new $T(\"That's the header area. Create your header here\")", Label.class)
                                 .build());
    // addSouth method
    typeSpec.addMethod(MethodSpec.methodBuilder("createSouth")
                                 .returns(Widget.class)
                                 .addStatement("return new $T(\"That's the footer area. Create your Footer here\")", Label.class)
                                 .build());
  }

  private void generatePresenterClass() {
    // TODO

  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;

    File directoryJava;

    String clientPackageJavaConform;

    PresenterData presenterData;

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

    public Builder presenterData(PresenterData presenterData) {
      this.presenterData = presenterData;
      return this;
    }

    public PresenterViewSourceGenerator build() {
      return new PresenterViewSourceGenerator(this);
    }
  }
}
