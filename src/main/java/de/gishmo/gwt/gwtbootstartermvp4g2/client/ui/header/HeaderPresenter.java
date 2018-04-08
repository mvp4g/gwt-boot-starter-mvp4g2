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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.header;

import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

@Presenter(viewClass = HeaderView.class, viewInterface = IHeaderView.class)
public class HeaderPresenter
  extends AbstractPresenter<GwtBootStarterMvp4g2EventBus,
                             IHeaderView>
  implements IHeaderView.Presenter {

  public HeaderPresenter() {
  }

  public void bind() {
    eventBus.setNorth(view.asWidget());
  }

  @EventHandler
  public void onStartApplication() {
//    ClientContext.get().getMailService().getAllMails(new AsyncCallback<ArrayList<Mail>>() {
//      @Override
//      public void onFailure(Throwable throwable) {
//        Window.alert("panic!");
//      }
//
//      @Override
//      public void onSuccess(ArrayList<Mail> listOfEmails) {
//        view.flush(listOfEmails);
//        eventBus.updateStatus("Found: " + Integer.toString(listOfEmails.size()) + " emails");
//      }
//    });
  }

//  @Override
//  public IHeaderView createView() {
//    return new HeaderView();
//  }
//
//  @Override
//  public void doSelectRow(String id) {
//    eventBus.selectEmail(id);
//  }
}
