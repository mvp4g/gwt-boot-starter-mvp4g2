package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PresenterData {

  private String id;

  private String name;
  private String historyName;

  private boolean shell;
  private boolean confirmation;
  private boolean showPresenterAtStart;

  private boolean deletable;
  private boolean editable;

  private List<Parameter> parameters;

  public PresenterData() {
    this(GUID.get(),
         "",
         "",
         false,
         false,
         false,
         true,
         true);
  }

  private PresenterData(String id,
                        String name,
                        String historyName,
                        boolean shell,
                        boolean showPresenterAtStart,
                        boolean confirmation,
                        boolean deletable,
                        boolean editable) {
    this.id = id;
    this.name = name;
    this.historyName = historyName;
    this.shell = shell;
    this.showPresenterAtStart = showPresenterAtStart;
    this.confirmation = confirmation;
    this.deletable = deletable;
    this.editable = editable;

    this.parameters = new ArrayList<>();
  }

  public PresenterData(String name,
                       String historyName,
                       boolean shell,
                       boolean showPresenterAtStart,
                       boolean confirmation,
                       boolean deletable,
                       boolean editable) {
    this(GUID.get(),
         name,
         historyName,
         shell,
         showPresenterAtStart,
         confirmation,
         deletable,
         editable);
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(),
                        getName(),
                        getHistoryName(),
                        isShell(),
                        isConfirmation(),
                        isShowPresenterAtStart(),
                        isDeletable(),
                        isEditable(),
                        getParameters());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PresenterData)) {
      return false;
    }
    PresenterData that = (PresenterData) o;
    return isShell() == that.isShell() && isConfirmation() == that.isConfirmation() && isShowPresenterAtStart() == that.isShowPresenterAtStart() && isDeletable() == that.isDeletable() && isEditable() == that.isEditable() && Objects.equals(getId(),
                                                                                                                                                                                                                                               that.getId()) && Objects.equals(getName(),
                                                                                                                                                                                                                                                                               that.getName()) && Objects.equals(getHistoryName(),
                                                                                                                                                                                                                                                                                                                 that.getHistoryName()) && Objects.equals(getParameters(),
                                                                                                                                                                                                                                                                                                                                                          that.getParameters());
  }

  public PresenterData clone() {
    return new PresenterData(id,
                             name,
                             historyName,
                             false,
                             true,
                             this.confirmation,
                             true,
                             true);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getHistoryName() {
    return historyName;
  }

  public boolean isShell() {
    return shell;
  }

  public void setShell(boolean shell) {
    this.shell = shell;
  }

  public boolean isConfirmation() {
    return confirmation;
  }

  public void setConfirmation(boolean confirmation) {
    this.confirmation = confirmation;
  }

  public boolean isShowPresenterAtStart() {
    return showPresenterAtStart;
  }

  public void setShowPresenterAtStart(boolean showPresenterAtStart) {
    this.showPresenterAtStart = showPresenterAtStart;
  }

  public boolean isDeletable() {
    return deletable;
  }

  public void setDeletable(boolean deletable) {
    this.deletable = deletable;
  }

  public boolean isEditable() {
    return editable;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public void setParameters(List<Parameter> parameters) {
    this.parameters = parameters;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public void setHistoryName(String historyName) {
    this.historyName = historyName;
  }

  public PresenterData copy() {
    return new PresenterData(name + " copy",
                             (this.historyName != null && this.historyName.length() > 0) ? this.historyName + " copy" : this.historyName,
                             false,
                             true,
                             this.confirmation,
                             true,
                             true);
  }
}
