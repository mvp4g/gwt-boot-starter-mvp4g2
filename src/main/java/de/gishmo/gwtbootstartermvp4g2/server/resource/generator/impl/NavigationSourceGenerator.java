package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

public class NavigationSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private File directoryJava;

  private String clientPackageJavaConform;

  private String presenterPackage;

  private File presenterPackageFile;

  private NavigationSourceGenerator(Builder builder) {
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
//
//    this.presenterPackage = this.clientPackageJavaConform + ".ui." + presenterData.getName()
//                                                                                  .toLowerCase();
//    this.presenterPackageFile = new File(this.directoryJava + File.separator + "ui" + File.separator + presenterData.getName()
//                                                                                                                    .toLowerCase() + File.separator);
//    if (!this.presenterPackageFile.exists()) {
//      this.presenterPackageFile.mkdirs();
//    }
//
//    this.generateIViewClass();
//    this.generateViewClass();
//    this.generatePresenterClass();
  }

//  private void generateIViewClass()
//    throws GeneratorException {
//    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")
//                                        .addJavadoc(CodeBlock.builder()
//                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
//                                                             .build())
//                                        .addModifiers(Modifier.PUBLIC)
//                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
//                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
//                                                                                                                                                         .toLowerCase(),
//                                                                                                   "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter")))
//                                        .addMethod(MethodSpec.methodBuilder("asWidget")
//                                                             .addModifiers(Modifier.PUBLIC,
//                                                                           Modifier.ABSTRACT)
//                                                             .returns(ClassName.get(Widget.class))
//                                                             .addJavadoc("mvp4g2 does not know Widget-, Element- or any other GWT specific class. So, the\n" + "presenter have to manage the widget by themselves. The method will\n" + "enable the presenter to get the view. (In our case it is a\n" + "GWT widget)\n" + "\n" + "@return The shell widget\n")
//                                                             .build());
//    if (this.presenterData.isShell()) {
//      typeSpec.addMethod(MethodSpec.methodBuilder("setCenter")
//                                   .addModifiers(Modifier.PUBLIC,
//                                                 Modifier.ABSTRACT)
//                                   .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
//                                                                       "widget")
//                                                              .build())
//                                   .build());
//      typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
//                                   .addModifiers(Modifier.PUBLIC,
//                                                 Modifier.ABSTRACT)
//                                   .addParameter(ParameterSpec.builder(ClassName.get(Widget.class),
//                                                                       "widget")
//                                                              .build())
//                                   .build());
//    }
//    typeSpec.addType(TypeSpec.interfaceBuilder("Presenter")
//                             .addModifiers(Modifier.PUBLIC,
//                                           Modifier.STATIC)
//                             .build());
//
//    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
//                                         typeSpec.build())
//                                .build();
//    try {
//      javaFile.writeTo(new File(directoryJava,
//                                ""));
//    } catch (IOException e) {
//      throw new GeneratorException("Unable to write generated file: >>I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
//    }
//  }

