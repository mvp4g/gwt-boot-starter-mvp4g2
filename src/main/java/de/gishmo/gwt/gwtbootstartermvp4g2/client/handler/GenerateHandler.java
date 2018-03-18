package de.gishmo.gwt.gwtbootstartermvp4g2.client.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.gishmo.gwt.gwtbootstartermvp4g2.client.GwtBootStarterMvp4g2EventBus;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;
import de.gishmo.gwt.mvp4g2.core.ui.AbstractHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.EventHandler;
import de.gishmo.gwt.mvp4g2.core.ui.annotation.Handler;

import elemental.js.json.JsJsonObject;
import elemental.js.util.Json;
import elemental.json.JsonObject;

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
    try {
      Request request = requestBuilder.sendRequest(toJson(model),
                                                   new RequestCallback() {
                                                     @Override
                                                     public void onResponseReceived(Request request,
                                                                                    Response response) {

                                                     }

                                                     @Override
                                                     public void onError(Request request,
                                                                         Throwable exception) {

                                                     }
                                                   });
    } catch (RequestException e) {
      e.printStackTrace();
    }

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

  public String toJson(Mvp4g2GeneraterParms model) {
    JsonObject jsonObject = JsJsonObject.create();
    jsonObject.put(Mvp4g2GeneraterParms.GWT_VERSION,
                   model.getGwtVersion());
    jsonObject.put(Mvp4g2GeneraterParms.GROUP_ID,
                   model.getGroupId());
    jsonObject.put(Mvp4g2GeneraterParms.ARTIFACT_ID,
                   model.getArtefactId());
//    jsonObject.put(InterAppMessage.KEY_TARGET,
//                   this.target);
//    jsonObject.put(InterAppMessage.KEY_EVENT_TYPE,
//                   this.eventType);
//    jsonObject.put(InterAppMessage.KEY_NUMBER_OF_PAREMETERS,
//                   Integer.toString(parameters.size()));
//    for (int i = 0; i < parameters.size(); i++) {
//      jsonObject.put(InterAppMessage.KEY_PARAMETER + Integer.toString(i),
//                     parameters.get(i));
//    }
    return jsonObject.toJson();
  }
}
