package de.gishmo.gwt.gwtbootstartermvp4g2.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

public interface ProjectServiceAsync {
  void generate(Mvp4g2GeneraterParms model,
                AsyncCallback<String> async);
}
