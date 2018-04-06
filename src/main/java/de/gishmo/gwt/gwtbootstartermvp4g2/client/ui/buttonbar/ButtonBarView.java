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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.buttonbar;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.github.mvp4g.mvp4g2.core.ui.LazyReverseView;

public class ButtonBarView
  extends LazyReverseView<IButonBarView.Presenter>
  implements IButonBarView {

  private CenterLayoutContainer container;
  private TextButton            generateButton;

  public ButtonBarView() {
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
                  .setBackgroundColor("#5D5D5D");

    this.generateButton = new TextButton("Generate");
    this.generateButton.setScale(ButtonCell.ButtonScale.LARGE);

    this.container.setWidget(this.generateButton);
  }

  @Override
  public void bind() {
    this.generateButton.addSelectHandler((e) -> getPresenter().doGenerate());
  }
}
