/*
 * Copyright (C) 2016 Frank Hossfeld <frank.hossfeld@googlemail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.content.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;

public class PresenterEditorView
  extends Dialog
  implements IPresenterEditorView,
             Editor<PresenterData> {

  @Path("name")
  TextField name;
  @Path("historyName")
  TextField historyName;
  @Path("confirmation")
  CheckBox  confirmation;
  private VerticalLayoutContainer container;
  private IPresenterEditorView.Presenter presenter;

  private TextButton saveButton;
  private TextButton cancelButton;

  private Driver driver;

  public PresenterEditorView() {
    super();

    super.setWidth(512);
    super.setClosable(false);
    super.setPredefinedButtons();

    this.createWidgets();
    this.build();
    this.bind();
  }

  private void createWidgets() {
    this.name = new TextField();
    this.name.setAllowBlank(false);

    this.historyName = new TextField();

    this.confirmation = new CheckBox();
    this.confirmation.setBoxLabel("implement confirmation for this presenter");

    this.saveButton = new TextButton("Save");
    this.cancelButton = new TextButton("Cancel");
  }

  private void build() {
    super.setBodyStyle("padding: 12px; background-color: white;");

    this.container = new VerticalLayoutContainer();
    super.setWidget(this.container);

    FieldLabel fl01 = new FieldLabel(this.name,
                                     "Presenter Class Name");
    fl01.setLabelAlign(FormPanel.LabelAlign.TOP);
    this.container.add(fl01,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    FieldLabel fl02 = new FieldLabel(this.historyName,
                                     "History Name (Optional)");
    fl02.setLabelAlign(FormPanel.LabelAlign.TOP);
    this.container.add(fl02,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(this.confirmation,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));
    super.getButtonBar().add(this.saveButton);
    super.getButtonBar().add(this.cancelButton);
  }

  public void bind() {
    this.saveButton.addSelectHandler(selectEvent -> {
      if (isValid()) {
        getPresenter().doSave(this.driver.flush());
      } else {
        Window.alert("please, correct all errors!");
      }
      super.hide();
    });

    this.cancelButton.addSelectHandler(selectEvent -> super.hide());

    this.driver = GWT.create(Driver.class);
    this.driver.initialize(this);
  }

  private boolean isValid() {
    return this.name.isValid();
  }

  @Override
  public void edit(PresenterData model,
                   boolean isNew) {
    super.setHeading(isNew ? "Create New Presenter" : "Update Presenter");
    this.driver.edit(model);
  }

  @Override
  public Presenter getPresenter() {
    return presenter;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void show() {
    super.show();
  }

  interface Driver
    extends SimpleBeanEditorDriver<PresenterData, PresenterEditorView> {
  }
}
