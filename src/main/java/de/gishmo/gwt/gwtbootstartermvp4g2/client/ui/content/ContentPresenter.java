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

import java.util.stream.IntStream;

import com.github.mvp4g.mvp4g2.core.ui.AbstractPresenter;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.ui.content.editor.PresenterEditor;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.DataConstants;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.WidgetLibrary;

@Presenter(viewClass = ContentView.class, viewInterface = IContentView.class)
public class ContentPresenter
  extends AbstractPresenter<GwtBootStarterMvp4g2EventBus, IContentView>
  implements IContentView.Presenter {

  private Mvp4g2GeneraterParms mvp4g2GeneraterParms;

  private PresenterEditor presenterEditor;

  public ContentPresenter() {
    super();
    this.createEditorPresenter();
  }

  private void createEditorPresenter() {
    presenterEditor = new PresenterEditor((model, isNew) -> {
      if (isNew) {
        mvp4g2GeneraterParms.getPresenters()
                            .add(model);
      } else {
        IntStream.range(0,
                        mvp4g2GeneraterParms.getPresenters()
                                            .size())
                 .filter(i -> mvp4g2GeneraterParms.getPresenters()
                                                  .get(i)
                                                  .getId()
                                                  .equals(model.getId()))
                 .findFirst()
                 .ifPresent(i -> mvp4g2GeneraterParms.getPresenters()
                                                     .set(i,
                                                          model));
      }
      view.updateGrid(this.mvp4g2GeneraterParms);
    });
  }

  public void bind() {
    this.mvp4g2GeneraterParms = new Mvp4g2GeneraterParms();

    this.mvp4g2GeneraterParms.setGroupId("com.example");
    this.mvp4g2GeneraterParms.setArtefactId("MyTestProject");
    this.mvp4g2GeneraterParms.setApplicationLoader(true);
    this.mvp4g2GeneraterParms.setDebug(true);
    this.mvp4g2GeneraterParms.setHistory(true);
    this.mvp4g2GeneraterParms.setHistoryOnStart(true);
    this.mvp4g2GeneraterParms.setGwtVersion(DataConstants.GWT_VERSION_2_8_2);
    this.mvp4g2GeneraterParms.setWidgetLibrary(WidgetLibrary.GWT);

    this.mvp4g2GeneraterParms.getPresenters()
                             .add(new PresenterData("search",
                                                    "R2D2",
                                                    false,
                                                    true,
                                                    ViewCreationMethod.VIEW_CREATION_METHOD_PRESENTER,
                                                    false,
                                                    true,
                                                    true));
    this.mvp4g2GeneraterParms.getPresenters()
                             .add(new PresenterData("list",
                                                    "C3P0",
                                                    false,
                                                    false,
                                                    ViewCreationMethod.VIEW_CREATION_METHOD_FRAMEWORK,
                                                    false,
                                                    true,
                                                    true));
    this.mvp4g2GeneraterParms.getPresenters()
                             .add(new PresenterData("detail",
                                                    "BB8",
                                                    false,
                                                    false,
                                                    ViewCreationMethod.VIEW_CREATION_METHOD_FRAMEWORK,
                                                    true,
                                                    true,
                                                    true));
    view.edit(this.mvp4g2GeneraterParms);
    eventBus.setCenter(view.asWidget());
  }

  @EventHandler
  public void onGenerateProject() {
    eventBus.showProgressBar();
    if (view.isValid()) {
      view.flush(this.mvp4g2GeneraterParms);
      eventBus.generate(this.mvp4g2GeneraterParms);
    } else {
      // TODO error message
    }
  }

  @Override
  public void doAdd() {
    this.presenterEditor.add(this.mvp4g2GeneraterParms.getPresenters());
  }

  @Override
  public void doEdit(PresenterData model) {
    this.presenterEditor.edit(this.mvp4g2GeneraterParms.getPresenters(),
                              model);
  }
}
