package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.Elements;

public class StatusBarElementoSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private File directoryJava;

  private String clientPackageJavaConform;

  private String presenterPackage;

  private StatusBarElementoSourceGenerator(Builder builder) {
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
    this.presenterPackage = this.clientPackageJavaConform + ".ui.statusbar";

    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();
  }

  private void generateIViewClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder("IStatusbarView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsLazyReverseView.class),
                                                                                     ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                                   "IStatusbarView.Presenter")))
                                        .addMethod(MethodSpec.methodBuilder("asWidget")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.ABSTRACT)
                                                             .returns(ClassName.get(Element.class))
                                                             .addJavadoc(GeneratorConstants.AS_WIDGET_TEXT)
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
      throw new GeneratorException("Unable to write generated file: >>IStatusbarView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateViewClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("StatusbarView")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .superclass(ParameterizedTypeName.get(ClassName.get(LazyReverseView.class),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                            "IStatusbarView.Presenter")))

                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                         "IStatusbarView"));
    typeSpec.addField(FieldSpec.builder(ClassName.get(HTMLElement.class),
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

    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>StatusbarView" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generatePresenterClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("StatusbarPresenter")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addAnnotation(AnnotationSpec.builder(Presenter.class)
                                                                     .addMember("viewClass",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                              "StatusbarView"))
                                                                     .addMember("viewInterface",
                                                                                "$T.class",
                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                              "IStatusbarView"))
                                                                     .build())
                                        .superclass(ParameterizedTypeName.get(ClassName.get(AbstractPresenter.class),
                                                                              ClassName.get(this.clientPackageJavaConform,
                                                                                            GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                              ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                                            "IStatusbarView")))
                                        .addSuperinterface(ClassName.get(this.clientPackageJavaConform + ".ui.statusbar",
                                                                         "IStatusbarView.Presenter"))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("bind")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addAnnotation(Override.class)
                                                             .addStatement("eventBus.setStatusbar(view.asWidget())")
                                                             .build());


    JavaFile javaFile = JavaFile.builder(this.presenterPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>StatusbarPresenter" + "<< -> " + "exception: " + e.getMessage());
    }
  }

  private void createViewMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("createView")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("container = $T.header().add(\"That's the footer area. Create your footer here\").asElement()",
                                                        Elements.class);
    typeSpec.addMethod(method.build());
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

    public StatusBarElementoSourceGenerator build() {
      return new StatusBarElementoSourceGenerator(this);
    }
  }
}