package de.gishmo.gwt.gwtbootstartermvp4g2.shared.model;

public enum ViewCreationMethod {

  VIEW_CREATION_METHOD_FRAMEWORK("view is created by framework"),
  VIEW_CREATION_METHOD_PRESENTER("view is created by presenter");

  private String text;

  ViewCreationMethod(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
