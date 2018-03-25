package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;

public class PresenterSourceGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryJava;
  private String               clientPackageJavaConform;
  private String               presenterPackage;
  private File                 presenterPackageFile;
  private PresenterData presenterData;

  private PresenterSourceGenerator(Builder builder) {
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

    this.presenterPackage = this.clientPackageJavaConform + "ui." + presenterData.getName()
                                                                                 .toLowerCase() + ".";
    this.presenterPackageFile = new File(this.directoryJava + "ui" + File.separator + presenterData.getName()
                                                                                                   .toLowerCase() + File.separator);
    if (!this.presenterPackageFile.exists()) {
      this.presenterPackageFile.mkdirs();
    }


    this.generateIViewClass();
    this.generateViewClass();
    this.generatePresenterClass();


//    TypeSpec.Builder typeSpec = TypeSpec.interfaceBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.EVENT_BUS))
//                                        .addJavadoc(CodeBlock.builder()
//                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
//                                                             .build())
//                                        .addModifiers(Modifier.PUBLIC)
//                                        .addSuperinterface(ClassName.get(IsEventBus.class))
//                                        .addAnnotation(AnnotationSpec.builder(EventBus.class)
//                                                                     .addMember("shell",
//                                                                                "$T.class",
//                                                                                ClassName.get(this.clientPackageJavaConform + ".ui.shell",
//                                                                                              "ShellPresenter"))
//                                                                     .build());
//    if (mvp4g2GeneraterParms.isDebug()) {
//      typeSpec.addAnnotation(AnnotationSpec.builder(Debug.class)
//                                           .addMember("logLevel",
//                                                      "$T.class", ClassName.get(Debug.LogLevel.class)).build());
//    }
//
//
//
//
//    // TODO ...
//
//
//    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
//                                         typeSpec.build())
//                                .build();
//    try {
//      javaFile.writeTo(new File(directoryJava,
//                                ""));
//    } catch (IOException e) {
//      throw new GeneratorException("Unable to write generated file: >>" + GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.EVENT_BUS) + "<< -> " + "exception: " + e.getMessage());
//    }
  }

  private void generateIViewClass() {


  }

  private void generateViewClass() {
  }

  private void generatePresenterClass() {

  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File                 directoryJava;
    String               clientPackageJavaConform;
    PresenterData        presenterData;

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

    public PresenterSourceGenerator build() {
      return new PresenterSourceGenerator(this);
    }
  }
}
