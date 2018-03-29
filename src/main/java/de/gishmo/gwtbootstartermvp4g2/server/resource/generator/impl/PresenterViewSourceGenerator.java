package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.history.IsNavigationConfirmation;
import com.github.mvp4g.mvp4g2.core.history.NavigationEventCommand;
import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
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
                                                             .addJavadoc("mvp4g2 does not know Widget-, Element- or any other GWT specific class. So, the\n" + "presenter have to manage the widget by themselves. The method will\n" + "enable the presenter to get the view. (In our case it is a\n" + "GWT widget)\n" + "\n" + "@return The shell widget\n")
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
                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"));
    typeSpec.addField(FieldSpec.builder(ClassName.get(SimplePanel.class),
                                        "container",
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
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generatePresenterClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "Prensenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Presenter.class)
                                                                     .addMember("viewClass",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                    .toLowerCase(),
                                                                                              GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"))
                                                                     .addMember("viewInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                    .toLowerCase(),
                                                                                              "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                                  .toLowerCase(),
                                                                                            "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                               .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View.Presenter"));
    if (presenterData.isConfirmation()) {
      typeSpec.addSuperinterface(IsNavigationConfirmation.class);
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

    MethodSpec.Builder onGotoMethod = MethodSpec.methodBuilder("onGoto" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()))
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
                                                   GeneratorUtils.setFirstCharacterToUperCase(presenterData.getName()))
                                     .build());
      }
    } else {
      if (presenterData.isShowPresenterAtStart()) {
        typeSpec.addMethod(MethodSpec.methodBuilder("onInitHistory")
                                     .addModifiers(Modifier.PUBLIC)
                                     .addAnnotation(EventHandler.class)
                                     .addStatement("eventBus.goto$L()",
                                                   GeneratorUtils.setFirstCharacterToUperCase(presenterData.getName()))
                                     .build());
      }
    }
    if (presenterData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("confirm")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .addParameter(ParameterSpec.builder(NavigationEventCommand.class,
                                                                       "event")
                                                              .build())
                                   .beginControlFlow("if (view.isDirty())")
                                   .beginControlFlow("if ($T.confirm(\"Do you really want to cancel?\"))",
                                                     Window.class)
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .nextControlFlow("else")
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .build());
    }

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "Presenter" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void createViewMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("createView")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("container = new $T()",
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("$T label = new $T($S)",
                                                        Label.class,
                                                        Label.class,
                                                        this.presenterData.getName())
                                          .addStatement("label.getElement().getStyle().setMargin(12, $T.Unit.PX)",
                                                        Style.class)
                                          .addStatement("container.setWidget(label)",
                                                        Label.class,
                                                        this.presenterData.getName());
    typeSpec.addMethod(method.build());
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
