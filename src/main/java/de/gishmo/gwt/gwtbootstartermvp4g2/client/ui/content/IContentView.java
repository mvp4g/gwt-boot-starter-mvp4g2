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

import com.google.gwt.user.client.ui.IsWidget;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;

import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;

public interface IContentView
  extends IsLazyReverseView<IContentView.Presenter>,
          IsWidget {

  void edit(Mvp4g2GeneraterParms model);

  void flush(Mvp4g2GeneraterParms model);

  boolean isValid();

  void updateGrid(Mvp4g2GeneraterParms mvp4g2GeneraterParms);

  interface Presenter {

    void doAdd();

    void doEdit(PresenterData model);

  }
}
