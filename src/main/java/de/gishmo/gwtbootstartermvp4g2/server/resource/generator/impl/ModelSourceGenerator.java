/*
 * Copyright (c) 2018 - Frank Hossfeld
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 *
 */

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;

public class ModelSourceGenerator
  extends AbstractSourceGenerator {

  private String modelPackage;

  private ModelSourceGenerator(Builder builder) {
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

    this.modelPackage = this.clientPackageJavaConform + ".model";

    this.generateUUIDClass();
    this.generateModelClass();
  }

  private void generateUUIDClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("UUID")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .addField(FieldSpec.builder(ArrayTypeName.of(char.class),
                                                                    "CHARS",
                                                                    Modifier.PRIVATE,
                                                                    Modifier.STATIC,
                                                                    Modifier.FINAL)
                                                           .initializer("$S.toCharArray()",
                                                                        "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
                                                           .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PRIVATE)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("get")
                                                             .addModifiers(Modifier.PUBLIC,
                                                                           Modifier.STATIC)
                                                             .returns(String.class)
                                                             .addStatement("char[] uuid = new char[36]")
                                                             .addStatement("int r")
                                                             .addStatement("uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-'")
                                                             .addStatement(" uuid[14] = '4'")
                                                             .beginControlFlow("for (int i = 0; i < 36; i++)")
                                                             .beginControlFlow("if (uuid[i] == 0)")
                                                             .addStatement("r = (int) (Math.random() * 16)")
                                                             .addStatement("uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf]")
                                                             .endControlFlow()
                                                             .endControlFlow()
                                                             .addStatement("return new $T(uuid)",
                                                                           String.class)
                                                             .build());
    JavaFile javaFile = JavaFile.builder(this.modelPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>UUID<< -> " + "exception: " + e.getMessage());
    }
  }

  private void generateModelClass()
    throws GeneratorException {
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("MyModel")
                                        .addJavadoc(CodeBlock.builder()
                                                             .add(GeneratorConstants.COPYRIGHT_JAVA)
                                                             .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addField(FieldSpec.builder(String.class,
                                                                    "uuid",
                                                                    Modifier.PRIVATE)
                                                           .build())
                                        .addField(FieldSpec.builder(String.class,
                                                                    "activeScreen",
                                                                    Modifier.PRIVATE)
                                                           .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addStatement("uuid = $T.get()",
                                                                           ClassName.get(this.modelPackage,
                                                                                         "UUID"
                                                                           ))
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addParameter(ParameterSpec.builder(String.class,
                                                                                                 "activeScreen")
                                                                                        .build())
                                                             .addStatement("uuid = $T.get()",
                                                                           ClassName.get(this.modelPackage,
                                                                                         "UUID"))
                                                             .addStatement("this.activeScreen = activeScreen")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("getUuid")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(String.class)
                                                             .addStatement("return this.uuid")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("getActiveScreen")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .returns(String.class)
                                                             .addStatement("return this.activeScreen")
                                                             .build())
                                        .addMethod(MethodSpec.methodBuilder("setActiveScreen")
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .addParameter(ParameterSpec.builder(String.class,
                                                                                                 "uuid")
                                                                                        .build())
                                                             .addStatement("this.activeScreen = activeScreen")
                                                             .build());


    JavaFile javaFile = JavaFile.builder(this.modelPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>MyModel<< -> " + "exception: " + e.getMessage());
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

    public ModelSourceGenerator build() {
      return new ModelSourceGenerator(this);
    }
  }
}
