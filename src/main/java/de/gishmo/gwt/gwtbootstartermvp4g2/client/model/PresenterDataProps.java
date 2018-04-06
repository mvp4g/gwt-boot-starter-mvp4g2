package de.gishmo.gwt.gwtbootstartermvp4g2.client.model;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.PresenterData;
import de.gishmo.gwt.gwtbootstartermvp4g2.shared.model.ViewCreationMethod;

public interface PresenterDataProps
  extends PropertyAccess<PresenterData> {

  @Editor.Path("id")
  ModelKeyProvider<PresenterData> key();

  @Editor.Path("name")
  ValueProvider<PresenterData, String> name();

  @Editor.Path("historyName")
  ValueProvider<PresenterData, String> historyName();

  @Editor.Path("confirmation")
  ValueProvider<PresenterData, Boolean> confirmation();

  @Editor.Path("viewCreationMethod")
  ValueProvider<PresenterData, ViewCreationMethod> viewCreationMethod();

  @Editor.Path("shell")
  ValueProvider<PresenterData, Boolean> shell();

}
