package de.gishmo.gwt.gwtbootstartermvp4g2.client.handler;

import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.service.ProjectService;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.service.ProjectServiceAsync;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.mvp4g2.core.ui.AbstractHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.EventHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.Handler;

@Handler
public class GenerateHandler
  extends AbstractHandler<GwtBootStarterMvp4g2EventBus> {

  private ProjectServiceAsync projectService;

  public GenerateHandler() {
    super();

    this.personService = GWT.create(PersonService.class);
    String pageBaseUrl = GWT.getHostPageBaseURL();
    ((RestServiceProxy) this.personService).setResource(new Resource(pageBaseUrl + "/services/person"));



    this.projectService = GWT.create(ProjectService.class);
  }

  @EventHandler
  public void onGenerate(Mvp4g2GeneraterParms model) {



    this.projectService.generate(model,
                                 new AsyncCallback<String>() {
                                   @Override
                                   public void onFailure(Throwable throwable) {
                                     eventBus.hideProgressBar();
                                     Window.alert("PANIC!!!!!!!!!!!!");
                                   }

                                   @Override
                                   public void onSuccess(String result) {
                                     GWT.debugger();
                                     String url = GWT.getModuleBaseURL() + "loadZip/" + result;
                                     Window.open(url,
                                                 "download window",
                                                 "");
                                     eventBus.hideProgressBar();
                                   }
                                 });


  }
}
