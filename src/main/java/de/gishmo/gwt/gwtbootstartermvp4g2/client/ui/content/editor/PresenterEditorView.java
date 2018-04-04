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
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.form.CheckBox;
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
  CheckBox confirmation;


  private IPresenterEditorView.Presenter presenter;

  private Driver driver;

//  private final static Margins MARGINS_LEFT  = new Margins(0,
//                                                           12,
//                                                           0,
//                                                           0);
//  private final static Margins MARGINS_RIGHT = new Margins(0,
//                                                           0,
//                                                           0,
//                                                           12);
//
//  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);
//
//  private HBoxLayoutContainer     container;
//  private VerticalLayoutContainer innerContainer;
//  private VerticalLayoutContainer wrapperContainer;
//
//  private ContentPanel            contentPanel;
//  private VerticalLayoutContainer cpInnerContainer;
//
//  private StringComboBox gwtVersion;
//
//  private TextField groupId;
//  private TextField artifactId;
//
//  private CheckBox applicationLoader;
//  private CheckBox debug;
//
//  private CheckBox historyOnStart;
//  private CheckBox history;
//
//  private TextButton addButton;
//  private TextButton editButton;
//  private TextButton deleteButton;
//
//  private PresenterDataProps presenterDataProps = GWT.create(PresenterDataProps.class);
//  private ListStore<PresenterData> store;
//  private Grid<PresenterData>      grid;

  public PresenterEditorView() {
    super();

    this.createWidgets();

    this.bind();
  }
//
//  @Override
//  public Widget asWidget() {
//    return this.wrapperContainer;
//  }
//
//  @Override
//  public void createView() {
//    this.wrapperContainer = new VerticalLayoutContainer();
//    this.wrapperContainer.setScrollMode(ScrollSupport.ScrollMode.AUTOY);
//    this.wrapperContainer.setAdjustForScroll(true);
//    this.wrapperContainer.getElement()
//                         .getStyle()
//                         .setBackgroundColor("white");
//
//    this.container = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.TOP);
//    this.wrapperContainer.add(container,
//                              new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                                             -1));
//    this.container.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
//    this.container.getElement()
//                  .getStyle()
//                  .setBackgroundColor("white");
//
//    this.innerContainer = new VerticalLayoutContainer();
//    this.innerContainer.setWidth(Constants.CONTENT_WIDTH);
//    this.container.add(this.innerContainer);
//
//    this.createWidgets();
//    this.createGrid();
//
//    this.setUpProjectMetaData();
//    this.setUpApplicationMetaData();
//    this.setUpScreenMetaData();
//
//    this.container.forceLayout();
//  }
//
//  private void createGrid() {
//    store = new ListStore<>(presenterDataProps.key());
//
//    ColumnConfig<PresenterData, String> ccName = new ColumnConfig<>(presenterDataProps.name(),
//                                                                    450,
//                                                                    "Name");
//    ColumnConfig<PresenterData, String> ccHistoryName = new ColumnConfig<>(presenterDataProps.historyName(),
//                                                                           125,
//                                                                           "History Name");
//    ccHistoryName.setCell(new AbstractCell<String>() {
//      @Override
//      public void render(Context context,
//                         String s,
//                         SafeHtmlBuilder safeHtmlBuilder) {
//        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s == null ? "-" : s));
//      }
//    });
//    ccHistoryName.setFixed(true);
//
//    ColumnConfig<PresenterData, Boolean> ccConfirmation = new ColumnConfig<>(presenterDataProps.confirmation(),
//                                                                      125,
//                                                                      "Confirmation");
//    ccConfirmation.setCell(new AbstractCell<Boolean>() {
//      @Override
//      public void render(Context context,
//                         Boolean s,
//                         SafeHtmlBuilder safeHtmlBuilder) {
//        safeHtmlBuilder.append(SafeHtmlUtils.fromTrustedString(s ? "Yes" : "No"));
//      }
//    });
//    ccConfirmation.setFixed(true);
//    ccConfirmation.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
//
//
//    List<ColumnConfig<PresenterData, ?>> list = new ArrayList<>();
//    list.add(ccName);
//    list.add(ccHistoryName);
//    list.add(ccConfirmation);
//    ColumnModel<PresenterData> cm = new ColumnModel<>(list);
//    grid = new Grid<>(store,
//                      cm);
//    grid.setSize("100%",
//                 "256px");
//    grid.getView()
//        .setStripeRows(true);
//    grid.setBorders(false);
//    grid.getView()
//        .setAutoExpandColumn(ccName);
//    grid.getView()
//        .setForceFit(true);
//  }

  private void createWidgets() {
//    this.addButton = new TextButton();
//    this.addButton.setIcon(ImageResources.IMAGES.iconNew());
//    this.editButton = new TextButton();
//    this.editButton.setIcon(ImageResources.IMAGES.iconEdit2());
//    this.editButton.setEnabled(false);
//    this.deleteButton = new TextButton();
//    this.deleteButton.setIcon(ImageResources.IMAGES.iconDelete());
//    this.deleteButton.setEnabled(false);
//
//    this.artifactId = new TextField();
//    this.artifactId.setAllowBlank(false);
//    this.artifactId.addValidator(new RegExValidator("^[a-zA-Z0-9.]*$",
//                                                    "a - z, A - Z, 0 - 9 and '.' allowed"));
//
//    this.applicationLoader = new CheckBox();
//    this.applicationLoader.setBoxLabel("Generate Apllication Loader class");
//
//    this.debug = new CheckBox();
//    this.debug.setBoxLabel("Generate Debug support (in development mode)");
//
//    this.groupId = new TextField();
//    this.groupId.setAllowBlank(false);
//    this.groupId.addValidator(new RegExValidator("^[a-z0-9.]*$",
//                                                 "a - z, 0 - 9 and '.' allowed"));
//
//
//    this.history = new CheckBox();
//    this.history.setBoxLabel("Check, if the application supports history handling");
//
//    this.historyOnStart = new CheckBox();
//    this.historyOnStart.setBoxLabel("Check, if the application handles history on start");
//    historyOnStart.setEnabled(false);
//
//
//    this.gwtVersion = new StringComboBox();
//    this.gwtVersion.add(new ArrayList<String>() {{
//      add(DataConstants.GWT_VERSION_2_8_2);
//    }});
//    this.gwtVersion.setValue(DataConstants.DEFAULT_GWT_VERSION);
//    this.gwtVersion.setAllowBlank(false);
//
  }

