package de.gishmo.gwt.gwtbootstartermvp4g2.client.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.mvp4g2.core.ui.AbstractHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.EventHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.Handler;

@Handler
public class GenerateHandler
  extends AbstractHandler<GwtBootStarterMvp4g2EventBus> {

  public GenerateHandler() {
    super();
  }

  @EventHandler
  public void onGenerate(Mvp4g2GeneraterParms model) {
    String url = GWT.getModuleBaseURL() + "GenerateProject";
    RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST,
                                                       url);


    Request request =


      this.projectService.generate(model,
                                   new AsyncCallback<String>() {
                                     @Override
                                     public void onFailure(Throwable throwable) {
                                       eventBus.hideProgressBar();
                                       Window.alert("PANIC!!!!!!!!!!!!");
                                     }

                                     @Override
                                     public void onSuccess(String s) {
                                       eventBus.hideProgressBar();
                                       GWT.debugger();
                                       // TODO download file ...

                                     }
                                   });


  }
}
