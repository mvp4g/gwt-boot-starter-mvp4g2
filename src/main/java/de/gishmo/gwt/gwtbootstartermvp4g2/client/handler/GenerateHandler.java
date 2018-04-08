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

package de.gishmo.gwt.gwtbootstartermvp4g2.client.handler;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.TextCallback;

import com.github.mvp4g.mvp4g2.core.ui.AbstractHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.EventHandler;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Handler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.themebuilder.base.client.config.ThemeDetails;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;

import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.service.ProjectService;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

@Handler
public class GenerateHandler
  extends AbstractHandler<GwtBootStarterMvp4g2EventBus> {

  private static ThemeDetails themeDetails = GWT.create(ThemeDetails.class);

  private ProjectService projectService;

  public GenerateHandler() {
    super();

    this.projectService = GWT.create(ProjectService.class);
    String pageBaseUrl = GWT.getHostPageBaseURL();
    ((RestServiceProxy) this.projectService).setResource(new Resource(pageBaseUrl + "/service/project"));
  }

  @EventHandler
  public void onGenerate(Mvp4g2GeneraterParms model) {
    GWT.debugger();
    this.projectService.generate(model,
                                 new TextCallback() {
                                   @Override
                                   public void onFailure(Method method,
                                                         Throwable throwable) {
                                     eventBus.hideProgressBar();
                                     Window.alert("PANIC: =>" + throwable.getMessage() + "\n" + throwable.getStackTrace());
                                   }

                                   @Override
                                   public void onSuccess(Method method,
                                                         String value) {
                                     GWT.debugger();
                                     String url = GWT.getHostPageBaseURL() + "loadZip/download?archive=" + model.getArtefactId() + ".zip";

                                     DownloadTemplate    template  = GWT.create(DownloadTemplate.class);
                                     HtmlLayoutContainer container = new HtmlLayoutContainer(template.getTemplate(url));
                                     container.getElement()
                                              .getStyle()
                                              .setProperty("fontSize",
                                                           themeDetails.field()
                                                                       .text()
                                                                       .size());
                                     container.getElement()
                                              .getStyle()
                                              .setProperty("fontFamily",
                                                           themeDetails.field()
                                                                       .text()
                                                                       .family());
                                     container.getElement()
                                              .getStyle()
                                              .setProperty("color",
                                                           themeDetails.field()
                                                                       .text()
                                                                       .color());

                                     Dialog dialog = new Dialog();
                                     dialog.setHeading("Download your project ...");
                                     dialog.setPixelSize(-1,
                                                         -1);
                                     dialog.setMinWidth(0);
                                     dialog.setMinHeight(0);
                                     dialog.setResizable(false);
                                     dialog.setShadow(true);
                                     dialog.setWidget(container);
                                     dialog.setPredefinedButtons(Dialog.PredefinedButton.CLOSE);
                                     dialog.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
                                     dialog.setBodyStyle("padding 12px;");

                                     //                                     Window.open(url,
                                     //                                                 "download window",
                                     //                                                 "");
                                     eventBus.hideProgressBar();

                                     dialog.show();
                                   }
                                 });
  }

  private String generateStacktrace(Throwable throwable) {
    String value = "";
    for (StackTraceElement e : throwable.getStackTrace()) {
      value += e.getClassName() + ":" + e.getMethodName() + "(" + e.getLineNumber() + ")\n";
    }
    return value;
  }

  public interface DownloadTemplate
    extends XTemplates {
    @XTemplate(source = "Download.html")
    SafeHtml getTemplate(String url);
  }
}
