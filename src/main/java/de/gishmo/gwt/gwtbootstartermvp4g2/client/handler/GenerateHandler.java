package de.gishmo.gwt.gwtbootstartermvp4g2.client.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.service.ProjectService;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.mvp4g2.core.ui.AbstractHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.EventHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.Handler;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.TextCallback;

@Handler
public class GenerateHandler
  extends AbstractHandler<GwtBootStarterMvp4g2EventBus> {

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
                                     Window.alert("PANIC!!!!!!!!!!!!");
                                   }

                                   @Override
                                   public void onSuccess(Method method,
                                                         String value) {
                                     GWT.debugger();
                                     String url = GWT.getHostPageBaseURL() +
                                                  "loadZip/download?archive=" +
                                                  model.getArtefactId() + ".zip";
                                     Window.open(url,
                                                 "download window",
                                                 "");
                                     eventBus.hideProgressBar();
                                   }
                                 });
  }
}
