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

import java.util.ArrayList;
import java.util.List;

import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;

public class PresenterEditor
  implements IPresenterEditorView.Presenter {

  private List<PresenterData> presenterDataList;

  private PresenterData           presenterData;
  private IPresenterEditorView    view;
  private PresenterEditorDelegate delegate;

  private boolean isNew;

  public PresenterEditor(PresenterEditorDelegate delegate) {
    super();

    this.view = new PresenterEditorView();
    this.view.setPresenter(this);

    this.delegate = delegate;

    this.presenterDataList = new ArrayList<>();
  }

  public void add(List<PresenterData> presenterDataList) {
    this.presenterDataList = presenterDataList;
    this.presenterData = new PresenterData();
    this.isNew = true;
    this.view.clearView();
    this.view.edit(this.presenterData,
                   isNew);
    this.view.show();
  }

  public void edit(List<PresenterData> presenterDataList,
                   PresenterData presenterData) {
    this.presenterDataList = presenterDataList;
    this.presenterData = presenterData;
    this.isNew = false;
    this.view.clearView();
    this.view.edit(this.presenterData,
                   isNew);
    this.view.show();
  }

  public void copy(List<PresenterData> presenterDataList,
                   PresenterData presenterData) {
    this.presenterDataList = presenterDataList;
    this.presenterData = presenterData.copy();
    this.isNew = true;
    this.view.clearView();
    this.view.edit(this.presenterData,
                   isNew);
    this.view.show();
  }

  @Override
  public boolean doIsHistoryNameAlreadyUsed(PresenterData model) {
    if (model.getHistoryName() == null || model.getHistoryName()
                                               .trim()
                                               .length() == 0) {
      return true;
    }
    for (PresenterData data : presenterDataList) {
      if (isNew) {
        if (model.getHistoryName()
                 .equals(data.getHistoryName())) {
          return false;
        }
      } else {
        if (model.getHistoryName()
                 .equals(data.getHistoryName())) {
          if (!model.getId()
                    .equals(data.getId())) {
            return false;
          }
        }
      }
    }
    return false;
  }

  @Override
  public void doSave(PresenterData model) {
    this.delegate.save(model,
                       this.isNew);
  }

  public interface PresenterEditorDelegate {

    void save(PresenterData model,
              boolean isNew);

  }
}
