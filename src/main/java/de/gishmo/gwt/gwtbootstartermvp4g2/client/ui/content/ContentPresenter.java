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

import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

@Presenter(viewClass = ContentView.class, viewInterface = IContentView.class)
public class ContentPresenter
  extends AbstractPresenter<GwtBootStarterMvp4g2EventBus,
                             IContentView>
  implements IContentView.Presenter {

  public ContentPresenter() {
  }

  public void bind() {
    eventBus.setCenter(view.asWidget());
  }

  @EventHandler
  public void onGenerateProject() {
    eventBus.showProgressBar();
    if (view.isValid()) {
      Mvp4g2GeneraterParms model = new Mvp4g2GeneraterParms();
      view.flush(model);
      eventBus.generate(model);
    } else {
      // TODO error message
    }
  }

//  @EventHandler
//  public void onShowContent(String id) {
//    view.showContent(id);
//  }
//
//  @Override
//  public void doRemoveMail(String id) {
//    eventBus.removeEmail(id);
//  }
}
