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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.elemento;

import java.io.File;

import javax.lang.model.element.Modifier;

import com.github.mvp4g.mvp4g2.core.history.NavigationEventCommand;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractPresenterViewSourceGenerator;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.Comments;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.Elements;

public class PresenterViewElementoSourceGenerator
    extends AbstractPresenterViewSourceGenerator {

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

  @Override
  protected TypeName getBaseElement() {
    return TypeName.get(Element.class);
  }

  @Override
  protected FieldSpec getContainerFieldSpec() {
    return FieldSpec.builder(HTMLDivElement.class,
                             "container",
                             Modifier.PRIVATE)
                    .build();
  }

  @Override
  protected FieldSpec getLabelFieldSpec() {
    return FieldSpec.builder(ClassName.get(HTMLElement.class),
                             "label",
                             Modifier.PRIVATE)
                    .build();
  }

  @Override
  protected void createViewCreationMethod(TypeSpec.Builder typeSpec) {
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("createView")
                                   .addJavadoc(Comments.CREATE_VIEW)
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(ClassName.get(this.clientPackageJavaConform +
                                                              ".ui." +
                                                              presenterData.getName()
                                                                           .toLowerCase(),
                                                          "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"))
                                   .addStatement("return new $T()",
                                                 ClassName.get(this.clientPackageJavaConform +
                                                                   ".ui." +
                                                                   presenterData.getName()
                                                                                .toLowerCase(),
                                                               GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"))
                                   .build());
    }
  }

  @Override
  protected void createConfirmMethod(TypeSpec.Builder typeSpec) {
    if (presenterData.isConfirmation()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("confirm")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .addParameter(ParameterSpec.builder(NavigationEventCommand.class,
                                                                       "event")
                                                              .build())
                                   .addJavadoc(Comments.CONFIRM)
                                   .addComment("check if there are changes")
                                   .beginControlFlow("if (view.isDirty())")
                                   .addComment("are you sure? :-)")
                                   .beginControlFlow("if ($T.window.confirm(\"Do you really want to cancel?\"))",
                                                     DomGlobal.class)
                                   .addComment("ok, but before, we check the entered data (type safety and required fields)")
                                   .beginControlFlow("if (view.isValid())")
                                   .addComment("move the data into the model")
                                   .addStatement("view.flush(model)")
                                   .addComment("navigate!")
                                   .addStatement("event.fireEvent()")
                                   .nextControlFlow("else")
                                   .addStatement("$T.window.alert(\"Please correct the error!\")",
                                                 DomGlobal.class)
                                   .endControlFlow()
                                   .endControlFlow()
                                   .nextControlFlow("else")
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .build());
    }
  }

  @Override
  protected String createEditStatement() {
    return "label.textContent = model.getActiveScreen()";
  }

  @Override
  protected void createViewMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("createView")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("label = $T.label().asElement()",
                                                        Elements.class)
                                          .addStatement("container = $T.div().add(label).asElement()",
                                                        Elements.class);
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
