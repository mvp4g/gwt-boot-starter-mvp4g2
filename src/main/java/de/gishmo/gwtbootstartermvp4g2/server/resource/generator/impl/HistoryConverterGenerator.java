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

import com.github.mvp4g.mvp4g2.core.history.IsHistoryConverter;
import com.github.mvp4g.mvp4g2.core.history.annotation.History;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorConstants;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;

public class HistoryConverterGenerator {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;
  private File                 directoryJava;
  private String               clientPackageJavaConform;

  private String historyPackage;

  private HistoryConverterGenerator(Builder builder) {
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

    this.historyPackage = this.clientPackageJavaConform + ".history";

    TypeSpec.Builder typeSpec = TypeSpec.classBuilder("DefaultHistoryConverter")
                                        .addAnnotation(AnnotationSpec.builder(History.class)
                                                                     .addMember("type",
                                                                                "$T.HistoryConverterType.SIMPLE",
                                                                                History.class)
                                                                     .build())
                                        .addModifiers(Modifier.PUBLIC)
                                        .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IsHistoryConverter.class),
                                                                                     ClassName.get(this.clientPackageJavaConform,
                                                                                                   GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS)))
                                        .addMethod(MethodSpec.constructorBuilder()
                                                             .addModifiers(Modifier.PUBLIC)
                                                             .build());

    MethodSpec.Builder convertFromTokenMethod = MethodSpec.methodBuilder("convertFromToken")
                                                          .addAnnotation(Override.class)
                                                          .addModifiers(Modifier.PUBLIC)
                                                          .addParameter(ParameterSpec.builder(String.class,
                                                                                              "historyName")
                                                                                     .build())
                                                          .addParameter(ParameterSpec.builder(String.class,
                                                                                              "param")
                                                                                     .build())
                                                          .addParameter(ParameterSpec.builder(ClassName.get(this.clientPackageJavaConform,
                                                                                                            GeneratorUtils.setFirstCharacterToUpperCase(this.mvp4g2GeneraterParms.getArtefactId()) + GeneratorConstants.EVENT_BUS),
                                                                                              "eventBus")
                                                                                     .build());

    convertFromTokenMethod.beginControlFlow("switch (historyName)");
    this.mvp4g2GeneraterParms.getPresenters()
                             .forEach(presenterData -> convertFromTokenMethod.addCode("case $S:\n",
                                                                                      "goto" + GeneratorUtils.setFirstCharacterToUpperCase(presenterData.getName()))
                                                                             .addStatement("eventBus.goto$L()",
                                                                                           GeneratorUtils.setFirstCharacterToUpperCase(presenterData.getName()))
                                                                             .addStatement("break"));
    convertFromTokenMethod.endControlFlow();
    typeSpec.addMethod(convertFromTokenMethod.build());

    typeSpec.addMethod(MethodSpec.methodBuilder("isCrawlable")
                                 .addAnnotation(Override.class)
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(TypeName.BOOLEAN)
                                 .addStatement("return false")
                                 .build());

    typeSpec.addMethod(MethodSpec.methodBuilder("convertToToken")
                                 .addModifiers(Modifier.PUBLIC)
                                 .returns(String.class)
                                 .addParameter(ParameterSpec.builder(String.class,
                                                                     "historyName")
                                                            .build())
                                 .addStatement("return \"\"")
                                 .build());

    JavaFile javaFile = JavaFile.builder(this.historyPackage,
                                         typeSpec.build())
                                .build();
    try {
      javaFile.writeTo(new File(directoryJava,
                                ""));
    } catch (IOException e) {
      throw new GeneratorException("Unable to write generated file: >>DefaultHistoryConverter<< -> exception: " + e.getMessage());
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

    public HistoryConverterGenerator build() {
      return new HistoryConverterGenerator(this);
    }
  }
}
