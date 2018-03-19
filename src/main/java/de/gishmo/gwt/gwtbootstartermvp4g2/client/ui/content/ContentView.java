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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.content;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.Constants;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.DataConstants;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.mvp4g2.core.ui.LazyReverseView;

import java.util.ArrayList;

public class ContentView
  extends LazyReverseView<IContentView.Presenter>
  implements IContentView {


  private HBoxLayoutContainer     container;
  private VerticalLayoutContainer innerContainer;

  private ContentPanel            contentPanel;
  private VerticalLayoutContainer cpInnerContainer;

  private StringComboBox gwtVersion;

  private TextField groupId;
  private TextField artifactId;

  private CheckBox       applicationLoader;

  private CheckBox       historyOnStart;
  private StringComboBox history;
  private CheckBox       usingHistoryNames;

  public ContentView() {
    super();
  }

  @Override
  public Widget asWidget() {
    return this.container;
  }

  @Override
  public void createView() {
    this.container = new HBoxLayoutContainer(HBoxLayoutContainer.HBoxLayoutAlign.TOP);
    this.container.setPack(BoxLayoutContainer.BoxLayoutPack.CENTER);
    this.container.getElement()
                  .getStyle()
                  .setBackgroundColor("white");

    this.innerContainer = new VerticalLayoutContainer();
    this.innerContainer.setWidth(Constants.CONTENT_WIDTH);
    this.container.add(this.innerContainer);

    this.setUpContainerGwtVersion();
    this.setUpContainerProjectMetaData();
    this.setUpContainerApplicationMetaData();
    this.setUpContainerHistoryMetaData();


//    this.contentPanel = this.createContentPanel("Project Data");
//    this.innerContainer.add(this.contentPanel,
//                            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                                           1,
//                                                                           new Margins(6,
//                                                                                       12,
//                                                                                       6,
//                                                                                       12)));


//    this.cpInnerContainer = new VerticalLayoutContainer();
//    this.contentPanel.setWidget(this.cpInnerContainer);
//
//
  }

  private void setUpContainerGwtVersion() {
    FieldSet fs = new FieldSet();
    fs.setHeading("GWT version");
    this.innerContainer.add(fs,
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           -1,
                                                                           new Margins(0,
                                                                                       Constants.MARGIN_CONTAINER,
                                                                                       0,
                                                                                       Constants.MARGIN_CONTAINER)));

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    fs.add(vlc);

    this.gwtVersion = new StringComboBox();
    this.gwtVersion.add(new ArrayList<String>() {{
      add(DataConstants.GWT_VERSION_2_8_2);
    }});
    this.gwtVersion.setValue(DataConstants.DEFAULT_GWT_VERSION);
//    FieldLabel flHistory = new FieldLabel(gwtVersion,
//                                          "Select the GWT version");
//    flHistory.setLabelAlign(FormPanel.LabelAlign.TOP);
    vlc.add(this.gwtVersion,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       Constants.MARGIN_FORM_ROW,
                                                                       0,
                                                                       Constants.MARGIN_FORM_ROW)));
  }

  private void setUpContainerProjectMetaData() {
    FieldSet fs = new FieldSet();
    fs.setHeading("Project Metadata");
    this.innerContainer.add(fs,
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           -1,
                                                                           new Margins(0,
                                                                                       Constants.MARGIN_CONTAINER,
                                                                                       0,
                                                                                       Constants.MARGIN_CONTAINER)));

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    fs.add(vlc);

    this.groupId = new TextField();
    FieldLabel flGroupId = new FieldLabel(groupId,
                                          "GroupId");
    flGroupId.setLabelAlign(FormPanel.LabelAlign.TOP);
    vlc.add(flGroupId,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       Constants.MARGIN_FORM_ROW,
                                                                       0,
                                                                       Constants.MARGIN_FORM_ROW)));

    this.artifactId = new TextField();
    FieldLabel flArtifactId = new FieldLabel(artifactId,
                                             "ArtifactId");
    flArtifactId.setLabelAlign(FormPanel.LabelAlign.TOP);
    vlc.add(flArtifactId,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       Constants.MARGIN_FORM_ROW,
                                                                       0,
                                                                       Constants.MARGIN_FORM_ROW)));
  }

  private void setUpContainerApplicationMetaData() {
    FieldSet fs = new FieldSet();
    fs.setHeading("Application Metadata");
    this.innerContainer.add(fs,
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           -1,
                                                                           new Margins(0,
                                                                                       Constants.MARGIN_CONTAINER,
                                                                                       0,
                                                                                       Constants.MARGIN_CONTAINER)));

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    fs.add(vlc);

    this.applicationLoader = new CheckBox();
    this.applicationLoader.setBoxLabel("Generate Apllication Loader class");
    vlc.add(applicationLoader,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       Constants.MARGIN_CONTAINER,
                                                                       0,
                                                                       Constants.MARGIN_CONTAINER)));
  }

  private void setUpContainerHistoryMetaData() {
    FieldSet fs = new FieldSet();
    fs.setHeading("History Metadata");
    this.innerContainer.add(fs,
                            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                           -1,
                                                                           new Margins(0,
                                                                                       Constants.MARGIN_CONTAINER,
                                                                                       0,
                                                                                       Constants.MARGIN_CONTAINER)));

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    fs.add(vlc);

    this.historyOnStart = new CheckBox();
    this.historyOnStart.setBoxLabel("Check, if the application should handle history on start");
    vlc.add(historyOnStart,
            new VerticalLayoutContainer.VerticalLayoutData(1,
                                                           -1,
                                                           new Margins(0,
                                                                       Constants.MARGIN_CONTAINER,
                                                                       0,
                                                                       Constants.MARGIN_CONTAINER)));

    //    this.history = new StringComboBox();
//    this.history.add(new ArrayList<String>() {{
//      add("no history support");
//      add("simple history support");
//      add("default history support");
//    }});
//    FieldLabel flHistory = new FieldLabel(history,
//                                          "Generate History Classes");
//    flHistory.setLabelAlign(FormPanel.LabelAlign.TOP);
//    vlc.add(flHistory,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));
//
//    this.usingHistoryNames = new CheckBox();
//    this.usingHistoryNames.setBoxLabel("Use Hisory Names");
//    vlc.add(usingHistoryNames,
//            new VerticalLayoutContainer.VerticalLayoutData(1,
//                                                           -1));

  }

  private ContentPanel createContentPanel(String heading) {
    ContentPanel cp = new ContentPanel();
    cp.setHeading(heading);
    cp.setBodyStyle("padding: 12px;");
    cp.setBodyBorder(true);
    return cp;
  }

  @Override
  public void flush(Mvp4g2GeneraterParms model) {
    model.setGwtVersion(gwtVersion.getValue());

    model.setGroupId(this.groupId.getValue());
    model.setArtefactId(this.artifactId.getValue());

    model.setApplicationLoader(this.applicationLoader.getValue());

    model.setHistoryOnStart(this.historyOnStart.getValue());
//    model.setHistory(this.history.getValue());
//    model.setUsingHistorynames(this.usingHistoryNames.getValue());
  }

  @Override
  public boolean isValid() {
    // TODO implement!
    return true;
  }
}
