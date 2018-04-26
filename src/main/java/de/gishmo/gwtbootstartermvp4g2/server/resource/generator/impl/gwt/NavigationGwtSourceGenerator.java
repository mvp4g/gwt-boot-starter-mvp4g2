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

package de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.gwt;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractNavigationSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.File;

public class NavigationGwtSourceGenerator
    extends AbstractNavigationSourceGenerator {

  private NavigationGwtSourceGenerator(Builder builder) {
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
                                                        SimplePanel.class)
                                          .addStatement("container.setSize(\"100%\",\"100%\")")
                                          .addStatement("container.getElement().getStyle().setBackgroundColor(\"snow\")")
                                          .addStatement("$T innerContainer = new $T()",
                                                        VerticalPanel.class,
                                                        VerticalPanel.class)
                                          .addStatement("container.setWidget(innerContainer)");

    this.mvp4g2GeneraterParms.getPresenters()
                             .forEach(presenterData -> {
                               TypeSpec clickHandler = TypeSpec.anonymousClassBuilder("")
                                                               .addSuperinterface(ClickHandler.class)
                                                               .addMethod(MethodSpec.methodBuilder("onClick")
                                                                                    .addAnnotation(Override.class)
                                                                                    .addModifiers(Modifier.PUBLIC)
                                                                                    .addParameter(ClickEvent.class,
                                                                                                  "event")
                                                                                    .addStatement("getPresenter().doNavigateTo($S)",
                                                                                                  presenterData.getName())
                                                                                    .build())
                                                               .build();
                               method.addStatement("$T anchor$L = new $T($S)",
                                                   Anchor.class,
                                                   presenterData.getName(),
                                                   Anchor.class,
                                                   presenterData.getName())
                                     .addStatement("anchor$L.addClickHandler($L)",
                                                   presenterData.getName(),
                                                   clickHandler)
                                     .addStatement("anchor$L.getElement().getStyle().setMargin(24, $T.PX)",
                                                   presenterData.getName(),
                                                   Style.Unit.class)
                                     .addStatement("innerContainer.add(anchor$L)",
                                                   presenterData.getName());
                             });

    typeSpec.addMethod(method.build());
  }

  @Override
  protected FieldSpec getContainerFieldSpec() {
    return FieldSpec.builder(ClassName.get(SimplePanel.class),
                             "container",
                             Modifier.PRIVATE)
                    .build();
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

    public NavigationGwtSourceGenerator build() {
      return new NavigationGwtSourceGenerator(this);
    }
  }
}
