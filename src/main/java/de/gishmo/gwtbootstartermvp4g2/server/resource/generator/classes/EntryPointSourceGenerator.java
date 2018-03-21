package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.classes;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.google.gwt.core.client.EntryPoint;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

public class EntryPointSourceGenerator {


  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File               directoryJava;
  private String               clientPackageJavaConform;

  private EntryPointSourceGenerator(Builder builder) {
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

    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(GeneratorUtils.setFirstCharacterToUperCase(this.mvp4g2GeneraterParms.getArtefactId()))
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ClassName.get(EntryPoint.class))
                                        .addMethod(MethodSpec.methodBuilder("onModuleLoad")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addStatement("$T application = new $LImpl()",
                                                                           ClassName.get(this.clientPackageJavaConform,
                                                                                         this
                                                                                           .mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.APPLICAITON),
                                                                           this.mvp4g2GeneraterParms.getArtefactId() + GeneratorConstants.APPLICAITON)
                                                             .addStatement("application.run()")
                                                             .build());

    JavaFile javaFile = JavaFile.builder(this.clientPackageJavaConform,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>" + this.mvp4g2GeneraterParms.getArtefactId() + "<< -> exception: " + e.getMessage());
    }
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;
    File               directoryJava;
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

    public EntryPointSourceGenerator build() {
      return new EntryPointSourceGenerator(this);
    }
  }
}
