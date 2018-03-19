package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Mvp4g2GeneraterParms
  implements IsSerializable {

  private String groupId;
  private String artefactId;

  private String gwtVersion;

  private boolean applicationLoader;

  private boolean historyOnStart;
  private String history;
  private boolean usingHistorynames;

  private String widgets;

  public Mvp4g2GeneraterParms() {
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getArtefactId() {
    return artefactId;
  }

  public void setArtefactId(String artefactId) {
    this.artefactId = artefactId;
  }

  public String getGwtVersion() {
    return gwtVersion;
  }

  public void setGwtVersion(String gwtVersion) {
    this.gwtVersion = gwtVersion;
  }

  public boolean isApplicationLoader() {
    return applicationLoader;
  }

  public void setApplicationLoader(boolean applicationLoader) {
    this.applicationLoader = applicationLoader;
  }

  public boolean isHistoryOnStart() {
    return historyOnStart;
  }

  public void setHistoryOnStart(boolean historyOnStart) {
    this.historyOnStart = historyOnStart;
  }

  public String getHistory() {
    return history;
  }

  public void setHistory(String history) {
    this.history = history;
  }

  public boolean isUsingHistorynames() {
    return usingHistorynames;
  }

  public void setUsingHistorynames(boolean usingHistorynames) {
    this.usingHistorynames = usingHistorynames;
  }

  public String getWidgets() {
    return widgets;
  }

  public void setWidgets(String widgets) {
    this.widgets = widgets;
  }
}
