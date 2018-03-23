package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import com.github.mvp4g.mvp4g2.core.application.IsApplication;
import com.github.mvp4g.mvp4g2.core.application.annotation.Application;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

import java.io.File;
import java.io.IOException;

public class ApplicationSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryJava;
  private String               clientPackageJavaConform;

  private ApplicationSourceGenerator(Builder builder) {
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

    AnnotationSpec.Builder annotation = AnnotationSpec.builder(Application.class)
                                                      .addMember("eventBus",
                                                                 this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.EVENT_BUS + ".class");
    if (this.mvp4g2GeneraterParms.isApplicationLoader()) {
      annotation.addMember("loader",
                           this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.LOADER + ".class");
    }
    if (this.mvp4g2GeneraterParms.isHistoryOnStart()) {
      annotation.addMember("historyOnStart",
                           "true");
    }

    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.APPLICAITON))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addSuperinterface(ClassName.get(IsApplication.class))
                                        .addAnnotation(annotation.build());

    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.APPLICAITON + "<< -> exception: " + e.getMessage());
    }
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

    public ApplicationSourceGenerator build() {
      return new ApplicationSourceGenerator(this);
    }
  }
}
