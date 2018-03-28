package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.google.gwt.user.client.ui.Widget;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;

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

    this.presenterPackage = this.clientPackageJavaConform + ".ui.navigation";
    this.presenterPackageFile = new File(this.directoryJava + File.separator + "ui" + File.separator + "navigation");
    if (!this.presenterPackageFile.exists()) {
      this.presenterPackageFile.mkdirs();
    }

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
                                                             .returns(ClassName.get(Widget.class))
                                                             .addJavadoc("mvp4g2 does not know Widget-, Element- or any other GWT specific class. So, the\n" + "presenter have to manage the widget by themselves. The method will\n" + "enable the presenter to get the view. (In our case it is a\n" + "GWT widget)\n" + "\n" + "@return The navigation widget\n")
                                                             .build());

    typeSpec.addType(TypeSpec.interfaceBuilder("Presenter")
                             .addModifiers(Modifier.PUBLIC,
                                           Modifier.STATIC)
                             .addMethod(MethodSpec.methodBuilder("navigateTo")
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

  private void generateViewClass()
    throws GeneratorException {
//    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("NavigationView")
//                                        .addJavadoc(CodeBlock.builder()
//                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
//                                                             .build())
//                                        .addModifiers(Modifier.PUBLIC)
//                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
//                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                                            "INavigationView.Presenter")))
//
//                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                         "INavigationView"));
//    typeSpec.addField(FieldSpec.builder(ClassName.get(ResizeLayoutPanel.class),
//                                        "navigation",
//                                        Modifier.PRIVATE)
//                               .build());
//    typeSpec.addField(FieldSpec.builder(ClassName.get(DockLayoutPanel.class),
//                                        "container",
//                                        Modifier.PRIVATE)
//                               .build());
//    typeSpec.addField(FieldSpec.builder(ClassName.get(Widget.class),
//                                        "widget",
//                                        Modifier.PRIVATE)
//                               .build());
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
//                                 .addStatement("return navigation")
//                                 .build());
//    // createView method
//    this.createViewMethodForNavigation(typeSpec);
//    // setNavigation method
//    typeSpec.addMethod(MethodSpec.methodBuilder("setNavigation")
//                                 .addAnnotation(Override.class)
//                                 .addModifiers(Modifier.PUBLIC)
//                                 .addParameter(Widget.class,
//                                               "widget")
//                                 .addStatement("container.addWest(widget, 212)")
//                                 .build());
//    // setCenter method
//    typeSpec.addMethod(MethodSpec.methodBuilder("setCenter")
//                                 .addAnnotation(Override.class)
//                                 .addModifiers(Modifier.PUBLIC)
//                                 .addParameter(Widget.class,
//                                               "widget")
//                                 .beginControlFlow("widget != null")
//                                 .addStatement("if (widget.removeFromParent())")
//                                 .endControlFlow()
//                                 .addStatement("container.add(widget)")
//                                 .addStatement("this.widget = widget")
//                                 .build());
//
//    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
//                                         typeSpec.build())
//                                .build();
//    try {
//      javaFile.writeTo(new File(directoryJava,
//                                ""));
//    } catch (IOException e) {
//      throw new GeneratorException("Unable to write generated file: >>NavigationView" + "<< -> " + "exception: " + e.getMessage());
//    }
  }

  private void generatePresenterClass()
    throws GeneratorException {
//    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("NavigationPresenter")
//                                        .addJavadoc(CodeBlock.builder()
//                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
//                                                             .build())
//                                        .addModifiers(Modifier.PUBLIC)
//                                        .addAnnotation(AnnotationSpec.builder(Presenter.class)
//                                                                     .addMember("viewClass",
//                                                                                "$T.class",
//                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                                              "NavigationView"))
//                                                                     .addMember("viewInterface",
//                                                                                "$T.class",
//                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                                              "INavigationView"))
//                                                                     .build())
//                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
//                                                                              ClassName.get(this.clientPackageJavaConform,
//                                                                                            GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
//                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                                            "INavigationView")))
//                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                         "INavigationView.Presenter"))
//                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsNavigation.class),
//                                                                                     ClassName.get(this.clientPackageJavaConform,
//                                                                                                   GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
//                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.navigation",
//                                                                                                   "INavigationView")))
//                                        .addMethod(MethodSpec.constructorBuilder()
//                                                             .addModifiers(Modifier.PUBLIC)
//                                                             .build())
//                                        .addMethod(MethodSpec.methodBuilder("onBeforeEvent")
//                                                             .addModifiers(Modifier.PUBLIC)
//                                                             .addParameter(String.class,
//                                                                           "eventName")
//                                                             .build())
//                                        .addMethod(MethodSpec.methodBuilder("onSetContent")
//                                                             .addModifiers(Modifier.PUBLIC)
//                                                             .addAnnotation(EventHandler.class)
//                                                             .addParameter(Widget.class,
//                                                                           "widget")
//                                                             .addStatement("view.setContent(widget)")
//                                                             .build())
//                                        .addMethod(MethodSpec.methodBuilder("onSetNavigation")
//                                                             .addModifiers(Modifier.PUBLIC)
//                                                             .addAnnotation(EventHandler.class)
//                                                             .addParameter(Widget.class,
//                                                                           "widget")
//                                                             .addStatement("view.setNavigation(widget)")
//                                                             .build())
//                                        .addMethod(MethodSpec.methodBuilder("setNavigation")
//                                                             .addModifiers(Modifier.PUBLIC)
//                                                             .addAnnotation(Override.class)
//                                                             .addStatement("$T.get().add(view.asWidget)",
//                                                                           RootLayoutPanel.class)
//                                                             .build());


//    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
//                                         typeSpec.build())
//                                .build();
//    try {
//      javaFile.writeTo(new File(directoryJava,
//                                ""));
//    } catch (IOException e) {
//      throw new GeneratorException("Unable to write generated file: >>NavigationPresenter" + "<< -> " + "exception: " + e.getMessage());
//    }
  }

  private void createViewMethodForNavigation(TypeSpec.Builder typeSpec) {
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
//                                 .addStatement("navigation = new $T",
//                                               ClassName.get(ResizeLayoutPanel.class))
//                                 .addStatement("navigation.setSize(\"100%\", \"100%\")")
//                                 .addStatement("navigation.addResizeHandler($L)",
//                                               resizeHandler)
//                                 .addCode("")
//                                 .addStatement("container = new $T($T.PX)",
//                                               DockLayoutPanel.class,
//                                               Style.Unit.class)
//                                 .addStatement("container.setSize(\"100%\", \"100%\")")
//                                 .addStatement("navigation.add(container)")
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

    public NavigationSourceGenerator build() {
      return new NavigationSourceGenerator(this);
    }
  }
}
