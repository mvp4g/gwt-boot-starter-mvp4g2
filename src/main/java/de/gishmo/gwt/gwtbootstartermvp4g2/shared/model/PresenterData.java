package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

import java.util.Objects;

public class PresenterData {

  private String id;

  private String name;
  private String historyName;

  private boolean shell;
  private boolean confirmation;

  private boolean deletable;
  private boolean editable;

  public PresenterData() {
  }

  public PresenterData(String name,
                       String historyName,
                       boolean shell,
                       boolean confirmation,
                       boolean deletable,
                       boolean editable) {
    this.id = GUID.get();
    this.name = name;
    this.historyName = historyName;
    this.shell = shell;
    this.confirmation = confirmation;
    this.deletable = deletable;
    this.editable = editable;
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

  public void setHistoryName(String historyName) {
    this.historyName = historyName;
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

  public boolean isDeletable() {
    return deletable;
  }

  public void setDeletable(boolean deletable) {
    this.deletable = deletable;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
    return isShell() == that.isShell() &&
           isConfirmation() == that.isConfirmation() &&
           isDeletable() == that.isDeletable() &&
           isEditable() == that.isEditable() &&
           Objects.equals(getId(),
                          that.getId()) &&
           Objects.equals(getName(),
                          that.getName()) &&
           Objects.equals(getHistoryName(),
                          that.getHistoryName());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getId(),
                        getName(),
                        getHistoryName(),
                        isShell(),
                        isConfirmation(),
                        isDeletable(),
                        isEditable());
  }
}
