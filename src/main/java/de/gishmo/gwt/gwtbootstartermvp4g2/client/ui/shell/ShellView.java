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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.shell;

import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.Constants;

public class ShellView
  extends LazyReverseView<IShellView.Presenter>
  implements IShellView {

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

  private Viewport                viewport;
  private VerticalLayoutContainer shell;
  private BorderLayoutContainer   container;
  private ContentPanel            northContainer;
  private ContentPanel            southContainer;
  private SimpleContainer         centerContainer;

  private AutoProgressMessageBox progressBar;

  public ShellView() {
    super();
  }

  @Override
  public Widget asWidget() {
    return this.viewport;
  }

  @Override
  public void hideProgressBar() {
    if (this.progressBar != null) {
      this.progressBar.hide();
    }
  }

  @Override
  public void setCenter(Widget widget) {
    if (this.centerContainer.getWidget() != null) {
      this.centerContainer.getWidget()
                          .removeFromParent();
    }
    this.centerContainer.setWidget(widget);
  }

  @Override
  public void setNorth(Widget widget) {
    if (this.northContainer.getWidget() != null) {
      this.northContainer.getWidget()
                         .removeFromParent();
    }
    this.northContainer.setWidget(widget);
  }

  @Override
  public void setSouth(Widget widget) {
    if (this.southContainer.getWidget() != null) {
      this.southContainer.getWidget()
                         .removeFromParent();
    }
    this.southContainer.setWidget(widget);
  }

  @Override
  public void showProgressBar() {
    if (this.progressBar == null) {
      this.progressBar = new AutoProgressMessageBox("Generate Project Files");
      this.progressBar.setProgressText("generating ... ");
    }
    this.progressBar.show();
  }

  @Override
  public void createView() {
    this.viewport = new Viewport();

    this.shell = new VerticalLayoutContainer();
    this.viewport.add(this.shell);

    this.container = new BorderLayoutContainer();
    this.shell.add(this.container,
                   new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                  1));

    this.shell.add(this.createBaseLine(),
                   new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                  64));

    this.northContainer = createContentPanel();
    BorderLayoutContainer.BorderLayoutData bldNorth = new BorderLayoutContainer.BorderLayoutData(128);
    bldNorth.setCollapsible(true);
    bldNorth.setSplit(true);
    this.container.setNorthWidget(this.northContainer,
                                  bldNorth);

    this.southContainer = createContentPanel();
    BorderLayoutContainer.BorderLayoutData bldSouth = new BorderLayoutContainer.BorderLayoutData(64);
    this.container.setSouthWidget(this.southContainer,
                                  bldSouth);

    this.centerContainer = new SimpleContainer();
    this.container.setCenterWidget(this.centerContainer);
  }

  private Widget createBaseLine() {
    CenterLayoutContainer clc = new CenterLayoutContainer();
    clc.getElement()
       .getStyle()
       .setBackgroundColor("#283949");

    HorizontalLayoutContainer innerContainer = new HorizontalLayoutContainer();
    innerContainer.setWidth(Constants.CONTENT_WIDTH);
    clc.add(innerContainer);

    this.createLink(innerContainer,
                    "Mvp4g2@Github",
                    "https://github.com/mvp4g/mvp4g2");
    this.createLink(innerContainer,
                    "Mvp4g2 Documentation",
                    "https://github.com/mvp4g/mvp4g2/wiki");

    this.createLink(innerContainer,
                    "Generator Documentation",
                    "https://github.com/mvp4g/gwt-boot-starter-mvp4g2");

    this.createLink(innerContainer,
                    "Generator Issues",
                    "https://github.com/mvp4g/gwt-boot-starter-mvp4g2/issues");

    return clc;
  }

  private ContentPanel createContentPanel() {
    ContentPanel cp = new ContentPanel();
    cp.setHeaderVisible(false);
    return cp;
  }

  private void createLink(HorizontalLayoutContainer container,
                          String link,
                          String url) {

    Anchor anchor = new Anchor(link);
    anchor.setHref(url);
    anchor.setTarget("_blank");
    anchor.getElement()
          .getStyle()
          .setFontSize(14,
                       Style.Unit.PX);
    anchor.getElement()
          .getStyle()
          .setColor("white");
    anchor.getElement()
          .getStyle()
          .setWhiteSpace(Style.WhiteSpace.NOWRAP);
    anchor.getElement()
          .getStyle()
          .setProperty("fontFamily",
                       themeDetails.panel()
                                   .font()
                                   .family());
    container.add(anchor,
                  new HorizontalLayoutContainer.HorizontalLayoutData(-1,
                                                                     1,
                                                                     new Margins(-6,
                                                                                 12,
                                                                                 0,
                                                                                 12)));
  }
}
