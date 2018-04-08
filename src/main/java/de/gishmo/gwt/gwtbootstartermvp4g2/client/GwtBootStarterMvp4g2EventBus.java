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

package de.gishmo.gwt.gwtbootstartermvp4g2.client;

import com.google.gwt.user.client.ui.Widget;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.buttonbar.ButtonBarPresenter;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.content.ContentPresenter;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.header.HeaderPresenter;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.shell.ShellPresenter;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Debug;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Event;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.EventBus;
import com.github.mvp4g.mvp4g2.core.eventbus.annotation.Start;

@EventBus(shell = ShellPresenter.class)
@Debug(logLevel = Debug.LogLevel.DETAILED)
public interface GwtBootStarterMvp4g2EventBus
  extends IsEventBus {

  /**
   * sets the center widget in the shell
   *
   * @param widget the new center widget
   */
  @Event
  void setCenter(Widget widget);

  /**
   * sets the north widget in the shell
   *
   * @param widget the new north widget
   */
  @Event
  void setNorth(Widget widget);

  /**
   * sets the south widget in the shell
   *
   * @param widget the new south widget
   */
  @Event
  void setSouth(Widget widget);

  /**
   * this event is fired at application start. Because it is marked with @Start
   * the vent will be fired by the framework.
   * <p>
   * we use this event to initialize the application layout
   * by using the bind-feature and load the list of mails
   */
  @Start
  @Event(bind = {HeaderPresenter.class,
                 ButtonBarPresenter.class,
                 ContentPresenter.class})
  void startApplication();

  /**
   * generate the project data
   */
  @Event
  void generateProject();

  /**
   * hide Progress bar
   */
  @Event
  void hideProgressBar();

  /**
   * show Progress bar
   */
  @Event
  void showProgressBar();

  /**
   * generate the project files (call server)
   * @param parms parameter for the generation
   */
  @Event
  void generate(Mvp4g2GeneraterParms parms);
}
