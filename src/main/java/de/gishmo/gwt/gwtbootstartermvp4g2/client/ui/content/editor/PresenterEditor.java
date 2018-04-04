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

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;

public class PresenterEditor
  implements IPresenterEditorView.Presenter {

  private PresenterData           presenterData;
  private IPresenterEditorView    view;
  private PresenterEditorDelegate delegate;

  private boolean isNew;

  public PresenterEditor(PresenterEditorDelegate delegate) {
    super();

    this.view = new PresenterEditorView();
    this.view.setPresenter(this);

    this.delegate = delegate;
  }

  public void add() {
    this.presenterData = new PresenterData();
    this.isNew = true;
    this.view.edit(this.presenterData,
                   isNew);
    this.view.show();
  }

  public void edit(PresenterData presenterData) {
    this.presenterData = presenterData;
    this.isNew = false;
    this.view.edit(this.presenterData,
                   isNew);
    this.view.show();
  }

  public void copy(PresenterData presenterData) {
    this.presenterData = presenterData.copy();
    this.isNew = true;
    this.view.edit(this.presenterData,
                   isNew);
    this.view.show();
  }

  public interface PresenterEditorDelegate {

    void save(PresenterData model);

  }
}
