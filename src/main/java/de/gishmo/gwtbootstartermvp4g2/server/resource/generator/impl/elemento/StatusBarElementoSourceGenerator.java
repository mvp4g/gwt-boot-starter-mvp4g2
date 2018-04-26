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

import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractStatusSourceGenerator;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.Elements;

import javax.lang.model.element.Modifier;
import java.io.File;

public class StatusBarElementoSourceGenerator
    extends AbstractStatusSourceGenerator {

  private StatusBarElementoSourceGenerator(Builder builder) {
    super();

    this.mvp4g2GeneraterParms = builder.mvp4g2GeneraterParms;
    this.directoryJava = builder.directoryJava;
    this.clientPackageJavaConform = builder.clientPackageJavaConform;
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
    return FieldSpec.builder(ClassName.get(HTMLElement.class),
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
  protected String getSetLabelValueStatement() {
    return "label.textContent = message";
  }

  @Override
  protected void createViewMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("createView")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("label = $T.div().asElement()",
                                                        Elements.class)
                                          .addStatement("container = $T.header().add(label.asElement()).asElement()",
                                                        Elements.class);
    typeSpec.addMethod(method.build());
  }

  public static class Builder {

    Mvp4g2GeneraterParms mvp4g2GeneraterParms;

    File directoryJava;

    String clientPackageJavaConform;

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
