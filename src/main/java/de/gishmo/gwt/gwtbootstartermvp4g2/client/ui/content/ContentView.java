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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.content;

import java.util.ArrayList;
import java.util.List;

import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.model.PresenterDataProps;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.resources.ImageResources;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.Constants;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.UiUtils;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.DataConstants;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;

public class ContentView
  extends LazyReverseView<IContentView.Presenter>
  implements IContentView {

  private final static Margins MARGINS_LEFT = new Margins(0,
                                                          12,
                                                          0,
                                                          0);

  private final static Margins MARGINS_RIGHT = new Margins(0,
                                                           0,
                                                           0,
                                                           12);

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

  private HBoxLayoutContainer container;

  private VerticalLayoutContainer innerContainer;

  private VerticalLayoutContainer wrapperContainer;

  private StringComboBox gwtVersion;

  private TextField groupId;

  private TextField artifactId;

  private CheckBox applicationLoader;

  private CheckBox debug;

  private CheckBox historyOnStart;

  private CheckBox history;

  private ComboBox<WidgetLibrary> widgetLibrary;

  private ListStore<WidgetLibrary> widgetLibraryStore;

  private TextButton addButton;

  private TextButton editButton;

  private TextButton deleteButton;

  private PresenterDataProps presenterDataProps = GWT.create(PresenterDataProps.class);

  private ListStore<PresenterData> store;

  private Grid<PresenterData> grid;

  public ContentView() {
    super();
  }

  @Override
  public Widget asWidget() {
    return this.wrapperContainer;
  }

  @Override
  public void createView() {
    this.wrapperContainer = new VerticalLayoutContainer();
    this.wrapperContainer.setScrollMode(ScrollSupport.ScrollMode.AUTOY);
    this.wrapperContainer.setAdjustForScroll(true);
    this.wrapperContainer.getElement()
                         .getStyle()
                         .setBackgroundColor("white");

    this.container = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.TOP);
    this.wrapperContainer.add(container,
                              new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                             -1));
    this.container.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    this.container.getElement()
                  .getStyle()
                  .setBackgroundColor("white");

    this.innerContainer = new VerticalLayoutContainer();
    this.innerContainer.setWidth(Constants.CONTENT_WIDTH);
    this.container.add(this.innerContainer);

    this.createWidgets();
    this.createGrid();

    this.setUpProjectMetaData();
    this.setUpApplicationMetaData();
    this.setUpScreenMetaData();

    this.container.forceLayout();
  }

  private void createGrid() {
    store = new ListStore<>(presenterDataProps.key());

    ColumnConfig<PresenterData, String> ccName = new ColumnConfig<>(presenterDataProps.name(),
                                                                    450,
                                                                    "Screen");

    ColumnConfig<PresenterData, String> ccHistoryName = new ColumnConfig<>(presenterDataProps.historyName(),
                                                                           112,
                                                                           "History Name");
    ccHistoryName.setCell(new AbstractCell<String>() {
      @Override
      public void render(Context context,
                         String s,
                         SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s == null ? "-" : s));
      }
    });
    ccHistoryName.setFixed(true);

    ColumnConfig<PresenterData, Boolean> ccShowyPresenterOnStart = new ColumnConfig<>(presenterDataProps.showPresenterAtStart(),
                                                                                      100,
                                                                                      "Start Screen");
    ccShowyPresenterOnStart.setCell(new AbstractCell<Boolean>() {
      @Override
      public void render(Context context,
                         Boolean s,
                         SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s ? "Yes" : "No"));
      }
    });
    ccShowyPresenterOnStart.setFixed(true);
    ccShowyPresenterOnStart.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    ColumnConfig<PresenterData, Boolean> ccConfirmation = new ColumnConfig<>(presenterDataProps.confirmation(),
                                                                             100,
                                                                             "Confirmation");
    ccConfirmation.setCell(new AbstractCell<Boolean>() {
      @Override
      public void render(Context context,
                         Boolean s,
                         SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s ? "Yes" : "No"));
      }
    });
    ccConfirmation.setFixed(true);
    ccConfirmation.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    ColumnConfig<PresenterData, ViewCreationMethod> ccViewGenerationMethod = new ColumnConfig<>(presenterDataProps.viewCreationMethod(),
                                                                                                175,
                                                                                                "View Generation Method");
    ccViewGenerationMethod.setCell(new AbstractCell<ViewCreationMethod>() {
      @Override
      public void render(Context context,
                         ViewCreationMethod s,
                         SafeHtmlBuilder safeHtmlBuilder) {
        GWT.debugger();
        if (ViewCreationMethod.VIEW_CREATION_METHOD_FRAMEWORK == s) {
          safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString("Framework"));
        } else {
          safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString("Presenter"));
        }
      }
    });
    ccViewGenerationMethod.setFixed(true);
    ccViewGenerationMethod.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

    List<ColumnConfig<PresenterData, ?>> list = new ArrayList<>();
    list.add(ccName);
    list.add(ccHistoryName);
    list.add(ccShowyPresenterOnStart);
    list.add(ccConfirmation);
    list.add(ccViewGenerationMethod);
    ColumnModel<PresenterData> cm = new ColumnModel<>(list);
    grid = new Grid<>(store,
                      cm);
    grid.setSize("100%",
                 "192px");
    grid.getView()
        .setStripeRows(true);
    grid.setBorders(false);
    grid.getView()
        .setAutoExpandColumn(ccName);
    grid.getView()
        .setForceFit(true);
  }

  private void createWidgets() {
    this.addButton = new TextButton();
    this.addButton.setIcon(ImageResources.IMAGES.iconNew());
    this.editButton = new TextButton();
    this.editButton.setIcon(ImageResources.IMAGES.iconEdit2());
    this.editButton.setEnabled(false);
    this.deleteButton = new TextButton();
    this.deleteButton.setIcon(ImageResources.IMAGES.iconDelete());
    this.deleteButton.setEnabled(false);

    this.artifactId = new TextField();
    this.artifactId.setAllowBlank(false);
    this.artifactId.addValidator(new RegExValidator("^[-a-zA-Z0-9/.]*$",
                                                    "a - z, A - Z, 0 - 9, '-' and '.' allowed"));

    this.applicationLoader = new CheckBox();
    this.applicationLoader.setBoxLabel("Generate Apllication Loader class");

    this.debug = new CheckBox();
    this.debug.setBoxLabel("Generate Debug support (in development mode)");

    this.groupId = new TextField();
    this.groupId.setAllowBlank(false);
    this.groupId.addValidator(new RegExValidator("^[a-z0-9.]*$",
                                                 "a - z, 0 - 9 and '.' allowed"));

    this.history = new CheckBox();
    this.history.setBoxLabel("Application supports history handling");

    this.historyOnStart = new CheckBox();
    this.historyOnStart.setBoxLabel("Application handles history token on start (book marking)");
    historyOnStart.setEnabled(false);

    this.gwtVersion = new StringComboBox();
    this.gwtVersion.add(new ArrayList<String>() {{
      add(DataConstants.GWT_VERSION_2_8_2);
    }});
    this.gwtVersion.setValue(DataConstants.DEFAULT_GWT_VERSION);
    this.gwtVersion.setAllowBlank(false);

    this.widgetLibraryStore = new ListStore<>(widgetLibrary -> widgetLibrary.name());
    this.widgetLibraryStore.add(WidgetLibrary.ELEMENTO);
    this.widgetLibraryStore.add(WidgetLibrary.GWT);
    this.widgetLibraryStore.add(WidgetLibrary.GXT);
    this.widgetLibrary = new ComboBox<>(this.widgetLibraryStore,
                                        widgetLibrary -> widgetLibrary.getText());
    this.widgetLibrary.setForceSelection(true);
    this.widgetLibrary.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
  }

  private void setUpProjectMetaData() {
    ContentPanel cp = this.createContentPanel("Project Meta Data");

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    cp.setWidget(vlc);

    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
    vlc.add(flc01,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc01.add(this.createFielLabal("Group Id",
                                   this.groupId),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentView.MARGINS_LEFT));
    flc01.add(this.createFielLabal("Artifact Id",
                                   this.artifactId),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentView.MARGINS_RIGHT));

    CssFloatLayoutContainer flc02 = new CssFloatLayoutContainer();
    vlc.add(flc02,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc02.add(this.createFielLabal("GWT-Version",
                                   this.gwtVersion),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentView.MARGINS_LEFT));
    flc02.add(this.createFielLabal("Widget Set",
                                   this.widgetLibrary),
              new CssFloatLayoutContainer.CssFloatData(0.5,
                                                       ContentView.MARGINS_RIGHT));
  }

  private void setUpApplicationMetaData() {
    ContentPanel cp = this.createContentPanel("Application Meta Data");

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    cp.setWidget(vlc);

    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
    vlc.add(flc01,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc01.add(this.applicationLoader,
              new CssFloatLayoutContainer.CssFloatData(.5,
                                                       new Margins(0,
                                                                   12,
                                                                   0,
                                                                   0)));
    flc01.add(this.debug,
              new CssFloatLayoutContainer.CssFloatData(.5,
                                                       new Margins(0,
                                                                   0,
                                                                   0,
                                                                   12)));

    vlc.add(UiUtils.createDistanceContainer(24),
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           24));

    CssFloatLayoutContainer flc03 = new CssFloatLayoutContainer();
    vlc.add(flc03,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1));
    flc03.add(this.history,
              new CssFloatLayoutContainer.CssFloatData(.5,
                                                       new Margins(0,
                                                                   12,
                                                                   0,
                                                                   0)));
    flc03.add(this.historyOnStart,
              new CssFloatLayoutContainer.CssFloatData(.5,
                                                       new Margins(0,
                                                                   0,
                                                                   0,
                                                                   12)));
  }

  private void setUpScreenMetaData() {
    ContentPanel cp = this.createContentPanel("Screen Meta Data",
                                              false);

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    cp.setWidget(vlc);

    ToolBar toolBar = new ToolBar();
    toolBar.add(this.addButton);
    toolBar.add(new SeparatorToolItem());
    toolBar.add(this.editButton);
    toolBar.add(this.deleteButton);
    toolBar.add(new FillToolItem());
    toolBar.getElement()
           .getStyle()
           .setProperty("borderBottom",
                        "1px solid " + themeDetails.borderColor());
    vlc.add(toolBar,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           45));
    vlc.add(UiUtils.createDistanceContainer(12),
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           12));
    vlc.add(grid,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       0,
                                                                       0,
                                                                       0)));
    vlc.add(UiUtils.createDistanceContainer(12),
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           12));
  }

  private ContentPanel createContentPanel(String heading) {
    return this.createContentPanel(heading,
                                   true);
  }

  private ContentPanel createContentPanel(String heading,
                                          boolean bodyPadding) {
    ContentPanel cp = new ContentPanel();
    cp.setHeading(heading);
    if (bodyPadding) {
      cp.setBodyStyle("padding: 12px;");
    }
    cp.setBodyBorder(true);

    this.innerContainer.add(UiUtils.createDistanceContainer(6),
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           6));
    this.innerContainer.add(cp,
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           -1));
    this.innerContainer.add(UiUtils.createDistanceContainer(6),
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           6));

    return cp;
  }

  private FieldLabel createFielLabal(String label,
                                     Widget widget) {
    FieldLabel fl = new FieldLabel(widget);
    fl.setText(label);
    fl.setLabelAlign(FormPanel.LabelAlign.TOP);
    return fl;
  }

  public void bind() {
    this.history.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> valueChangeEvent) {
        if (valueChangeEvent.getValue()) {
          historyOnStart.setEnabled(true);
        } else {
          historyOnStart.setEnabled(false);
          historyOnStart.setValue(false);
        }
      }
    });

    this.grid.addRowClickHandler(rowClickEvent -> {
      editButton.setEnabled(this.store.get(rowClickEvent.getRowIndex()) != null);
      deleteButton.setEnabled(this.store.get(rowClickEvent.getRowIndex()) != null);
    });

    this.addButton.addSelectHandler(selectEvent -> getPresenter().doAdd());
    this.editButton.addSelectHandler(selectEvent -> getPresenter().doEdit(grid.getSelectionModel()
                                                                              .getSelectedItem()
                                                                              .clone()));
    this.deleteButton.addSelectHandler(selectEvent -> {
      ConfirmMessageBox confirm = new ConfirmMessageBox("Delete Screen?",
                                                        "Are you sure to delete the selected screen?");
      confirm.addDialogHideHandler(hideEvent -> {
        if (Dialog.PredefinedButton.YES == hideEvent.getHideButton()) {
          this.getPresenter().doDelete(grid.getSelectionModel()
                                           .getSelectedItem());
          this.store.remove(grid.getSelectionModel()
                                .getSelectedItem());
        }
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
      });
      confirm.show();
    });
  }

  @Override
  public void edit(Mvp4g2GeneraterParms model) {
    this.groupId.setValue(model.getGroupId());
    this.artifactId.setValue(model.getArtefactId());
    this.gwtVersion.setValue(model.getGwtVersion());
    this.widgetLibrary.setValue(model.getWidgetLibrary());
    this.applicationLoader.setValue(model.isApplicationLoader());
    this.debug.setValue(model.isDebug());
    this.history.setValue(model.isHistory(),
                          true);
    this.historyOnStart.setValue(model.isHistoryOnStart());

    this.store.clear();
    this.store.addAll(model.getPresenters());
  }

  @Override
  public void flush(Mvp4g2GeneraterParms model) {
    model.setGwtVersion(gwtVersion.getValue());
    model.setWidgetLibrary(widgetLibrary.getValue());

    model.setGroupId(this.groupId.getValue());
    model.setArtefactId(this.artifactId.getValue());

    model.setApplicationLoader(this.applicationLoader.getValue());
    model.setDebug(this.debug.getValue());

    model.setHistoryOnStart(this.historyOnStart.getValue());
    model.setHistory(this.history.getValue());
  }

  @Override
  public boolean isValid() {
    // TODO implement!
    return true;
  }

  @Override
  public void updateGrid(Mvp4g2GeneraterParms mvp4g2GeneraterParms) {
    this.store.clear();
    this.store.addAll(mvp4g2GeneraterParms.getPresenters());

    this.editButton.setEnabled(false);
    this.deleteButton.setEnabled(false);
  }
}