//  private void setUpProjectMetaData() {
//    ContentPanel cp = this.createContentPanel("Project Meta Data");
//
//    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//    cp.setWidget(vlc);
//
//    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
//    vlc.add(flc01,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//    flc01.add(this.createFielLabal("Group Id",
//                                   this.groupId),
//              new CssFloatLayoutContainer.CssFloatData(0.5,
//                                                       PresenterEditorView.MARGINS_LEFT));
//    flc01.add(this.createFielLabal("Artifact Id",
//                                   this.artifactId),
//              new CssFloatLayoutContainer.CssFloatData(0.5,
//                                                       PresenterEditorView.MARGINS_RIGHT));
//
//    CssFloatLayoutContainer flc02 = new CssFloatLayoutContainer();
//    vlc.add(flc02,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//    flc02.add(this.createFielLabal("GWT-Version",
//                                   this.gwtVersion),
//              new CssFloatLayoutContainer.CssFloatData(0.5,
//                                                       PresenterEditorView.MARGINS_LEFT));
//  }
//
//  private void setUpApplicationMetaData() {
//    ContentPanel cp = this.createContentPanel("Application Meta Data");
//
//    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//    cp.setWidget(vlc);
//
//    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
//    vlc.add(flc01,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//    flc01.add(this.applicationLoader,
//              new CssFloatLayoutContainer.CssFloatData(1));
//
//    vlc.add(this.addDistance("12px"),
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           12));
//
//    CssFloatLayoutContainer flc02 = new CssFloatLayoutContainer();
//    vlc.add(flc02,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//    flc02.add(this.debug,
//              new CssFloatLayoutContainer.CssFloatData(1));
//
//    vlc.add(this.addDistance("24px"),
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           24));
//
//    CssFloatLayoutContainer flc03 = new CssFloatLayoutContainer();
//    vlc.add(flc03,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//    flc03.add(this.history,
//              new CssFloatLayoutContainer.CssFloatData(1));
//    vlc.add(this.addDistance("12px"),
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           12));
//
//    CssFloatLayoutContainer flc04 = new CssFloatLayoutContainer();
//    vlc.add(flc04,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//    flc04.add(this.historyOnStart,
//              new CssFloatLayoutContainer.CssFloatData(1));
//  }
//
//  private void setUpScreenMetaData() {
//    ContentPanel cp = this.createContentPanel("Screen Meta Data",
//                                              false);
//
//    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
//    cp.setWidget(vlc);
//
//    ToolBar toolBar = new ToolBar();
//    toolBar.add(this.addButton);
//    toolBar.add(new SeparatorToolItem());
//    toolBar.add(this.editButton);
//    toolBar.add(this.deleteButton);
//    toolBar.add(new FillToolItem());
//    toolBar.getElement()
//           .getStyle()
//           .setProperty("borderBottom",
//                        "1px solid " + themeDetails.borderColor());
//    vlc.add(toolBar,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           45));
//    vlc.add(addDistance("12px"),
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           12));
//    vlc.add(grid,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1,
//                                                           new Margins(0,
//                                                                       0,
//                                                                       0,
//                                                                       0)));
//    vlc.add(addDistance("12px"),
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           12));
//
//
////    CssFloatLayoutContainer flc01 = new CssFloatLayoutContainer();
////    vlc.add(flc01,
////            new VerticalLayoutContainer.VerticalLayoutData(1,
////                                                           -1));
////    flc01.add(this.applicationLoader,
////              new CssFloatLayoutContainer.CssFloatData(1));
////
////    vlc.add(this.addDistance("12px"),
////            new VerticalLayoutContainer.VerticalLayoutData(1,
////                                                           12));
////
////    CssFloatLayoutContainer flc02 = new CssFloatLayoutContainer();
////    vlc.add(flc02,
////            new VerticalLayoutContainer.VerticalLayoutData(1,
////                                                           -1));
////    flc02.add(this.history,
////              new CssFloatLayoutContainer.CssFloatData(1));
////    vlc.add(this.addDistance("12px"),
////            new VerticalLayoutContainer.VerticalLayoutData(1,
////                                                           12));
////
////    CssFloatLayoutContainer flc03 = new CssFloatLayoutContainer();
////    vlc.add(flc03,
////            new VerticalLayoutContainer.VerticalLayoutData(1,
////                                                           -1));
////    flc03.add(this.historyOnStart,
////              new CssFloatLayoutContainer.CssFloatData(1));
////    flc01.add(this.createFielLabal("Artifact Id",
////                                   this.artifactId),
////              new CssFloatLayoutContainer.CssFloatData(0.5,
////                                                       ContentView.MARGINS));
////
////    CssFloatLayoutContainer flc02 = new CssFloatLayoutContainer();
////    vlc.add(flc02,
////            new VerticalLayoutContainer.VerticalLayoutData(1,
////                                                           -1));
////    flc02.add(this.createFielLabal("GWT-Version",
////                                   this.gwtVersion),
////              new CssFloatLayoutContainer.CssFloatData(0.5,
////                                                       ContentView.MARGINS));
//  }
//
//  private ContentPanel createContentPanel(String heading) {
//    return this.createContentPanel(heading,
//                                   true);
//  }
//
//  private ContentPanel createContentPanel(String heading,
//                                          boolean bodyPadding) {
//    ContentPanel cp = new ContentPanel();
//    cp.setHeading(heading);
//    if (bodyPadding) {
//      cp.setBodyStyle("padding: 12px;");
//    }
//    cp.setBodyBorder(true);
//
//    this.innerContainer.add(addDistance("6px"),
//                            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                                           6));
//    this.innerContainer.add(cp,
//                            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                                           -1));
//    this.innerContainer.add(addDistance("6px"),
//                            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                                           6));
//
//    return cp;
//  }
//
//  private FieldLabel createFielLabal(String label,
//                                     Widget widget) {
//    FieldLabel fl = new FieldLabel(widget);
//    fl.setText(label);
//    fl.setLabelAlign(FormPanel.LabelAlign.TOP);
//    return fl;
//  }
//
//  private Widget addDistance(String height) {
//    SimplePanel sp = new SimplePanel();
//    sp.setHeight(height);
//    return sp;
//  }

  public void bind() {
//    this.history.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
//      @Override
//      public void onValueChange(ValueChangeEvent<Boolean> valueChangeEvent) {
//        if (valueChangeEvent.getValue()) {
//          historyOnStart.setEnabled(true);
//        } else {
//          historyOnStart.setEnabled(false);
//          historyOnStart.setValue(false);
//        }
//      }
//    });

    this.driver = GWT.create(Driver.class);
    this.driver.initialize(this);
  }

