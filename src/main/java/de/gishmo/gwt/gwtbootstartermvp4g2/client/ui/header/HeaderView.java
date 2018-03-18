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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.Constants;
import de.gishmo.gwt.mvp4g2.core.ui.LazyReverseView;

public class HeaderView
  extends LazyReverseView<IHeaderView.Presenter>
  implements IHeaderView {

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

  private CenterLayoutContainer     container;
  private HorizontalLayoutContainer innerContainer;

  public HeaderView() {
    super();
  }

  @Override
  public Widget asWidget() {
    return this.container;
  }

  @Override
  public void createView() {
    this.container = new CenterLayoutContainer();
    this.container.getElement()
                       .getStyle()
                       .setBackgroundColor("#34302D");

    this.innerContainer = new HorizontalLayoutContainer();
    this.innerContainer.setWidth(Constants.CONTENT_WIDTH);
    this.container.add(this.innerContainer);

    Label leftLabel = new Label("Mvp4g2 Initializer");
    leftLabel.getElement()
             .getStyle()
             .setFontWeight(Style.FontWeight.BOLD);
    leftLabel.getElement()
             .getStyle()
             .setFontSize(40,
                          Style.Unit.PX);
    leftLabel.getElement()
             .getStyle()
             .setColor("white");
    leftLabel.getElement()
             .getStyle()
             .setProperty("fontFamily",
                          themeDetails.panel()
                                      .font()
                                      .family());

    this.innerContainer.add(leftLabel,
                            new HorizontalLayoutContainer.HorizontalLayoutData(-1,
                                                                               -1,
                                                                               new Margins(0,
                                                                                           0,
                                                                                           0,
                                                                                           12)));

    Label rightLabel = new Label("bootstrap your mvp4g2 application now ... ");
    rightLabel.getElement()
              .getStyle()
              .setFontWeight(Style.FontWeight.BOLD);
    rightLabel.getElement()
              .getStyle()
              .setFontSize(20,
                           Style.Unit.PX);
    rightLabel.getElement()
              .getStyle()
              .setColor("#777777");
    rightLabel.getElement()
              .getStyle()
              .setProperty("fontFamily",
                           themeDetails.panel()
                                       .font()
                                       .family());

    this.innerContainer.add(rightLabel,
                            new HorizontalLayoutContainer.HorizontalLayoutData(1,
                                                                               -1,
                                                                               new Margins(18,
                                                                                           12,
                                                                                           0,
                                                                                           12)));
  }
}
