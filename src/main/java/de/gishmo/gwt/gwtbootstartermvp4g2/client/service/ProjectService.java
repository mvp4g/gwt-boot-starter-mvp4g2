package de.gishmo.gwt.gwtbootstartermvp4g2.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.GeneratorException;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.Mvp4g2GeneraterParms;

@RemoteServiceRelativePath("projectService")
public interface ProjectService
  extends RemoteService {

  String generate(Mvp4g2GeneraterParms model)
    throws GeneratorException;

}
