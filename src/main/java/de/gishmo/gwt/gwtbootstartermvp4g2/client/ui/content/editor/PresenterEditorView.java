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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.content.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;

public class PresenterEditorView
  extends Dialog
  implements IPresenterEditorView,
             Editor<PresenterData> {

  @Path("name")
  TextField                    name;
  @Path("historyName")
  TextField                    historyName;
  @Path("confirmation")
  CheckBox                     confirmation;
  @Path("viewCreationMethod")
  ComboBox<ViewCreationMethod> viewCreationMethod;
  private ListStore<ViewCreationMethod> viewGenerationMethodListStore;

  private VerticalLayoutContainer        container;
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
    this.name.addValidator(new RegExValidator("^[a-zA-Z0-9.]*$",
                                              "a - z, A - Z, 0 - 9 and '.' allowed"));

    this.historyName = new TextField();
    this.name.addValidator(new RegExValidator("^[a-zA-Z0-9]*$",
                                              "a - z, A - Z and 0 - 9 allowed"));

    this.confirmation = new CheckBox();
    this.confirmation.setBoxLabel("implement confirmation for this presenter");

    this.viewGenerationMethodListStore = new ListStore<>(viewCreationMethod -> viewCreationMethod.name());
    this.viewGenerationMethodListStore.add(ViewCreationMethod.VIEW_CREATION_METHOD_FRAMEWORK);
    this.viewGenerationMethodListStore.add(ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER);
    this.viewCreationMethod = new ComboBox<>(this.viewGenerationMethodListStore,
                                               viewCreationMethod -> viewCreationMethod.getText());
    this.viewCreationMethod.setForceSelection(true);
    this.viewCreationMethod.setTriggerAction(ComboBoxCell.TriggerAction.ALL);

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

    FieldLabel fl03 = new FieldLabel(this.viewCreationMethod,
                                     "View Creation Method");
    fl03.setLabelAlign(FormPanel.LabelAlign.TOP);
    this.container.add(fl03,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));

    this.container.add(this.confirmation,
                       new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                      -1));
    super.getButtonBar()
         .add(this.saveButton);
    super.getButtonBar()
         .add(this.cancelButton);
  }

  public void bind() {
    this.saveButton.addSelectHandler(selectEvent -> {
      if (isValid()) {
        getPresenter().doSave(this.driver.flush());
        super.hide();
      } else {
        Window.alert("please, correct all errors!");
      }
    });

    this.cancelButton.addSelectHandler(selectEvent -> super.hide());

    this.driver = GWT.create(Driver.class);
    this.driver.initialize(this);
  }

  private boolean isValid() {
    boolean nameIsValid = this.name.isValid();
    boolean historyNameIsValid = this.historyName.isValid();
    if (nameIsValid && historyNameIsValid) {
      if (getPresenter().doIsHistoryNameAlreadyUsed(driver.flush())) {
        this.historyName.markInvalid("History Name must be unique!");
      }
    }
    return nameIsValid && historyNameIsValid;
  }

  @Override
  public void clearView() {
    this.name.setValue("");
    this.name.clearInvalid();

    this.historyName.setValue("");
    this.historyName.clearInvalid();

    this.confirmation.setValue(false);
    this.confirmation.clearInvalid();
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
    this.name.focus();
  }

  interface Driver
    extends SimpleBeanEditorDriver<PresenterData, PresenterEditorView> {
  }
}