//  private void generateViewClass()
//    throws GeneratorException {
//    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")
//                                        .addJavadoc(CodeBlock.builder()
//                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
//                                                             .build())
//                                        .addModifiers(Modifier.PUBLIC)
//                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
//                                                                              ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
//                                                                                                                                                  .toLowerCase(),
//                                                                                            "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter")))
//
//                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
//                                                                                                                               .toLowerCase(),
//                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"));
//    if (this.presenterData.isShell()) {
//      typeSpec.addField(FieldSpec.builder(ClassName.get(ResizeLayoutPanel.class),
//                                          "shell",
//                                          Modifier.PRIVATE)
//                                 .build());
//      typeSpec.addField(FieldSpec.builder(ClassName.get(DockLayoutPanel.class),
//                                          "container",
//                                          Modifier.PRIVATE)
//                                 .build());
//      typeSpec.addField(FieldSpec.builder(ClassName.get(Widget.class),
//                                          "widget",
//                                          Modifier.PRIVATE)
//                                 .build());
//    } else {
//
//    }
//    // constrcutor
//    typeSpec.addMethod(MethodSpec.constructorBuilder()
//                                 .addStatement("super()")
//                                 .addModifiers(Modifier.PUBLIC)
//                                 .build());
//    // asWidget method
//    typeSpec.addMethod(MethodSpec.methodBuilder("asWidget")
//                                 .addAnnotation(Override.class)
//                                 .addModifiers(Modifier.PUBLIC)
//                                 .returns(Widget.class)
//                                 .addStatement("return shell")
//                                 .build());
//    // createView method
//    if (this.presenterData.isShell()) {
//      this.createViewMethodForShell(typeSpec);
//      // setNavigation method
//      typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
//                                   .addAnnotation(Override.class)
//                                   .addModifiers(Modifier.PUBLIC)
//                                   .addParameter(Widget.class,
//                                                 "widget")
//                                   .addStatement("container.addWest(widget, 212)")
//                                   .build());
//      // setCenter method
//      typeSpec.addMethod(MethodSpec.methodBuilder("setCenter")
//                                   .addAnnotation(Override.class)
//                                   .addModifiers(Modifier.PUBLIC)
//                                   .addParameter(Widget.class,
//                                                 "widget")
//                                   .beginControlFlow("widget != null")
//                                   .addStatement("if (widget.removeFromParent())")
//                                   .endControlFlow()
//                                   .addStatement("container.add(widget)")
//                                   .addStatement("this.widget = widget")
//                                   .build());
//    } else {
//
//    }
//
//    //    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")
//    //                                        .addJavadoc(CodeBlock.builder()
//    //                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
//    //                                                             .build())
//    //                                        .addModifiers(Modifier.PUBLIC)
//    //                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
//    //                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.shell",
//    //                                                                                            "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter")))
//    //
//    //                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.shell",
//    //                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter"))
//    //                                        .addField(container)
//    //                                        .addMethod(MethodSpec.constructorBuilder()
//    //                                                             .addStatement("super()")
//    //                                                             .build());
//    //                                        .addMethod(MethodSpec.methodBuilder("asWidget")
//    //                                                             .addModifiers(Modifier.PUBLIC,
//    //                                                                           Modifier.ABSTRACT)
//    //                                                             .returns(ClassName.get(Widget.class))
//    //                                                             .addJavadoc("mvp4g2 does not know Widget-, Element- or any other class. So, the\n" + "presenter have to manage the widget by themselves. The method will\n" + "enable the presenter to get the view. (In our case it is a\n" + "GWT widget)\n" + "\n" + "@return The shell widget\n")
//    //                                                             .build());
//    // TODO
//
//    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
//                                         typeSpec.build())
//                                .build();
//    try {
//      javaFile.writeTo(new File(directoryJava,
//                                ""));
//    } catch (IOException e) {
//      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
//    }
//  }
//
//  private void generatePresenterClass() {
//    // TODO
//
//  }
//
//  private void createViewMethodForShell(TypeSpec.Builder typeSpec) {
//    TypeSpec resizeHandler = TypeSpec.anonymousClassBuilder("")
//                                     .addSuperinterface(ResizeHandler.class)
//                                     .addMethod(MethodSpec.methodBuilder("onResize")
//                                                          .addAnnotation(Override.class)
//                                                          .addParameter(ResizeEvent.class,
//                                                                        "event")
//                                                          .addStatement("forceLayout()")
//                                                          .build())
//                                     .build();
//
//    typeSpec.addMethod(MethodSpec.methodBuilder("createView")
//                                 .addAnnotation(Override.class)
//                                 .addModifiers(Modifier.PUBLIC)
//                                 .addStatement("shell = new $T",
//                                               ClassName.get(ResizeLayoutPanel.class))
//                                 .addStatement("shell.setSize(\"100%\", \"100%\")")
//                                 .addStatement("shell.addResizeHandler($L)",
//                                               resizeHandler)
//                                 .addCode("")
//                                 .addStatement("container = new $T($T.PX)",
//                                               DockLayoutPanel.class,
//                                               Style.Unit.class)
//                                 .addStatement("container.setSize(\"100%\", \"100%\")")
//                                 .addStatement("shell.add(container)")
//                                 .addCode("")
//                                 .addStatement("$T header = createNorth()",
//                                               Widget.class)
//                                 .addStatement("container.addNorth(header, 128)")
//                                 .addCode("")
//                                 .addStatement("$T footer = createSouth()",
//                                               Widget.class)
//                                 .addStatement("container.addSouth(footer, 42)")
//                                 .build());
//
//    // addNorth method
//    typeSpec.addMethod(MethodSpec.methodBuilder("createNorth")
//                                 .returns(Widget.class)
//                                 .addModifiers(Modifier.PRIVATE)
//                                 .addStatement("return new $T(\"That's the header area. Create your header here\")",
//                                               Label.class)
//                                 .build());
//    // addSouth method
//    typeSpec.addMethod(MethodSpec.methodBuilder("createSouth")
//                                 .returns(Widget.class)
//                                 .addModifiers(Modifier.PRIVATE)
//                                 .addStatement("return new $T(\"That's the footer area. Create your footer here\")",
//                                               Label.class)
//                                 .build());
//  }

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

    public NavigationSourceGenerator build() {
      return new NavigationSourceGenerator(this);
    }
  }
}
