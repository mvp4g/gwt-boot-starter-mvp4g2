package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.history.IsNavigationConfirmation;
import com.github.mvp4g.mvp4g2.core.history.NavigationEventCommand;
import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.IsViewCreator;
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
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import org.jboss.gwt.elemento.core.Elements;

public class PresenterViewElementoSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private File directoryJava;

  private String clientPackageJavaConform;

  private String presenterPackage;

  private PresenterData presenterData;

  private PresenterViewElementoSourceGenerator(Builder builder) {
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
                                                             .returns(ClassName.get(Element.class))
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
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLDivElement.class),
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
                                 .returns(Element.class)
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
    AnnotationSpec.Builder presenterAnnotation = AnnotationSpec.builder(Presenter.class)
                                                               .addMember("viewClass",
                                                                          "$T.class",
                                                                          ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                              .toLowerCase(),
                                                                                        GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"))
                                                               .addMember("viewInterface",
                                                                          "$T.class",
                                                                          ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                                              .toLowerCase(),
                                                                                        "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"));
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      presenterAnnotation.addMember("viewCreator",
                                    "$T.VIEW_CREATION_METHOD.PRESENTER",
                                    Presenter.class);
    }
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "Prensenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(presenterAnnotation.build())
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
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      typeSpec.addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsViewCreator.class),
                                                           ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                               .toLowerCase(),
                                                                         "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View")));
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
                                   .beginControlFlow("if ($T.window.confirm(\"Do you really want to cancel?\"))",
                                                     DomGlobal.class)
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .nextControlFlow("else")
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .build());
    }
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("createView")
                                   .addJavadoc("Because we have told mvp4g2, that this presenter will create it's view\n" +
                                               "(viewCreator = Presenter.VIEW_CREATION_METHOD.PRESENTER), we have to\n" +
                                               "implement this method.\n" +
                                               "\n" +
                                               "This enables use, to use GWT.create or something else instead of new (what the framework is doing!)\n" +
                                               "Because this implementation does not know 'GWT.create()' we will do a simple new ... \n" +
                                               "\n" +
                                               "@return a new instance of the view.\n")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                .toLowerCase(),
                                                          "I" + GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"))
                                   .addStatement("return new $T()",
                                                 ClassName.get(this.clientPackageJavaConform + ".ui." + presenterData.getName()
                                                                                                                     .toLowerCase(),
                                                               GeneratorUtils.setFirstCharacterToUperCase(this.presenterData.getName()) + "View"))
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
                                          .addStatement("container = $T.div().add($T.label().textContent($S).style(\"margin: 12px; font-size: medium;\").asElement()).asElement()",
                                                        Elements.class,
                                                        Elements.class,
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

    public PresenterViewElementoSourceGenerator build() {
      return new PresenterViewElementoSourceGenerator(this);
    }
  }
}