//
//  @Override
//  public void edit(Mvp4g2GeneraterParms model) {
//    this.groupId.setValue(model.getGroupId());
//    this.artifactId.setValue(model.getArtefactId());
//    this.applicationLoader.setValue(model.isApplicationLoader());
//    this.debug.setValue(model.isDebug());
//    this.history.setValue(model.isHistory(),
//                          true);
//    this.historyOnStart.setValue(model.isHistoryOnStart());
//
//    this.store.clear();
//    this.store.addAll(model.getPresenters());
//  }
//
//  @Override
//  public void flush(Mvp4g2GeneraterParms model) {
//    model.setGwtVersion(gwtVersion.getValue());
//
//    model.setGroupId(this.groupId.getValue());
//    model.setArtefactId(this.artifactId.getValue());
//
//    model.setApplicationLoader(this.applicationLoader.getValue());
//    model.setDebug(this.debug.getValue());
//
//    model.setHistoryOnStart(this.historyOnStart.getValue());
//    model.setHistory(this.history.getValue());
//  }
//
//  @Override
//  public boolean isValid() {
//    // TODO implement!
//    return true;
//  }
//
//  private void setThemeFontDetails(Element el) {
//    el.getStyle()
//      .setProperty("fontSize",
//                   themeDetails.panel()
//                               .font()
//                               .size());
//    el.getStyle()
//      .setProperty("fontFamily",
//                   themeDetails.panel()
//                               .font()
//                               .family());
//    el.getStyle()
//      .setProperty("color",
//                   themeDetails.panel()
//                               .font()
//                               .color());
//  }

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
