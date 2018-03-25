package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

import java.util.ArrayList;
import java.util.List;

public class Mvp4g2GeneraterParms {

  private String groupId;
  private String artefactId;

  private String gwtVersion;

  private boolean applicationLoader;

  private boolean historyOnStart;
  private boolean history;
  private boolean debug;

  private List<PresenterData> presenters;

  public Mvp4g2GeneraterParms() {
    this.presenters = new ArrayList<>();
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

  public boolean isHistory() {
    return history;
  }

  public void setHistory(boolean history) {
    this.history = history;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public List<PresenterData> getPresenters() {
    return presenters;
  }

  public void setPresenters(List<PresenterData> presenters) {
    this.presenters = presenters;
  }
}
