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

import com.github.mvp4g.mvp4g2.core.history.NavigationEventCommand;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.squareup.javapoet.*;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.GeneratorUtils;
import de.gishmo.gwtbootstartermvp4g2.server.resource.generator.impl.AbstractPresenterViewSourceGenerator;

import javax.lang.model.element.Modifier;
import java.io.File;

public class PresenterViewGwtSourceGenerator
    extends AbstractPresenterViewSourceGenerator {

  private PresenterViewGwtSourceGenerator(Builder builder) {
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
    return TypeName.get(Widget.class);
  }

  @Override
  protected FieldSpec getContainerFieldSpec() {
    return FieldSpec.builder(SimplePanel.class,
                             "container",
                             Modifier.PRIVATE)
                    .build();
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
                                   .beginControlFlow("if (view.isDirty())")
                                   .beginControlFlow("if ($T.confirm(\"Do you really want to cancel?\"))",
                                                     Window.class)
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .nextControlFlow("else")
                                   .addStatement("event.fireEvent()")
                                   .endControlFlow()
                                   .build());
    }
  }

  @Override
  protected void createViewCreationMethod(TypeSpec.Builder typeSpec) {
    if (ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER == this.presenterData.getViewCreationMethod()) {
      typeSpec.addMethod(MethodSpec.methodBuilder("createView")
                                   .addJavadoc("Because we have told mvp4g2, that this presenter will create it's view\n" +
                                                   "(viewCreator = Presenter.VIEW_CREATION_METHOD.PRESENTER), we have to\n" +
                                                   "implement this method.\n" +
                                                   "\n" +
                                                   "This enables use, to use GWT.create instead of new (what the framework is doing!)\n" +
                                                   "\n" +
                                                   "@return a new instance of the view.\n")
                                   .addModifiers(Modifier.PUBLIC)
                                   .addAnnotation(Override.class)
                                   .returns(ClassName.get(this.clientPackageJavaConform +
                                                              ".ui." +
                                                              presenterData.getName()
                                                                           .toLowerCase(),
                                                          "I" + GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"))
                                   .addStatement("return $T.create($T.class)",
                                                 GWT.class,
                                                 ClassName.get(this.clientPackageJavaConform +
                                                                   ".ui." +
                                                                   presenterData.getName()
                                                                                .toLowerCase(),
                                                               GeneratorUtils.setFirstCharacterToUpperCase(this.presenterData.getName()) + "View"))
                                   .build());
    }
  }

  @Override
  protected void createViewMethod(TypeSpec.Builder typeSpec) {
    MethodSpec.Builder method = MethodSpec.methodBuilder("createView")
                                          .addAnnotation(Override.class)
                                          .addModifiers(Modifier.PUBLIC)
                                          .addStatement("container = new $T()",
                                                        ClassName.get(SimplePanel.class))
                                          .addStatement("$T label = new $T($S)",
                                                        Label.class,
                                                        Label.class,
                                                        this.presenterData.getName())
                                          .addStatement("label.getElement().getStyle().setMargin(12, $T.Unit.PX)",
                                                        Style.class)
                                          .addStatement("container.setWidget(label)",
                                                        Label.class,
                                                        this.presenterData.getName());
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

    public PresenterViewGwtSourceGenerator build() {
      return new PresenterViewGwtSourceGenerator(this);
    }
  }
}
