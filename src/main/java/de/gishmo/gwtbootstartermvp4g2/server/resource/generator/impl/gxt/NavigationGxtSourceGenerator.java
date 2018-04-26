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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gxt;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractNavigationSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.File;

public class NavigationGxtSourceGenerator
    extends AbstractNavigationSourceGenerator {

  private NavigationGxtSourceGenerator(Builder builder) {
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
    return TypeName.get(Widget.class);
  }

  @Override
  protected void createViewMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("createView")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("container = new $T()",
                                                        VerticalLayoutContainer.class);

    this.mvp4g2GeneraterParms.getPresenters()
                             .forEach(presenterData -> {
                               TypeSpec selectHandler = TypeSpec.anonymousClassBuilder("")
                                                                .addSuperinterface(SelectEvent.SelectHandler.class)
                                                                .addMethod(MethodSpec.methodBuilder("onSelect")
                                                                                     .addAnnotation(Override.class)
                                                                                     .addModifiers(Modifier.PUBLIC)
                                                                                     .addParameter(SelectEvent.class,
                                                                                                   "event")
                                                                                     .addStatement("getPresenter().doNavigateTo($S)",
                                                                                                   presenterData.getName())
                                                                                     .build())
                                                                .build();
                               method.addStatement("$T textButton$L = new $T($S)",
                                                   TextButton.class,
                                                   presenterData.getName(),
                                                   TextButton.class,
                                                   presenterData.getName())
                                     .addStatement("textButton$L.addSelectHandler($L)",
                                                   presenterData.getName(),
                                                   selectHandler)
                                     .addStatement("container.add(textButton$L, new $T(1, -1, new $T(12)))",
                                                   presenterData.getName(),
                                                   VerticalLayoutContainer.VerticalLayoutData.class,
                                                   Margins.class);
                             });

    typeSpec.addMethod(method.build());
  }

  @Override
  protected FieldSpec getContainerFieldSpec() {
    return FieldSpec.builder(VerticalLayoutContainer.class,
                             "container",
                             Modifier.PRIVATE)
                    .build();
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

    public NavigationGxtSourceGenerator build() {
      return new NavigationGxtSourceGenerator(this);
    }
  }
}
